/**
 *
 */
package lumi.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import lumi.dao.DAO;
import lumi.misc.CredentialDigester;
import lumi.vo.AuthChainDTO;
import lumi.vo.UserRegisterVO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * Twitter認証連携した情報を扱うサービス。
 * Twitterからログインした場合の扱いをハンドリングする。
 * すでに利用したことのあるTwitterIDであれば、ユーザ情報を取得してSpringSecurityで認証をするアカウント情報を取得する。
 * もしシステム未利用であればアカウント情報に仮登録し、その後SpringSecurityへ渡す。
 * @author A-pZ
 *
 */
@Scope("prototype")
@Service
@Slf4j
@Transactional(
		propagation = Propagation.REQUIRED,
		isolation = Isolation.DEFAULT,
		readOnly = false,
		rollbackFor = { RuntimeException.class, Exception.class })
public class TwitterChainService extends LumiService {

	/**
	 * TwitterのOAuth認証情報から、SpringSecurity認証へ連携を行う。
	 * @param twitter Twiiter4j認証情報
	 * @return 認証連携結果。成功したらtrue。
	 * @throws Exception
	 */
	public boolean chainSpringSecurity(Twitter twitter) throws Exception {

		// 登録済みユーザであるかを検索する
		authChain = searchTwitterChain(twitter);

		// もし登録済みユーザでなければ新規登録する
		if (StringUtils.isBlank(authChain.getTwitterId() )) {
			authChain = this.registerNewTwitterChain(twitter);
		} else {
			// 表示名のみ更新する
			this.updateDisplayName(twitter);
		}

		return true;
	}

	/**
	 * SpringSecurityで認証を実施する。
	 * @return 認証結果
	 * @throws Exception
	 */
	public boolean startAuthentication() throws Exception {
		// SpringSecurityへ認証連携するユーザ情報を取得する

		// プリンシパルの生成
		Principal principal = new Principal() {
			public String getName() {
				return authChain.getUsername();
			}
		};

		// 許可ロールの設定
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

		// SpringSecurity認証 - ユーザID/パスワード認証を設定する
		log.debug("- custom spring security authentication start.");
		Authentication authentication =
				new UsernamePasswordAuthenticationToken(
						principal, authChain.getPassword(), authorities);

		// SpringSecurity認証マネージャ経由にて認証を行う。
		log.debug(" - auth...");
		authentication = authManager.authenticate(authentication);

		// 認証結果を取得
		boolean isAuthenticated = authentication.isAuthenticated();
		log.debug(" - result : " + isAuthenticated);

		if (!isAuthenticated) {
			log.warn("+ authentication chain failure. ");
			return false;
		}

		// SpringSecurityコンテキストを生成し、SpringSecurityの認証情報を格納する
		SecurityContextImpl context = new SecurityContextImpl();
		context.setAuthentication(authentication);

		// SpringSecurityコンテキストをコンテキストホルダーへ格納し、認証連携を完了する。
		// この処理にて、SpringSecurityの設定(=HTTP用認証)を完了する。
		SecurityContextHolder.setContext(context);

		return true;
	}

	/**
	 * Twitter認証済み情報から、Twitter認証連携のユーザ情報を取得する。
	 * @param twitter Twitter4j認証情報インスタンス
	 * @return 認証連携DTO
	 * @throws Exception
	 */
	public AuthChainDTO searchTwitterChain(Twitter twitter) throws Exception {

		AuthChainDTO param = new AuthChainDTO();
		param.setTwitterId(Long.toString(twitter.getId()));

		List<AuthChainDTO> result = dao.select(Query.searchTwitterChain.name(), param);

		if (result == null || result.size() != 1) {
			return new AuthChainDTO();
		}

		return result.get(0);
	}


	/**
	 * Twitter認証済み情報から、Twitter認証連携のユーザ情報を新規登録する。
	 * 新規登録されるユーザ情報は、ユーザIDはtwitterのユーザIDと、ハッシュ化したパスワード64文字。
	 * @param twitter Twitter4j認証情報インスタンス
	 * @return 登録成功なら認証連携DTOを返す
	 * @throws Exception
	 */
	public AuthChainDTO registerNewTwitterChain(Twitter twitter) throws Exception {
		// Twitter連携情報の登録
		if (!registerTwitterChain(twitter)) {
			throw new Exception("failure register twitter chain.");
		}

		// ユーザ情報にTwitterユーザ情報を登録する
		if (!registerLoginAccountForTwitterChain(twitter)) {
			throw new Exception("failure register user.");
		}

		// ロールの登録
		if (!registerUserRole(twitter)) {
			throw new Exception("failure register role.");
		}

		// 登録が完了したユーザ認証情報を返す。ユーザ情報を再検索する。
		return searchTwitterChain(twitter);
	}

