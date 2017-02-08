package utils;

import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import com.opencsv.CSVReader;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;


public class Formatter {
	private static String FormatTime = Environment.ReturnTimeFormat();
	private static String FormatDate = Environment.ReturnDateFormat();
	private static String FormatStamp = Environment.ReturnStampFormat();
	
	
	
	public static String GetDate() {
		DateFormat df = new SimpleDateFormat(FormatDate);
		Date date = new Date();
		return df.format(date);
	}

	public static String GetTime() {
		DateFormat df = new SimpleDateFormat(FormatTime);
		Date time = new Date();
		return df.format(time);
	}

	
	public static String GetDateTime() {
		return GetDate() + " " + GetTime();
	}
	
	public static String GetStamp(){
		DateFormat df = new SimpleDateFormat(FormatStamp);
		Date stamp = new Date();
		return df.format(stamp);
	}

	
	
	public static String StringRemoveLastChar(String text) {
		return text.substring(0, text.length() - 1);
	}
	
	public static List<String> StringConvertCSVLineToArray(String csvLine) {
		return Arrays.asList(csvLine.split("\\s*,\\s*"));
	}
	
	public static LinkedHashMap<String, String> ConvertCSVToMap(String csvFile) {
		return ConvertCSVToMap(csvFile, 1);
	}
	
	public static LinkedHashMap<String, String> ConvertCSVToMap(String csvFile, int column) {
		try {
			if (FileHandling.CheckExistence(csvFile)) {
				CSVReader csvReader = new CSVReader(new FileReader(csvFile));
				String [] rows;
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				while ((rows = csvReader.readNext()) != null) {
					map.put(rows[0], rows[column]);
				}
				csvReader.close();
				return map;
			} else {
				Log.Console("Could not find the CSV file: " + csvFile);
			}
		} catch (Exception e){
			Log.ExceptionError(e);
		}
		return null;
	}
	
	public static void LogMap(String MapName, LinkedHashMap<String, String> map) {
		String val;
		Log.Console("==Map " + MapName + "==");
		for (String key : map.keySet()) {
	    val = map.get(key);
	    Log.Console(key + " - Value: " + val);
		}	
	}
	
	public static List<String[]> ConvertCSVToArray(String csvfile) {
		CsvParserSettings parserSettings = new CsvParserSettings();
		parserSettings.getFormat().setLineSeparator("\n");
		CsvParser parser = new CsvParser(parserSettings);
		try {
			if (FileHandling.CheckExistence(csvfile)) {
				return parser.parseAll(new FileReader(csvfile));
			} else {
				Log.Console("Could not find the CSV file: " + csvfile);
			}
		} catch (Exception e){
			Log.ExceptionError(e);
		}
		return null;
	}
	

}
