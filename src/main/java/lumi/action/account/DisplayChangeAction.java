package lumi.action.account;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import lumi.action.LumiActionSupport;
import lumi.service.AccountService;
import lumi.vo.AccountVO;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.annotations.Blocked;

/**
 * アカウント管理の表示Actionクラス。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/account")
@ParentPackage("lumi-default")
@Results({
	// location属性に指定したhtmlファイル名は、/WEB-INF/content 以下からの相対パス。
	@Result(name = ActionSupport.SUCCESS, location = "account/passwordChange" , type="thymeleaf-spring"),
})
@Controller
@Scope("prototype")
@Log4j2
public class DisplayChangeAction extends LumiActionSupport {

	/**
	 * アカウント情報（パスワード）変更の表示アクション。
	 */
	@Action("displayChange")
	public String start() throws Exception {

		addActionMessage(getText("account.password.display"));

		return SUCCESS;
	}

	@Blocked
	@Autowired
	@Getter @Setter
	private AccountService account;

	@Getter @Setter
	private AccountVO vo;
}
