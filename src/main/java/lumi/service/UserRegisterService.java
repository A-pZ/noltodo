package lumi.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lumi.dao.DAO;
import lumi.vo.UserRegisterVO;
import lumi.vo.UserVO;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * ユーザ登録Serviceクラス。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
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
public class UserRegisterService extends LumiService {

	/**
	 * ユーザ登録。
	 * @param vo ユーザ情報vo
	 * @return 登録したタスク番号
	 * @throws Exception
	 */
	public void register(UserVO vo) throws Exception {

		UserRegisterVO registerVO = new UserRegisterVO();
		BeanUtils.copyProperties(registerVO, vo);
		registerVO.setActivate(1);
		registerVO.setUserrole("ROLE_ADMIN");

		// 登録する
		int count =  dao.insert(Query.registerUser.name(), registerVO);
		if ( count == 0 ) {
			result = false;
			addErrorMessage("signup.failure");
		} else {
			// ロールの更新
			int roleCount = dao.insert(Query.registerUserRole.name(), registerVO);
			if ( roleCount == 0 ) {
				result = false;
				addErrorMessage("signup.role.failure");
			} else {
				result = true;
				addInfoMessage("signup.complete");
			}
		}
	}

	/**
	 * ユーザ更新。
	 * @param vo
	 * @throws Exception
	 */
	public void update(UserVO vo) throws Exception {
		int count =  dao.update(Query.updateUser.name(), vo);
		if ( count == 1 ) {
			result = true;
			addInfoMessage("account.update.complete");
		} else {
			result = false;
			addErrorMessage("account.update.failure");
		}
	}

	/**
	 * すでに存在しているユーザであるかを検索する。
	 * @param username ユーザID
	 * @return ヒット件数
	 * @throws Exception 発生した例外
	 */
	public Integer existUserSearch(String username) throws Exception {
		return (Integer)dao.selectObject(Query.existUser.name(), username);
	}

	/**
	 * DAOの指定。Mybatisを利用してデータベースアクセスを実行する。
	 */
	@Autowired
	private DAO dao;

	/**
	 * Mybatisで定義するSQLのSQL-ID。
	 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
	 *
	 */
	public enum Query {
		registerUser , registerUserRole ,updateUser, existUser
	}

	@Setter @Getter
	private boolean result;

}
