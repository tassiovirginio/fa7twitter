package br.com.fa7.twitter.selenium;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.fa7.twitter.selenium.pages.BuscarPage;

public class FindTest extends FuncionalTestBase {

	private static BuscarPage buscarPage;
	
	@BeforeClass
	public static void setUp() {
		buscarPage = new BuscarPage(driver);
	}
	
	@Test
	public void buscarUmUsuario() throws InterruptedException {
		buscarPage.loadPage();
		buscarPage.click("lkFindUser");
		buscarPage.buscar("lu");
		Assert.assertEquals(1, buscarPage.quantidadeDeUsuariosEncontrados());
	}
	
	@Test
	public void buscarDoisUsuario() throws InterruptedException {
		buscarPage.loadPage();
		buscarPage.click("lkFindUser");
		buscarPage.buscar("l");
		Assert.assertEquals(2, buscarPage.quantidadeDeUsuariosEncontrados());
	}
}
