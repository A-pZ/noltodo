/**
 *
 */
package lumi.vo;

import lombok.Data;

/**
 * Twitter -> noltodo認証連携用DTO。
 * @author A-pZ
 *
 */
@Data
public class AuthChainDTO {

	public AuthChainDTO() {
	}

	public AuthChainDTO(String twitterId) {
		this.twitterId = twitterId;
	}

	private String twitterId;
	private String displayName;
	private String username;
	private String password;
}
