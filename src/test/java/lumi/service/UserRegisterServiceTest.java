/**
 *
 */
package lumi.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import lumi.dao.DAO;
import lumi.dao.DAOImpl;
import lumi.service.UserRegisterService.Query;
import lumi.vo.UserRegisterVO;
import lumi.vo.UserVO;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author A-pZ
 *
 */
public class UserRegisterServiceTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Mock
	DAO dao = new DAOImpl();

	@InjectMocks
	UserRegisterService service;

	/**
	 * {@link lumi.service.UserRegisterService#register(lumi.vo.UserVO)} のためのテスト・メソッド。
	 */
	@Test
	public void testユーザ登録成功() throws Exception {
		UserRegisterVO user = new UserRegisterVO();
		user.setUsername("test");
		user.setPassword("password");
		user.setActivate(1);
		user.setUserrole("ROLE_ADMIN");

		when(dao.insert(Query.registerUser.name(), user)).thenReturn(1);
		when(dao.insert(Query.registerUserRole.name(), user)).thenReturn(1);
		service.register(user);

		assertTrue( service.isResult() );
	}

	@Test
	public void testユーザ登録失敗_既存ユーザ() throws Exception {
		UserVO user = new UserVO();
		user.setUsername("test");
		user.setPassword("password");

		when(dao.insert(Query.registerUser.name(), user)).thenReturn(0);
		when(dao.insert(Query.registerUserRole.name(), user)).thenReturn(1);
		service.register(user);

		assertFalse(service.isResult());
	}

	@Test
	public void testユーザ登録失敗_ロール登録失敗() throws Exception {
		UserVO user = new UserVO();
		user.setUsername("test");
		user.setPassword("password");

		when(dao.insert(Query.registerUser.name(), user)).thenReturn(1);
		when(dao.insert(Query.registerUserRole.name(), user)).thenReturn(0);
		service.register(user);

		assertFalse(service.isResult());
	}

	/**
	 * {@link lumi.service.UserRegisterService#update(lumi.vo.UserVO)} のためのテスト・メソッド。
	 */
	@Test
	public void testユーザ更新() throws Exception {
		UserVO user = new UserVO();
		user.setUsername("test");
		user.setPassword("password");

		when(dao.update(Query.updateUser.name(), user)).thenReturn(1);
		service.update(user);

		assertTrue(service.isResult());
	}

	@Test
	public void testユーザ更新失敗() throws Exception {
		UserVO user = new UserVO();
		user.setUsername("test");
		user.setPassword("password");

		doReturn(0).when(dao).update(Query.updateUser.name(), user);

		service.update(user);

		assertFalse(service.isResult());
	}

	/**
	 * {@link lumi.service.UserRegisterService#existUserSearch(java.lang.String)} のためのテスト・メソッド。
	 */
	@Test
	public void test既存ユーザ検索() throws Exception {
		String username = "test";

		when(dao.selectObject(Query.existUser.name(), username)).thenReturn(1);

		assertSame(service.existUserSearch(username), 1);
	}

}
