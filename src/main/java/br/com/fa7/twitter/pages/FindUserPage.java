package br.com.fa7.twitter.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.twitter.business.UserBusiness;
import br.com.fa7.twitter.entities.User;
import br.com.fa7.twitter.pages.base.PageBase;

public class FindUserPage extends PageBase {
	
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private UserBusiness userBusiness;
	
	private String busca;
	
	private List<User> users = new ArrayList<User>();
	
	public FindUserPage(List<User> userList) {
		
		users = userList;
		
		Form form = new Form("form"){
			protected void onSubmit() {				
				users = userBusiness.findByName(busca);
				setResponsePage(new FindUserPage(users));
			};
		};
		
		form.add(new TextField<String>("tfBusca",new PropertyModel(this,"busca")));
		
		add(form);
		
		add(new Label("lbSize",users.size()+""));
		
		ListView<User> listView = new ListView<User>("lvListUsers",users) {
			@Override
			protected void populateItem(ListItem<User> item) {
				
				final User user = (User)item.getModelObject();
				
//				item.setModel(new CompoundPropertyModel(message));
				Link<User> lkUserMessage = new Link<User>("lkname") {
					public void onClick() {
//						User user = getModelObject();
						setResponsePage(new UserMessagePage(user));
					}
				};
//				lkUserMessage.setModelObject(user);
				lkUserMessage.add(new Label("name", user.getName()));
				item.add(lkUserMessage);
			}
			
		};
		
		add(listView);
		
	}

}
