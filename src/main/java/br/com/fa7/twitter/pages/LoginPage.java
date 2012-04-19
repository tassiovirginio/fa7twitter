package br.com.fa7.twitter.pages;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.twitter.business.MessageBusiness;
import br.com.fa7.twitter.entities.Message;
import br.com.fa7.twitter.entities.User;
import br.com.fa7.twitter.pages.base.PageBase;

public class LoginPage extends PageBase {
	
	private static final long serialVersionUID = 1L;
	
	
	
	private String login;
	private String senha;
	
	public LoginPage() {
		TextArea<String> tLogin = new TextArea<String>("tLogin", new PropertyModel(this,"login"));
		TextArea<String> tSenha = new TextArea<String>("tSenha", new PropertyModel(this,"senha"));
		Form form = new Form("form"){
			
			//Fazer validação
			
			protected void onSubmit() {
				
				setResponsePage(new UserMessagePage(login));
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
