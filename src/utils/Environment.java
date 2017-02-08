package utils;

import java.util.ArrayList;
import java.util.List;

public class Environment {
	private static String geckoDriverPath = "C:\\Projects\\TPL\\geckodriver.exe";
	private static String dateFormat = "MM/dd/yyyy";
	private static String timeFormat = "HH:mm:ss";
	private static String stampFormat = "yyyy-dd-MM_HH.mm.ss";
	private static String PathWorkspace = "C:\\Projects\\";
	private static String fileConfigFormat = "project.cfg";
	private static String fileDataFormat = ".csv";
	private static String fileMasterFormat = ".csv";
	private static String fileLogFormat = ".txt";
	private static String fileReportFormat = ".html";
	public static int StepRetries = 15;
	public static int StepMaxTimeOut = 60;
	public static int CountTestPass, CountTestFail, CountTestTotal;
	public static int CountStepPass, CountStepFail, CountStepTotal;
	public static String TimeStarted, TimeEnded, TimeElapsed;
	public static String SuiteStarted, SuiteEnded, SuiteElapsed;
	public static String TestCaseStarted, TestCaseEnded, TestCaseElapsed;
	public static int ActionStatus = 0;
	public static int StepNumber = 1;
	public static String TestProject, TestName, TestSuite, BaseURL, BrowserName, ActionCurrent, ActionArgument, ActionObject;
	public static String FileConfig, FileLog, FileMaster, FileOutput, FileReport, FileData;
	public static String PathRoot, PathData, PathLog, PathOutput, PathMapping, PathReports;
	public static String[] Summary = new String[0];
	public static String[] Performance = new String[0];
	public static String[] Logs = new String[0];
	public static String[] Steps = new String[0];
	public static String[] TestSummary = new String[0];
	public static String[] Report = new String[0];
	
	/* Getters */
	public static String ReturnGeckoDriverPath() {
		return geckoDriverPath;
	}
	
	public static String ReturnDateFormat() {
		return dateFormat;
	}
	
	public static String ReturnTimeFormat(){
		return timeFormat;
	}

	public static String ReturnStampFormat(){
		return stampFormat;
	}
	
	/* Set Directory paths */
	private static void SetPathRoot(String project) {
		PathRoot = PathWorkspace + project;
		Log.Debug("Root directory set to: " + PathRoot);
	}
	private static void SetPathData() {
		PathData = PathRoot + "\\data\\";
		Log.Debug("Data directory set to: " + PathData);
	}
	private static void SetPathLog() {
		PathLog = PathRoot + "\\logs\\";
		Log.Debug("Log directory set to: " + PathLog);
	}
	private static void SetPathOutput() {
		PathOutput = PathRoot + "\\output\\";
		Log.Debug("Output directory set to: " + PathOutput);
	}
	private static void SetPathMapping() {
		PathMapping = PathRoot + "\\mapping\\";
		Log.Debug("Mapping directory set to: " + PathMapping);
	}
	private static void SetPathReports() {
		PathReports = PathRoot + "\\reports\\";
		Log.Debug("Reports directory set to: " + PathReports);
	}
	
	/*Set File paths*/
	private static void SetFileConfig() {
		FileConfig = PathRoot + "\\" +  fileConfigFormat;
		Log.Debug("Config file path set to: " + FileConfig);
	}
	
	private static void SetFileLog() {
		FileLog = PathLog + TestProject + "-" + Formatter.GetStamp() + fileLogFormat;
	}

	public static void SetFileOutput() {
		FileOutput = PathOutput + TestSuite + "-" + Formatter.GetStamp() +  fileLogFormat;
	}
	
	public static void SetFileData() {
		FileData = PathData + TestSuite + fileDataFormat;
	}

	public static void SetFileMaster() {
		FileMaster = PathRoot + "\\" + TestProject + fileMasterFormat;
	}
	
