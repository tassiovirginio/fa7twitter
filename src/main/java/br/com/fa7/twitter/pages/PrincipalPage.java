package br.com.fa7.twitter.pages;

import java.util.List;
import java.util.Set;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.twitter.business.MessageBusiness;
import br.com.fa7.twitter.entities.Message;
import br.com.fa7.twitter.entities.User;
import br.com.fa7.twitter.pages.base.PageBase;

public class PrincipalPage extends PageBase {
	
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private MessageBusiness messageBusiness;
	
	private String msg;
	
	public PrincipalPage() {
		if (loggedUser == null) {
			throw new RestartResponseAtInterceptPageException(LoginPage.class);
		}
		Form form = new Form("form"){
			
			protected void onSubmit() {
				
				Message message = new Message(msg, loggedUser);
				messageBusiness.save(message);
				setResponsePage(new PrincipalPage());
				
			};
		};
		add(form);
		
		TextArea<String> taMsg = new TextArea<String>("taMsg");
		taMsg.setModel(new PropertyModel(this,"msg"));
		form.add(taMsg);
		
		List<Message> listMessage = messageBusiness.loadByUser(loggedUser);
		
		// lista de mensagens de quem ele segue
		Set<User> following = loggedUser.getFollowing();
		for (User user : following) {
			listMessage.addAll(messageBusiness.loadByUser(user));
		}		
		
		Label lbUserName = new Label("lbUserName", loggedUser.getName());
		add(lbUserName);		

		Label lbSize = new Label("lbSize", String.valueOf(listMessage.size()));
		add(lbSize);
		
		ListView<Message> listView = new ListView<Message>("lvMsg", listMessage) {
			@Override
			protected void populateItem(ListItem<Message> item) {
				final Message message = (Message)item.getModelObject();
				item.add(new Label("msg", message.getMsg()));
//				item.add(new Label("userMsg", message.getUser().getName()));
//				item.add(new Label("login", "@" + message.getUser().getLogin()));
				
				
				Link link = UserMessagePage.link("lkUser", message.getUser());
				link.add(new Label("login", "@" + message.getUser().getLogin()));
				item.add(link);
				
//				Link link = new Link("lkUser") {
//					public void onClick() {
//						//TODO Implementar
//						System.out.println("Teste");
//					}
//				};
//				link.add(new Label("login", "@" + message.getUser().getLogin()));
				
//				item.add(link);
				
			}
		};
		
		add(listView);
		
	}

	public static Link<Void> link(String id) {
		Link<Void> result = new BookmarkablePageLink<Void>(id, PrincipalPage.class);
		return result;
	}

}
