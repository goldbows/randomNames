package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	public static void main(String[] args) {

		try {
			printToTheSQLFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//printToTheCSVFile();


	}

	public static void printToTheSQLFile() throws FileNotFoundException {

		PrintWriter out = new PrintWriter("/usr/apps/fileRead/sqlNamesForInsert.txt");

		for (int i = 0; i<= 100000; i++) {
			Person person = new Person();
			String passportNo = randomString("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray(), 8);
			Integer idNumber = (1111111111 + (int)(Math.random() * 999999999));
			Integer date = (i%29+1);

			System.out.println(person.givenName + " " + person.surname);

			out.println("Insert into T_PAX_INFO (ID,PAX_TYPE,TITLE,FIRST_NAME,LAST_NAME,DOB,NATINALITY_ID,ID_NUM,PASSPORT_NUM) values (SEQ_T_PAX_INFO.nextval,'ADT','MR','"+person.givenName+"','"+person.surname+"',to_timestamp('"+date.toString()+"-MAR-00 12.00.00.000000000 AM','DD-MON-RR HH.MI.SSXFF AM'),193,'"+idNumber.toString()+"','"+passportNo+"');");
		}
	}

	public static String randomString(char[] characterSet, int length) {
		Random random = new SecureRandom();
		char[] result = new char[length];
		for (int i = 0; i < result.length; i++) {
			// picks a random index out of character set > random character
			int randomCharIndex = random.nextInt(characterSet.length);
			result[i] = characterSet[randomCharIndex];
		}
		return new String(result);
	}

	public static void printToTheCSVFile() {
		BufferedReader br = null;
		BufferedReader brLast = null;
		String ibeAgent = "IBE";

		try {

			String firstName;
			String lastName;
			List<String> firstNameList = new ArrayList<String>();
			List<String> lastNameList = new ArrayList<String>();

			br = new BufferedReader(new FileReader("/usr/apps/fileRead/firstNames.txt"));
			brLast = new BufferedReader(new FileReader("/usr/apps/fileRead/lastNames.txt"));
			PrintWriter out = new PrintWriter("/usr/apps/fileRead/appendedNamesIBE.txt");
			PrintWriter outAgent = new PrintWriter("/usr/apps/fileRead/appendedNamesAGENT.txt");

			while (((firstName = br.readLine()) != null) && (lastName = brLast.readLine()) != null) {
				firstNameList.add(firstName);
				lastNameList.add(lastName);

				outAgent.println("\""+lastName+"\"" +",");
			}

/*			if (ibeAgent.equals("IBE")) {
				for (int i=0; i<firstNameList.size() ; i++) {
					out.println(
						"\"[{\"\"sequence\"\":1,\"\"paxType\"\":\"\"ADT\"\",\"\"additionalInfo\"\":{\"\"passportNumber\"\": \"\"fmvVxwC1ATMYG0o\"\",\"\"identityCardNumber\"\": \"\"8905265412\"\",\"\"passportCountryID\"\": 239,\"\"passportExpiryDateStr\"\": \"\"02/07/2018\"\"},\"\"title\"\": \"\"Mr\"\",\"\"passengerTitleID\"\": \"\"1\"\",\"\"firstName\"\":\"\"" +
							firstNameList.get(firstNameList.size()-i-1).trim() + "\"\",\"\"middleName\"\":\"\"\"\",\"\"lastName\"\":\"\"" + lastNameList.get(i).trim() + "\"\",\"\"dateOfBirthStr\"\":\"\"16/04/1957\"\",\"\"gender\"\":\"\"M\"\",\"\"nationalityID\"\":4,\"\"ffpNumber\"\": \"\"325\"\",\"\"localForeign\"\":\"\"Local\"\"}]\"");
				}
			}

			if (ibeAgent.equals("AGENT")) {

				for (int i=0; i<firstNameList.size() ; i++) {
					outAgent.println(
						"\"[{\"\"sequence\"\":1,\"\"paxType\"\":\"\"ADT\"\",\"\"tba\"\":\"\"N\"\",\"\"additionalInfo\"\":{\"\"passportNumber\"\": \"\"fmvVxwC1ATMYG0o\"\",\"\"identityCardNumber\"\": \"\"8905265412\"\",\"\"passportCountryID\"\": 239,\"\"passportExpiryDateStr\"\": \"\"02/07/2018\"\"},\"\"passengerTitleID\"\": \"\"1\"\",\"\"firstName\"\":\"\""
							+ lastNameList.get(firstNameList.size()-i-1).trim() + "\"\",\"\"lastName\"\":\"\"" + firstNameList.get(i).trim() + "\"\",\"\"dateOfBirthStr\"\":\"\"16/04/1957\"\",\"\"gender\"\":\"\"M\"\",\"\"nationalityID\"\":4,\"\"ffpNumber\"\": \"\"325\"\"}]\"");
				}

			}*/



		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
