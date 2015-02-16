/**
 *
 */
package lumi.vo;

import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * パスワード更新VO。AccountVOをそのまま利用する。
 * @author A-pZ
 *
 */
@Validations(
	requiredStrings = {
		@RequiredStringValidator(fieldName="oldPassword" , key="account.password.oldPassword.required"),
		@RequiredStringValidator(fieldName="password" , key="account.password.password.required"),
		@RequiredStringValidator(fieldName="confirm" , key="account.password.confirm.required"),
	} ,

	stringLengthFields = {
		@StringLengthFieldValidator(fieldName="oldPassword" , key="account.password.oldPassword.length" , minLength="1" , maxLength="64"),
		@StringLengthFieldValidator(fieldName="password" , key="account.password.password.length" , minLength="1" , maxLength="64"),
		@StringLengthFieldValidator(fieldName="confirm" , key="account.password.confirm.length" , minLength="1" , maxLength="64"),
	} ,

	regexFields = {
		@RegexFieldValidator(fieldName="oldPassword" , key="account.password.oldPassword.regex" , regex="[ -~]*") ,
		@RegexFieldValidator(fieldName="password" , key="account.password.password.regex" , regex="[ -~]*") ,
		@RegexFieldValidator(fieldName="confirm" , key="account.password.confirm.regex" , regex="[ -~]*") ,
	} ,

	customValidators = {
		@CustomValidator(type="passwordChange")
	}
)
public class PasswordUpdateVO extends AccountVO {

}
