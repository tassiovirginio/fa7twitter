package br.com.fa7.twitter.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class Page {

	protected WebDriver driver;

	public Page(WebDriver driver) {
		this.driver = driver;
	}
	
	public abstract void loadPage();

	public WebElement findLinkText(String name){
		return driver.findElement(By.linkText(name));
	}
	
	public WebElement findElementById(String id){
		return driver.findElement(By.id(id));
	}
	
	public WebElement findElementByName(String name){
		return driver.findElement(By.name(name));
	}

	public void click(String id){
		findElementById(id).click();
		sleep();
	}
	
	public WebElement findElementAndSendKeys(String name, String keys){
		WebElement element = findElementByName(name);
		element.clear();
		element.sendKeys(keys);
		sleep();
		return element;
	}

	public WebElement getByTagName(String name){
		return driver.findElement(By.tagName(name));
	}

	public WebElement getContentPage(){
		return getByTagName("body");
	}		
	
	public String getTextPage(){
		return getContentPage().getText();
	}		
	
	public void sleep(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
}
