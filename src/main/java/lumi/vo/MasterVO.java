package lumi.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * コードマスター情報のValueObject。
 * @author koji.azuma
 *
 */
@Data
public class MasterVO implements Serializable {
	/** カテゴリ名 */
	private String category;
	/** キー名 */
	private String code;
	/** 値 */
	private String value;
}
