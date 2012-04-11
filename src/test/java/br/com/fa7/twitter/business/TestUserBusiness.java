package br.com.fa7.twitter.business;

import java.util.List;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.fa7.twitter.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TestUserBusiness {
	
	@Autowired
	private UserBusiness userBusiness;
	
//	@Before
	public void setUp() {
		
		
	}

	@Test
	public void testFindUsersByNameSuccess() {
		
		User user = new User("NomeDeTeste", "login", "password", "email");
		userBusiness.save(user);
		List<User> userTeste = userBusiness.findByName("Teste");
		Assert.assertTrue(userTeste.contains(user));
		
	}

	@Test
	public void testFindUsersByNameError() {
		
		User user = new User("NomeDeTeste", "login", "password", "email");
		userBusiness.save(user);
		List<User> userTeste = userBusiness.findByName("NomeInexistente");
		boolean userReturn = userTeste.contains(user);
		Assert.assertFalse(userReturn);
		
	}
	
//	@After
	public void finalize(){
	}

}
