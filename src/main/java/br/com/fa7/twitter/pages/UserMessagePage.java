package br.com.fa7.twitter.pages;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
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
	
	public UserMessagePage(User donoDaPagina) {
		this.caraDessaPagina = donoDaPagina;
		this.initializeComponents();
	}
	
	private void initializeComponents(){
		Label lbUserNameHeader = new Label("lbUserNameHeader", caraDessaPagina.getName());
		add(lbUserNameHeader);
		
		Label lbSeguindoCount = new Label("lbSeguindoCount", caraDessaPagina.getListFollowing().size() + "");
		add(lbSeguindoCount);
		
		Form form = new Form("form");
		
		Button btnSeguir = new Button("btnSeguir") {
			public void onSubmit() {
				loggedUser.getListFollowing().add(caraDessaPagina);
				userBusiness.save(loggedUser);
				setResponsePage(new UserMessagePage(caraDessaPagina));
			}
		};
		form.add(btnSeguir);
		
		Button btnAbandonar = new Button("btnAbandonar"){
			public void onSubmit() {
				loggedUser.getListFollowing().remove(caraDessaPagina);
				userBusiness.save(loggedUser);
				setResponsePage(new UserMessagePage(caraDessaPagina));
			}
		};
		form.add(btnAbandonar);
		
		add(form);
		
		if ((loggedUser.getListFollowing() != null) && (loggedUser.getListFollowing().contains(caraDessaPagina))) {
			btnAbandonar.setVisible(true);
			btnSeguir.setVisible(false);
		} else {
			btnSeguir.setVisible(true);
			btnAbandonar.setVisible(false);
		}

		List<Message> listMessage = messageBusiness.loadByUser(caraDessaPagina);
		Label lbSize = new Label("lbSize", String.valueOf(listMessage.size()));
		add(lbSize);
		Label lbUserName = new Label("lbUserName", caraDessaPagina.getName());
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
