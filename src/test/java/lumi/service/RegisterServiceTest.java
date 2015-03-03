/**
 *
 */
package lumi.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;

import lumi.dao.DAO;
import lumi.dao.DAOImpl;
import lumi.service.RegisterService.Query;
import lumi.vo.RegisterVO;
import lumi.vo.SearchVO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author A-pZ
 *
 */
public class RegisterServiceTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		when( searchService.detail( (SearchVO)anyObject())).thenReturn(generetaeDefaultSearchVO());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * {@link lumi.service.RegisterService#execute(lumi.vo.RegisterVO)} のためのテスト・メソッド。
	 */
	@Test
	public void testタスク登録() throws Exception {
		RegisterVO vo = new RegisterVO();

		when(dao.insert(Query.insertTask.name(), vo)).thenReturn(1);
		when(dao.selectObject(Query.selectMaxId.name() , vo)).thenReturn(123);


		assertEquals(service.execute(vo), 123);
	}

	/**
	 * {@link lumi.service.RegisterService#execute(lumi.vo.RegisterVO)} のためのテスト・メソッド。
	 * @throws Exception
	 */
	@Test
	public void testタスク更新() throws Exception {
		RegisterVO vo = new RegisterVO();
		vo.setId(200);
		when(dao.update(Query.updateTask.name(), vo)).thenReturn(1);

		assertEquals(service.execute(vo), 200);
	}

	/**
	 * {@link lumi.service.RegisterService#reSearchDetail(lumi.vo.RegisterVO)} のためのテスト・メソッド。
	 */
	//@Test
	public void testReSearchDetail() {
		fail("まだ実装されていません"); // TODO
	}

	@Mock
	DAO dao = new DAOImpl();

	@Mock
	SearchService searchService;

	@InjectMocks
	RegisterService service;

	SearchVO generetaeDefaultSearchVO() {
		SearchVO searchVO = new SearchVO();
		Timestamp ts = new Timestamp(0);

		searchVO.setLimitdate(ts);
		searchVO.setWritedate(ts);

		return searchVO;
	}

}
