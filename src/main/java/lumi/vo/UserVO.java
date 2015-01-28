/**
 *
 */
package lumi.vo;

import lombok.Data;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * サインアップ用ユーザ情報VO。
 * @author A-pZ
 *
 */
@Validations(
	requiredStrings={
		@RequiredStringValidator(fieldName="username",trim=true , key="signup.userid.required" ) ,
		@RequiredStringValidator(fieldName="password",trim=true,key="signup.password.required") ,
		@RequiredStringValidator(fieldName="confirm",trim=true,key="signup.confirm.required") ,
	} ,

	stringLengthFields={
		@StringLengthFieldValidator(fieldName="username",trim=true,maxLength="64",key="signup.userid.length"),
		@StringLengthFieldValidator(fieldName="password",trim=true,maxLength="64",key="signup.password.length"),
		@StringLengthFieldValidator(fieldName="confirm",trim=true,maxLength="64",key="signup.confirm.length"),
	} ,

	customValidators={
		// TODO ユーザIDとパスワードの一致チェック
		// TODO パスワードの複雑性チェック
	}
)
@Data
public class UserVO {

	private String username;

	private String password;

	private String confirm;

}
