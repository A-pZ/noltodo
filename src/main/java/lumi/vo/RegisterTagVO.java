/**
 *
 */
package lumi.vo;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 登録時に使うTagVO。
 * @author A-pZ
 *
 */
@Validations(
	requiredStrings = {
		@RequiredStringValidator(fieldName="display" , key="tag.display.required")
	}
)
public class RegisterTagVO extends TagVO {

}
