/**
 *
 */
package lumi.validator;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import lumi.action.LumiActionSupport;
import lumi.service.UserRegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * パスワード検証用拡張バリデータ。
 * @author A-pZ
 *
 */
@Log4j2
@Service
@Scope("singleton")
public class PasswordVerifyValidator extends FieldValidatorSupport {

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

		// ユーザ名
		String username = (String) getFieldValue("username", paramObject);
		// パスワード
		String password = (String) getFieldValue("password", paramObject);
		// 確認パスワード
		String confirm = (String) getFieldValue("confirm", paramObject);

		// もしパスワードの中にユーザ名が含まれていた場合はエラー
		if (password.contains(username)) {
			action.addActionError(action.getText("signup.password.contain.userid"));
			return;
		}

		// パスワードと確認パスワードが不一致の場合はエラー
		if (!password.equals(confirm)) {
			action.addActionError(action.getText("signup.password.confirm.mismatch"));
			return;
		}

		// すでに登録済みのユーザ名の場合はエラー
		//Integer count = (Integer)dao.selectObject(Query.existUser.name(), username);
		Integer count = 0;

		try {
			count = userRegisterService.existUserSearch(username);
		} catch (Exception e) {
			throw new ValidationException(e.getMessage());
		}
		if ( count > 0 ) {
			action.addActionError(action.getText("signup.userid.exists"));
			return;
		}
	}

	@Autowired
	@Setter
	private UserRegisterService userRegisterService;

	public enum Query {
		existUser ,
	}
}
