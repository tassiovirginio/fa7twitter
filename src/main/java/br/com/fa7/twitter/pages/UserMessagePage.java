package br.com.fa7.twitter.pages;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.twitter.business.MessageBusiness;
import br.com.fa7.twitter.entities.Message;
import br.com.fa7.twitter.entities.User;
import br.com.fa7.twitter.pages.base.PageBase;

public class UserMessagePage extends PageBase {
	
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private MessageBusiness messageBusiness;
	
	private User user;
	
	public UserMessagePage() {
		this(null);
	}
	
	public UserMessagePage(User followedUser) {
		
		user = followedUser;
		
		if (user == null){
			user = loggedUser;
		}
		
		List<Message> listMessage = messageBusiness.loadByUser(user);
		Label lbSize = new Label("lbSize",  String.valueOf(listMessage.size()));
		add(lbSize);
		Label lbUserName = new Label("lbUserName",  user.getName());
		add(lbUserName);
		
		ListView<Message> listView = new ListView<Message>("lvListMsg",listMessage) {
			@Override
			protected void populateItem(ListItem<Message> item) {
				
				final Message message = (Message)item.getModelObject();
				
//				item.setModel(new CompoundPropertyModel(message));
				
				item.add(new Label("msg", message.getMsg()));
			}
			
		};
		
		add(listView);
		
	}

}
