package lumi.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import lumi.dao.DAO;
import lumi.dao.DAOImpl;
import lumi.service.TagService.Query;
import lumi.vo.AccessControlDTO;
import lumi.vo.TagVO;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class TagServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	@InjectMocks
	TagService service;

	@Mock
	DAO dao = new DAOImpl();

	@Spy
	SystemTimestampService systemTimestampService;

	@Test
	public void testタスクに登録しているタグ一覧() throws Exception {
		TagVO vo = new TagVO();
		vo.setTagid("A1");
		List<Object> resultList = new ArrayList<Object>();
		resultList.add(vo);

		when( dao.select(Query.getTagInTask.name(), vo)).thenReturn(resultList);

		assertArrayEquals(resultList.toArray() , service.getTaglistOfTask(vo).toArray());
	}

	@Test
	public void testタグ一覧() throws Exception {
		AccessControlDTO dto = new AccessControlDTO();
		dto.setUsername("testUser");

		TagVO vo = new TagVO();
		vo.setTagid("A1");
		List<Object> resultList = new ArrayList<Object>();
		resultList.add(vo);

		when(dao.select(Query.tagAllList.name(), dto)).thenReturn(resultList);

		assertArrayEquals(resultList.toArray(), service.getTagAllList(dto).toArray());
	}

	@Test
	public void test新規タグ登録() throws Exception {
		TagVO vo = new TagVO();
		vo.setTagid("A1");
		vo.setDisplay("display");

		String existTagid = "";
		when( dao.selectObject(Query.existTag.name(), vo)).thenReturn(existTagid);

		when(dao.insert(Query.registerTagForTask.name(), vo)).thenReturn(1);

		assertTrue(service.registerTag(vo));
	}

	@Test
	public void test既存タグ登録() throws Exception {
		TagVO vo = new TagVO();
		vo.setTagid("B1");
		vo.setDisplay("display");

		String existTagid = "B1";
		when( dao.selectObject(Query.existTag.name(), vo)).thenReturn(existTagid);

		when(dao.insert(Query.registerTagForTask.name(), vo)).thenReturn(1);

		assertTrue(service.registerTag(vo));
	}

	@Test
	public void testタスクにタグ関連付け() throws Exception {
		TagVO vo = new TagVO();
		vo.setTagid("B1");
		when( dao.insert(Query.registerTagForTask.name(), vo)).thenReturn(1);

		assertTrue(service.linkTagForTask(vo));
	}

	@Test
	public void testタスクからタグ削除() throws Exception  {
		TagVO vo = new TagVO();
		vo.setTagid("B1");
		when(dao.delete(Query.removeTagForTask.name(), vo)).thenReturn(1);

		assertTrue(service.dropTagForTask(vo));
	}

}