	/**
	 * ログイン済みユーザがTwitter連携を許可したときに連携する。
	 * @param twitter Twitter4j認証情報インスタンス
	 * @param username 連携するログインユーザ名
	 * @throws Exception
	 */
	public void registerExistUserFromTwitter(Twitter twitter , String username) throws Exception {

		// もし既にTwitterIDが登録済みであればいったん削除する。
		if ( existTwitterChainUser(twitter)) {
			log.info(" - delete exists username(twitterId) {}" , twitter.getId());
			if ( !deleteTwitterChainInfo(twitter)) {
				throw new Exception("failure delete twitter chain.");
			}
		}

		// Twitter連携情報の登録
		if (!registerTwitterChain(twitter,username)) {
			throw new Exception("failure register twitter chain.");
		}

		// ユーザ情報にTwitterユーザ情報を登録する
		if (!registerLoginAccountForTwitterChain(twitter)) {
			throw new Exception("failure register user.");
		}

		// ロールの登録
		if (!registerUserRole(twitter)) {
			throw new Exception("failure register role.");
		}

		if (!updateUsernameToTwitterDisplayName(twitter , username)) {
			throw new Exception("failure chaining exist account.");
		}
	}

	/**
	 * Twitter連携でユーザ登録しているかを検索する。
	 * @param twitter Twitter4j認証情報インスタンス
	 * @return 登録済みであればtrue
	 * @throws Exception
	 */
	boolean existTwitterChainUser(Twitter twitter) throws Exception {

		long id = twitter.getId();

		// TwitterIDで登録されているユーザの件数を取得
		Integer count = (Integer)dao.selectObject(Query.existUser.name(), Long.toString(id));

		return isSingleRecord(count);
	}

	/**
	 * 認証テーブル(userlist,userrole,twitter_chain)から連携したTwitterIDのデータを削除する。
	 * @param twitter Twitter4j認証情報インスタンス
	 * @return 削除完了したらtrue
	 * @throws Exception
	 */
	boolean deleteTwitterChainInfo(Twitter twitter) throws Exception {
		String username = Long.toString(twitter.getId());

		deleteUsername(username);
		deleteUserrole(username);
		deleteTwitterChain(username);

		return true;
	}

	/**
	 * 指定したユーザをユーザテーブルから除去する。
	 * @param username ユーザ名
	 * @return 削除結果。削除件数が0でもtrue。
	 * @throws Exception
	 */
	boolean deleteUsername(String username) throws Exception {
		Integer count = dao.delete(Query.deleteUsername.name(), username);
		return true;
	}

	/**
	 * 指定したユーザをユーザロールテーブルから除去する。
	 * @param username ユーザ名
	 * @return 削除結果。削除件数が0でもtrue。
	 * @throws Exception
	 */
	boolean deleteUserrole(String username) throws Exception {
		Integer count = dao.delete(Query.deleteUserrole.name(), username);
		return true;
	}

	/**
	 * 指定したユーザをツイッター連携テーブルから除去する。
	 * @param username ユーザ名
	 * @return 削除結果。削除件数が0でもtrue。
	 * @throws Exception
	 */
	boolean deleteTwitterChain(String username) throws Exception {
		Integer count = dao.delete(Query.deleteTwitterChain.name(), username);
		return true;
	}

	/**
	 * Twitter認証情報から、表示名を取得して更新する。
	 * @param twitter Twitter4j認証情報インスタンス
	 * @return 更新が成功したらtrue
	 * @throws Exception
	 */
	public boolean updateDisplayName(Twitter twitter) throws Exception {
		User twitUser = twitter.verifyCredentials();

		AuthChainDTO param = new AuthChainDTO(Long.toString(twitter.getId()) );
		param.setDisplayName(twitUser.getName());

		int count = dao.update(Query.updateDisplayName.name(), param);

		if (! isSingleRecord(count)) {
			throw new Exception("failure update screenName");
		}

		return true;
	}

	/**
	 * Twitter認証済み情報と登録済みユーザ情報を関連付けする。
	 * @param twitter Twitter4j認証情報インスタンス
	 * @param username ログイン済みユーザ名
	 * @return 登録成功ならtrue
	 * @throws Exception
	 */
	public boolean chainingTwitterId(Twitter twitter, String username) throws Exception {
		AuthChainDTO param = new AuthChainDTO(Long.toString(twitter.getId()) );
		param.setUsername(username);

		int count = dao.update(Query.chainingTwitterId.name(), param);

		if (! isSingleRecord(count)) {
			throw new Exception("failure chaining TwitterId");
		}

		return true;
	}

	/**
	 * Twitter認証連携の情報を追加する。
	 * @param twitter Twitter4j認証情報インスタンス
	 * @return 登録成功時はtrue
	 * @throws Exception
	 */
	boolean registerTwitterChain(Twitter twitter) throws Exception {
		AuthChainDTO param = new AuthChainDTO();
		param.setTwitterId(Long.toString(twitter.getId()));
		param.setUsername(Long.toString(twitter.getId()));

		int count = dao.insert(Query.registerTwitterChain.name(), param);
		return isSingleRecord(count);
	}

