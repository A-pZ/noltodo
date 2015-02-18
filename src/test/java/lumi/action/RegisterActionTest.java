package lumi.action;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;

import org.apache.struts2.StrutsSpringJUnit4TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		  locations={"classpath:spring/applicationContext.xml",
		             "classpath:spring/applicationContext-*.xml" })
public class RegisterActionTest extends
		StrutsSpringJUnit4TestCase<RegisterAction> {

	@Override
	public void setUp() throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		super.setUp();
		Principal principal = spy(new UserPrincipal() {
			@Override
			public String getName() {
				return "TestUser";
			}
		});

		request.setUserPrincipal(principal);

		request.addParameter("_csrf.parameterName", "CSRF_DUMMY");
	}

	@Test
	public void test登録失敗_タスク名なし() throws Exception {

		request.addParameter("vo.name", "");
		request.addParameter("vo.task", "### タスクh3");
		request.addParameter("vo.userid", "user01");
		request.addParameter("vo.jobid", "sam");
		request.addParameter("vo.status", "op");
		request.addParameter("vo.limitdate", "2015/04/08");
		request.addParameter("vo.publish", "1");
		request.addParameter("vo.priority", "100");

		ActionProxy proxy = getActionProxy("/register");
		String result = proxy.execute();
		assertEquals(ActionSupport.INPUT, result);
	}

	public void test登録失敗_タスクなし() throws Exception {
		request.addParameter("vo.name", "taskname");
		request.addParameter("vo.task", "");
		request.addParameter("vo.userid", "user01");
		request.addParameter("vo.jobid", "sam");
		request.addParameter("vo.status", "op");
		request.addParameter("vo.limitdate", "2015/04/08");
		request.addParameter("vo.publish", "1");
		request.addParameter("vo.priority", "100");

		ActionProxy proxy = getActionProxy("/register");
		String result = proxy.execute();
		assertEquals(ActionSupport.INPUT, result);
	}

	public void test登録失敗_特化なし() throws Exception {
		request.addParameter("vo.name", "taskname");
		request.addParameter("vo.task", "## h2");
		request.addParameter("vo.userid", "userid");
		request.addParameter("vo.jobid", "");
		request.addParameter("vo.status", "op");
		request.addParameter("vo.limitdate", "2015/04/08");
		request.addParameter("vo.publish", "1");
		request.addParameter("vo.priority", "100");

		ActionProxy proxy = getActionProxy("/register");
		String result = proxy.execute();
		assertEquals(ActionSupport.INPUT, result);
	}

	public void test登録失敗_タスク名が65文字() throws Exception {
		StringBuilder sbBuilder = new StringBuilder();
		for ( int i=0;i<65;i++ ) { sbBuilder.append("*"); }

		request.addParameter("vo.name", sbBuilder.toString());
		request.addParameter("vo.task", "## h2");
		request.addParameter("vo.userid", "");
		request.addParameter("vo.jobid", "sam");
		request.addParameter("vo.status", "op");
		request.addParameter("vo.limitdate", "2015/04/08");
		request.addParameter("vo.publish", "1");
		request.addParameter("vo.priority", "100");

		ActionProxy proxy = getActionProxy("/register");
		String result = proxy.execute();
		assertEquals(ActionSupport.INPUT, result);
	}

	public void test登録失敗_タスクが2049文字() throws Exception {
		StringBuilder sbBuilder = new StringBuilder();
		for ( int i=0;i<2049;i++ ) { sbBuilder.append("a"); }

		request.addParameter("vo.name", "タスク");
		request.addParameter("vo.task", sbBuilder.toString());
		request.addParameter("vo.userid", "");
		request.addParameter("vo.jobid", "sam");
		request.addParameter("vo.status", "op");
		request.addParameter("vo.limitdate", "2015/04/08");
		request.addParameter("vo.publish", "1");
		request.addParameter("vo.priority", "100");

		ActionProxy proxy = getActionProxy("/register");
		String result = proxy.execute();
		assertEquals(ActionSupport.INPUT, result);
	}

	public void test登録成功() throws Exception {
		StringBuilder sbBuilder = new StringBuilder();
		for ( int i=0;i<2048;i++ ) { sbBuilder.append("@"); }

		request.addParameter("vo.name", "タスク");
		request.addParameter("vo.task", sbBuilder.toString());
		request.addParameter("vo.userid", "");
		request.addParameter("vo.jobid", "sam");
		request.addParameter("vo.status", "op");
		request.addParameter("vo.limitdate", "2015/04/08");
		request.addParameter("vo.publish", "1");
		request.addParameter("vo.priority", "100");

		ActionProxy proxy = getActionProxy("/register");
		String result = proxy.execute();
		assertEquals(ActionSupport.SUCCESS, result);
	}
}
