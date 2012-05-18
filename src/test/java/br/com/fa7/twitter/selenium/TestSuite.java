package br.com.fa7.twitter.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.fa7.twitter.selenium.components.FuncionalTestContext;

@RunWith(value=Suite.class)
@SuiteClasses(value={
		LoginTest.class,
		Teste.class,
  }
)
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
