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

import br.com.fa7.twitter.business.exception.BusinessException;
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
		userBusiness.clearAll();
	}

	@Test
	public void testFindUsersByNameSuccess() throws BusinessException {
		User user = new User("NomeDeTeste", "login", "password", "email@dominio.com");
		userBusiness.newUser(user);
		List<User> users = userBusiness.findByName("Teste");
		Assert.assertTrue(users.contains(user));
	}

	@Test
	public void testFindUsersByEmailSuccess() throws BusinessException {
		User user = new User("NomeDeTeste", "login", "password", "email@dominio.com");
		userBusiness.newUser(user);
		User userAchado = userBusiness.findByEmail("email@dominio.com");
		Assert.assertEquals(user, userAchado);
	}
	
	@Test
	public void testFindUsersByNameSuccessIgnoreCase() throws BusinessException {
		User user = new User("NomeDeTeste", "login", "password", "email@dominio.com");
		userBusiness.newUser(user);
		List<User> users = userBusiness.findByName("teste");
		Assert.assertTrue(users.contains(user));
	}

	@Test
	public void testFindUsersByNameError() throws BusinessException {
		User user = new User("NomeDeTeste", "login", "password", "email@dominio.com");
		userBusiness.newUser(user);
		List<User> users = userBusiness.findByName("NomeInexistente");
		boolean userReturn = users.contains(user);
		Assert.assertFalse(userReturn);
	}

	@Test
	public void testAddOneFollowingSuccess() throws BusinessException {
		User loggedUser = new User("NomeUsuarioLogado", "login", "password", "email@dominio.com");
		userBusiness.newUser(loggedUser);
		
		Message message = new Message("Mensagem do usuario logado", loggedUser);
		messageBusiness.save(message);
		
		userBusiness.follow(loggedUser, createUserFollowing("following"));
		List<Message> messages = messageBusiness.loadByUser(loggedUser);
		Assert.assertEquals(1, messages.size());
	}
	
	@Test
	public void testAddTwoFollowingSuccess() throws BusinessException {
		User loggedUser = new User("NomeUsuarioLogado", "login", "password", "email@dominio.com");
		userBusiness.newUser(loggedUser);
		
		Message message = new Message("Mensagem do usuario logado", loggedUser);
		messageBusiness.save(message);
		
		//Primeiro Following
		userBusiness.follow(loggedUser, createUserFollowing("following"));
		//Segundo Following
		userBusiness.follow(loggedUser, createUserFollowing("following2"));
		List<Message> messages = messageBusiness.loadByUser(loggedUser);
		Assert.assertEquals(1, messages.size());
	}
	
	private User createUserFollowing(String login) throws BusinessException{
		User userFollowing = new User("NomeUsuarioSeguido", login, "password", login + "@dominio.com");
		userBusiness.newUser(userFollowing);
		Message messageFollowing = new Message("Messagem usuario seguido", userFollowing);
		messageBusiness.save(messageFollowing);
		messageBusiness.loadByUser(userFollowing);
		return userFollowing;
	}
	
	@Test
	public void testFindAllUsers() throws BusinessException {
		User user = new User("NomeDeTeste", "login", "password", "email@dominio.com");
		userBusiness.newUser(user);
		List<User> users = userBusiness.listAll();
		Assert.assertTrue(users.size() == 1);
	}
	
	@Test
	public void testFindAllTwoUsers() throws BusinessException {
		User user = new User("NomeDeTeste", "login", "password", "email@dominio.com");
		userBusiness.newUser(user);
		User user2 = new User("NomeDeTeste", "login2", "password", "email2@dominio.com");
		userBusiness.newUser(user2);
		List<User> users = userBusiness.listAll();
		Assert.assertTrue(users.size() == 2);
	}
	
	@Test
	public void testSizeListUsers() throws BusinessException {
		User user = new User("NomeDeTeste", "login", "password", "email@dominio.com");
		userBusiness.newUser(user);
		int size = userBusiness.size();
		Assert.assertTrue(size == 1);
	}
	
	@Test
	public void testFindUsersById() throws BusinessException {
		User user = new User("NomeDeTeste", "login", "password", "email@dominio.com");
		userBusiness.newUser(user);
		User user2 = userBusiness.findById(user.getId());
		Assert.assertTrue(user.equals(user2));
	}
	
	@Test
	public void testFindUsersByLogin() throws BusinessException {
		User user = new User("NomeDeTeste", "login", "password", "email@dominio.com");
		userBusiness.newUser(user);
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
		User user = new User("NomeDeTeste", "login", "password", "email@dominio.com");
		userBusiness.newUser(user);
		User userLogged = userBusiness.login("login", "password");
		Assert.assertNotNull("Usuario nao recuperado", userLogged);
		Assert.assertEquals(userLogged.getLogin(), "login");
		Assert.assertEquals(userLogged.getName(), "NomeDeTeste");
		Assert.assertEquals(userLogged.getPassword(), "password");
		Assert.assertEquals(userLogged.getEmail(), "email@dominio.com");	
	}
	
	@Test
	public void testLoginWrongPasswordFail() throws BusinessException {
		User user = new User("NomeDeTeste", "login", "password", "email@dominio.com");
		userBusiness.newUser(user);
		try {
			userBusiness.login("login","pass");
			Assert.fail("Senha errada, deve disparar excecao");
		} catch (BusinessException expected) {
			Assert.assertEquals("O login ou a senha inserido está incorreto.", expected.getMessage()) ;
		}
	}
	
	@Test
	public void testLoginNotFoundFail() {
		try {
			userBusiness.login("login","pass");
			Assert.fail("Login nao cadastrado, deve disparar excecao");
		} catch (BusinessException expected) {
			Assert.assertEquals("O login ou a senha inserido está incorreto.", expected.getMessage()) ;
		}
	}
	
	@Test
	public void testFollowAUserAndListHimFollowingGettingUserFromDB() throws BusinessException {
		User user = new User("Following", "following", "password", "following@dominio.com");
		userBusiness.newUser(user);
		User follower = new User("Follower", "follower", "password", "follower@dominio.com");
		userBusiness.newUser(follower);
		
		userBusiness.follow(follower, user);
		
		User followerFromDB = userBusiness.findByLogin("follower");
		Assert.assertEquals(1, followerFromDB.getFollowing().size());
	}
	
	@Test
	public void testFollowAUserAndListHimFollowersGettingUserFromDB() throws BusinessException {
		User user = new User("Following", "following", "password", "following@dominio.com");
		userBusiness.newUser(user);
		User follower = new User("Follower", "follower", "password", "follower@dominio.com");
		userBusiness.newUser(follower);
		
		userBusiness.follow(follower, user);
		
		User userFromDB = userBusiness.findByLogin("following");
		Assert.assertEquals(1, userFromDB.getFollowers().size());
	}
	
	@Test
	public void testFollowAUserAndListByManyToMany() throws BusinessException {
		User userToFollow = new User("Following", "following", "password", "following@dominio.com");
		userBusiness.newUser(userToFollow);
		User follower = new User("Follower", "follower", "password", "follower@dominio.com");
		userBusiness.newUser(follower);
		
		userBusiness.follow(follower, userToFollow);
		
		User followerDB = userBusiness.findById(follower.getId());
		Assert.assertEquals(1, followerDB.getFollowing().size());
		
		User userToFollowDB = userBusiness.findById(userToFollow.getId());
		Assert.assertEquals(1, userToFollowDB.getFollowers().size());
	}
	
	@Test
	public void testUnfollowAUserAndList() throws BusinessException{
		User userThatHeFollow = new User("Follower", "follower", "password", "follower@dominio.com");
		userBusiness.newUser(userThatHeFollow);
		User user = new User("Following", "following", "password", "following@dominio.com");
		user.getFollowing().add(userThatHeFollow);
		userBusiness.newUser(user);
		Assert.assertEquals(1, user.getFollowing().size());
		
		userBusiness.unfollow(user, userThatHeFollow);
		Assert.assertEquals(0, user.getFollowing().size());
		User userDB = userBusiness.findById(user.getId());
		Assert.assertEquals(0, userDB.getFollowing().size());
	}
	
	@Test
	public void sucessoValidarDadosNovoUsuario() throws BusinessException {
		User user = new User("Nome", "LOGIN", "password", "EMAIL@DOMINIO.COM");
		userBusiness.validarDadosNovoUsuario(user);
		Assert.assertEquals("Nome", user.getName());
		Assert.assertEquals("login", user.getLogin());
		Assert.assertEquals("password", user.getPassword());
		Assert.assertEquals("email@dominio.com", user.getEmail());
	}

	@Test
	public void falhaEmailValidarDadosNovoUsuario() {
		User user = new User("nome", "login", "password", "emaildominio.com");
		try {
			userBusiness.validarDadosNovoUsuario(user);
			Assert.fail("exception esperado");
		} catch (BusinessException e) {
			Assert.assertEquals("Email 'emaildominio.com' inválido.", e.getMessage());
		}
	}
	
	@Test
	public void falhaLoginIndisponivelValidarDadosNovoUsuario() throws BusinessException {
		User user = new User("nome", "login", "password", "email@dominio.com");
		userBusiness.newUser(user);
		User user2 = new User("nome", "login", "password", "email2@dominio.com");
		try {
			userBusiness.validarDadosNovoUsuario(user2);
			Assert.fail("exception esperado");
		} catch (BusinessException e) {
			Assert.assertEquals("Login 'login' indisponível.", e.getMessage());
		}
	}
	
	@Test
	public void falhaEmailJaCadastradoValidarDadosNovoUsuario() throws BusinessException {
		User user = new User("nome", "login", "password", "email@dominio.com");
		userBusiness.newUser(user);
		User user2 = new User("nome", "login2", "password", "email@dominio.com");
		try {
			userBusiness.validarDadosNovoUsuario(user2);
			Assert.fail("exception esperado");
		} catch (BusinessException e) {
			Assert.assertEquals("Email 'email@dominio.com' já cadastrado.", e.getMessage());
		}
	}
	
	@Test
	public void falhaCamposObrigatoriosValidarDadosNovoUsuario() throws BusinessException {
		User user = new User("", "", "", "");
		try {
			userBusiness.validarDadosNovoUsuario(user);
			Assert.fail("exception esperado");
		} catch (BusinessException e) {
			Assert.assertEquals("Nome obrigatório.", e.getMessage());
		}
		user.setName("Nome");
		try {
			userBusiness.validarDadosNovoUsuario(user);
			Assert.fail("exception esperado");
		} catch (BusinessException e) {
			Assert.assertEquals("Login obrigatório.", e.getMessage());
		}
		user.setLogin("login");
		try {
			userBusiness.validarDadosNovoUsuario(user);
			Assert.fail("exception esperado");
		} catch (BusinessException e) {
			Assert.assertEquals("Senha obrigatória.", e.getMessage());
		}
		user.setPassword("password");
		user.setEmail("email@dominio.com");
		userBusiness.validarDadosNovoUsuario(user);
	}
	
	@After
	public void finalize(){
	}

}
