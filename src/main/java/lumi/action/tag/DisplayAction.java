package lumi.action.tag;

import java.util.List;

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
 * タグ一覧を返す。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/tag")
@ParentPackage("lumi-default")
@Results({
	// location属性に指定したhtmlファイル名は、/WEB-INF/content 以下からの相対パス。
	@Result(name = ActionSupport.SUCCESS, location = "tag/taglist" , type="thymeleaf-spring"),
})
@Controller
@Scope("prototype")
@Log4j2
public class DisplayAction extends BaseTagAction {

	/**
	 * デフォルトアクション。
	 */
	@Action("display")
	public String display() throws Exception {

		// タスクの所有者であるかを判定
		taskOwner = isAccessibleTask(vo);

		// タグ検索結果
		resultList = service.getTaglistOfTask(vo);

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
	private List<TagVO> resultList;

	@Getter
	private boolean taskOwner;
}
