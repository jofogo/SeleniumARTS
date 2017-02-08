package test;

import org.openqa.selenium.WebDriver;

import utils.Browser;

public class WebTest {
	public static void main(String[] args) throws InterruptedException {
		
		Browser.SetBrowserEnvironment("ff");

		
		// Create a new instance of the Firefox driver
		WebDriver driver = Browser.BrowserDriver;
		
        //Launch the Online Store Website
		Browser.GoToURL("www.google.com");
 
 
		//Wait for 5 Sec
		Thread.sleep(5);
		
        // Close the driver
        driver.quit();
    
	}
}
