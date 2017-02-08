package utils;

public class Log {
	public static int DebugMode = 0; 

	public static void Console(String msg) {
		String logMsg = Formatter.GetDateTime() + " : " + msg;
		Reporter.LogWriteLine(msg);
		System.out.println(logMsg);
	}
	
	public static void Console(String msg, int stat) {
		String logMsg = Formatter.GetDateTime() + " : " + msg + " (" + stat + ")";
		System.out.println(logMsg);
	}
	
	public static void Debug (String msg) {
		if (DebugMode == 1) {
			String logMsg = Formatter.GetDateTime() + " : " + msg;
			System.out.println(logMsg);
		}
	}
	
	public static void DumpData(String[] data) {
		for (String line : data) {
			Log.Debug(line);
		}

	}
	
	public static void ExceptionError (Exception e) {
		String ExceptionMessage = "[" + e.getClass() +  "]" + e.getMessage();
		System.out.println(ExceptionMessage);
	}

	public static void FlushToOutputFile() {
		Console("Suite run time: " + Timers.SuiteTotal());
		Environment.SetFileOutput();
		Console("Flushing to output file: " + Environment.FileOutput);
		//Reporter.OutputFilesWriteLine(Environment.TestName + "," + Environment.FileOutput + "," + Environment.SuiteElapsed);
		FileHandling.Write(Environment.FileOutput, Environment.Steps);
	}
	
	public static void FlushToLogFile() {
		Console("Total run time: " + Environment.TimeElapsed);
		Console("Flushing to log file: " + Environment.FileLog);
		FileHandling.Write(Environment.FileLog, Environment.Logs);
	}
	
}
