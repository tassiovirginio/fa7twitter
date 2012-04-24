package br.com.fa7.twitter.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.twitter.business.UserBusiness;
import br.com.fa7.twitter.entities.User;

public class LoginPage extends WebPage {
	
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
				User user = userBusiness.validateLogin(login, senha);
				if(user != null){
					getSession().setAttribute("loggedUser", user);
					setResponsePage(new UserMessagePage(user));
				}else{
					
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
