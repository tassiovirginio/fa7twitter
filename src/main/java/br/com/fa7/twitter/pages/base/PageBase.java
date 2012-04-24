package br.com.fa7.twitter.pages.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.twitter.business.UserBusiness;
import br.com.fa7.twitter.entities.User;
import br.com.fa7.twitter.pages.FindUserPage;
import br.com.fa7.twitter.pages.LoginPage;
import br.com.fa7.twitter.pages.PrincipalPage;
import br.com.fa7.twitter.pages.UserMessagePage;

public class PageBase extends WebPage {
	private static final long serialVersionUID = 1L;
	
	protected User loggedUser;
	
	@SpringBean
	private UserBusiness userBusiness;

	@SuppressWarnings({ "rawtypes", "serial" })
	public PageBase() {
		
		Object obj = getSession().getAttribute("loggedUser");
		if(obj == null)
			setResponsePage(new LoginPage());
		
		this.loggedUser = (User)obj;
		if(this.loggedUser == null)
			setResponsePage(new LoginPage());
		
		setDefaultModel(new CompoundPropertyModel(loggedUser));
		
		Link lkUserMessage = UserMessagePage.link("lkUserMessage", loggedUser);
		add(lkUserMessage.add(new Label("login")));
		
		Link lkHome = PrincipalPage.link("lkHome");
		add(lkHome);
		
		Link lkFindUser = FindUserPage.link("lkFindUser");
		add(lkFindUser);
		
		Link lkLogoff = new Link("lkLogoff"){
			public void onClick() {
				getSession().setAttribute("loggedUser", null);
				setResponsePage(new LoginPage());
			}
		};
		add(lkLogoff);
		
	}
}
