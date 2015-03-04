package lumi.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import lumi.dao.DAO;
import lumi.dao.DAOImpl;
import lumi.service.SearchService.Query;
import lumi.vo.SearchVO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SearchServiceTest {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		service.setUserId("testUserId");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test検索条件なし() throws Exception {
		SearchVO vo = null;
		List<Object> mockResult = new ArrayList<Object>();

		when(dao.select(Query.searchTask.name(), vo)).thenReturn(mockResult);
		List<SearchVO> result =	service.execute(vo);

		assertArrayEquals(mockResult.toArray(), result.toArray());
	}

	@Test
	public void test検索条件あり() throws Exception {
		SearchVO vo = mock(SearchVO.class);
		List<Object> mockResult = new ArrayList<Object>();
		mockResult.add(vo);

		when(dao.select(Query.searchTask.name(), vo)).thenReturn(mockResult);
		List<SearchVO> result =	service.execute(vo);

		assertArrayEquals(mockResult.toArray(), result.toArray());
	}

	@Test
	public void test明細0件() throws Exception {
		SearchVO vo = mock(SearchVO.class);
		List<Object> mockResult = new ArrayList<Object>();

		when(dao.select(Query.searchTask.name(), vo)).thenReturn(mockResult);
		SearchVO result = service.detail(vo);

		assertNotSame(result , vo);
	}

	@Test
	public void test明細1件() throws Exception {
		SearchVO vo = new SearchVO();
		vo.setUserid("testUserId");
		List<Object> mockResult = new ArrayList<Object>();
		mockResult.add(vo);

		when(dao.select(Query.searchTask.name(), vo )).thenReturn(mockResult);
		SearchVO result = service.detail(vo);

		assertSame(result , vo);
	}

	@Test
	public void test公開明細タスク() throws Exception {
		SearchVO vo = new SearchVO();
		vo.setUserid("otherUserId");
		vo.setPublish(1);
		List<Object> mockResult = new ArrayList<Object>();
		mockResult.add(vo);

		when(dao.select(Query.searchTask.name(), vo )).thenReturn(mockResult);
		SearchVO result = service.detail(vo);

		assertSame(result , vo);
	}

	public void test非公開明細タスク() throws Exception {
		SearchVO vo = new SearchVO();
		vo.setUserid("otherUserId");
		vo.setPublish(0);

		when(dao.select(Query.searchTask.name(), vo )).thenReturn(null);
		SearchVO result = service.detail(vo);

		assertNull(result);
	}

	@Mock
	DAO dao = new DAOImpl();

	@InjectMocks
	SearchService service;
}
