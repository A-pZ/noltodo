package lumi.action.tag;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import lumi.service.TagService;
import lumi.vo.TagVO;

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
 * タグをタスクに新規登録する。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/json/tag")
@ParentPackage("lumi-default")
@Results({
	// location属性に指定したhtmlファイル名は、/WEB-INF/content 以下からの相対パス。
	@Result(name = ActionSupport.SUCCESS, location = "register" , type="json" , params={"root","result"}),
	@Result(name=BaseTagAction.ACL_ERROR_RESULT , type="thymeleaf-spring" , location="application_error"),
	@Result(name=BaseTagAction.ACL_ERROR_AJAX_RESULT, type="json" , params={"root","message"})
})
@Controller
@Scope("prototype")
@Log4j2
public class LinkTagForTaskAction extends BaseTagAction {

	/**
	 * デフォルトアクション。
	 */
	@Action("linkTag")
	public String newTag() throws Exception {

		if (! isAccessibleTask(vo)) {
			return accessControlErrorResult();
		}

		service.linkTagForTask(vo);

		//result = service.registerTag(vo);

		// Result値。ActionSupportの定数値を返すか、別途定義した値を返すこと。
		// この値は@Resultで指定したname値となる。
		return SUCCESS;
	}

	/**
	 * Actionクラスから実行する業務ロジックのServiceクラス。Autowiredがついたフィールドが自動的に対象となる。
	 */
	@Blocked
	@Autowired
	@Getter @Setter
	private TagService service;

	@Getter @Setter
	private TagVO vo;

	@Getter
	private boolean result;
}
