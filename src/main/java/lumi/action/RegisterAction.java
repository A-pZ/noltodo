package lumi.action;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import lumi.service.RegisterService;
import lumi.service.TagService;
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
	@Result(name = ActionSupport.SUCCESS, location = "register" , type="thymeleaf-spring"),
	@Result(name = ActionSupport.ERROR, location = "register" , type="thymeleaf-spring"),
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
public class RegisterAction extends LumiActionSupport {

	/**
	 * デフォルトアクション。
	 */
	@Action("register")
	public String start() throws Exception {

		// Serviceクラスの呼び出し
		service.execute(vo);

		// 実行結果の取得
		val result = service.isResult();
		log.debug(" - service.result:" + result);

		// 登録後、再検索した値を取得
		vo = service.getRegisterVO();

		// 1.3.0 タグ一覧の取得
		tagList = tagService.getEffectiveTagList();

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

	@Blocked
	@Autowired
	private TagService tagService;

	@Getter @Setter
	private RegisterVO vo;

	@Getter @Setter
	private List<String> tagList;
}
