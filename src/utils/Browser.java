package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import utils.Log;
/*import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
*/
public class Browser {
	public static WebDriver BrowserDriver = null;

	public static void SetBrowserEnvironment(){
		SetBrowserEnvironment("FF");
		SetDriver_FF();
	}

	public static void SetBrowserEnvironment(String browser){
		System.setProperty("webdriver.gecko.driver",Environment.ReturnGeckoDriverPath());
		if (browser.toUpperCase().contains("FIREFOX") || browser.toUpperCase().contains("FF")) {
			SetDriver_FF();
			Log.Console("Browser set to: FireFox");
		} else {
			SetDriver_FF();
			Log.Console("Browser set to: FireFox");
		}
	}
	
	public static void SetDriver_FF (){
		FirefoxProfile prof = new FirefoxProfile();
		prof.setPreference("network.proxy.type", 4); //Set proxy to auto-detect
		BrowserDriver = new FirefoxDriver(prof);
		Environment.BrowserName = "FireFox";
		Log.Debug(prof.toString());
	}
	
	public static String GoToURL(String url) {
		Environment.ActionCurrent = "navigate";
		Environment.ActionObject = "[Browser]" + Environment.BrowserName;
		Environment.ActionArgument = url;
		Timers.ActionStart();
		String title = "";
		//WebDriver driver = BrowserDriver;
		try {
			if (!url.toLowerCase().startsWith("http")) {
				url = "http://" + url;
			}
		} catch (NullPointerException npe) {
			Log.ExceptionError(npe);
			Timers.ActionEnd();
			Environment.ActionStatus=2;
			return "";
		}
		try {
			BrowserDriver.get(url);
			BrowserDriver.manage().window().maximize();
			title = BrowserDriver.getTitle();
		} catch (WebDriverException wde) {
			Log.ExceptionError(wde);
			Timers.ActionEnd();
			Log.Console("Error navigating to URL: " + url);
			Environment.ActionStatus=2;
			return "";
		} 
		Timers.ActionEnd();
		Log.Console("Navigated to: " + url + Timers.ActionTotal());
		Environment.ActionStatus=0;
		return title;
	};
}
