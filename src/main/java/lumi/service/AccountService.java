package lumi.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lumi.dao.DAO;
import lumi.vo.AccountVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * アカウント管理Serviceクラス。
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
public class AccountService extends LumiService {

	/**
	 * アカウント情報の取得。
	 * @return アカウント情報
	 * @throws Exception
	 */
	public AccountVO getAccountInfo() throws Exception {
		// ユーザ情報の取得
		AccountVO returnVo = (AccountVO)dao.selectObject(Query.selectUser.name(), getUserId());

		return returnVo;
	}

	/**
	 * パスワードの更新を行う。
	 * 本メソッドはValidationの中から実行され、その件数を返す。
	 * 1件あれば更新完了となる。
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int updatePassword(AccountVO vo) throws Exception {
		int count = dao.update(Query.updatePassword.name(), vo);

		if ( count != 1) {
			throw new Exception("更新件数 :" + count +". のためエラー");
		}

		return count;
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
		selectUser , updatePassword
	}

	@Setter @Getter
	private boolean result;

}
