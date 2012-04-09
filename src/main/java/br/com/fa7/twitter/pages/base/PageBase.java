package br.com.fa7.twitter.pages.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.twitter.business.UserBusiness;
import br.com.fa7.twitter.entities.User;
import br.com.fa7.twitter.pages.PrincipalPage;

public class PageBase extends WebPage {
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	@SpringBean
	private UserBusiness userBusiness;

	@SuppressWarnings({ "rawtypes", "serial" })
	public PageBase() {
		
		this.user = userBusiness.findById(1l);
		
		setDefaultModel(new CompoundPropertyModel(user));
		
		add(new Label("name"));
		
		Link lkSobre = new Link("lkSobre") {
			@Override
			public void onClick() {
				setResponsePage(new PrincipalPage());
			}
		};
		add(lkSobre);
		
	}
	
}
