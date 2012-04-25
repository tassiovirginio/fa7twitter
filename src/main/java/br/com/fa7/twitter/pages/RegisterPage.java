package br.com.fa7.twitter.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.twitter.business.UserBusiness;
import br.com.fa7.twitter.business.exception.BusinessException;
import br.com.fa7.twitter.entities.User;

public class RegisterPage extends WebPage {
	
	private static final long serialVersionUID = 1L;
	
	private User newUser;
	
	@SpringBean
	private UserBusiness userBusiness;
	
	public RegisterPage() {
		this(new User());
	}
	
	public RegisterPage(User user) {
		
		newUser = user;
		
		Form form = new Form("form"){
			
			protected void onSubmit() {
				try {
					userBusiness.newUser(newUser);
					info("Usuario Criado Com Sucesso.");
					setResponsePage(new LoginPage());
				} catch (BusinessException e) {
					error(e.getMessage());
					setResponsePage(new RegisterPage(newUser));
				}
			};
		};
		add(form);
		
		form.add(new TextField<String>("login").setRequired(true));
		form.add(new PasswordTextField("password").setRequired(true));
		form.add(new TextField<String>("email").setRequired(true));
		form.add(new TextField<String>("name").setRequired(true));
		
		form.setDefaultModel(new CompoundPropertyModel(newUser));
		
		form.add(new FeedbackPanel("feedback"));
		
		Link link = new Link("lkLogin") {
			@Override
			public void onClick() {
				setResponsePage(new LoginPage());
			}
		};
		add(link);
		
	}
	
	public static Link<Void> link(String id) {
		Link<Void> result = new BookmarkablePageLink<Void>(id, RegisterPage.class);
		return result;
	}
}
