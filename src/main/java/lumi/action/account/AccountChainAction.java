package lumi.action.account;

import lombok.extern.slf4j.Slf4j;
import lumi.action.LumiActionSupport;
import lumi.misc.SessionKeys;
import lumi.service.AccountService;
import lumi.service.TwitterChainService;
import lumi.vo.AccountVO;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import twitter4j.Twitter;

import com.opensymphony.xwork2.ActionSupport;

/**
 * アカウント管理の表示Actionクラス。
 *
 * @author A-pZ ( Serendipity 3 ./ as sundome goes by. )
 */
@Namespace("/account")
@ParentPackage("lumi-default")
@Results({
	// location属性に指定したhtmlファイル名は、/WEB-INF/content 以下からの相対パス。
	@Result(name = ActionSupport.SUCCESS, location = "account/menu" , type="thymeleaf-spring"),
	@Result(name = AccountChainAction.REDIRECT_STRING , location= "/twitter/signin" , type="redirect" ) ,
	@Result(name = ActionSupport.ERROR , location = "account/menu" , type="thymeleaf-spring")
})
@Controller
@Scope("prototype")
@Slf4j
public class AccountChainAction extends LumiActionSupport {

	/**
	 * デフォルトアクション。
	 */
	@Action("twitterChain")
	public String start() throws Exception {

		getSession().put(SessionKeys.TWITTER_ACCOUNT_CHAIN_REQUEST_KEY.name(), true);

		return REDIRECT_STRING;
	}

	/**
	 * Twitter認証完了後に、ユーザIDとTwitterのIDをひもづける。
	 * @return
	 * @throws Exception
	 */
	@Action("afterChain")
	public String afterChain() throws Exception {
		boolean chain = (Boolean)getSession().get(SessionKeys.TWITTER_ACCOUNT_CHAIN_REQUEST_KEY.name());

		if (! chain ) {
			addActionError(getText("twitter.chain.error"));
			return ERROR;
		}

		getSession().remove(SessionKeys.TWITTER_ACCOUNT_CHAIN_REQUEST_KEY.name());

		// Twitterインスタンスの取得
		Twitter twitter = (Twitter) getSession().get(SessionKeys.TWITTER4J_INSTANCE.name());
		if ( twitter == null ) {
			addActionError(getText("twitter.chain.no.twitter.instance"));
			return ERROR;
		}

		// Twitter認証情報の登録
		service.registerExistUserFromTwitter(twitter, getLoginUsername());

		//
		accountService.setUserId(getLoginUsername());
		AccountVO vo = accountService.getAccountInfo();
		vo.setActivate(2);

		getSession().put(SessionKeys.ACCOUNT.name(),vo);

		addActionMessage(getText("twitter.chain.success"));
		return SUCCESS;
	}

	public static final String REDIRECT_STRING = "redirect";

	@Autowired
	TwitterChainService service;

	@Autowired
	AccountService accountService;
}
