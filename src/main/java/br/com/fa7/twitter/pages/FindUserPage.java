package br.com.fa7.twitter.pages;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.twitter.business.MessageBusiness;
import br.com.fa7.twitter.entities.Message;
import br.com.fa7.twitter.pages.base.PageBase;

public class FindUserPage extends PageBase {
	
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private MessageBusiness messageBusiness;
	
	private String msg;
	
	public FindUserPage() {
		
		Label lbSize = new Label("lbSize",  messageBusiness.size()+"");
		add(lbSize);
		
		List<Message> listMessage = messageBusiness.listAll();
		
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
