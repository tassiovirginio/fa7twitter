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
		
		Form<String> form = new Form<String>("form"){
			private static final long serialVersionUID = 1L;
			
			protected void onSubmit() {
				if(msg!=null && !msg.isEmpty()){
					Message message = new Message(msg, loggedUser);
					messageBusiness.save(message);
				}
				setResponsePage(new PrincipalPage());
			};
		};
		add(form);
		
		form.add(new TextArea<String>("taMsg",new PropertyModel<String>(this,"msg")));
		
		List<Message> listMessage = messageBusiness.loadByUser(loggedUser);
		
		Set<User> following = loggedUser.getFollowing();
		
		for (User user : following) {
			listMessage.addAll(messageBusiness.loadByUser(user));
		}		

		add(new Label("lbUserName", loggedUser.getName()));

		add(new Label("lbSize", String.valueOf(listMessage.size())));
		
		ListView<Message> listView = new ListView<Message>("lvMsg", listMessage) {
			private static final long serialVersionUID = 1L;
			
			protected void populateItem(ListItem<Message> item) {
				final Message message = (Message)item.getModelObject();
				
				item.add(new Label("msg", message.getMsg()));
				Link<Void> link = ProfilePage.link("lkUser", message.getUser());
				link.add(new Label("login", "@" + message.getUser().getLogin()));
				item.add(link);
				
			}
		};
		add(listView);
		
	}

	public static Link<Void> link(String id) {
		return new BookmarkablePageLink<Void>(id, PrincipalPage.class);
	}

}
