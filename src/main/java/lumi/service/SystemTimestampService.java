package lumi.service;

import java.sql.Timestamp;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.extern.log4j.Log4j2;
import lumi.dao.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * システム日付を統一的に提供するServiceクラス。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 *
 */
@Scope("singleton")
@Service
@Log4j2
public class SystemTimestampService extends LumiService {

	/**
	 * 起動時の処理。
	 * @throws Exception
	 */
	@PostConstruct
	public void start() throws Exception {
		log.info(" - start.");
	}

	/**
	 * 終了時の処理。
	 * @throws Exception
	 */
	@PreDestroy
	public void destroy() throws Exception {
		log.info(" - destroy.");
	}

	/**
	 * システム時刻をTimestamp型で返却する。
	 * @return 現在時刻のTimestamp型
	 * @throws Exception
	 */
	public Timestamp getSystemTimestamp() throws Exception {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * DAOの指定。Mybatisを利用してデータベースアクセスを実行する。
	 */
	@Autowired
	private DAO dao;
}
