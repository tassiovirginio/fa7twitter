package br.com.fa7.twitter.business;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.fa7.twitter.entities.Message;
import br.com.fa7.twitter.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TestUserBusiness {
	
	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private MessageBusiness messageBusiness;
		
	static Message message;
		
	@Before
	public void setUp() {
		messageBusiness.clearAll();
		userBusiness.clearAllFollows();
		userBusiness.clearAll();
	}

	@Test
	public void testFindUsersByNameSuccess() {
		User user = new User("NomeDeTeste", "login", "password", "email@.com");
		userBusiness.save(user);
		List<User> users = userBusiness.findByName("Teste");
		Assert.assertTrue(users.contains(user));
	}

	@Test
	public void testFindUsersByNameError() {
		User user = new User("NomeDeTeste", "login", "password", "email@.com");
		userBusiness.save(user);
		List<User> users = userBusiness.findByName("NomeInexistente");
		boolean userReturn = users.contains(user);
		Assert.assertFalse(userReturn);
	}

	@Test
	public void testAddOneFollowingSuccess() {
		User loggedUser = new User("NomeUsuarioLogado", "login", "password", "email@.com");
		userBusiness.save(loggedUser);
		
		Message message = new Message("Mensagem do usuario logado", loggedUser);
		messageBusiness.save(message);
		
		try{
			userBusiness.follow(loggedUser, createUserFollowing());
			List<Message> messages = messageBusiness.loadByUser(loggedUser);
			Assert.assertEquals(1, messages.size());
			
		} catch (Exception e) {
			Assert.fail("A quantidade de mensagens do usuario logado deveria ser = 1 ao inves de " + messageBusiness.loadByUser(loggedUser).size());
		}
	}
	
	@Test
	public void testAddTwoFollowingSuccess() {
		User loggedUser = new User("NomeUsuarioLogado", "login", "password", "email@.com");
		userBusiness.save(loggedUser);
		
		Message message = new Message("Mensagem do usuario logado", loggedUser);
		messageBusiness.save(message);
		
		try{
			//Primeiro Following
			userBusiness.follow(loggedUser, createUserFollowing());
			//Segundo Following
			userBusiness.follow(loggedUser, createUserFollowing());
			List<Message> messages = messageBusiness.loadByUser(loggedUser);
			Assert.assertEquals(1, messages.size());
			
		} catch (Exception e) {
			Assert.fail("A quantidade de mensagens do usuario logado deveria ser = 1 ao inves de " + messageBusiness.loadByUser(loggedUser).size());
		}
	}
	
	private User createUserFollowing(){
		User userFollowing = new User("NomeUsuarioSeguido", "login", "password", "email@.com");
		userBusiness.save(userFollowing);
		Message messageFollowing = new Message("Messagem usuario seguido", userFollowing);
		messageBusiness.save(messageFollowing);
		messageBusiness.loadByUser(userFollowing);
		return userFollowing;
	}
	
	@Test
	public void testFindAllUsers() {
		User user = new User("NomeDeTeste", "login", "password", "email@.com");
		userBusiness.save(user);
		List<User> users = userBusiness.listAll();
		Assert.assertTrue(users.size() == 1);
	}
	
	@Test
	public void testFindAllTwoUsers() {
		User user = new User("NomeDeTeste", "login", "password", "email@.com");
		userBusiness.save(user);
		User user2 = new User("NomeDeTeste", "login", "password", "email@.com");
		userBusiness.save(user2);
		List<User> users = userBusiness.listAll();
		Assert.assertTrue(users.size() == 2);
	}
	
	@Test
	public void testSizeListUsers() {
		User user = new User("NomeDeTeste", "login", "password", "email@.com");
		userBusiness.save(user);
		int size = userBusiness.size();
		Assert.assertTrue(size == 1);
	}
	
	@Test
	public void testFindUsersById() {
		User user = new User("NomeDeTeste", "login", "password", "email@.com");
		userBusiness.save(user);
		User user2 = userBusiness.findById(user.getId());
		Assert.assertTrue(user.equals(user2));
	}
	
	@Test
	public void testFindUsersByLogin() {
		User user = new User("NomeDeTeste", "login", "password", "email@.com");
		userBusiness.save(user);
		User user2 = userBusiness.findByLogin(user.getLogin());
		Assert.assertTrue(user.equals(user2));
	}
	
	@Test
	public void testFindUsersByLoginErro() {
		User user = userBusiness.findByLogin("");
		Assert.assertNull(user);
	}
	
	@Test
	public void testLoginSuccess() throws Exception {
		User user = new User("NomeDeTeste", "login", "password", "email@.com");
		userBusiness.save(user);
		User userLogged = userBusiness.login("login", "password");
		Assert.assertNotNull("Usuario nao recuperado", userLogged);
		Assert.assertEquals(userLogged.getLogin(), "login");
		Assert.assertEquals(userLogged.getName(), "NomeDeTeste");
		Assert.assertEquals(userLogged.getPassword(), "password");
		Assert.assertEquals(userLogged.getEmail(), "email@.com");	
	}
	
	@Test
	public void testLoginWrongPasswordFail() {
		User user = new User("NomeDeTeste", "login", "password", "email@.com");
		userBusiness.save(user);
		try {
			userBusiness.login("login","pass");
			Assert.fail("Senha errada, deve disparar excecao");
		} catch (Exception expected) {
			Assert.assertEquals("Usuario e senha invalidos", expected.getMessage()) ;
		}
	}
	
	@Test
	public void testLoginNotFoundFail() {
		try {
			userBusiness.login("login","pass");
			Assert.fail("Login nao cadastrado, deve disparar excecao");
		} catch (Exception expected) {
			Assert.assertEquals("Login nao cadastrado", expected.getMessage()) ;
		}
	}
	
	@Test
	public void testFollowAUserAndListHimFollowingGettingUserFromDB() {
		User user = new User("Following", "following", "password", "email@.com");
		userBusiness.save(user);
		User follower = new User("Follower", "follower", "password", "email@.com");
		userBusiness.save(follower);
		
		userBusiness.follow(follower, user);
		
		User followerFromDB = userBusiness.findByLogin("follower");
		Assert.assertEquals(1, followerFromDB.getListFollowing().size());
	}
	
	@Test
	public void testFollowAUserAndListHimFollowersGettingUserFromDB() {
		User user = new User("Following", "following", "password", "email@.com");
		userBusiness.save(user);
		User follower = new User("Follower", "follower", "password", "email@.com");
		userBusiness.save(follower);
		
		userBusiness.follow(follower, user);
		
		User userFromDB = userBusiness.findByLogin("following");
		Assert.assertEquals(1, userFromDB.getListFollower().size());
	}
	
	@Test @Ignore
	public void testFollowAUserAndListHimFollowers() {
		User user = new User("User", "user", "password", "email@.com");
		userBusiness.save(user);
		User follower = new User("Follower", "follower", "password", "email@.com");
		userBusiness.save(follower);
		
		userBusiness.follow(follower, user);
		Assert.assertEquals(1, user.getListFollower().size());
	}
	
	@Test
	public void testFollowAUserAndListByManyToMany() {
		User userToFollow = new User("Following", "following", "password", "email@.com");
		userBusiness.save(userToFollow);
		User follower = new User("Follower", "follower", "password", "email@.com");
		userBusiness.save(follower);
		
		Set<User> follow = new HashSet<User>();
		follow.add(userToFollow);
		follower.setListFollowing(follow);
		
		userBusiness.save(follower);
		
		userBusiness.follow(follower, userToFollow);
		
		User followerDB = userBusiness.findById(follower.getId());
		Assert.assertEquals(1, followerDB.getListFollowing().size());
		
		User userToFollowDB = userBusiness.findById(userToFollow.getId());
		Assert.assertEquals(1, userToFollowDB.getListFollower().size());
	}
	
	@After
	public void finalize(){
	}

}
