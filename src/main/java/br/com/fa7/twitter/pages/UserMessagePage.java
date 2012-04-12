package br.com.fa7.twitter.pages;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.twitter.business.MessageBusiness;
import br.com.fa7.twitter.business.UserBusiness;
import br.com.fa7.twitter.entities.Message;
import br.com.fa7.twitter.entities.User;
import br.com.fa7.twitter.pages.base.PageBase;

public class UserMessagePage extends PageBase {
	
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private MessageBusiness messageBusiness;
	
	@SpringBean
	private UserBusiness userBusiness;
	
	private User caraDessaPagina;
	
	public UserMessagePage() {
		
		caraDessaPagina = userBusiness.findById(2l);
		
		List<Message> listMessage = messageBusiness.loadByUser(loggedUser);
		Label lbSize = new Label("lbSize", String.valueOf(listMessage.size()));
		add(lbSize);
		Label lbUserName = new Label("lbUserName", loggedUser.getName());
		add(lbUserName);
		Label lbUserNameHeader = new Label("lbUserNameHeader", loggedUser.getName());
		add(lbUserNameHeader);
		
		Form form = new Form("form"){
			protected void onSubmit() {
				loggedUser.getListFollower().add(caraDessaPagina);
				userBusiness.save(loggedUser);
				//setResponsePage(new UserMessagePage());
			};
		};		
		add(form);
		
		String folowingMsg = "Segue aew!";
		
		if ((loggedUser.getListFollower() != null) && (loggedUser.getListFollower().contains(caraDessaPagina))) {
			folowingMsg = "Tá seguindo";
		}
		Label lbFolowing = new Label("lbFolowing", folowingMsg);
		form.add(lbFolowing);
		
//		Button btnSeguir = new Button("btnSeguir");
//		btnSeguir.setVisible(!loggedUser.isFollow());
//		add(btnSeguir);
		
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
