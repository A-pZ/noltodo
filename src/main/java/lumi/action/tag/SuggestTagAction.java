/**
 *
 */
package lumi.action.tag;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import lumi.service.TagService;

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
 * サジェストするタグ一覧を返す。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/tag")
@ParentPackage("lumi-default")
@Results({
	@Result(name = ActionSupport.SUCCESS, type="json" , params={"root","tagList"}),
	@Result(name=BaseTagAction.ACL_ERROR_RESULT , type="thymeleaf-spring" , location="application_error"),
	@Result(name=BaseTagAction.ACL_ERROR_AJAX_RESULT, type="json" , params={"root","message"})
})
@Controller
@Scope("prototype")
@Log4j2
public class SuggestTagAction extends BaseTagAction {

	@Action("suggest")
	public String suggest() throws Exception {
		tagList = tagService.getEffectiveTagList();

		return SUCCESS;
	}

	@Blocked
	@Autowired
	@Getter @Setter
	private TagService tagService;

	@Getter @Setter
	private List<String> tagList;
}
