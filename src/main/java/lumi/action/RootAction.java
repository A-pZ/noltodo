package lumi.action;

import lombok.extern.slf4j.Slf4j;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 1レコードの表示Actionクラス。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/")
@ParentPackage("lumi-default")
@Results({
	// location属性に指定したhtmlファイル名は、/WEB-INF/content 以下からの相対パス。
	@Result(name = ActionSupport.SUCCESS , type="redirectAction" , params={"namespace","/","actionName","list"}) ,
})
@Controller
@Scope("prototype")
@Slf4j
public class RootAction extends LumiActionSupport {

	/**
	 * ルートパスの場合。
	 * @return リダイレクトAction
	 * @throws Exception
	 */
	@Action("")
	public String rootUrl() throws Exception {
		log.debug(" - access root path /");

		return SUCCESS;
	}
}
