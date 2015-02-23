/**
 *
 */
package lumi.exception;

import lombok.extern.slf4j.Slf4j;
import lumi.action.LumiActionSupport;

import com.opensymphony.xwork2.ActionContext;

/**
 * アクセス権限例外。
 * @author A-pZ
 *
 */
@Slf4j
public class AccessControlException extends RuntimeException {

	public AccessControlException() {
		super();
	}

	public AccessControlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(getActionMessage(message), cause, enableSuppression, writableStackTrace);
	}

	public AccessControlException(String message, Throwable cause) {
		super(getActionMessage(message), cause);
	}

	public AccessControlException(String message) {
		super(getActionMessage(message));
	}

	public AccessControlException(Throwable cause) {
		super(cause);
	}

	/**
	 * Actionクラスからメッセージリソースを取得して、その内容を返す。
	 * @param message 例外クラスのメッセージ
	 * @return Actionクラスのリソースから読み込んだメッセージ内容
	 */
	protected static String getActionMessage(String message) {
		ActionContext context = ActionContext.getContext();
		LumiActionSupport action = (LumiActionSupport) context.getActionInvocation().getAction();
		return action.getText(message);
	}
}
