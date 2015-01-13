package lumi.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import lumi.dao.DAO;
import lumi.vo.SearchVO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 一覧検索Serviceクラス。
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
public class SearchService extends LumiService {

	/**
	 * 検索のService。
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<SearchVO> execute(SearchVO vo) throws Exception {

		if ( vo == null ) {
			vo = new SearchVO();
		}

		if ( StringUtils.isBlank(vo.getStatus()) ) {
			vo.setStatus("cl");
		}

		// 検索を行う。
		List<SearchVO> resultList = dao.select(Query.searchTask.name(), vo);

		if ( resultList.size() == 0 ) {
			addWarnMessage("search.result.none");
		} else {
			// メッセージ
			String[] msg = {String.valueOf(resultList.size())};
			addInfoMessage("search.result", msg);
		}


		// 登録結果を返す。
		return resultList;
	}

	/**
	 * 明細情報を表示する。
	 * @param vo 検索条件
	 * @return タスク検索結果。
	 * @throws Exception
	 */
	public SearchVO detail(SearchVO vo) throws Exception {
		// 検索を行う。
		vo.setStatus("all");
		List<SearchVO> resultList = dao.select(Query.searchTask.name(), vo);

		if ( resultList == null || resultList.size() ==0 ) {
			addErrorMessage("detail.record.none");
			return null;
		}

		if ( resultList.size() > 2) {
			addErrorMessage("detail.record.toomatch");
			return vo;
		} else {
			addInfoMessage("detail.display");
		}
		SearchVO returnVo = resultList.get(0);
		returnVo.setUpdate(true);

		return returnVo;
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
		searchTask ,
	}
}
