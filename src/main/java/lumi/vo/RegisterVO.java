/**
 *
 */
package lumi.vo;

import java.sql.Timestamp;

import lombok.Data;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * タスク登録VO。
 *
 * @author A-pZ
 *
 */
@Validations(
	requiredFields={
		@RequiredFieldValidator(
			fieldName="limitdate" , key="register.limitdate.require"
		)
	} ,
	requiredStrings={
		@RequiredStringValidator(
			fieldName="name" , trim=true , key="register.name.require"
		) ,
		@RequiredStringValidator(
			fieldName="task" , trim=true , key="register.task.require"
		) ,
		@RequiredStringValidator(
			fieldName="jobid" , trim=true , key="register.jobid.require"
		) ,
	} ,
	stringLengthFields={
		@StringLengthFieldValidator(
			fieldName="name" , trim=true , maxLength="64" , key="register.name.length"
		) ,
		@StringLengthFieldValidator(
			fieldName="task" , trim=true , maxLength="2048" , key="register.task.length"
		) ,
	} ,
	customValidators={

	}
)

@Data
public class RegisterVO {
	/** タスクID */
	private int id;
	/** タスク名 */
	private String name;
	/** タスク本文(markdown変換前) */
	private String task;
	/** 登録（更新）日時 */
	private Timestamp writedate;
	/** ユーザID */
	private String userid;
	/** 職業・特化ID */
	private String jobid;
	/** 職業名 */
	private String jobname;
	/** 期限 */
	private Timestamp limitdate;
	/** タスクステータス */
	private String status;
	/** 公開/非公開フラグ */
	private int publish;
}
