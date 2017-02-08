package utils;



import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

import bin.Run;

public class Web {
	public static WebElement element;
	public static WebDriver driver;
	public static String WebElementID;
	
	
	//Find the element
	public static WebElement FindElement(String identifier) throws InterruptedException {
		WebElementID = "";
		element = null;
		WebDriver page = Browser.BrowserDriver;
			if (! identifier.equals("")) {
				WebElement elements;
				int ElementFound = 0;
				for (int ctrRetries = 0; ctrRetries < Environment.StepRetries; ctrRetries++) {
					Log.Console("Finding " + identifier + "... Tries: " + (ctrRetries+1));
					try{
						Log.Debug("Using CSS Selector");
						elements = page.findElement(By.cssSelector(identifier));
						if (elements!=null) {
							element = elements;
							Log.Debug("Found using CssSelector");
							ElementFound++;
							break;
						}
					} catch(NoSuchElementException|NullPointerException onf) {
						Thread.sleep(250);
					}
					try{
						Log.Debug("Using ID");
						elements = page.findElement(By.id(identifier));
						if (elements!=null) {
							element = elements;
							Log.Debug("Found using ID");
							ElementFound++;
							break;
						}
					} catch(NoSuchElementException|NullPointerException onf) {
						Thread.sleep(250);
					}
					try{
						Log.Debug("Using Text");
						elements = page.findElement(By.linkText(identifier));
						if (elements!=null) {
							element = elements;
							Log.Debug("Found using Text");
							ElementFound++;
							break;
						}
					} catch(NoSuchElementException|NullPointerException onf) {
						Thread.sleep(250);
					}
					try{
						Log.Debug("Using Name");
						elements = page.findElement(By.name(identifier));
						if (elements!=null) {
							element = elements;
							Log.Debug("Found using Name");
							ElementFound++;
							break;
						}
					} catch(NoSuchElementException|NullPointerException onf) {
						Thread.sleep(250);
					}
					try{
						Log.Debug("Using xPath");
						elements = driver.findElement(By.xpath(identifier));
						if (elements!=null) {
							element = elements;
							Log.Debug("Found using xPath");
							ElementFound++;
							break;
						}
					} catch(NoSuchElementException|NullPointerException onf) {
						Thread.sleep(250);
					}
				}
				if (ElementFound > 0) {
					WebElementID = identifier;
					//Environment.ActionObject = "[" + element.getTagName() + "]" + WebElementID;
					Log.Debug("Element set to: " + Environment.ActionObject);
				} else {
					Log.Console("Unable to identify: " + identifier);
					Environment.ActionObject = "";
					element = null;
					WebElementID = "";
					return null;
				}				
			}
		return element;
//			driver.findElement(By.linkText(identifier));
//			driver.findElement(By.partialLinkText(identifier));
	}
	
