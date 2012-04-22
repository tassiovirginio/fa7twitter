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

public class LoginPage extends WebPage {
	
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String senha;
	
	@SpringBean
	private UserBusiness userBusiness;
	
	public LoginPage() {
		
		TextArea<String> tLogin = new TextArea<String>("tLogin", new PropertyModel(this,"login"));
		TextArea<String> tSenha = new TextArea<String>("tSenha", new PropertyModel(this,"senha"));
		
		Form form = new Form("form"){
			
			//Fazer validação
			
			protected void onSubmit() {
				User user = userBusiness.validateLogin(login, senha);
				if(user != null){
					setResponsePage(new UserMessagePage(user));
				}else{
					
				}
			};
		};
		add(form);
		
		
		form.add(tLogin);
		form.add(tSenha);
		
		Link lkCadastro = PrincipalPage.link("lkCadastro");
		add(lkCadastro);
	}
	
	public static Link<Void> link(String id) {
		Link<Void> result = new BookmarkablePageLink<Void>(id, LoginPage.class);
		return result;
	}
}
