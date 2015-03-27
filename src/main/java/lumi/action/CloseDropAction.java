package lumi.action;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lumi.service.CloseDropService;
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
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.interceptor.annotations.Blocked;

/**
 * クローズしたタスクの一括削除をする。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/")
@ParentPackage("lumi-default")
@Results({
	// location属性に指定したhtmlファイル名は、/WEB-INF/content 以下からの相対パス。
	@Result(name = ActionSupport.SUCCESS, location = "closeDrop" , type="thymeleaf-spring"),
	@Result(name = ActionSupport.ERROR, location = "closeDrop" , type="thymeleaf-spring"),
})
@Controller
@Scope("prototype")
@Slf4j
public class CloseDropAction extends LumiActionSupport implements Preparable {

	/**
	 * 前処理。
	 */
	@Override
	public void prepare() throws Exception {
		if ( vo == null ) {
			this.vo = new SearchVO();
		}
	}

	/**
	 * デフォルトアクション。
	 */
	@Action("closeDrop")
	public String start() throws Exception {

		// Serviceクラスの呼び出し
		int count = service.execute();

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
	private CloseDropService service;

	@Getter @Setter
	private SearchVO vo;
}
