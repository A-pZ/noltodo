package lumi.action.account;

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

/**
 * アカウント管理の表示Actionクラス。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/account")
@ParentPackage("lumi-default")
@Results({
	// location属性に指定したhtmlファイル名は、/WEB-INF/content 以下からの相対パス。
	@Result(name = ActionSupport.SUCCESS, location = "account/menu" , type="thymeleaf-spring"),
})
@Controller
@Scope("prototype")
@Slf4j
public class DisplayAction extends LumiActionSupport {

	/**
	 * デフォルトアクション。
	 */
	@Action("")
	public String start() throws Exception {

		addActionMessage(getText("account.menu.display"));

		return SUCCESS;
	}
}
