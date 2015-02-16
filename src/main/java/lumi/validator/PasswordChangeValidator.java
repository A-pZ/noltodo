/**
 *
 */
package lumi.validator;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lumi.action.LumiActionSupport;
import lumi.service.AccountService;
import lumi.vo.AccountVO;
import lumi.vo.PasswordUpdateVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * パスワード変更拡張バリデータ。
 * @author A-pZ
 *
 */
@Slf4j
@Service
@Scope("singleton")
public class PasswordChangeValidator extends FieldValidatorSupport {

	/* (非 Javadoc)
	 * @see com.opensymphony.xwork2.validator.Validator#validate(java.lang.Object)
	 */
	@Override
	public void validate(Object paramObject) throws ValidationException {

		// すでに何らかのValidatorを検知した場合は処理をスキップ
		if (getValidatorContext().hasFieldErrors()) {
			log.debug("- field-error exists, skipped.");
			return;
		}

		// Actionインスタンス
		LumiActionSupport action =
				(LumiActionSupport) ActionContext.getContext().getActionInvocation().getAction();

		if ( action.hasFieldErrors()) {
			log.debug("- action : field-error exists, skipped.");
			return;
		}

		// 旧パスワード名
		String oldPassword = (String) getFieldValue("oldPassword", paramObject);
		// 新パスワード
		String password = (String) getFieldValue("password", paramObject);
		// 確認パスワード
		String confirm = (String) getFieldValue("confirm", paramObject);

		// ログインユーザ名
		String username = action.getLoginUsername();

		// もしパスワードの中にユーザ名が含まれていた場合はエラー
		if (password.contains(username)) {
			action.addActionError(action.getText("account.password.contain.userid"));
			return;
		}
		if (confirm.contains(username)) {
			action.addActionError(action.getText("account.password.contain.userid"));
			return;
		}

		// パスワードと確認パスワードが不一致の場合はエラー
		if (!password.equals(confirm)) {
			action.addActionError(action.getText("account.password.confirm.mismatch"));
			return;
		}

		// 旧パスワードが異なる場合はエラー
		//Integer count = (Integer)dao.selectObject(Query.existUser.name(), username);
		Integer count = 0;

		AccountVO vo = new PasswordUpdateVO();
		vo.setOldPassword(oldPassword);
		vo.setPassword(password);
		vo.setConfirm(confirm);
		vo.setUsername(username);

		try {
			count = accountService.updatePassword(vo);
		} catch (Exception e) {
			action.addActionError(action.getText("account.password.update.failure"));
			//throw new ValidationException(e.getMessage());
		}
	}

	@Autowired
	@Setter
	private AccountService accountService;

	public enum Query {
		existUser ,
	}
}
