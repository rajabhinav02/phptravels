package com.qa.php.pages;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class loginpage {
	WebDriver driver;
	public Logger log = LogManager.getLogger(loginpage.class.getName());
	public loginpage(WebDriver driver) {
		this.driver=driver;
	}
	
	private By email = By.cssSelector("div[class=\"form-group\"] input");
	private By pwd = By.cssSelector("[name='password']");
	private By submit = By.xpath("//button[@type='submit' and contains(@class,'btn-default')]");
	
	public void sendUsername(String usrname) {
		driver.findElement(email).clear();
		driver.findElement(email).sendKeys(usrname);
		log.info("email entered");
	}
	
	public void sendPwd(String paswd) {
		driver.findElement(pwd).clear();
		driver.findElement(pwd).sendKeys(paswd);
		log.info("password entered");
	}
	
	public void clickSubmit() {
		driver.findElement(submit).click();
		log.info("Submit clicked");
	}
	
	public void login(String usrname, String paswd) {
		try {
		sendUsername(usrname);
		sendPwd(paswd);
		clickSubmit();
		log.info("login completed");
		}catch (Exception e) {
			log.error("login failed");	
		}
	}
}
