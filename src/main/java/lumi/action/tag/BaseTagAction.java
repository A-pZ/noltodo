/**
 *
 */
package lumi.action.tag;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import lombok.extern.log4j.Log4j2;
import lumi.action.LumiActionSupport;
import lumi.exception.AccessControlException;
import lumi.service.AccessControlService;
import lumi.vo.AccessControlDTO;
import lumi.vo.TagVO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * タグ機能の基本Actionクラス。
 * @author A-pZ
 *
 */
@Log4j2
@Controller
public class BaseTagAction extends LumiActionSupport {

	public static final String JQUERY_AJAX_HEADER_KEY = "X-Requested-With";
	public static final String JQUERY_AJAX_HEADER_VALUE = "XMLHttpRequest";

	public static final String ACL_ERROR_AJAX_RESULT = "acl_error_ajax";
	public static final String ACL_ERROR_RESULT = "acl_error";

	/**
	 * アクセス可能なタスクであるかを判定する。
	 * @param vo タグ用VO
	 * @return アクセス可能ならtrue
	 */
	boolean isAccessibleTask(TagVO vo) {
		log.debug("acl check.");
		if ( vo == null ) {
			throw new AccessControlException("acl.tagvo.null");
		}

		if ( StringUtils.isBlank(vo.getTaskid()) ) {
			throw new AccessControlException("acl.taskid.blank");
		}

		val dto = new AccessControlDTO();
		dto.setTaskid(vo.getTaskid());
		dto.setUsername(getLoginUsername());
		// タスクの所有者であるかを判定
		val taskOwner = acs.isTaskOwner(dto);

		return taskOwner;
	}

	/**
	 * アクセス制御エラーだった場合のResult値を判定する。
	 * @return Result値
	 */
	String accessControlErrorResult() {
		errorMessage = getText("acl.denied");

		return isAjaxRequest() ? ACL_ERROR_AJAX_RESULT : ACL_ERROR_RESULT;
	}

	/**
	 * AJAXリクエストであるかを判定する。
	 * @return jQueryのAJAXリクエストならtrue
	 */
	boolean isAjaxRequest() {
		val ajaxHeader = getServletRequest().getHeader(JQUERY_AJAX_HEADER_KEY);
		if ( StringUtils.isBlank(ajaxHeader)) return false;

		return ( ajaxHeader.equals(JQUERY_AJAX_HEADER_VALUE));
	}

	@Autowired
	AccessControlService acs;

	@Getter @Setter
	private String errorMessage;
}
