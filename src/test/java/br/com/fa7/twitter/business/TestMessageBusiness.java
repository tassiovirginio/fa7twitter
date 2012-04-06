package br.com.fa7.twitter.business;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.util.Assert;

import br.com.fa7.twitter.entities.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TestMessageBusiness {
	
	@Autowired
	private MessageBusiness messageBusiness;
	
	@Before
	public void setUp() {
	}

	@Test
	public void testInsertOneMessage() {
		messageBusiness.save(new Message("test1"));
		Assert.assertEquals(1,messageBusiness.size());
	}
	
	@Test
	public void testInsertOneMessageReturntest1() {
		messageBusiness.save(new Message("test1"));
		Assert.assertEquals("test1",messageBusiness.listAll().get(0).getMsg());
	}
	
	@After
	public void finalize(){
		System.out.println("size -> " + messageBusiness.size());
	}

}
