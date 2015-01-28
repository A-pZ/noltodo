package lumi.action.signup;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lumi.action.LumiActionSupport;
import lumi.service.UserRegisterService;
import lumi.vo.UserVO;

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
 * サインアップ機能の登録Action。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/signup")
@ParentPackage("lumi-default")
@Results({
	// location属性に指定したhtmlファイル名は、/WEB-INF/content 以下からの相対パス。
	@Result(name = ActionSupport.SUCCESS, location = "signup/complete" , type="thymeleaf-spring"),
	@Result(name = ActionSupport.ERROR, location = "signup/signup" , type="thymeleaf-spring"),
	@Result(name = ActionSupport.INPUT, location = "signup/signup" , type="thymeleaf-spring"),
})
@Validations(
	visitorFields={
		@VisitorFieldValidator(appendPrefix=false,fieldName="vo")
	}
)
@Controller
@Scope("prototype")
@Slf4j
public class RegisterAction extends LumiActionSupport {

	/**
	 * デフォルトアクション。
	 */
	@Action("register")
	public String start() throws Exception {

		service.register(vo);

		boolean result = service.isResult();

		return ( result ) ? SUCCESS : ERROR;
	}

	/**
	 * Actionクラスから実行する業務ロジックのServiceクラス。Autowiredがついたフィールドが自動的に対象となる。
	 */
	@Blocked
	@Autowired
	@Getter @Setter
	private UserRegisterService service;

	@Getter @Setter
	private UserVO vo;
}
