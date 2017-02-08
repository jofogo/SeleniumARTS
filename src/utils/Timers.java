package utils;

public class Timers {
	public static long TimeSuiteStart;
	public static long TimeSuiteEnd;
	public static long TimeSuiteTotal;
	public static long TimeProcessStart;
	public static long TimeProcessEnd;
	public static long TimeProcessTotal;
	public static long TimeActionStart;
	public static long TimeActionEnd;
	public static long TimeActionTotal;
	public static long TimeTestCaseStart;
	public static long TimeTestCaseEnd;
	public static long TimeTestCaseTotal;
	

	
	public static void ActionStart() {
		TimeActionStart = System.currentTimeMillis();
	}
	public static void ActionEnd() {
		TimeActionEnd = System.currentTimeMillis();
	}
	
	public static long ActionTotal() {
		TimeActionTotal = (TimeActionEnd - TimeActionStart)/1000;
		return TimeActionTotal;
		//return " [" + TimeActionTotal + " sec(s).]";
	}

	public static void ProcessStart() {
		Environment.TimeStarted = Formatter.GetDateTime();
		TimeProcessStart = System.currentTimeMillis();
	}
	public static void ProcessEnd() {
		Environment.TimeEnded = Formatter.GetDateTime();
		TimeProcessEnd = System.currentTimeMillis();
	}
	
	public static long ProcessTotal() {
		TimeProcessTotal = (TimeProcessEnd - TimeProcessStart)/1000;
		Environment.TimeElapsed = TimeProcessTotal + " sec(s)";
		return TimeProcessTotal;
		//return " [" + TimeProcessTotal + " sec(s).]";
	}	

	public static void SuiteStart() {
		Environment.SuiteStarted = Formatter.GetTime();
		TimeSuiteStart = System.currentTimeMillis();
	}
	public static void SuiteEnd() {
		Environment.SuiteEnded = Formatter.GetTime();
		TimeSuiteEnd = System.currentTimeMillis();
	}
	
	public static long SuiteTotal() {
		TimeSuiteTotal = (TimeSuiteEnd - TimeSuiteStart)/1000;
		Environment.SuiteElapsed = TimeProcessTotal + " sec(s)";
		return TimeSuiteTotal;
	}		

	public static void TestCaseStart() {
		Environment.TestCaseStarted = Formatter.GetTime();
		TimeTestCaseStart = System.currentTimeMillis();
	}
	public static void TestCaseEnd() {
		Environment.TestCaseEnded = Formatter.GetTime();
		TimeTestCaseEnd = System.currentTimeMillis();
	}
	
	public static long TestCaseTotal() {
		TimeTestCaseTotal = (TimeTestCaseEnd - TimeTestCaseStart)/1000;
		Environment.TestCaseElapsed = TimeTestCaseTotal + " sec(s)";
		return TimeTestCaseTotal;
	}		

	
	
}
