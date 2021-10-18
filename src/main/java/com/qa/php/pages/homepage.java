package com.qa.php.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.qa.php.utility.utilities;


public class homepage {
	public Logger log = LogManager.getLogger(homepage.class.getName());
	WebDriver driver;
	Actions action;
	utilities utl=new utilities();
	
	public homepage(WebDriver driver) {
		this.driver= driver;
	}
	
	private By signin = By.xpath("//a[contains(text(), 'Sign in')]");
	private By searchtextbox = By.xpath("//input[@placeholder='Search']");
	private By searchbutton = By.xpath("(//button[@type='submit'])[position()=1]");
	private By logo = By.cssSelector("[alt='My Store']");
	private By addbutton = By.xpath("parent::h5/following-sibling::div[2]/a[position()=1]");
	private By brandname = By.xpath("//a[@class='product-name']");
	private By categorynames = By.xpath("//ul[contains(@class,'menu-content')]/li/a");
	private By slider = By.xpath("//a[contains(@class,'ui-slider')][position()=1]");
	private By sortdropdown = By.xpath("//select[@id='selectProductSort']");
	private By header = By.xpath("//span[@class='cat-name']");
	
	
	public void clicklogo() {
		action = new Actions(driver);
		action.moveToElement(driver.findElement(logo)).click().build().perform();
		
	}
	
	public void clickProduct(ArrayList al) {
		Object[] obj = al.toArray();
		
		List<WebElement> itemnames = driver.findElements(brandname);
		List<WebElement> addbuttons = driver.findElements(addbutton);
		
		for (WebElement item:itemnames) {
			if (al.contains(item.getText())) {
				int j = itemnames.indexOf(item);
				action.moveToElement(item);
				addbuttons.get(j).click();
			}
			
		}
	}
	
	public Boolean validatesorting() {
		//utl= new utilities();
		Boolean value=utl.validateDescSorting(sortdropdown);
		return value;
	}
	
	public void selectdropdown() {
		utl.selectdropdown(addbutton, 1);
	}
	
	public void slider() {
		action = new Actions(driver);
		WebElement slide = driver.findElement(slider);
		action.dragAndDropBy(slide, 200, 0);
	}
}
