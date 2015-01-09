package lumi.service;

import lombok.extern.slf4j.Slf4j;
import lumi.dao.DAO;
import lumi.vo.RegisterVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 登録Serviceクラス。
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
public class RegisterService extends LumiService {

	/**
	 * 登録のService。
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public boolean execute(RegisterVO vo) throws Exception {

		// 登録を行う。
		int count = dao.insert(Query.insertTask.name(), vo);

		// 登録結果を返す。
		return ( count == 1);
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
		insertTask ,
	}
}
