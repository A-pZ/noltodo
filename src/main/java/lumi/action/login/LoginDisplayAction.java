package lumi.action.login;

import lombok.extern.log4j.Log4j2;
import lumi.action.LumiActionSupport;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ログイン画面表示Action。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/login")
@ParentPackage("lumi-default")
@Results({
	// location属性に指定したhtmlファイル名は、/WEB-INF/content 以下からの相対パス。
	@Result(name = ActionSupport.SUCCESS, location = "/login/login" , type="thymeleaf-spring"),
})
@Controller
@Scope("prototype")
@Log4j2
public class LoginDisplayAction extends LumiActionSupport {

	/**
	 * デフォルトアクション。
	 */
	@Action("display")
	public String start() throws Exception {
		addActionMessage(getText("login.welcome"));

		// Result値。ActionSupportの定数値を返すか、別途定義した値を返すこと。
		// この値は@Resultで指定したname値となる。
		return SUCCESS;
	}

	@Action("failure")
	public String loginFailure() throws Exception {

		addActionError(getText("login.failure"));

		return SUCCESS;
	}
}
