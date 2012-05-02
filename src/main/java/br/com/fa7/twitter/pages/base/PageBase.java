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
import br.com.fa7.twitter.pages.ProfilePage;

public class PageBase extends WebPage {
	private static final long serialVersionUID = 1L;
	
	protected User loggedUser;
	
	@SpringBean
	private UserBusiness userBusiness;

	@SuppressWarnings({ "rawtypes", "serial" })
	public PageBase() {
		
		Object obj = getSession().getAttribute("loggedUser");
		this.loggedUser = (User)obj;
		
		setDefaultModel(new CompoundPropertyModel<User>(loggedUser));
		
		add(ProfilePage.link("lkUserMessage", loggedUser).add(
				new Label("login")));
		
		add(PrincipalPage.link("lkHome").setVisible(estaLogado()));
		
		add(FindUserPage.link("lkFindUser"));
		
		add(new Link("lkLogoff") {
			public void onClick() {
				getSession().invalidateNow();
				setResponsePage(new LoginPage());
			}
		}.setVisible(estaLogado()));
		
		add(LoginPage.link("lkLogin").setVisible(!estaLogado()));
	}

	public boolean estaLogado() {
		return loggedUser != null;
	}
}
