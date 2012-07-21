package br.com.fa7.twitter.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
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
	
	public FindUserPage() {
		initComponents();
	}

	public FindUserPage(List<User> userList) {
		users = userList;
		initComponents();
	}

	private void initComponents() {
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
				Link<Void> lkUser = ProfilePage.link("lkname", user);
		        add(lkUser);
		        lkUser.add(new Label("login", user.getLogin()));
				item.add(lkUser);
			}
			
		};
		add(listView);
	}

	public static Link<Void> link(String id) {
		Link<Void> result = new BookmarkablePageLink<Void>(id, FindUserPage.class);
		return result;
	}

}
