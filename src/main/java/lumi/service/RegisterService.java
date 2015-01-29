package lumi.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lumi.dao.DAO;
import lumi.vo.RegisterVO;
import lumi.vo.SearchVO;

import org.apache.commons.beanutils.BeanUtils;
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
	 * @return 登録したタスク番号
	 * @throws Exception
	 */
	public int execute(RegisterVO vo) throws Exception {

		int count = 0;

		vo.setUserid(getUserId());

		if ( vo.getId() == 0 ) {
			log.debug(" - add new task.");
			// 登録を行う。
			count = dao.insert(Query.insertTask.name(), vo);
			if ( count == 1 ) {
				// 登録後、発行したタスク番号を格納する。
				int registerId = selectMaxId(vo);
				vo.setId(registerId);
			}

		} else {
			log.debug(" - update task. id=" + vo.getId());
			// 更新を行う。
			count = dao.update(Query.updateTask.name(), vo);
		}

		// 実行結果を格納
		result = ( count == 1);

		// 登録件数のチェックとメッセージ
		if ( result ) {
			addInfoMessage("register.complete");

			// 明細の再表示
			SearchVO searchVO = new SearchVO();
			searchVO.setId(vo.getId());
			searchVO.setStatus(vo.getStatus());
			searchService.setUserId(getUserId());
			searchVO = searchService.detail(searchVO);
			registerVO = new RegisterVO();
			BeanUtils.copyProperties(registerVO, searchVO);

		} else {
			addErrorMessage("register.failure");
			throw new Exception("register.failure:register rows:" + count);
		}

		// 登録したタスク番号を返す。
		return vo.getId();
	}

	/**
	 * タスクの削除Service。
	 * @param vo リクエストパラメータVO
	 * @throws Exception 発生する例外すべて
	 */
	public void delete(RegisterVO vo) throws Exception {
		int count = 0;
		vo.setUserid(getUserId());

		// 削除
		count = dao.delete(Query.deleteTask.name(), vo);

		String[] msg = { String.valueOf(vo.getId())};
		if ( count != 1 ) {
			addErrorMessage("delete.failure" , msg);
			setResult(false);
		} else {
			addInfoMessage("delete.success",msg);
			setResult(true);
		}
	}

	/**
	 * 発行した最新タスク番号を取得
	 * @param vo 登録時VO
	 * @return 発行したタスク番号
	 * @throws Exception
	 */
	int selectMaxId(RegisterVO vo) throws Exception {
		return (Integer)dao.selectObject(Query.selectMaxId.name() , vo);
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
		insertTask , updateTask , deleteTask , selectMaxId
	}

	@Setter @Getter
	private boolean result;

	@Getter @Setter
	private RegisterVO registerVO;

	@Autowired
	private SearchService searchService;
}
