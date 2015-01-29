package lumi.action;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lumi.service.RegisterService;
import lumi.vo.RegisterVO;

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
 * Actionクラスのテンプレートサンプル。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/")
@ParentPackage("lumi-default")
@Results({
	// location属性に指定したhtmlファイル名は、/WEB-INF/content 以下からの相対パス。
	@Result(name = ActionSupport.SUCCESS, location = "delete" , type="thymeleaf-spring"),
	@Result(name = ActionSupport.ERROR, location = "delete" , type="thymeleaf-spring"),
	@Result(name = ActionSupport.INPUT, location = "register" , type="thymeleaf-spring"),
})
@Validations(
	visitorFields={
		@VisitorFieldValidator(appendPrefix=false,fieldName="vo")
	}
)
@Controller
@Scope("prototype")
@Slf4j
public class DeleteAction extends LumiActionSupport {

	/**
	 * デフォルトアクション。
	 */
	@Action("delete")
	public String start() throws Exception {

		// Serviceクラスの呼び出し
		service.delete(vo);

		// 実行結果の取得
		boolean result = service.isResult();
		log.debug(" - service.result:" + result);

		// Result値。ActionSupportの定数値を返すか、別途定義した値を返すこと。
		// この値は@Resultで指定したname値となる。
		return ( result ? SUCCESS : ERROR );
	}

	/**
	 * Actionクラスから実行する業務ロジックのServiceクラス。Autowiredがついたフィールドが自動的に対象となる。
	 */
	@Blocked
	@Autowired
	@Getter @Setter
	private RegisterService service;

	@Getter @Setter
	private RegisterVO vo;
}
