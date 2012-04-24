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

public class RegisterPage extends WebPage {
	
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String senha;
	private String email;
	private String nome;
	
	@SpringBean
	private UserBusiness userBusiness;
	
	public RegisterPage() {
		
		TextField tLogin = new TextField("tLogin", new PropertyModel(this,"login"));
		tLogin.setRequired(true);
		PasswordTextField tSenha = new PasswordTextField("tSenha", new PropertyModel(this,"senha"));
		tSenha.setRequired(true);
		TextField<String> tEmail = new TextField<String>("tEmail", new PropertyModel(this,"email"));
		tEmail.setRequired(true);
		TextField<String> tNome = new TextField<String>("tNome", new PropertyModel(this,"nome"));
		tNome.setRequired(true);
		
		Form form = new Form("form"){
			
			//Fazer validação
			
			protected void onSubmit() {
				setResponsePage(new LoginPage());
			};
		};
		add(form);
		
		
		form.add(tLogin);
		form.add(tSenha);
		form.add(tEmail);
		form.add(tNome);
		
		form.add(new FeedbackPanel("feedback"));
		
	}
	
	public static Link<Void> link(String id) {
		Link<Void> result = new BookmarkablePageLink<Void>(id, RegisterPage.class);
		return result;
	}
}
