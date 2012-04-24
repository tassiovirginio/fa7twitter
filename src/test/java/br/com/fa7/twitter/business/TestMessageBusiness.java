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
		messageBusiness.save(new Message("test1", user));
		Assert.assertEquals(1, messageBusiness.size());
	}

	private User createUser() throws BusinessException {
		User user = new User("name", "login", "password", "email@dominio.com");
		userBusiness.newUser(user);
		return user;
	}

	@Test
	public void testInsertOneMessageReturntest1() throws BusinessException {
		User user = createUser();
		messageBusiness.save(new Message("test1", user));
		Assert.assertEquals("test1", messageBusiness.listAll().get(0).getMsg());
	}

	@Test
	public void testLoadUserMessages() throws BusinessException {
		User user = createUser();
		messageBusiness.save(new Message("test3", user));
		List<Message> result = messageBusiness.loadByUser(user);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals("test3", result.get(0).getMsg());
	}

	@After
	public void finalize() {
	}

}
