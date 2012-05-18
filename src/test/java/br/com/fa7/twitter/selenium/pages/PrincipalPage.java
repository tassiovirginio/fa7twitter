package br.com.fa7.twitter.selenium.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.fa7.twitter.selenium.components.FuncionalTestContext;

public class PrincipalPage extends Page {

	public PrincipalPage(WebDriver driver) {
		super(driver);
	}

	public void sendMessage(String message){
		findElementAndSendKeys("taMsg", message).submit();
	}

	@Override
	public void loadPage() {
		driver.navigate().to("http://127.0.0.1:9999/");
	}
	
	public int quantidadeDeMensagensEncontradas() {
		final String XPATH_EXPRESSION = 
				"//tbody//tr[1]//td[1]//span[";
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		WebElement tbMensagens = findElementById("mensagens");
		int resultCount = 0;
		while (true) {
			try {
				String xpath_toline = XPATH_EXPRESSION + (resultCount + 1) + "]";
				tbMensagens.findElement(By.xpath(xpath_toline));
				resultCount++;
			} catch (NoSuchElementException expected) { 
				break;
			}
		}

		FuncionalTestContext.setDriverAsDefault();
		return resultCount;
	}

	public void sair() {
		loadPage();
		findLinkText("Sair").click();
	}
}