	public static void Focus() {
        Actions action = new Actions(driver); //instantiate Actions class
        action.moveToElement(element).perform(); //points the mouse pointer to the element
	}
	
//	//Perform an action without an argument
//	public static int Perform(String action) {
//		int status = 0;
//		if (element != null) {
//			Timers.ActionStart();
//			Log.Debug("Action: " + action);
//			switch (action.toUpperCase()) {
//			case "CLICK":
//				Environment.ActionCurrent="click";
//				element.click();
//				status = 0;
//				Timers.ActionEnd();
//			}
//		} else {
//			status = 2;
//		}
////		Log.Debug("Action= " + action + " Object= " + Environment.ActionObject + " = DONE");
//		//Formatter.WriteTestStep();
//		return status;
//	}


//	public static void WaitForObject(WebElement element) {
//		Wait<WebDriver> wait = new FluentWait<WebDriver>(Browser.BrowserDriver)
//			//Wait for the condition
//			.withTimeout(Environment.StepMaxTimeOut, TimeUnit.SECONDS) 
//			// which to check for the condition with interval of 5 seconds. 
//			.pollingEvery(3, TimeUnit.SECONDS)
//			 //Which will ignore the NoSuchElementException
//			.ignoring(NoSuchElementException.class);
//		wait.until(ExpectedConditions.presenceOfElementLocated(locator))
//	}
	static void waitForPageLoad() {
	    WebDriverWait wait = new WebDriverWait(Browser.BrowserDriver, Environment.StepMaxTimeOut);

	    Predicate<WebDriver> pageLoaded = new Predicate<WebDriver>() {

	        @Override
	        public boolean apply(WebDriver input) {
	            return ((JavascriptExecutor) input).executeScript("return document.readyState").equals("complete");
	        }

	    };
	    wait.until(pageLoaded);
	}
    
	
	//Perform an action with a specified argument
	public static int Perform(String action, String argument) {
		int status = 0;
		if (element != null) {
//			Timers.ActionStart();
			waitForPageLoad();
			switch (action.toUpperCase()) {
			case "CLICK":
				for (int ctr = 0; ctr < 3; ctr ++) {
					try {
						element.click();
						status = 0;
						ctr = 3;
					} catch (ElementNotVisibleException enve) {
						Log.ExceptionError(enve);
						status = 2;
					}
				}
				break;
			case "SELECT":
				//element.sendKeys(argument);
				try {
					Select select = new Select(element);
					if(argument != "") {
						select.selectByVisibleText(argument);
						status = 0;
					} else {
						select.getFirstSelectedOption();
						status = 0;
					}
				} catch (NoSuchElementException nsee) {
					Log.Debug("Select value: " + argument + " not found in list.");
					status = 5;
				}
				break;
			case "TYPE":
				//element.sendKeys(argument);
				element.clear();
				if(argument != "") {
					if (Environment.ActionObject.toUpperCase().contains("PASSWORD") && Run.Password != "") {
						element.clear();
						element.sendKeys(Run.Password);
						status = 0;
					} else {
						element.clear();
						element.sendKeys(argument);
						status = 0;
					}
				}
				status = 0;
				break;
			case "VALIDATE":
				if (! argument.equals("")) {
					if (argument.toUpperCase().contains("TRUE")) {
						if (ValidateObjectAccessible()) {
							status = 0;
						} else {
							status = 2;
						}
					} else if (argument.toUpperCase().contains("FALSE")) {
						if (! ValidateObjectAccessible()) {
							status = 0;
						} else {
							status = 2;
						}
					} else {
						if (ValidateObjectText(argument)) {
							status = 0;
						} else {
							status = 2;
						}
					}
					if(ValidateObjectAccessible()) {
						Log.Debug("Validation successful!");
					};
				} 
				break;
			}
		} else {
			status = 2;
		}
		Timers.ActionEnd();
		Timers.ActionTotal();
		return status;
	}
	
	public static boolean ValidateObjectAccessible() {
		if (ValidateObjectExists() && ValidateObjectEnabled()) {
			return true;
		}
		return false;
	}
	
	public static boolean ValidateObjectExists() {
		if (element.isDisplayed()) {
//		if (element.getAttribute("hidden").toString()=="false") {
			return true;
		}
		return false;
	}
	public static boolean ValidateObjectEnabled() {
		if (element.isEnabled()) {
//		if (element.getAttribute("disabled").toString()=="false") {
			return true;
		}
		return false;
	}
	
	public static boolean ValidateObjectText(String text) {
		if (element.getText().contains(text)) {
			return true;
		}
		return false;
	}
	
	
	public static boolean ValidateObjectClass(String className) {
		if (element.getClass().toString().equalsIgnoreCase(className)) {
			return true;
		}
		return false;
	}

	//Find an element then perform an action without an argument
	public static void PerformOnObject (String action, String identifier) {
		try {
			FindElement(identifier);
			Perform(action, "");
		} catch (InterruptedException ie) {
			Log.ExceptionError(ie);
		}
	}

	//Find an element then perform an action without an argument
	public static void PerformOnObject (String action, String identifier, String argument) {
		try {
        	Timers.ActionStart();
			Environment.ActionCurrent = action;
			Environment.ActionObject = Maps.GetKeyFromValue(Maps.TestObjects, identifier);
			//Log.Debug("Retrieved object: " + Environment.ActionObject);
			Environment.ActionArgument = argument; 

			if (Environment.ActionObject != "") {
				if(action.toUpperCase().contains("VALIDATE") && argument.length()==0) {
					Environment.ActionStatus = 3; //skip
				} else if (argument.equals("SKIP")){
					Environment.ActionStatus = 3; //skip
				} else {
					if (FindElement(identifier) != null) {
						Log.Debug("Action: " + action);
						if (Perform(action, argument)==0) {
							Environment.ActionStatus = 0; //pass
						} else {
							Environment.ActionStatus = 2; //fail
						}
					} else {
						Environment.ActionStatus = 4; //object not found
					}
				}
				
			} else {
				Environment.ActionStatus = 3; //skip because no object found
			}
			
		} catch (InterruptedException ie) {
			Log.ExceptionError(ie);
		}
	}
	
	

}
