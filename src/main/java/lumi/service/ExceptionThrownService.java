/**
 *
 */
package lumi.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author A-pZ
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
public class ExceptionThrownService extends LumiService {
	public boolean thrownTestService(boolean flag) throws Exception {

		if ( flag ) {
			throw new Exception("Spring-Serviceからの例外です");
		}

		return flag;
	}
}
