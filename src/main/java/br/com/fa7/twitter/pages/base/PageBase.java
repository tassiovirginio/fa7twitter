package br.com.fa7.twitter.pages.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.twitter.business.UserBusiness;
import br.com.fa7.twitter.entities.User;
import br.com.fa7.twitter.pages.FindUserPage;
import br.com.fa7.twitter.pages.PrincipalPage;
import br.com.fa7.twitter.pages.UserMessagePage;

public class PageBase extends WebPage {
	private static final long serialVersionUID = 1L;
	
	protected User loggedUser;
	
	@SpringBean
	private UserBusiness userBusiness;

	@SuppressWarnings({ "rawtypes", "serial" })
	public PageBase() {
		this.loggedUser = userBusiness.findById(1l);

		setDefaultModel(new CompoundPropertyModel(loggedUser));
		add(new Label("name"));
		Link lkFindUser = FindUserPage.link("lkFindUser");
		add(lkFindUser);
		
		Link lkUserMessage = UserMessagePage.link("lkUserMessage", loggedUser);
		add(lkUserMessage);
		
		Link lkHome = PrincipalPage.link("lkHome");
		add(lkHome);
		
		/*Link lkSobre = PrincipalPage.link("lkSobre");
		add(lkSobre);*/
	}
}
