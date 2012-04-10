package br.com.fa7.twitter;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.fa7.twitter.pages.PrincipalPage;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TestHomePage {
	private WicketTester tester;

	@Before
	public void setUp() {
		tester = new WicketTester(new WicketApplication());
	}

	@Test
	@Ignore
	public void homepageRendersSuccessfully() {
		
		// start and render the test page
		tester.startPage(PrincipalPage.class);

		// assert rendered page class
		tester.assertRenderedPage(PrincipalPage.class);
	}
}
