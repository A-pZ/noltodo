package lumi.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

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
			vo.setStatus("op");
		}
		/*
		if (! "all".equals(vo.getStatus())) {
			vo.setUserid(getUserId());
		}*/
		vo.setUserid(getUserId());

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

		if (!StringUtils.equals(returnVo.getUserid(), getUserId()) ) {
			returnVo.setStatus("read");
		}
		returnVo.setUpdate(true);

		return returnVo;
	}

	/**
	 * 新規入力するVOを生成する。次の期限を次回メンテナンス日にちに設定する。
	 * @return 新規生成したVO
	 * @throws Exception
	 */
	public SearchVO blank() throws Exception {
		SearchVO vo = new SearchVO();

		Calendar calendar = GregorianCalendar.getInstance();

		// 期限を次のメンテナンス時間にする
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		// 水曜日との差を取得(マイナスないしは水曜日だったら+7する)
		int diffDay = Calendar.WEDNESDAY - dayOfWeek;
		diffDay = ( diffDay <= 0 ) ? diffDay + Calendar.SATURDAY : diffDay;
		calendar.add(Calendar.DAY_OF_MONTH, diffDay);

		vo.setLimitdate( new Timestamp(calendar.getTimeInMillis()));

		// タスクの重要度を中に。
		vo.setPriority("mid");

		return vo;
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
