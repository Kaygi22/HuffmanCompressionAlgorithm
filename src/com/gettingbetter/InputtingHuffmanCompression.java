package com.gettingbetter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author El Mehdi
 *
 *         We will try to get things better, and creating a better version.
 */

public class InputtingHuffmanCompression {

	public static void main(String[] args) throws IOException {
		
		String res = "111100000000000000000000000000000000000000000000000000000";
		
		char[] chars = res.toCharArray();
		char cache = chars[0];
		boolean start = true;
		String result = "";
		int counter = 0;
		for (int i = 0; i < chars.length; i++) {
			if(chars[i]==cache && i<chars.length-1) {
				counter++;
			}else {
				if(cache=='1')
					result += counter+" ";
				else
					result += "-"+counter+" ";
				
				counter=1;
				cache = chars[i];
			}
		}
		System.out.println(res);
		System.out.println(result);
		/*

		try {
		      FileInputStream fis = new FileInputStream(new File("data.dat"));
		      char current;
		      long zero = 0;
		      boolean start = true;
		      char cache;
		      while (fis.available() > 0) {
		        current = (char) fis.read();
//		        if(current == '0')
//		        	System.out.println(++zero);
		        //System.out.print(current);
		      }

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		*/

	}

}
