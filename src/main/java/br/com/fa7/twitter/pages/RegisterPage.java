package br.com.fa7.twitter.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.twitter.business.UserBusiness;
import br.com.fa7.twitter.entities.User;

public class RegisterPage extends WebPage {
	
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String senha;
	private String email;
	private String nome;
	
	@SpringBean
	private UserBusiness userBusiness;
	
	public RegisterPage() {
		
		TextArea<String> tLogin = new TextArea<String>("tLogin", new PropertyModel(this,"login"));
		TextArea<String> tSenha = new TextArea<String>("tSenha", new PropertyModel(this,"senha"));
		TextArea<String> tEmail = new TextArea<String>("tEmail", new PropertyModel(this,"email"));
		TextArea<String> tNome = new TextArea<String>("tNome", new PropertyModel(this,"nome"));
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
		
	}
	
	public static Link<Void> link(String id) {
		Link<Void> result = new BookmarkablePageLink<Void>(id, RegisterPage.class);
		return result;
	}
}
