package lumi.action;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lumi.service.SearchService;
import lumi.service.TagService;
import lumi.vo.SearchVO;

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
 * 1レコードの表示Actionクラス。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/")
@ParentPackage("lumi-default")
@Results({
	// location属性に指定したhtmlファイル名は、/WEB-INF/content 以下からの相対パス。
	@Result(name = ActionSupport.SUCCESS, location = "register" , type="thymeleaf-spring"),
	@Result(name = ActionSupport.ERROR, location = "application_error" , type="thymeleaf-spring"),
})
@Controller
@Scope("prototype")
@Slf4j
public class DisplayAction extends LumiActionSupport {

	/**
	 * デフォルトアクション。
	 */
	@Action("display")
	public String display() throws Exception {

		if ( vo != null && vo.getId() > 0 ) {
			// Serviceクラスの呼び出し
			vo = service.detail(vo);

			if ( vo == null ) {
				log.warn(" - fail to getting vo.");
				return ERROR;
			} else {
				log.debug(" - getting vo.id=" + vo.getId());
			}

		} else {
			// ない場合はブランク作成
			vo = service.blank();
			log.debug(" - create blank.");
		}

		// 1.3.0 有効なタスクについているタグ名称一覧を取得する。
		tagList = tagService.getEffectiveTagList();

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
	private SearchService service;

	@Blocked
	@Autowired
	private TagService tagService;

	@Getter @Setter
	private SearchVO vo;

	@Getter @Setter
	private List<String> tagList;
}
