package br.com.fa7.twitter.pages.base;

import java.util.ArrayList;

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

		Link lkFindUser = new Link("lkFindUser") {
			@Override
			public void onClick() {
				setResponsePage(new FindUserPage(new ArrayList<User>()));
			}
		};
		
		add(lkFindUser);
		
		Link lkUserMessage = new Link("lkUserMessage") {
			@Override
			public void onClick() {
				User tassio = userBusiness.findById(1l);
				setResponsePage(new UserMessagePage(tassio));
			}
		};
		
		add(lkUserMessage);
		
		Link lkHome = new Link("lkHome") {
			@Override
			public void onClick() {
				setResponsePage(new PrincipalPage());
			}
		};
		add(lkHome);
		
		Link lkSobre = new Link("lkSobre") {
			@Override
			public void onClick() {
				setResponsePage(new PrincipalPage());
			}
		};
		add(lkSobre);
		
		Link lkTiago = new Link("lkTiago") {
			@Override
			public void onClick() {
				User tiago = userBusiness.findById(2l);
				setResponsePage(new UserMessagePage(tiago));
			}
		};
		add(lkTiago);
	}
}
