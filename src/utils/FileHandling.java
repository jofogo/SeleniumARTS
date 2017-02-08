package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class FileHandling {
	public static String PathFile;
	
	public static String[] Read(String pathFile) throws IOException {
		
		BufferedReader bufferedReader = null;
		String[] fileContents = new String[0];
		try {
			String line;
			bufferedReader = new BufferedReader(new FileReader(pathFile));
			while ((line = bufferedReader.readLine()) != null) {
				fileContents = Arrays.copyOf(fileContents, fileContents.length+1);
				fileContents[fileContents.length-1] = line;
			}
		} catch (Exception e) {
			Log.ExceptionError(e);
			return null;
		} finally {
			bufferedReader.close();
		}
		return fileContents;
	}
	
//	public static void Append() {
//		
//	}
	
	public static void Write(String pathFile, String[] contents) {
		try {
			PrintWriter pw = new PrintWriter(pathFile, "UTF-8");
			for (String line : contents) {
				
				pw.println(line);
			}
			pw.close();
		} catch (IOException ioe) {
			Log.ExceptionError(ioe);
		}
	}
	
//	public static void Delete() {
//		
//	}
	
	public static void SetPaths(String path) {
		try {
			if(CheckExistence(path)) {
				PathFile=path;
			} else {
				
			}
		} catch (NullPointerException npe) {
			Log.ExceptionError(npe);
			Log.Console("Could not set the path to :" + path);
		}
	}
	
	public static boolean CheckExistence(String path) {
		File fORp = new File(path);
		if (fORp.exists()) {
			Log.Debug(path + " exists.");
			return true;
		} else {
			Log.Debug(path + " does not exist!");
		}
		return false;
	}
	

	

}
