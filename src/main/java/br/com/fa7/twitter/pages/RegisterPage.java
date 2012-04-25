package br.com.fa7.twitter.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.twitter.business.UserBusiness;
import br.com.fa7.twitter.business.exception.BusinessException;
import br.com.fa7.twitter.entities.User;

public class RegisterPage extends WebPage {
	
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private UserBusiness userBusiness;
	
	public RegisterPage() {
		this(new User());
	}
	
	@SuppressWarnings("rawtypes")
	public RegisterPage(final User newUser) {
		
		Form<User> form = new Form<User>("form"){
			private static final long serialVersionUID = 1L;

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
		
		
		add(
			form
				.add(new TextField<String>("login").setRequired(true))
				.add(new PasswordTextField("password").setRequired(true))
				.add(new TextField<String>("email").setRequired(true))
				.add(new TextField<String>("name").setRequired(true))
				.setDefaultModel(new CompoundPropertyModel<User>(newUser))
				.add(new FeedbackPanel("feedback"))
	    );
		
		add(new Link("lkLogin") {
			private static final long serialVersionUID = 1L;
			public void onClick() {
				setResponsePage(new LoginPage());
			}
		});
		
	}
	
	public static Link<Void> link(String id) {
		return new BookmarkablePageLink<Void>(id, RegisterPage.class);
	}
}
