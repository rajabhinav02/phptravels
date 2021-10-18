package phptravels;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.php.base.base;
import com.qa.php.pages.homepage;
import com.qa.php.utility.utilities;

public class testHomepage extends base {

	homepage hm;
	utilities ul;
	
	@BeforeMethod
	public void presetup() throws IOException {
		driver=setup();
		hm = new homepage(driver);
		ul = new utilities();
	}
	
	@Test
	public void testSelection() throws IOException {
		
		ArrayList<String>pl=ul.getData("TC1");
		hm.clickProduct(pl);
		
	}
	
	@AfterMethod
	public void getss(ITestResult result) throws IOException {
		
		if (result.getStatus()==ITestResult.FAILURE) {
			utilities.getSS(result.getName(), driver);
		}
	}
}
