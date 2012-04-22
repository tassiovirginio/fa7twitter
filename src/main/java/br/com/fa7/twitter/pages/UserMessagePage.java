package br.com.fa7.twitter.pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
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
	
	private User userPage;

	private boolean isPaginaPessoal;
	
	public UserMessagePage(PageParameters parameters) {
		String donoDaPagina = parameters.get("login").toString();
		this.userPage = userBusiness.findByLogin(donoDaPagina);
		isPaginaPessoal = (userPage.equals(loggedUser));
		this.initializeComponents();
	}

//	public UserMessagePage(String userLogin) {
//		this.userPage = userBusiness.findByLogin(userLogin);
//		isPaginaPessoal = (userPage.equals(loggedUser));
//		this.initializeComponents();
//	}

	public UserMessagePage(User user) {
		if(loggedUser == null){
			loggedUser = user;
		}
		isPaginaPessoal = (user.equals(loggedUser));
		this.userPage = user;
		this.initializeComponents();
	}
	
	private void initializeComponents(){

		Label lbUserNameHeader = new Label("lbUserNameHeader", userPage.getName());
		add(lbUserNameHeader);
		
		Form form = new Form("form");
		
		Button btnSeguir = new Button("btnSeguir") {
			public void onSubmit() {

				loggedUser.getFollowing().add(userPage);
				
				System.out.println("[BEFORE SAVE] " + loggedUser.getName() +" tem :"+ messageBusiness.loadByUser(loggedUser).size() + " mensagens.");
				
				userBusiness.save(loggedUser);
				
				System.out.println("[AFTER SAVE] " + loggedUser.getName() +" tem :"+ messageBusiness.loadByUser(loggedUser).size() + " mensagens.");

				setResponsePage(new UserMessagePage(userPage));
			}
		};
		form.add(btnSeguir);
		
		Button btnAbandonar = new Button("btnAbandonar"){
			public void onSubmit() {
				loggedUser.getFollowing().remove(userPage);
				userBusiness.save(loggedUser);
				setResponsePage(new UserMessagePage(loggedUser));
			}
		};
		form.add(btnAbandonar);
		
		add(form);
		
		if (isPaginaPessoal) {
			btnSeguir.setVisible(false);
			btnAbandonar.setVisible(false);
		} else {
			if ((loggedUser.getFollowing() != null) && (loggedUser.getFollowing().contains(userPage))) {
				btnAbandonar.setVisible(true);
				btnSeguir.setVisible(false);
			} else {
				btnSeguir.setVisible(true);
				btnAbandonar.setVisible(false);
			}
		}

		Set<User> following = userPage.getFollowing();
		Set<User> followers = userPage.getFollowers();
		
		Label lbFollowingCount = new Label("lbFollowingCount", following.size() + "");
		add(lbFollowingCount);
		Label lbFollowingCount2 = new Label("lbFollowingCount2", following.size() + "");
		add(lbFollowingCount2);
		Label lbFollowersCount2 = new Label("lbFollowersCount2", followers.size() + "");
		add(lbFollowersCount2);
		Label lbUser = new Label("lbUser", userPage.getName());
		add(lbUser);				
		
		List<User> listaItens = new ArrayList<User>(following);
		ListView<User> listViewFollowing = new ListView<User>("lvListFollowing", listaItens) {
			@Override
			protected void populateItem(ListItem<User> item) {
				final User userFollowing = (User)item.getModelObject();
				item.add(new Label("name", userFollowing.getName()));
			}
		};
		
		add(listViewFollowing);
		
		List<Message> listMessage = messageBusiness.loadByUser(userPage);
		
		Label lbSize = new Label("lbSize", String.valueOf(listMessage.size()));
		add(lbSize);
		Label lbUserName = new Label("lbUserName", userPage.getName());
		add(lbUserName);		
		
		ListView<Message> listView = new ListView<Message>("lvListMsg", listMessage) {
			@Override
			protected void populateItem(ListItem<Message> item) {
				final Message message = (Message)item.getModelObject();
				item.add(new Label("msg", message.getMsg()));
			}
		};
		
		add(listView);
		
	}

	public static Link<Void> link(String id, final User user) {
		PageParameters params = new PageParameters();
		params.set("login", user.getLogin());
		Link<Void> result = new BookmarkablePageLink<Void>(id, UserMessagePage.class, params);
		return result;
	}
}
