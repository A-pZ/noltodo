package lumi.action.twitter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lumi.action.LumiActionSupport;
import lumi.misc.SessionKeys;
import lumi.service.AccountService;
import lumi.service.TwitterChainService;
import lumi.vo.AccountVO;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import twitter4j.Twitter;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Twitter認証済みのリクエストからユーザ情報を取得し、本アプリで認証済みとするActionクラス。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/twitter")
@ParentPackage("lumi-default")
@Results({
	@Result(name=ActionSupport.SUCCESS , location="/list" , type="redirect") ,
	@Result(name=ActionSupport.ERROR , location = "/login/login" , type="thymeleaf-spring"),
	@Result(name=AuthenticationChainAction.TWITTER_CHAIN_STRING , location="/account/afterChain" , type="redirect" )
})
@Controller
@Scope("prototype")
@Slf4j
public class AuthenticationChainAction extends LumiActionSupport {

	/**
	 * TwitterOAuthからのコールバックアクション。
	 */
	@Action("callback")
	public String calback() throws Exception {

		// Twitter4jのインスタンスをセッションから取得する。
		Twitter twitter = (Twitter) getSession().get(SessionKeys.TWITTER4J_INSTANCE.name());
		if ( twitter == null ) {
			// もしインスタンスがない場合は警告メッセージ＋ログイン画面
			log.warn("twitter instance is null.");
			addActionWarning(getText("callback.error.twitter4j"));
			return ERROR;
		}

		// セッションからOAuthリクエストトークンを取得
		RequestToken reqToken = (RequestToken)getSession().get(SessionKeys.REQUEST_TOKEN.name());
		if ( reqToken == null ) {
			// もしリクエストトークンが取得できない場合は警告メッセージ＋ログイン画面
			log.warn("request token is null.");
			addActionWarning(getText("callback.error.requestToken"));
			return ERROR;
		}

		// AccessTokenの取得
		AccessToken accessToken = twitter.getOAuthAccessToken(reqToken, oauth_verifier);

		// セッションからリクエストトークンを取り除く
		getSession().remove("requestToken");

		// TwitterのユーザID(数値)を取得
		long twitterId = twitter.getId();
		log.debug("-- twitterId:" + twitterId);

		// セッションからTwitterChainの要求であったかを判定する
		Object requestKeyFlag = getSession().get(SessionKeys.TWITTER_ACCOUNT_CHAIN_REQUEST_KEY.name());
		if ( requestKeyFlag != null ) {
			if ((Boolean)requestKeyFlag) {
				log.info(" - twitter account chain request -> redirect to afterChain.");
				return TWITTER_CHAIN_STRING;
			}
		}

		// Twitter認証連携を登録する
		boolean isChain = service.chainSpringSecurity(twitter);
		if ( !isChain ) {
			addActionError(getText("callback.error.twitterInfo"));
			return ERROR;
		}

		// SpringSecurityで認証しなおす
		boolean isAuth = service.startAuthentication();
		if ( !isAuth ) {
			addActionError(getText("callback.error.authchain"));
			return ERROR;
		}

		// SpringSecurityのContext情報を取得する
		SecurityContext context = SecurityContextHolder.getContext();
		String userId = context.getAuthentication().getName();	// ユーザID
		if ( StringUtils.isBlank(userId)) {
			addActionError(getText("callback.error.nouser"));
			return ERROR;
		}

		accountService.setUserId(userId);
		AccountVO vo = accountService.getAccountInfo();

		getSession().put(SessionKeys.ACCOUNT.name(),vo);

		return SUCCESS;
	}

	@Autowired
	@Getter @Setter
	private TwitterChainService service;
	@Autowired
	AccountService accountService;
	@Getter @Setter
	private String oauth_verifier;

	/** アカウント管理からツイッター連携をした場合のRESULT値 */
	public static final String TWITTER_CHAIN_STRING = "twitterChain";
}
