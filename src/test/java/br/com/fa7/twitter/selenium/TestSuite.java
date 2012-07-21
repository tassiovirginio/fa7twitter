package br.com.fa7.twitter.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import br.com.fa7.twitter.selenium.components.FuncionalTestContext;

//@RunWith(value=Suite.class)
//@SuiteClasses(value={
//		LoginTest.class,
//		FindTest.class,
//		PrincipalTest.class,
//		ApplicationTest.class
//  }
//)
public class TestSuite { 
	
	@BeforeClass
	public static void setUp() {
		FuncionalTestContext.inititialize();
	}
	
	@AfterClass
	public static void tearDown(){
		FuncionalTestContext.done();
	}
}
