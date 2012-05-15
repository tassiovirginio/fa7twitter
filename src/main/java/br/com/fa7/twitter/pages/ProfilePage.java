package br.com.fa7.twitter.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.RestartResponseAtInterceptPageException;
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

public class ProfilePage extends PageBase {
	
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private MessageBusiness messageBusiness;
	
	@SpringBean
	private UserBusiness userBusiness;
	
	private User viewingUser;

	private boolean isPaginaPessoal;
	
	public ProfilePage(PageParameters parameters) {
		String donoDaPagina = parameters.get("login").toString();
		this.viewingUser = userBusiness.findByLogin(donoDaPagina);
		if (viewingUser == null) {
			throw new RestartResponseAtInterceptPageException(ErrorPage.class);
		}
		isPaginaPessoal = (viewingUser.equals(loggedUser));
		this.initializeComponents();
	}
	
	public ProfilePage(User user) {
		isPaginaPessoal = (user.equals(loggedUser));
		this.viewingUser = user;
		this.initializeComponents();
	}	

	private void initializeComponents() {

		Label lbUserNameHeader = new Label("lbUserNameHeader", viewingUser.getName());
		add(lbUserNameHeader);
		
		Form form = new Form("form");
		
		Button btnSeguir = new Button("btnSeguir") {
			public void onSubmit() {
				userBusiness.follow(loggedUser, viewingUser);
				setResponsePage(new ProfilePage(userBusiness.findById(viewingUser.getId())));
			}
		};
		form.add(btnSeguir);
		
		Button btnAbandonar = new Button("btnAbandonar"){
			public void onSubmit() {
				userBusiness.unfollow(loggedUser, viewingUser);
				setResponsePage(new ProfilePage(userBusiness.findById(viewingUser.getId())));
			}
		};
		form.add(btnAbandonar);
		
		add(form);
		
		if (isPaginaPessoal || !estaLogado()) {
			btnSeguir.setVisible(false);
			btnAbandonar.setVisible(false);
		} else {
			if ((loggedUser.isFollowing(viewingUser))) {
				btnAbandonar.setVisible(true);
				btnSeguir.setVisible(false);
			} else {
				btnSeguir.setVisible(true);
				btnAbandonar.setVisible(false);
			}
		}

		Set<User> following = viewingUser.getFollowing();
		Set<User> followers = viewingUser.getFollowers();
		
		Label lbFollowingCount = new Label("lbFollowingCount", following.size() + "");
		add(lbFollowingCount);
		Label lbFollowingCountHeader = new Label("lbFollowingCountHeader", following.size() + "");
		add(lbFollowingCountHeader);
		Label lbFollowersCount = new Label("lbFollowersCount", followers.size() + "");
		add(lbFollowersCount);
		Label lbFollowersCountHeader = new Label("lbFollowersCountHeader", followers.size() + "");
		add(lbFollowersCountHeader);
		
		List<Message> listMessage = messageBusiness.loadByUser(viewingUser);
		
		Label lbSize = new Label("lbSize", String.valueOf(listMessage.size()));
		add(lbSize);
		Label lbUserName = new Label("lbUserName", viewingUser.getName());
		add(lbUserName);		
		
		//Mensagens
		ListView<Message> listView = new ListView<Message>("lvListMsg", listMessage) {
			@Override
			protected void populateItem(ListItem<Message> item) {
				final Message message = (Message)item.getModelObject();
				item.add(new Label("msg", message.getMsg()));
			}
		};
		add(listView);
		
		//Following
		Label lbUserNameFollowing = new Label("lbUserNameFollowing", viewingUser.getName());
		add(lbUserNameFollowing);
		List<User> listaItensFollowing = new ArrayList<User>(following);
		ListView<User> listViewFollowing = new ListView<User>("lvListFollowing", listaItensFollowing) {
			@Override
			protected void populateItem(ListItem<User> item) {
				final User userFollowing = (User)item.getModelObject();
				item.add(new Label("name", userFollowing.getName()));
			}
		};
		add(listViewFollowing);

		//Followers
		Label lbUserNameFollowers = new Label("lbUserNameFollowers", viewingUser.getName());
		add(lbUserNameFollowers);
		List<User> listaItensFollowers = new ArrayList<User>(followers);
		ListView<User> listViewFollowers = new ListView<User>("lvListFollowers", listaItensFollowers) {
			@Override
			protected void populateItem(ListItem<User> item) {
				final User userFollowing = (User)item.getModelObject();
				item.add(new Label("name", userFollowing.getName()));
			}
		};
		add(listViewFollowers);
		
	}

	public static Link<Void> link(String id, final User user) {
		PageParameters params = new PageParameters();
		if (user != null)
			params.set("login", user.getLogin());
		Link<Void> result = new BookmarkablePageLink<Void>(id, ProfilePage.class, params);
		if (user == null)
			result.setVisible(false);
		return result;
	}
}

