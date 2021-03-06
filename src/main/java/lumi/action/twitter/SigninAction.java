package lumi.action.twitter;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import lombok.extern.log4j.Log4j2;
import lumi.action.LumiActionSupport;
import lumi.misc.SessionKeys;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 1レコードの表示Actionクラス。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/twitter")
@ParentPackage("lumi-default")
@Results({
	@Result(name=ActionSupport.SUCCESS , location="%{authenticationUrl}" , type="redirect")
})
@Controller
@Scope("prototype")
@Log4j2
public class SigninAction extends LumiActionSupport {

	/**
	 * デフォルトアクション。
	 */
	@Action("signin")
	public String signin() throws Exception {

		ConfigurationBuilder config = new ConfigurationBuilder();
		config.setOAuthConsumerKey(getText("twitter4j.oauth.consumerKey"))
		      .setOAuthConsumerSecret(getText("twitter4j.oauth.consumerSecret"))
		      .setHttpConnectionTimeout(100000);

		val twitter = new TwitterFactory(config.build()).getInstance();

		log.debug("tw4j.getInstance ->");
		getSession().put(SessionKeys.TWITTER4J_INSTANCE.name(), twitter );

		val callbackUrl = servletRequest.getRequestURL();
		val idx = callbackUrl.lastIndexOf("/");
		callbackUrl.replace(idx, callbackUrl.length(), "").append("/callback");
		log.debug(" - callbackUrl : " + callbackUrl);

		val reqToken = twitter.getOAuthRequestToken(callbackUrl.toString());
		getSession().put(SessionKeys.REQUEST_TOKEN.name(), reqToken );
		log.debug(" - requestToken: " + reqToken);

		authenticationUrl = reqToken.getAuthenticationURL();

		// Result値。ActionSupportの定数値を返すか、別途定義した値を返すこと。
		// この値は@Resultで指定したname値となる。
		return SUCCESS;
	}

	@Getter @Setter
	private String authenticationUrl;
}
