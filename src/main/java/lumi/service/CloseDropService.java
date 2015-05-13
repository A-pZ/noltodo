package lumi.service;

import lombok.extern.slf4j.Slf4j;
import lumi.dao.DAO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 一括削除Serviceクラス。
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
public class CloseDropService extends LumiService {

	/**
	 * 一括削除のService。
	 * @param param
	 * @return 登録したタスク番号
	 * @throws Exception
	 */
	public int execute() throws Exception {
		String userid = getUserId();
		if ( StringUtils.isBlank(userid)) {
			throw new Exception("userid is blank.");
		}

		int count = dao.delete(Query.closeDrop.name(), userid);

		if ( count == 0 ) {
			addWarnMessage("close.drop.failure");
		} else {
			addInfoMessage("close.drop.success", String.valueOf(count));
		}

		return count;
	}

	@Autowired
	DAO dao;

	enum Query {
		closeDrop ,
	}
}
