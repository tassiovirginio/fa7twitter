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
	
	private String login;
	private String senha;
	
	@SpringBean
	private UserBusiness userBusiness;
	
	public LoginPage() {
		
		TextField<String> tLogin = new TextField<String>("login", new PropertyModel(this,"login"));
		tLogin.setRequired(true);
		
		PasswordTextField tSenha = new PasswordTextField("senha", new PropertyModel(this,"senha"));
		tSenha.setRequired(true);
		
		Form form = new Form("form"){
			
			//Fazer validação
			protected void onSubmit() {
				User user;
				try {
					user = userBusiness.login(login, senha);
					getSession().setAttribute("loggedUser", user);
					setResponsePage(new PrincipalPage());
				} catch (BusinessException e) {
					error(e.getMessage());
				}
			};
		};
		add(form);
		
		
		form.add(tLogin);
		form.add(tSenha);
		
		Link lkCadastro = RegisterPage.link("lkCadastro");
		add(lkCadastro);
		
		form.add(new FeedbackPanel("feedback"));
	}
	
	public static Link<Void> link(String id) {
		Link<Void> result = new BookmarkablePageLink<Void>(id, LoginPage.class);
		return result;
	}
}
