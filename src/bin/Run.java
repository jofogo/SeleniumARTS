package bin;

import static utils.Environment.ReadConfig;


import utils.Browser;
import utils.Environment;
import utils.Log;
import utils.Maps;
import utils.Reporter;
import utils.Timers;



public class Run {
	private static String Project ="Amaysim";
	public static String Password = "";
	
	public static void main (String[] args) {
		
		
		if (args.length > 0) {
			Project = args[0];
			if (args.length>1) {
				Password = args[1];
			}
		}
		
		Timers.ProcessStart();
		Log.DebugMode = 1;
		Maps.InitializeMaps();
		ReadConfig(Project);
		String URL = Maps.Configs.get("baseURL");
		Log.Debug("URL: " + URL);
		
		
		Browser.SetBrowserEnvironment(Environment.BrowserName);
		if (Browser.GoToURL(URL) == "") {
			Log.Console("Base URL could not be loaded! Terminating...");
			Browser.BrowserDriver.close();
		} else {
			Environment.ReadTestList();
			for (int ctrTestList = 1; ctrTestList < Maps.TestList.size(); ctrTestList++) {
				String Suites = Maps.TestList.get(ctrTestList)[0];
				String Execute = Maps.TestList.get(ctrTestList)[1];
				Log.Debug("Test Suite: " + Suites);
				Log.Debug("Execute? " + Execute);
				if (Execute.toUpperCase().contains("TRUE")) {
					Log.Debug("Running Test Suite: " + Suites);
					Environment.Steps = new String[0];					
					Environment.SuiteStart();
					Environment.TestSuite = Suites;
					Environment.SetFileData();
					
					Environment.TestLoad(); // Iterates through the test cases

					Timers.SuiteEnd();
					Reporter.PerformanceWriteLine(Suites + "," + Timers.SuiteTotal() + ",%");
					Reporter.SummaryWriteLine(Suites + "," + Environment.SuiteStarted + "," + Environment.SuiteEnded + "," + Timers.SuiteTotal());
					Log.FlushToOutputFile();
				} else if (Execute.toUpperCase().contains("FALSE")) {
					Environment.TestSuite = Suites;
					Reporter.SummaryWriteLine(Environment.TestSuite + ",n/a,n/a,Skipped");					
					Reporter.PerformanceWriteLine(Suites + ",0,Skipped");
				}
			}
		}
		Browser.BrowserDriver.quit();
		Timers.ProcessEnd();
		Timers.ProcessTotal();
		Log.FlushToLogFile();
		Reporter.WriteHTMLReport();
	}
}			
			
/*			for (String Suites : Maps.TestList.keySet()) {
				String Execute = Maps.TestList.get(Suites);
				Log.Debug("Test Suite: " + Suites);
				Log.Debug("Execute? " + Execute);
				if (Execute.toUpperCase().contains("TRUE")) {
					Log.Debug("Running Test Suite: " + Suites);
					Environment.Steps = new String[0];					
					Environment.SuiteStart();
					Environment.TestSuite = Suites;
					Environment.SetFileData();
					
					Environment.TestLoad(); // Iterates through the test cases
					
					Timers.SuiteEnd();
//					Log.DumpData(Environment.Report);
					Reporter.PerformanceWriteLine(Suites + "," + Timers.SuiteTotal() + ",%");
//					Log.Debug("Performance");
//					Log.DumpData(Environment.Performance);
//					Log.Debug("Summary");
//					Log.DumpData(Environment.Summary);
//					Log.Debug("TestSummary");
//					Log.DumpData(Environment.TestSummary);
//					Log.Debug("Steps");
//					Log.DumpData(Environment.Steps);
					Log.FlushToOutputFile();

					Reporter.SummaryWriteLine(Suites + "," + Environment.SuiteStarted + "," + Environment.SuiteEnded + "," + Timers.SuiteTotal());					
				} else if (Execute.toUpperCase().contains("FALSE")) {
					Environment.TestSuite = Suites;
					Reporter.SummaryWriteLine(Environment.TestSuite + ",n/a,n/a,Skipped");					
					Reporter.PerformanceWriteLine(Suites + ",0,Skipped");
				}*/
//				TestSummaryWriteLine("WelcomeScreen,Login_Fail," + Formatter.GetTime() + "," + Formatter.GetTime() + "," + "0 sec(s)," + "5,0,100");

		
/*		Timers.ProcessEnd();
		Timers.ProcessTotal();
		//Log.Debug(Environment.Report.toString());
		Log.FlushToLogFile();
		Reporter.WriteHTMLReport();
	}*/


