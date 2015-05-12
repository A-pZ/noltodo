package lumi.action.tag;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import lumi.service.TagService;
import lumi.vo.AccessControlDTO;
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
 * 一覧画面で表示するタグ一覧を返す。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/tag")
@ParentPackage("lumi-default")
@Results({
	// location属性に指定したhtmlファイル名は、/WEB-INF/content 以下からの相対パス。
	@Result(name = ActionSupport.SUCCESS, location = "tag/tagAllList" , type="thymeleaf-spring"),
})
@Controller
@Scope("prototype")
@Slf4j
public class DisplayAllAction extends BaseTagAction {

	/**
	 * デフォルトアクション。
	 */
	@Action("displayAll")
	public String displayAll() throws Exception {

		val dto = new AccessControlDTO();
		dto.setUsername(getLoginUsername());

		// タグ検索結果
		resultList = service.getTagAllList(dto);

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

	@Getter
	private List<TagVO> resultList;
}