	public static void SetFileReport() {
		FileReport = PathReports + TestProject + "-" + Formatter.GetStamp() + fileReportFormat;
	}
	
	
	public static void TestLoad() {
		List<String []> TestCases = new ArrayList<String[]>();

	    SetFileData();
		TestCases = Formatter.ConvertCSVToArray(FileData);
    	if(! TestCases.isEmpty()) {
        	Maps.GetTestObjectMap();
        	Maps.GetTestStepsMap();
    		int ctrTestCases = TestCases.get(0).length;
    	
		    Log.Console("Test Cases found: " + (ctrTestCases -3));
        	for (int ctr = 3; ctr < ctrTestCases; ctr++) {
        		//If test case is enabled
		    	Maps.GetTestDataMap(ctr);
		    	String TestCase = Maps.TestValues.get("Object_ID"); 
    		    if (TestCase.toUpperCase().contains("TRUE")) {
    		    	TestName = Maps.TestValues.get("Object");
//    		    	Timers.TestCaseStart();
    		    	TestCaseStart();
    		    	Log.Console("Running Test Case: " + Environment.TestName);
                	String action, argument, object;
                	int ctrStep = 0;
                	for (String Steps : Maps.TestSteps.keySet()) {
                		if (ctrStep > 1) {
//                        	Timers.ActionStart();

                			object = Maps.TestObjects.get(Steps).toString();
                    		action = Maps.TestSteps.get(Steps).toString();
                    		argument = Maps.TestValues.get(Steps).toString();
//                    		Log.Debug("Key:" + Steps);
//                    		Log.Debug("Object:" + object);
//                    		Log.Debug("Action:" + action.toUpperCase());
//                    		Log.Debug("Argument:" + argument);
                    		if (object.isEmpty() == false) {
                    			Web.PerformOnObject(action, object, argument);
                        		Reporter.TestStep();
                        		StepNumber++;
                    		}
                    		
                		}
                		ctrStep++;
                	}
                	Timers.TestCaseEnd();
					Reporter.WriteTestSummary();

        		}
        	}
        	
    	}
	}
	
	public static void SuiteStart() {
		CountTestPass = 0;
		CountTestFail = 0;
		CountTestTotal = 0;
		SuiteStarted = "";
		SuiteEnded = "";
		SuiteElapsed = "";
		Steps = new String[0];
		Timers.SuiteStart();
	}
	
	
	public static void TestCaseStart() {
		CountStepPass = 0;
		CountStepFail = 0;
		CountStepTotal = 0;
		StepNumber = 1;
		Timers.TestCaseStart();
	}
	
	public static void ActionStart() {
		ActionCurrent = "";
		ActionObject = "";
		ActionArgument = "";
		Timers.ActionStart();
		ActionStatus = 0;
	}
	
	//Initialize the values for a new test
//	public static void TestStart() {
//		CountTestPass = 0;
//		CountTestFail = 0;
//		CountTestTotal = 0;
//		ActionStatus = 0;
//		Timers.Reset();
//		TestName = "";
//		TestSuite = "";
//		BaseURL = "";
//		FileLog = "";
//		FileOutput = "";
//		FileReport = "";
//		ActionCurrent = "";
//		ActionObject = "";
//		ActionArgument = "";
//		BrowserName = "";
//		Steps = new String[0];
//		Maps.ClearTestMaps();
//		
//		//Tests.clear();
//		//DataObjects.clear();
//		//Logs = new String[1];
//		
//		
//	}

	public static void ReadConfig(String project) {
		String root = PathWorkspace + project;

		if(FileHandling.CheckExistence(root)){
			SetPathRoot(project);
			SetFileConfig();
			if (FileHandling.CheckExistence(FileConfig)){
				Maps.Configs = Formatter.ConvertCSVToMap(FileConfig);
				Formatter.LogMap("Configuration", Maps.Configs);
				TestProject = (String) Maps.Configs.get("title");
				BaseURL = (String) Maps.Configs.get("baseURL");
				BrowserName = Maps.Configs.get("browser");
			} else {
				Log.Console("Could not find the project's configuration file");
			}
			SetPathData();
			SetPathLog();
			SetPathOutput();
			SetPathMapping();
			SetPathReports();
			SetFileLog();
			SetFileOutput();
			SetFileReport();
			SetFileMaster();
		} else {
			Log.Console("Project not set up correctly! Kindly ask for assistance from the developers.");
		};
		
	}
	
	public static void ReadTestList() {
		if (FileHandling.CheckExistence(FileMaster)) {
			Maps.GetTestListMap();
		} else {
			Log.Console("Could not locate the test list file! Kindly ask for assistance from the developers.");
		}
	}

	
}
