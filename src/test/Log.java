package test;

import utils.*;

public class Log {
	public static void main(String[] args) {
		utils.Log.Console(Formatter.StringRemoveLastChar("Hello World!"));
		utils.Log.Console("Hello World!", 1);
		utils.Log.Console(Formatter.GetDateTime() + " Test");

	}
}
