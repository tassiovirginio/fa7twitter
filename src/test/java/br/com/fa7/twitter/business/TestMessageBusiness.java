package br.com.fa7.twitter.business;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.fa7.twitter.business.exception.BusinessException;
import br.com.fa7.twitter.entities.Message;
import br.com.fa7.twitter.entities.User;
import br.com.fa7.twitter.util.FakeUrlShortener;
import br.com.fa7.twitter.util.URLShortener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class TestMessageBusiness {

	@Autowired
	private MessageBusiness messageBusiness;

	@Autowired
	private UserBusiness userBusiness;

	static User user;

	@Before
	public void setUp() {
		messageBusiness.clearAll();
		userBusiness.clearAll();
	}

	@Test
	public void testInsertOneMessage() throws BusinessException {
		User user = createUser();
		messageBusiness.postMessage(user, "test1", new FakeUrlShortener());
		Assert.assertEquals(1, messageBusiness.size());
	}

	private User createUser() throws BusinessException {
		User user = new User("name", "login", "password", "email@dominio.com");
		userBusiness.newUser(user);
		return user;
	}

	@Test
	public void testInsertOneMessageAndReturnIt() throws BusinessException {
		User user = createUser();
		messageBusiness.postMessage(user, "test1", new FakeUrlShortener());
		Assert.assertEquals("test1", messageBusiness.listAll().get(0).getMsg());
	}

	@Test
	public void testLoadUserMessages() throws BusinessException {
		User user = createUser();
		messageBusiness.postMessage(user, "test3", new FakeUrlShortener());
		List<Message> result = messageBusiness.loadByUser(user);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals("test3", result.get(0).getMsg());
	}
	
	@Test
	public void testCriarLinkParaPaginaDoUsuarioNaMensagem() {
		Message message = new Message("Olá @oneuser", user);
		String messageReadyToShow = messageBusiness.toExibition(message);
		Assert.assertEquals("Olá <a href=\"http://localhost:9999/user/oneuser/\">@oneuser</a>", messageReadyToShow);
	}
	
	@Test
	public void testCriarLinkParaUsuarioComNumero() {
		Message message = new Message("Olá @user1", user);
		String messagereadyToShow = messageBusiness.toExibition(message);
		Assert.assertEquals("Olá <a href=\"http://localhost:9999/user/user1/\">@user1</a>", messagereadyToShow);
	}
	
	@Test
	public void testCriarLinkParaUsuarioSemAlterarEspacosDaMensagem() {
		Message message = new Message("Olá @user ", user);
		String messagereadyToShow = messageBusiness.toExibition(message);
		Assert.assertEquals("Olá <a href=\"http://localhost:9999/user/user/\">@user</a> ", messagereadyToShow);
	}
	
	@Test
	public void testCriarLinkParaTodosOsUsuarioNaMensagem() {
		Message message = new Message("Olá @oneuser , @otheruser ", user);
		String messagereadyToShow = messageBusiness.toExibition(message);
		Assert.assertEquals("Olá <a href=\"http://localhost:9999/user/oneuser/\">@oneuser</a> , <a href=\"http://localhost:9999/user/otheruser/\">@otheruser</a> ", messagereadyToShow);
	}
	
	@Test
	public void testNaoCriarLinkParaNomeDeUsuarioIniciadoComAcento() {
		Message message = new Message("Olá @úser ", user);
		String messagereadyToShow = messageBusiness.toExibition(message);
		Assert.assertEquals("Olá @úser ", messagereadyToShow);
	}
	
	@Test
	public void testNaoCriarLinkParaNomeDeUsuarioIniciadoComLetraMaiuscula() {
		Message message = new Message("Olá @User ", user);
		String messagereadyToShow = messageBusiness.toExibition(message);
		Assert.assertEquals("Olá @User ", messagereadyToShow);
	}
	
	@Test
	public void testCriarLinkParaUsuarioInvalidosAteOndeForValido() {
		Message message = new Message("Olá @usêr ", user);
		String messagereadyToShow = messageBusiness.toExibition(message);
		Assert.assertEquals("Olá <a href=\"http://localhost:9999/user/us/\">@us</a>êr ", messagereadyToShow);
	}
	
	@Test
	public void urlNaMensagem() {
		URLShortener urlShortener = new URLShortener() {
			@Override
			public String shorten(String url) {
				return "http://curta";
			}
		};
		
		Assert.assertEquals("http://curta", messageBusiness.prepareMessage("http://www.tassiovirginio.com", urlShortener));
	}

	@After
	public void finalize() {
	}

}
