/**
 *
 */
package lumi.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import lombok.extern.log4j.Log4j2;

import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * タスクの集計Service。
 * @author A-pZ
 *
 */
@Scope("prototype")
@Service
@Log4j2
@Transactional(
	    propagation = Propagation.REQUIRED,
	    isolation = Isolation.DEFAULT,
	    readOnly = true,
	    rollbackFor = { RuntimeException.class, Exception.class })
public class TaskSummaryService {
	@Async
	public Future<String> asyncProcess(CountDownLatch latch) {
		try {
			return new AsyncResult<String>("complete.");
		} finally {
			latch.countDown();
		}
	}

}
