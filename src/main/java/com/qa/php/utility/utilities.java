package com.qa.php.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.qa.php.base.base;

public class utilities extends base {
	
	public static Logger log = LogManager.getLogger(utilities.class.getName());
		
	ExtentReports ext;
	int column;
	ArrayList<String>al = new ArrayList<String>();
	
	
	public ExtentReports getExtReport() {
		String path = System.getProperty("user.dir")+"\\result+\\report.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Travel");
		
		ext = new ExtentReports();
		ext.attachReporter(reporter);
		return ext;
	}
	
	public static void getSS(String tcname, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		String dst = System.getProperty("user.dir")+"\\report\\"+ tcname +".png";
		FileUtils.copyFile(src, new File (dst));
	}
	
	public ArrayList<String> getData(String tcname) throws IOException {
		int k=0;
		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\TestData.xlxs");
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		int sheets=wb.getNumberOfSheets();
		
		for (int i=0; i<sheets; i++) {
			XSSFSheet sheet=wb.getSheetAt(i);
			if (sheet.getSheetName().equalsIgnoreCase("Data")) {
				Iterator<Row> rows=sheet.rowIterator();
				Row fr=rows.next();
				Iterator<Cell>cells=fr.cellIterator();
				while (cells.hasNext()) {
					if (cells.next().getStringCellValue().equalsIgnoreCase("Test Case")) {
						column=k;
					}k++;
				}
				while (rows.hasNext()) {
					Row frs=rows.next();
					if (frs.getCell(i).getStringCellValue().equalsIgnoreCase(tcname)) {
						Iterator<Cell> cl = frs.cellIterator();
						while(cl.hasNext()) {
							if (cl.next().getCellType().equals(CellType.STRING)) {
								if (!cl.next().getStringCellValue().equals(tcname)) {
								al.add(cl.next().getStringCellValue());
								}else {
									cl.next();
								}
							}else {
								
								al.add(NumberToTextConverter.toText(cl.next().getNumericCellValue()));
							}
						}
						
					}
				}
			}
		}return al;
	}
	
	
	public boolean verifybrokenlink(String linkurl) throws MalformedURLException {
		try {
		URL url = new URL(linkurl);
		HttpURLConnection httpurlconnect = (HttpURLConnection)url.openConnection();
		httpurlconnect.setConnectTimeout(20);
		httpurlconnect.connect();
		if (httpurlconnect.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND) {
			log.info(linkurl+ " is  broken "+httpurlconnect.getResponseMessage());
			return false;
		}else  {
			log.info(linkurl +" is not broken "+ httpurlconnect.getResponseMessage());
			return true;
		}
		
		}catch(Exception e) {
			log.error(linkurl);
			return false;
		} 
		
				
	}
	
	public void validatecountbrokenlink() throws MalformedURLException {
		List<WebElement>links = driver.findElements(By.tagName("a"));
		for(WebElement link:links) {
			String url=link.getAttribute("href");
			verifybrokenlink(url);
		}
			
		
	}
	
	public boolean validateDescSorting(By locator) {
		ArrayList<String>al = new ArrayList<String>();
		Select select = new Select(driver.findElement(locator));
		List<WebElement> options = select.getOptions();
		
		for (int i=0; i < options.size(); i++) {
			al.add(options.get(i).getText());
		}
		
		List<String>temp = new ArrayList<String>();
		temp.addAll(al);
		Collections.sort(al, Collections.reverseOrder());
		if (al.equals(temp)) {
			return true;
		}else {
			return false;
		}
	}
	
	public void selectdropdown(By locator, int index) {
		Select select = new Select(driver.findElement(locator));
		select.selectByIndex(index);
	}
}
