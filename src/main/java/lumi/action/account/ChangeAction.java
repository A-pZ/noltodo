package lumi.action.account;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lumi.action.LumiActionSupport;
import lumi.service.AccountService;
import lumi.vo.PasswordUpdateVO;

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
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

/**
 * アカウント管理のパスワード変更Actionクラス。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/account")
@ParentPackage("lumi-default")
@Results({
	// location属性に指定したhtmlファイル名は、/WEB-INF/content 以下からの相対パス。
	@Result(name = ActionSupport.SUCCESS, location = "account/menu" , type="thymeleaf-spring"),
	@Result(name = ActionSupport.INPUT, location = "account/passwordChange" , type="thymeleaf-spring"),
})
@Validations(
		visitorFields={
			@VisitorFieldValidator(appendPrefix=false,fieldName="vo")
		}
	)
@Controller
@Scope("prototype")
@Slf4j
public class ChangeAction extends LumiActionSupport {

	/**
	 * デフォルトアクション。
	 */
	@Action("passwordChange")
	public String start() throws Exception {

		addActionMessage(getText("account.password.update.complete"));

		return SUCCESS;
	}

	@Blocked
	@Autowired
	@Getter @Setter
	private AccountService account;

	@Getter @Setter
	private PasswordUpdateVO vo;
}
