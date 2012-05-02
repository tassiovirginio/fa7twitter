package br.com.fa7.twitter.pages;


import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.twitter.business.UserBusiness;
import br.com.fa7.twitter.business.exception.BusinessException;
import br.com.fa7.twitter.entities.User;
import br.com.fa7.twitter.pages.base.PageBase;

public class LoginPage extends PageBase {
	
	private static final long serialVersionUID = 1L;
	
	private String login,senha;
	
	@SpringBean
	private UserBusiness userBusiness;
	
	public LoginPage() {
		
		Form<String> form = new Form<String>("form"){
			private static final long serialVersionUID = -533938191530567861L;
			protected void onSubmit() {
				try {
					User user = userBusiness.login(login, senha);
					getSession().setAttribute("loggedUser", user);
					setResponsePage(new PrincipalPage());
				} catch (BusinessException e) {
					error(e.getMessage());
				}
			};
		};
		add(form);
		
		form.add(new TextField<String>("login", new PropertyModel<String>(this,"login")).setRequired(true));
		
		form.add(new PasswordTextField("senha", new PropertyModel<String>(this,"senha")).setRequired(true));
		
		form.add(new FeedbackPanel("feedback"));
		
		add(RegisterPage.link("lkCadastro"));
		
	}
	
	public static Link<Void> link(String id) {
		return new BookmarkablePageLink<Void>(id, LoginPage.class);
	}
}
