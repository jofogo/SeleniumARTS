package utils;

import java.util.LinkedHashMap;
import java.util.List;

public class Maps {
	public static LinkedHashMap<String, String> Actions = new LinkedHashMap<String, String>();
	public static LinkedHashMap<String, String> ActionDescriptions = new LinkedHashMap<String, String>();
	public static LinkedHashMap<String, String> Status = new LinkedHashMap<String, String>();
	public static LinkedHashMap<String, String> Configs;
	public static LinkedHashMap<String, String> Tests;
//	public static LinkedHashMap<String, String> TestList;
	public static List<String []> TestList;
	public static LinkedHashMap<String, String> TestSteps;
	public static LinkedHashMap<String, String> TestObjects;
	public static LinkedHashMap<String, String> TestValues;
	
	private static void SetStatusMap() {
		Status.put("0", "Pass");
		Status.put("1", "Warning");
		Status.put("2", "Fail");
		Status.put("3", "Skipped");
		Status.put("4", "Fail - Object not found");
		Status.put("5", "Fail - List item not found");
	}
	
	private static void SetActionDescriptions() {
		ActionDescriptions.put("NAVIGATE", "Navigate to URL ");
		ActionDescriptions.put("CLICK", "Click ");
		ActionDescriptions.put("TYPE", "Type ");
		ActionDescriptions.put("VALIDATE", "Validate ");
		ActionDescriptions.put("SELECT", "Select ");
	}
	
	private static void SetActions() {
		Actions.put("navigate", "0");
		Actions.put("click", "1");
		Actions.put("type", "2");
		Actions.put("validate", "3");
//		System.out.println(Actions.keySet());
	}
	
	public static void InitializeMaps() {
		SetStatusMap();
		SetActions();
		SetActionDescriptions();
	}
	
	public static void ClearTestMaps() {
		TestObjects.clear();
		//TestValues.clear();
		//TestSteps.clear();
	}
	
	public static void GetTestListMap() {
//		TestList=Formatter.ConvertCSVToMap(Environment.FileMaster);
		TestList=Formatter.ConvertCSVToArray(Environment.FileMaster);
		//Formatter.LogMap("Test List", TestList);
	}
	
	public static void GetTestObjectMap() {
		TestObjects=Formatter.ConvertCSVToMap(Environment.FileData);
		Formatter.LogMap("Test Objects", TestObjects);
	}

	public static void GetTestDataMap(int TestCaseNumber) {
		TestValues=Formatter.ConvertCSVToMap(Environment.FileData, TestCaseNumber);
		Formatter.LogMap("Test Values", TestValues);
	}
	public static void GetTestStepsMap() {
		TestSteps=Formatter.ConvertCSVToMap(Environment.FileData, 2);
		Formatter.LogMap("Test Steps", TestSteps);
	}

	public static String GetKeyFromValue(LinkedHashMap<String, String> map, String value) {
		int keysFound = 0;
		if (map.containsValue(value)) {
			for (String key : map.keySet()) {
				if (!value.isEmpty() && map.get(key) == value ) {
					Log.Console("Key: " + key + " found for value: " + value);
					keysFound++;
					return key;
				}
			}
			if (keysFound == 0) {
				if (value.isEmpty()) {
					Log.Console("No key found for blank value");
				} else {
					Log.Console("No key found for value: " + value);
				}
				return "";
			}
		} else {
			Log.Console("Value: " + value + " is not in the map!");
		}
		return "";
	}
	
}
