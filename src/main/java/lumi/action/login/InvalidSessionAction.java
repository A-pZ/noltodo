package lumi.action.login;

import lombok.extern.slf4j.Slf4j;
import lumi.action.LumiActionSupport;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * セッションエラーのページを表示する。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/error")
@ParentPackage("lumi-default")
@Results({
	// location属性に指定したhtmlファイル名は、/WEB-INF/content 以下からの相対パス。
	@Result(name = ActionSupport.SUCCESS, location = "login/login" , type="thymeleaf-spring"),
})
@Controller
@Scope("prototype")
@Slf4j
public class InvalidSessionAction extends LumiActionSupport implements Preparable {


	/**
	 * Action事前処理。
	 * @throws Exception
	 */
	@Override
	public void prepare() throws Exception {
	}

	/**
	 * デフォルトアクション。
	 */
	@Action("session")
	public String start() throws Exception {
		log.info("login failure display.");

		addActionError(getText("session.invalid"));

		// Result値。ActionSupportの定数値を返すか、別途定義した値を返すこと。
		// この値は@Resultで指定したname値となる。
		return SUCCESS;
	}
}
