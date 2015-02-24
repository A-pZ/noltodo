package lumi.service;

import javax.naming.OperationNotSupportedException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lumi.dao.DAO;
import lumi.vo.AccessControlDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * タスクのアクセス権を管理するService。
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
	    readOnly = true,
	    rollbackFor = { RuntimeException.class, Exception.class })
public class AccessControlService extends LumiService {

	public boolean isEditable(AccessControlDTO dto) throws Exception {
		throw new OperationNotSupportedException();
	}

	public boolean isVisible(AccessControlDTO dto) throws Exception {
		throw new OperationNotSupportedException();
	}

	/**
	 * 指定のタスク作成者かを返す。
	 * 作成したユーザと同じユーザIDであればtrue
	 * @param dto アクセス制御用のDTO
	 * @return 同じユーザならtrue
	 */
	public boolean isTaskOwner(AccessControlDTO dto) {
		int count = (Integer)dao.selectObject(Query.isTaskOwner.name(), dto);

		return isSingleRow(count);
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
		isTaskOwner ,
	}

	@Setter @Getter
	private boolean result;

	/**
	 * 単一行であればtrueを返す。
	 * @param count 登録・更新行数のint
	 * @return 1であればtrue
	 */
	boolean isSingleRow(int count) {
		return ( count == 1);
	}
}
