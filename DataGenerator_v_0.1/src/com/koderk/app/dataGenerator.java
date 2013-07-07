package com.koderk.app;

import java.nio.charset.Charset;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.koderk.app.dataGenerator;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.*;

public class dataGenerator {

	private final String username = "root";
	private final String password = "1080levent";
	private final String ip = "localhost";
	private final int port = 3306;
	private final String database = "koderk";

	public static Connection con = null;

	public dataGenerator() throws IOException {
	}

	BufferedWriter out = new BufferedWriter(new FileWriter("demo_data.sql"));

	public void onStart() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.con = (Connection) DriverManager.getConnection("jdbc:mysql://"
					+ this.ip + ":" + this.port + "/" + this.database
					+ "?user=" + this.username + "&password=" + this.password
					+ "&database=" + this.database);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		java.lang.System.out.println("Connected to the Database Server..");

	}

	public void generate(int numberOfSample) throws SQLException {

		Statement stmt = (Statement) dataGenerator.con.createStatement();
		String sql = "drop table if exists stock";
		stmt.executeUpdate(sql);
		sql = "drop table if exists emp_satisfaction";
		stmt.executeUpdate(sql);
		sql = "drop table if exists invoice_row";
		stmt.executeUpdate(sql);
		sql = "drop table if exists employee";
		stmt.executeUpdate(sql);
		sql = "drop table if exists product";
		stmt.executeUpdate(sql);
		sql = "drop table if exists invoice";
		stmt.executeUpdate(sql);
		sql = "drop table if exists customer_contact";
		stmt.executeUpdate(sql);
		sql = "drop table if exists customer";
		stmt.executeUpdate(sql);

		sql = "CREATE TABLE Customer(TC_Vergi_No VARCHAR(11) NOT NULL, c_surname VARCHAR(15) NOT NULL,"
				+ "c_name VARCHAR(15),PRIMARY KEY (TC_Vergi_No))";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE Customer_Contact(contact_id INTEGER NOT NULL,cust_id VARCHAR(11) NOT NULL,e_posta VARCHAR(50),"
				+ "telefon VARCHAR(11) NOT NULL,faturaAdresi VARCHAR(255) NOT NULL,"
				+ "teslimatAdresi VARCHAR(255) NOT NULL,PRIMARY KEY(contact_id),"
				+ "FOREIGN KEY(cust_id) REFERENCES Customer(TC_Vergi_No))";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE Product(p_id INTEGER NOT NULL,p_name VARCHAR(30),alis_fiyati FLOAT,PRIMARY KEY (p_id))";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE employee(emp_id VARCHAR(11) NOT NULL,emp_surname VARCHAR(20) NOT NULL,emp_name VARCHAR(20),PRIMARY KEY (emp_id))";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE stock(prod_id INTEGER NOT NULL,quantity INTEGER,FOREIGN KEY (prod_id) REFERENCES product(p_id))";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE Invoice(i_id INTEGER,contact_id INTEGER NOT NULL,duzenleme_tarihi VARCHAR(19),"
				+ "PRIMARY KEY (i_id),FOREIGN KEY (contact_id) REFERENCES Customer_Contact(contact_id))";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE invoice_row(I_id INTEGER NOT NULL,product_id INTEGER NOT NULL,adet INTEGER, birim_satis_fiyati FLOAT,"
				+ "emp_id VARCHAR(11)NOT NULL,FOREIGN KEY(I_id) REFERENCES invoice(I_id),FOREIGN KEY(emp_id) REFERENCES employee(emp_id),"
				+ "FOREIGN KEY(product_id) REFERENCES product(p_id))";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE emp_satisfaction(emp_id VARCHAR(11) NOT NULL,i_id INTEGER NOT NULL,puan TINYINT NOT NULL,FOREIGN KEY(emp_id) REFERENCES employee(emp_id),"
				+ "FOREIGN KEY(i_id) REFERENCES invoice(I_id))";
		stmt.executeUpdate(sql);
		DataCustomer(numberOfSample);
		DataCustomerContact();
		DataProduct(numberOfSample);
		DataEmployee(numberOfSample);
		DataStock();
		DataInvoice();
		DataInvoiceRow();
		DataEmployeeSatisfaction();
		// Close the demo_data file
		try {
			this.out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TC Kimlik generator
	public String TC_Kimlik_Gen() {
		Random rand = new Random(System.currentTimeMillis());
		int pickedNumber = (1 + rand.nextInt(9)) * 10000 + rand.nextInt(10000);
		int pickedNumber2 = (1 + rand.nextInt(9)) * 100000
				+ rand.nextInt(100000);
		String pickedNumber_ = Integer.toString(pickedNumber);
		String pickedNumber2_ = Integer.toString(pickedNumber2);
		return pickedNumber_ + pickedNumber2_;
	}

	// String generator
	public String String_Gen(int size) {
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < random.nextInt(size) + 1; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}
// firstname Generator
	public String Name_Gen() {
		String randName = null;
		try {
			FileInputStream fis = new FileInputStream("sampleNames.txt"); 
	        InputStreamReader in = new InputStreamReader(fis, Charset.forName("ISO-8859-9"));
	        BufferedReader reader = new BufferedReader(in);
			LineNumberReader reader2 = new LineNumberReader(new FileReader(
					"sampleNames.txt"));
			int cnt = 0;
			String lineRead = "";
			List<String> nameArray = new ArrayList<String>();
			
			try {
				while ((lineRead = reader2.readLine()) != null) {
				}
				cnt = reader2.getLineNumber();
				reader2.close();
				int randNum = Int_Gen(cnt);
				String tmp= null;
				while ((tmp = reader.readLine()) != null) {
					nameArray.add(tmp);
				}
				reader.close();
				randName = nameArray.get(randNum);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return randName;
	}
	// surname generator
	public String Surname_Gen() {
		String randName = null;
		try {
			FileInputStream fis = new FileInputStream("sampleSurnames.txt"); 
	        InputStreamReader in = new InputStreamReader(fis, "UTF-8");
	        BufferedReader reader = new BufferedReader(in);
			LineNumberReader reader2 = new LineNumberReader(new FileReader(
					"sampleSurNames.txt"));
			int cnt = 0;
			String lineRead = "";
			List<String> surnameArray = new ArrayList<String>();
			
			try {
				while ((lineRead = reader2.readLine()) != null) {
				}
				cnt = reader2.getLineNumber();
				reader2.close();
				int randNum = Int_Gen(cnt);
				String tmp= null;
				while ((tmp = reader.readLine()) != null) {
					surnameArray.add(tmp);
				}
				reader.close();
				randName = surnameArray.get(randNum);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return randName;
	}
	//Adress Generator
	public String Adress_Gen() {
		String randName = null;
		try {
			FileInputStream fis = new FileInputStream("sampleAdress.txt"); 
	        InputStreamReader in = new InputStreamReader(fis, Charset.forName("ISO-8859-9"));
	        BufferedReader reader = new BufferedReader(in);
			LineNumberReader reader2 = new LineNumberReader(new FileReader(
					"sampleAdress.txt"));
			int cnt = 0;
			String lineRead = "";
			List<String> surnameArray = new ArrayList<String>();
			
			try {
				while ((lineRead = reader2.readLine()) != null) {
				}
				cnt = reader2.getLineNumber();
				reader2.close();
				int randNum = Int_Gen(cnt);
				String tmp= null;
				while ((tmp = reader.readLine()) != null) {
					surnameArray.add(tmp);
				}
				reader.close();
				randName = surnameArray.get(randNum);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return randName;
	}
	//Mail generator
	public String Mail_Gen() {
		String randName = null;
		try {
			FileInputStream fis = new FileInputStream("sampleEmail.txt"); 
	        InputStreamReader in = new InputStreamReader(fis, Charset.forName("ISO-8859-9"));
	        BufferedReader reader = new BufferedReader(in);
			LineNumberReader reader2 = new LineNumberReader(new FileReader(
					"sampleEmail.txt"));
			int cnt = 0;
			String lineRead = "";
			List<String> surnameArray = new ArrayList<String>();
			
			try {
				while ((lineRead = reader2.readLine()) != null) {
				}
				cnt = reader2.getLineNumber();
				reader2.close();
				int randNum = Int_Gen(cnt);
				String tmp= null;
				while ((tmp = reader.readLine()) != null) {
					surnameArray.add(tmp);
				}
				reader.close();
				randName = surnameArray.get(randNum);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return randName;
	}
	//ProductName Generator
	public String Product_Gen() {
		String randName = null;
		try {
			FileInputStream fis = new FileInputStream("sampleProduct.txt"); 
	        InputStreamReader in = new InputStreamReader(fis, Charset.forName("ISO-8859-9"));
	        BufferedReader reader = new BufferedReader(in);
			LineNumberReader reader2 = new LineNumberReader(new FileReader(
					"sampleProduct.txt"));
			int cnt = 0;
			String lineRead = "";
			List<String> surnameArray = new ArrayList<String>();
			
			try {
				while ((lineRead = reader2.readLine()) != null) {
				}
				cnt = reader2.getLineNumber();
				reader2.close();
				int randNum = Int_Gen(cnt);
				String tmp= null;
				while ((tmp = reader.readLine()) != null) {
					surnameArray.add(tmp);
				}
				reader.close();
				randName = surnameArray.get(randNum);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return randName;
	}
	
	// TelephoneNumber Generator
	public String PhoneNo_Gen() {
		Random rand = new Random(System.currentTimeMillis());
		int pickedNumber = (1 + rand.nextInt(9)) * 10000 + rand.nextInt(10000);
		int pickedNumber2 = (1 + rand.nextInt(9)) * 1000
				+ rand.nextInt(1000);
		String pickedNumber_ = Integer.toString(pickedNumber);
		String pickedNumber2_ = Integer.toString(pickedNumber2);
		return "05" + pickedNumber_ + pickedNumber2_;
	}
	// Double generator
	public Double Double_Gen() {
		Random rand = new Random(System.currentTimeMillis());
		return rand.nextDouble();
	}
	//Price Gen
	public float Price_Gen(int max) {
		Random rand = new Random(System.currentTimeMillis());
		float num = (rand.nextInt(max) + 0);
		return num/100;
	}
	// Int- quantity- generator
	public int Int_Gen(int max) {
		Random rand = new Random(System.currentTimeMillis());
		return (rand.nextInt(max) + 0);
	}

	public void DataCustomer(int size) {
		for (int i = 0; i < size; i++) {
			// For table CUSTOMER
			Statement stmt;
			String TC_No = TC_Kimlik_Gen();
			// surname generator
			String surname = Surname_Gen();
			// name generator
			String name = Name_Gen();
			try {
				stmt = (Statement) dataGenerator.con.createStatement();
				String sql = "insert into customer values('" + TC_No + "', '"
						+ surname + "', '" + name + "')";
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				this.out.write("INSERT INTO customer VALUES('" + TC_No + "', '"
						+ surname + "', '" + name + "')" + "\n");
			} catch (IOException e) {
			}

		}
	}

	public void DataCustomerContact() {
		Statement stmt;
		int contact_id = 0;
		try {
			stmt = (Statement) dataGenerator.con.createStatement();
			String sql = "select TC_Vergi_No from customer";
			ResultSet rs = stmt.executeQuery(sql);
			String foreignKey = "";
			while (rs.next()) {
				contact_id++;
				foreignKey = rs.getString("TC_Vergi_No");
				String e_mail = Mail_Gen();
				String phone = PhoneNo_Gen();
				String fatura_adres = Adress_Gen();
				String teslimat_adres = Adress_Gen();
				try {
					stmt = (Statement) dataGenerator.con.createStatement();
					String sql2 = "insert into customer_contact values('"
							+ contact_id + "', '" + foreignKey + "', '"
							+ e_mail + "'," + " '" + phone + "', '"
							+ fatura_adres + "', '" + teslimat_adres + "' )";
					stmt.executeUpdate(sql2);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					this.out.write("insert into customer_contact values('"
							+ contact_id + "', '" + foreignKey + "', '"
							+ e_mail + "'," + " '" + phone + "', '"
							+ fatura_adres + "', '" + teslimat_adres + "' )"
							+ "\n");
				} catch (IOException e) {
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void DataProduct(int size) {
		int p_id = 0;
		for (int i = 0; i < size; i++, p_id++) {
			Statement stmt;
			String p_name = Product_Gen();
			float alis_fiyati =  Price_Gen(10000);
			try {
				stmt = (Statement) dataGenerator.con.createStatement();
				String sql = "insert into product values('" + p_id + "', '"
						+ p_name + "', '" + alis_fiyati + "')";
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				this.out.write("INSERT INTO product VALUES('" + p_id + "', '"
						+ p_name + "', '" + alis_fiyati + "')" + "\n");
			} catch (IOException e) {
			}
		}
	}

	public void DataEmployee(int size) {
		for (int i = 0; i < size; i++) {
			Statement stmt;
			String emp_id = TC_Kimlik_Gen();
			String emp_surname = Surname_Gen();
			String emp_name = Name_Gen();
			try {
				stmt = (Statement) dataGenerator.con.createStatement();
				String sql = "insert into employee values('" + emp_id + "', '"
						+ emp_surname + "', '" + emp_name + "')";
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				this.out.write("INSERT INTO employee VALUES('" + emp_id
						+ "', '" + emp_surname + "', '" + emp_name + "')"
						+ "\n");
			} catch (IOException e) {
			}
		}
	}

	public void DataStock() {
		Statement stmt;
		try {
			stmt = (Statement) dataGenerator.con.createStatement();
			String sql = "select p_id from product";
			ResultSet rs = stmt.executeQuery(sql);
			String prod_id = "";
			while (rs.next()) {
				prod_id = rs.getString("p_id");
				int quantity = Int_Gen(5000);
				try {
					stmt = (Statement) dataGenerator.con.createStatement();
					String sql2 = "insert into stock values('" + prod_id
							+ "', '" + quantity + "' )";
					stmt.executeUpdate(sql2);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					this.out.write("INSERT INTO stock VALUES('" + prod_id
							+ "', '" + quantity + "' )" + "\n");
				} catch (IOException e) {
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void DataInvoice() {
		int i_id = 0;
		Statement stmt;
		try {
			stmt = (Statement) dataGenerator.con.createStatement();
			String sql = "select contact_id from customer_contact";
			ResultSet rs = stmt.executeQuery(sql);
			String contact_id = "";
			while (rs.next()) {
				i_id++;
				contact_id = rs.getString("contact_id");
				String date = "2013-03-23 15:28:06";
				// DateFormat dateFormat = new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// Date date = new Date();
				// dateFormat.format(date);
				try {
					stmt = (Statement) dataGenerator.con.createStatement();
					String sql2 = "insert into invoice values('" + i_id
							+ "', '" + contact_id + "', '" + date + "' )";
					stmt.executeUpdate(sql2);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					this.out.write("INSERT INTO invoice VALUES('" + i_id
							+ "', '" + contact_id + "', '" + date + "' )"
							+ "\n");
				} catch (IOException e) {
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void DataInvoiceRow() {
		try {
			Statement stmt = (Statement) dataGenerator.con.createStatement();
			Statement stmt2 = (Statement) dataGenerator.con.createStatement();
			Statement stmt3 = (Statement) dataGenerator.con.createStatement();
			String sql = "select i_id from invoice";
			ResultSet rs = stmt.executeQuery(sql);
			String sql2 = "select p_id from product";
			ResultSet rs2 = stmt2.executeQuery(sql2);
			String sql3 = "select emp_id from employee";
			ResultSet rs3 = stmt3.executeQuery(sql3);

			String I_id;
			String product_id;
			String emp_id;

			while (rs.next()) {
				rs2.next();
				rs3.next();
				I_id = rs.getString("i_id");
				product_id = rs2.getString("p_id");
				emp_id = rs3.getString("emp_id");
				int adet = Int_Gen(5000);
				float birim_satis_fiyati = Price_Gen(10000);
				try {
					Statement stmt4 = (Statement) dataGenerator.con
							.createStatement();
					String sql4 = "insert into invoice_row values('" + I_id
							+ "', '" + product_id + "', '" + adet + "', '"
							+ birim_satis_fiyati + "', '" + emp_id + "' )";
					stmt4.executeUpdate(sql4);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					this.out.write("insert into invoice_row values('" + I_id
							+ "', '" + product_id + "', '" + adet + "', '"
							+ birim_satis_fiyati + "', '" + emp_id + "' )"
							+ "\n");
				} catch (IOException e) {
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void DataEmployeeSatisfaction() {
		try {
			Statement stmt = (Statement) dataGenerator.con.createStatement();
			Statement stmt2 = (Statement) dataGenerator.con.createStatement();
			String sql = "select emp_id from employee";
			ResultSet rs = stmt.executeQuery(sql);
			String sql2 = "select i_id from invoice";
			ResultSet rs2 = stmt2.executeQuery(sql2);

			String emp_id;
			String i_id;
			while (rs.next()) {
				rs2.next();
				emp_id = rs.getString("emp_id");
				i_id = rs2.getString("i_id");
				int puan = Int_Gen(10);
				try {
					Statement stmt3 = (Statement) dataGenerator.con
							.createStatement();
					String sql3 = "insert into emp_satisfaction values('"
							+ emp_id + "', '" + i_id + "', '" + puan + "' )";
					stmt3.executeUpdate(sql3);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					this.out.write("INSERT INTO emp_satisfaction VALUES('"
							+ emp_id + "', '" + i_id + "', '" + puan + "' )"
							+ "\n");
				} catch (IOException e) {
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
