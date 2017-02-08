package utils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class Reporter {
		private static String[] HTMLOutput = new String[0];
		
		private static void WriteToLogArray(String line) {
			Environment.Logs[Environment.Logs.length-1] = line;
		}
		
		private static void WriteToStepArray(String line) {
			Environment.Steps[Environment.Steps.length-1] = line;
		}

		private static void WriteToReportArray(String line) {
			Environment.Report[Environment.Report.length-1] = line;
		}

		private static void WriteToPerformanceArray(String line) {
			Environment.Performance[Environment.Performance.length-1] = line;
		}
		
		private static void WriteToHTMLArray(String line) {
			HTMLOutput[HTMLOutput.length-1] = line;
		}
		private static void WriteToSummaryArray(String line) {
			Environment.Summary[Environment.Summary.length-1] = line;
		}
		
		private static void WriteToTestSummaryArray(String line) {
			Environment.TestSummary[Environment.TestSummary.length-1] = line;
		}
		
		private static void HTMLWriteLine(String line) {
			ExpandHTMLArray();
			WriteToHTMLArray(line);
		}

		public static void OutputWriteLine(String line) {
			ExpandStepArray();
			WriteToStepArray(line);
		}
		public static void ReportWriteLine(String line) {
			ExpandReportArray();
			WriteToReportArray(line);
		}

		public static void SummaryWriteLine(String line) {
			ExpandSummaryArray();
			WriteToSummaryArray(line);
		}

		public static void TestSummaryWriteLine(String line) {
			ExpandTestSummaryArray();
			WriteToTestSummaryArray(line);
		//	Log.Debug(line);
		}
		
		public static void LogWriteLine(String line) {
			ExpandLogArray();
			WriteToLogArray(line);
		}

		public static void PerformanceWriteLine(String line) {
			ExpandPerformanceArray();
			WriteToPerformanceArray(line);
		}
		
		private static void ExpandPerformanceArray() {
			Environment.Performance = Arrays.copyOf(Environment.Performance, Environment.Performance.length+1);
		}
		
		private static void ExpandSummaryArray() {
			Environment.Summary = Arrays.copyOf(Environment.Summary, Environment.Summary.length+1);
		}

		private static void ExpandTestSummaryArray() {
			Environment.TestSummary = Arrays.copyOf(Environment.TestSummary, Environment.TestSummary.length+1);
		}
		
		private static void ExpandHTMLArray() {
			HTMLOutput = Arrays.copyOf(HTMLOutput, HTMLOutput.length+1);
		}

		private static void ExpandLogArray() {
			Environment.Logs = Arrays.copyOf(Environment.Logs, Environment.Logs.length+1);
		}
		
		private static void ExpandStepArray() {
			Environment.Steps = Arrays.copyOf(Environment.Steps, Environment.Steps.length+1);
		}
		
		private static void ExpandReportArray() {
			Environment.Report = Arrays.copyOf(Environment.Logs, Environment.Logs.length+1);
		}
		

		public static void TestStep() {
			String Step = Environment.TestName + "," + Environment.StepNumber +",";
			String action = Maps.ActionDescriptions.get(Environment.ActionCurrent.toUpperCase());
			if (action.toUpperCase().contains("CLICK")) {
				Step += action + Environment.ActionObject;
			} else {
				Step += action + Environment.ActionArgument + " to " + Environment.ActionObject;
			}
			String stat = Maps.Status.get(Integer.toString(Environment.ActionStatus));
			Log.Debug("stat number:" + stat);
			if(stat.toUpperCase().contains("PASS")) {
				Environment.CountStepPass++;
			} else if (stat.toUpperCase().contains("FAIL")) {
				Environment.CountStepFail++;
			}
			Environment.CountStepTotal++;
			Step += "," + stat + ",";
			Step += Timers.TimeActionTotal;
			Log.Debug(Step);
			OutputWriteLine(Step);
		}
		
//		public static void TestStep() {
//			//teststep, testcase, action, object, argument, pass/fail, time
//			String TestStep = 
//					Environment.TestName + "," + //Test Name 
//					Environment.Steps.length+1 + "," + // Test Step
//					Environment.ActionCurrent + "," +
//					Environment.ActionObject + "," +
//					Environment.ActionArgument + ",";
//			
//			String Status =	Maps.Status.get(Integer.toString(Environment.ActionStatus));
//			Log.Debug("Status code: " + Status);
//			TestStep += "," + Status;
//			if (Status == "pass") {
//				Environment.CountStepPass++;
//			} else {
//				Environment.CountStepFail++;
//			}
//			Timers.ActionEnd();
//			TestStep += "," + Timers.ActionTotal();
//			Log.Debug(TestStep);
//			ExpandStepArray();
//			WriteToStepArray(TestStep);
//		}

		public static void WriteTestSummary() {
			String TestSummary =
					Environment.TestSuite + "," + Environment.TestName + "," + 
					Environment.TestCaseStarted + "," +
					Environment.TestCaseEnded + "," +
					Timers.TestCaseTotal() + "," +
					Environment.CountStepPass + "," +
					Environment.CountStepFail + "," + 
					(Environment.CountStepFail + Environment.CountStepPass)+ ","; 

			float rate = Environment.CountStepPass + Environment.CountStepFail;
			rate = Environment.CountStepPass  / rate;
			rate = rate * 100.0f;
			
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			TestSummary += df.format(rate);
			//			float rate = (Environment.CountStepPass / (Environment.CountStepPass + Environment.CountStepFail)) * 100.0f;  
			//rate = rate * 100;
			//TestSummary += rate;
			
		Log.Debug(TestSummary);
		
			TestSummaryWriteLine(TestSummary);
			
			OutputWriteLine(TestSummary);
		}

		
		public static void WriteHTMLReport() {
			HTMLWriteLine("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
			HTMLWriteLine("<html>");
			HTMLHeader();
			HTMLWriteLine("<body>");
			Title();
			SummaryOverall();
			PerformanceChart();
			SuiteHeader();
			StepsHeader();
			HTMLWriteLine("</body>");
			HTMLWriteLine("</html>");
			Environment.SetFileReport();
			FileHandling.Write(Environment.FileReport, HTMLOutput);
		}

		private static void Title() {
			HTMLWriteLine("<div class=\"header\" align=\"center\">");
			
			HTMLWriteLine("<h1>" + Environment.TestProject + " Test Report as of " + Formatter.GetDateTime() + "</h1>");			
			HTMLWriteLine("</div>");
		}
		
		private static void HTMLHeader() {
			HTMLWriteLine("<head>");
			HTMLWriteLine("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">");
			HTMLWriteLine("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.1.3/Chart.min.js\"></script>");
			HTMLWriteLine("<title>" + Environment.TestProject + " Summary Report as of " + Formatter.GetDateTime() + "</title>");
			try {
				String[] cssContents = FileHandling.Read(Environment.PathRoot + "\\styles.css");
				for (String cssLine: cssContents) {
					HTMLWriteLine(cssLine);
				}
			} catch (IOException e) {
				Log.ExceptionError(e);
			}			HTMLWriteLine("</head>");
		}
		
		private static void SummaryOverall() {
			HTMLWriteLine("<div class=\"params\" >");
			HTMLWriteLine("<h2> Over-all Summary </h2>");
			HTMLWriteLine("<b> Project: </b> " + Environment.TestProject + "<br />");
			HTMLWriteLine("<b> Base URL: </b> " + Environment.BaseURL + "<br />");
			HTMLWriteLine("<b> Browser: </b> " + Environment.BrowserName + "<br />");
			HTMLWriteLine("<b> Started: </b> " + Environment.TimeStarted + "<br />");
			HTMLWriteLine("<b> Ended: </b> " + Environment.TimeEnded + "<br />");
			HTMLWriteLine("<b> Elapsed Time: </b> " + Environment.TimeElapsed + "<br />");
			HTMLWriteLine("</div>");
		}
		
		private static void StepsHeader() {
			HTMLWriteLine("<br /> <div class=\"steps\">");
			HTMLWriteLine("<h2> Tests </h2>");
			HTMLWriteLine("<table class = \"test_steps\">");
			HTMLWriteLine("<tr class=\"header\">");
			HTMLWriteLine("<td> Test Suite </td>");
			HTMLWriteLine("<td> Test Case </td>");
			HTMLWriteLine("<td> Started </td>");
			HTMLWriteLine("<td> Ended </td>");
			HTMLWriteLine("<td> Elapsed (secs) </td>");
			HTMLWriteLine("<td> Passed </td>");
			HTMLWriteLine("<td> Failed </td>");
			HTMLWriteLine("<td> Total </td>");
			HTMLWriteLine("<td> Rate (%) </td>");
			HTMLWriteLine("</tr>");
			StepsBody();
			HTMLWriteLine("</table>");
			
		}
		
		private static void StepsBody() {
//			TestSummaryWriteLine("WelcomeScreen,Login_Admin," + Formatter.GetTime() + "," + Formatter.GetTime() + "," + "0 sec(s)," + "5,0,100");
//			TestSummaryWriteLine("WelcomeScreen,Login_Fail," + Formatter.GetTime() + "," + Formatter.GetTime() + "," + "0 sec(s)," + "5,0,100");
			if (Environment.TestSummary.length<1) {
				return;
			}
			for (int ctr = 0; ctr < Environment.TestSummary.length ; ctr++) {
				if (ctr % 2 == 0 || ctr == 0) {
					HTMLWriteLine("<tr class=\"step_odd\">");
				} else {
					HTMLWriteLine("<tr class=\"step_even\">");
				}
				List<String> summaryLine = Formatter.StringConvertCSVLineToArray(Environment.TestSummary[ctr]);
				for (String perItem : summaryLine ) {
					HTMLWriteLine("<td>" + perItem + "</td>");
				}
				HTMLWriteLine("</tr>");
			}
		}
		
		private static void SuiteHeader() {
			HTMLWriteLine("<br /> <div class=\"test_steps\">");
			HTMLWriteLine("<h2> Tests Suites </h2>");
			HTMLWriteLine("<table class = \"test_steps\">");
			HTMLWriteLine("<tr class=\"header\">");
			HTMLWriteLine("<td> Suite Name </td>");
			HTMLWriteLine("<td> Started </td>");
			HTMLWriteLine("<td> Ended </td>");
			HTMLWriteLine("<td> Elapsed (secs) </td>");
			HTMLWriteLine("</tr>");
			SuiteBody();
			HTMLWriteLine("</table>");
			
		}
		
		private static void PerformanceChart() {
			
			String labels = "";
			String data = "";
			String templine = "";
			if (Environment.Performance.length<1) {
				return;
			}
			
			for (int ctr = 0; ctr < Environment.Performance.length ; ctr++) {
				List<String> summaryLine = Formatter.StringConvertCSVLineToArray(Environment.Performance[ctr]);
				labels += "\"" + summaryLine.get(0) + "\",";
				data += summaryLine.get(1) + ",";
			}
			data = Formatter.StringRemoveLastChar(data);
			labels = Formatter.StringRemoveLastChar(labels);
			HTMLWriteLine("<span class=\"perfgraph\" align=\"center\">");
			HTMLWriteLine("<h3> Performance Summary</h3>");			
			HTMLWriteLine("<br />");			
			HTMLWriteLine("<canvas id=\"chart_performance\" width=\"400\" height=\"250\"></canvas>");			
			HTMLWriteLine("<script>");			
			HTMLWriteLine("var ctx = document.getElementById(\"chart_performance\");");			
			HTMLWriteLine("var chart_performance = new Chart(ctx, {");			
			HTMLWriteLine("type: 'line',");			
			HTMLWriteLine("data: {");
			templine = "labels: [" + labels +"],";
			HTMLWriteLine(templine); //
			HTMLWriteLine("datasets: [{");			
			HTMLWriteLine(" label: 'Time to complete(secs)',");			
			HTMLWriteLine(" borderColor: \"#0066CC\",");			
			HTMLWriteLine(" pointBorderWidth: 2,");			
			HTMLWriteLine(" pointHoverRadius: 5,");			
			HTMLWriteLine("");			
			templine = "data: [" + data + "]";
			HTMLWriteLine(templine); //
			HTMLWriteLine("}]");			
			HTMLWriteLine("},");			
			HTMLWriteLine("options: { ");			
			HTMLWriteLine("scales: {");			
			HTMLWriteLine("yAxes: [{");			
			HTMLWriteLine("ticks: {");			
			HTMLWriteLine("beginAtZero:true");			
			HTMLWriteLine("}");			
			HTMLWriteLine("}]");			
			HTMLWriteLine("}");			
			HTMLWriteLine("}");			
			HTMLWriteLine("});");			
			HTMLWriteLine("</script>");			
			HTMLWriteLine("</span>");			
			
			
			
		}
		
		private static void SuiteBody() {
//			SummaryWriteLine("WelcomeScreen," + Formatter.GetTime() + "," + Formatter.GetTime() + "," + "0" + ","  + "100");
//			SummaryWriteLine("WelcomeScreen," + Formatter.GetTime() + "," + Formatter.GetTime() + "," + "0" + ","  + "100");
//			SummaryWriteLine("WelcomeScreen," + Formatter.GetTime() + "," + Formatter.GetTime() + "," + "0" + ","  + "100");
			if (Environment.Summary.length<1) {
				return;
			}
			for (int ctr = 0; ctr < Environment.Summary.length ; ctr++) {
				if (ctr % 2 == 0 || ctr == 0) {
					HTMLWriteLine("<tr class=\"step_odd\">");
				} else {
					HTMLWriteLine("<tr class=\"step_even\">");
				}
				List<String> summaryLine = Formatter.StringConvertCSVLineToArray(Environment.Summary[ctr]);
				for (String perItem : summaryLine ) {
					HTMLWriteLine("<td>" + perItem + "</td>");
				}
				HTMLWriteLine("</tr>");
			}
		}
}
