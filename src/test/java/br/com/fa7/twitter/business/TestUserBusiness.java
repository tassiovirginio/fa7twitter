package br.com.fa7.twitter.business;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
		userBusiness.clearAll();
	}

	@Test
	public void testFindUsersByNameSuccess() {
		
		User user = new User("NomeDeTeste", "login", "password", "email");
		userBusiness.save(user);
		List<User> users = userBusiness.findByName("Teste");
		Assert.assertTrue(users.contains(user));
		
	}

	@Test
	public void testFindUsersByNameError() {
		
		User user = new User("NomeDeTeste", "login", "password", "email");
		userBusiness.save(user);
		List<User> users = userBusiness.findByName("NomeInexistente");
		boolean userReturn = users.contains(user);
		Assert.assertFalse(userReturn);
		
	}

	@Test
	public void testAddOneFollowingSuccess() {
		
		User loggedUser = new User("NomeUsuarioLogado", "login", "password", "email");
		userBusiness.save(loggedUser);
		
		Message message = new Message("Mensagem do usuario logado", loggedUser);
		messageBusiness.save(message);
		
		try{
			
			loggedUser.getListFollowing().add(createUserFollowing());
			
			userBusiness.save(loggedUser);
			
			List<Message> messages = messageBusiness.loadByUser(loggedUser);
			
			Assert.assertEquals(1, messages.size());
			
		} catch (Exception e) {
			Assert.fail("A quantidade de mensagens do usuario logado deveria ser = 1 ao inves de " + messageBusiness.loadByUser(loggedUser).size());
		}
		
	}
	
	//@Ignore
	@Test
	public void testAddTwoFollowingSuccess() {
		User loggedUser = new User("NomeUsuarioLogado", "login", "password", "email");
		userBusiness.save(loggedUser);
		
		Message message = new Message("Mensagem do usuario logado", loggedUser);
		messageBusiness.save(message);
		
		try{
			//Primeiro Following
			loggedUser.getListFollowing().add(createUserFollowing());
			userBusiness.save(loggedUser);
			
			//Segundo Following
			loggedUser.getListFollowing().add(createUserFollowing());
			userBusiness.save(loggedUser);
			
			List<Message> messages = messageBusiness.loadByUser(loggedUser);
			
			Assert.assertEquals(1, messages.size());
			
		} catch (Exception e) {
			Assert.fail("A quantidade de mensagens do usuario logado deveria ser = 1 ao inves de " + messageBusiness.loadByUser(loggedUser).size());
		}
	}
	
	private User createUserFollowing(){
		
		User userFollowing = new User("NomeUsuarioSeguido", "login", "password", "email");
		userBusiness.save(userFollowing);
		
		Message messageFollowing = new Message("Messagem usuario seguido", userFollowing);
		messageBusiness.save(messageFollowing);
		
		messageBusiness.loadByUser(userFollowing).add(messageFollowing);
		
		return userFollowing;
		
	}
	
	
	@Test
	public void testFindAllUsers() {
		User user = new User("NomeDeTeste", "login", "password", "email");
		userBusiness.save(user);
		List<User> users = userBusiness.listAll();
		Assert.assertTrue(users.size() == 1);
		
	}
	
	@Test
	public void testFindAllTwoUsers() {
		User user = new User("NomeDeTeste", "login", "password", "email");
		userBusiness.save(user);
		User user2 = new User("NomeDeTeste", "login", "password", "email");
		userBusiness.save(user2);
		List<User> users = userBusiness.listAll();
		Assert.assertTrue(users.size() == 2);
		
	}
	
	
	@Test
	public void testSizeListUsers() {
		User user = new User("NomeDeTeste", "login", "password", "email");
		userBusiness.save(user);
		int size = userBusiness.size();
		Assert.assertTrue(size == 1);
		
	}
	
	
	@Test
	public void testFindUsersById() {
		User user = new User("NomeDeTeste", "login", "password", "email");
		userBusiness.save(user);
		User user2 = userBusiness.findById(user.getId());
		Assert.assertTrue(user.equals(user2));
	}
	
	@Test
	public void testFindUsersByLogin() {
		User user = new User("NomeDeTeste", "login", "password", "email");
		userBusiness.save(user);
		User user2 = userBusiness.findByLogin(user.getLogin());
		Assert.assertTrue(user.equals(user2));
	}
	
	
	@Test
	public void testFindUsersByLoginErro() {
		User user = userBusiness.findByLogin("");
		Assert.assertNull(user);
	}
	

	@After
	public void finalize(){
		messageBusiness.clearAll();
	}

}