	/**
	 * Twitter認証連携の情報を、ログインユーザIDと関連付けして追加する。
	 * @param twitter Twitter4j認証情報インスタンス
	 * @param username システムログインユーザID
	 * @return 登録成功時はtrue
	 * @throws Exception
	 */
	boolean registerTwitterChain(Twitter twitter , String username) throws Exception {
		AuthChainDTO param = new AuthChainDTO();
		param.setTwitterId(Long.toString(twitter.getId()));
		param.setUsername(username);

		int count = dao.insert(Query.registerTwitterChain.name(), param);
		return isSingleRecord(count);
	}

	/**
	 * 登録済みログイン情報の表示名をTwitterの表示名に切り替える。
	 * @param twitter Twitter4j認証情報インスタンス
	 * @param username システムログインユーザID
	 * @return 登録成功時はtrue
	 * @throws Exception
	 */
	boolean updateUsernameToTwitterDisplayName(Twitter twitter , String username) throws Exception {
		UserRegisterVO userVO = generateUserRegisterVO(twitter , username);

		int count = dao.update(Query.updateUserFromTwitter.name(), userVO);
		return isSingleRecord(count);
	}

	/**
	 * Twitterアカウント情報からユーザ情報を登録する。
	 * @param twitter Twitter4j認証情報インスタンス
	 * @return 登録成功時はtrue
	 * @throws Exception
	 */
	boolean registerLoginAccountForTwitterChain(Twitter twitter) throws Exception {
		UserRegisterVO user = generateUserRegisterVO(twitter);

		return registerLoginAccountForTwitterChain(user,twitter);
	}

	/**
	 * Twitterアカウント情報から登録済みユーザ情報を登録する。
	 * @param twitter Twitter4j認証情報インスタンス
	 * @param username ログインユーザ名
	 * @return 登録成功時はtrue
	 * @throws Exception
	 */
	boolean registerLoginAccountForTwitterChain(Twitter twitter,String username) throws Exception {
		UserRegisterVO user = generateUserRegisterVO(twitter,username);

		return registerLoginAccountForTwitterChain(user,twitter);
	}

	/**
	 * ユーザテーブルにユーザ情報を登録する。
	 * @param user UserRegisterVO
	 * @param twitter Twitter4j認証情報インスタンス
	 * @return 登録成功時はtrue
	 * @throws Exception
	 */
	boolean registerLoginAccountForTwitterChain(UserRegisterVO user , Twitter twitter) throws Exception {
		String digestTarget = twitter.getScreenName().concat(Long.toString(System.currentTimeMillis()));
		user.setPassword(CredentialDigester.generate(digestTarget));
		int registerCount = dao.insert(Query.registerUser.name(), user);
		return isSingleRecord(registerCount);
	}

	/**
	 * Twitterアカウントのロールを登録する。
	 * @param Twitter4j認証情報インスタンス
	 * @return 登録成功時はtrue
	 * @throws Exception
	 */
	boolean registerUserRole(Twitter twitter) throws Exception {
		UserRegisterVO user = generateUserRegisterVO(twitter);

		int roleCount = dao.insert(Query.registerUserRole.name(), user);
		return isSingleRecord(roleCount);
	}

	/**
	 * 登録ユーザのVOを生成する。
	 * @param twitter Twitter認証情報
	 * @return ユーザVO。
	 */
	UserRegisterVO generateUserRegisterVO(Twitter twitter) throws TwitterException {
		User twitUser = twitter.verifyCredentials();

		// ユーザ情報の作成
		UserRegisterVO user = new UserRegisterVO();
		user.setActivate(2);
		user.setUsername(Long.toString(twitter.getId()));
		user.setDisplayName(twitUser.getName());
		user.setUserrole("ROLE_ADMIN");

		return user;
	}

	/**
	 * 登録ユーザのデータ更新用VOを生成する。
	 * @param twitter Twitter認証情報
	 * @param username ログインユーザID
	 * @return ユーザVO。
	 */
	UserRegisterVO generateUserRegisterVO(Twitter twitter,String username) throws TwitterException {
		User twitUser = twitter.verifyCredentials();

		// ユーザ情報の作成
		UserRegisterVO user = new UserRegisterVO();
		user.setActivate(1);
		user.setUsername(username);
		user.setDisplayName(twitUser.getName());
		user.setUserrole("ROLE_ADMIN");

		return user;
	}

	/**
	 * 登録・更新件数が1の場合はtrueを返す。
	 * @param count 登録件数
	 * @return 件数が1の場合のみtrue
	 */
	private boolean isSingleRecord(int count) {
		return (count == 1);
	}

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	DAO dao;

	private AuthChainDTO authChain;

	enum Query {
		searchTwitterChain, updateDisplayName , registerTwitterChain, chainingTwitterId,
		searchAlreadyRegisterTwitterId, registerUser, registerUserRole , updateUserFromTwitter,
		existUser , deleteUsername , deleteUserrole , deleteTwitterChain
	}
}
