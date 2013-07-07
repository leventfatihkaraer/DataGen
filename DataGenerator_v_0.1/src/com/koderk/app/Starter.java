package com.koderk.app;

import java.io.IOException;

public class Starter {
	public static dataGenerator getSystemHandler() throws IOException {
		dataGenerator system_handler = null;

		/******************************************************************
		 * Create new instance of the class that implements System interface and
		 * assign it to system_handler variable.
		 ******************************************************************/
		system_handler = (dataGenerator) new dataGenerator();

		return system_handler;
	}
}

