package com.qa.php.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class base {
	public WebDriver driver;
	public Properties pro;
	public WebDriver setup() throws IOException {

	FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//config.properties");
	Properties pro = new Properties();
	pro.load(fis);
	
	if (System.getProperty("browser").contains("chrome")) {
		if (System.getProperty("browser").contains("headless")){
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
		}
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		if (System.getProperty("channel").equalsIgnoreCase("ete")) {
			driver.get(pro.getProperty("eteurl"));
		}else if(System.getProperty("channel").contains("prod")) {
			driver.get(pro.getProperty("produrl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		
	} else if (System.getProperty("browser").contains("ff")) {
		if (System.getProperty("browser").contains("headless")){
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
		}
		System.setProperty("webdriver.firefox.driver", System.getProperty("user.dir")+"//chromedrive.exe");
		WebDriver driver = new ChromeDriver();
		
		if (System.getProperty("channel").equalsIgnoreCase("ete")) {
			driver.get(pro.getProperty("eteurl"));
		}else if(System.getProperty("channel").contains("prod")) {
			driver.get(pro.getProperty("produrl"));
		}
	}
	//driver.manage().window().maximize();
	//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	return driver;
	}
}
