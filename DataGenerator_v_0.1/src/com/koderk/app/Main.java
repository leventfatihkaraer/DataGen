package com.koderk.app;

import java.io.IOException;
import java.sql.SQLException;


import com.koderk.app.Starter;
import com.koderk.app.dataGenerator;

public class Main {
	public static void main(String args[]) throws IOException{
		Starter st = new Starter ();
        dataGenerator system_handler = st.getSystemHandler();
        system_handler.onStart(); 
        try {
        	System.out.println("Generating the data...");
			system_handler.generate(300);
			System.out.println("Your data is generated.");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
