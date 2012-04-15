package br.com.fa7.twitter.business;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
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
		
//	@Before
	public void setUp() {
		
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
		
		messageBusiness.loadByUser(loggedUser).add(message);
		
		try{
			
			loggedUser.getListFollowing().add(createUserFollowing());
			
			userBusiness.save(loggedUser);
			
			List<Message> messages = messageBusiness.loadByUser(loggedUser);
			
			Assert.assertEquals(1, messages.size());
			
		} catch (Exception e) {
			Assert.fail("A quantidade de mensagens do usuario logado deveria ser = 1 ao inves de " + messageBusiness.loadByUser(loggedUser).size());
		}
		
	}
	
	@Ignore
	@Test
	public void testAddTwoFollowingSuccess() {
		
		User loggedUser = new User("NomeUsuarioLogado", "login", "password", "email");
		userBusiness.save(loggedUser);
		
		Message message = new Message("Mensagem do usuario logado", loggedUser);
		messageBusiness.save(message);
		
		messageBusiness.loadByUser(loggedUser).add(message);
		
		try{
			
			loggedUser.getListFollowing().add(createUserFollowing());
			
			userBusiness.save(loggedUser);
			
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

	@After
	public void finalize(){
		messageBusiness.clearAll();
	}

}
