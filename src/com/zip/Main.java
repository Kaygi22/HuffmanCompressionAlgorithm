package com.zip;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

/**
 * 
 * @author El Mehdi
 * @gitHub kaygi22
 * @Description Hiffman compression algorithm
 *
 */
public class Main {

	public static BufferedWriter key;
	public static BufferedWriter binary;

	public static void main(String[] args) throws IOException {

		key = new BufferedWriter(new FileWriter("key.txt"));
		binary = new BufferedWriter(new FileWriter("alice29.dat"));


		HashMap<Character, Integer> map = new HashMap<>();
		String RES = "";

		try {
			FileInputStream fis = new FileInputStream(new File("alice29.txt"));
			char current;
			while (fis.available() > 0) {
				current = (char) fis.read();
				binary.write(Integer.toBinaryString(current));
				RES += current;
				if (map.containsKey(current)) {
					map.put(current, map.get(current) + 1);
				} else {
					map.put(current, 1);
				}

			}

			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<Character, Integer> sortedMapDesc = sortByComparator(map);
		// printMap(sortedMapDesc);

		System.out.println("Dictionnary");

		String binaryCode = "0";
		int counter = 0;
		for (Map.Entry<Character, Integer> entry : sortedMapDesc.entrySet()) {
			// binaryCode = generateUniqueCode(counter);
			// dictionnary.put(entry.getKey(), binaryCode);
			// counter++;
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}

		int numberOfCharsInTheText = sortedMapDesc.size();
		System.out.println("Size of hashmap: " + numberOfCharsInTheText);
		PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(numberOfCharsInTheText, new Complarator());
		// Creating nodes
		for (Map.Entry<Character, Integer> entry : sortedMapDesc.entrySet()) {
			HuffmanNode node = new HuffmanNode();
			node.character = entry.getKey();
			node.data = entry.getValue();
			node.left = null;
			node.right = null;

			q.add(node);

		}

		HuffmanNode root = null;

		while (q.size() > 1) {

			HuffmanNode x = q.peek();
			q.poll();
			HuffmanNode y = q.peek();
			q.poll();
			HuffmanNode f = new HuffmanNode();
			f.data = x.data + y.data;
			f.character = '-';
			f.left = x;
			f.right = y;
			root = f;
			q.add(f);

			//System.out.println("Data: " + f.data + " Chars: " + f.character + " left: " + f.left.toString() + " right: "
			//		+ f.right.toString());
		}

		printCode(root, "", sortedMapDesc);
		key.close();

		Map<Character, String> dictionnary = new HashMap<Character, String>();

		BufferedReader br = new BufferedReader(new FileReader("key.txt"));
		String str;
		str = br.readLine();

		while (str != null) {
			System.out.println("Scanned string: " + str);
			if(!"".equals(str)) {
				char[] res = str.toCharArray();
				Character charac = str.toCharArray()[0];
				if(charac != ':' && charac != null) {
					String[] result = str.split(":");
					dictionnary.put(result[0].toCharArray()[0], result[1]);
				}
			}
			

			str = br.readLine();
		}

		System.out.println("Dict" + dictionnary);

		try {
			FileOutputStream out = new FileOutputStream("data.dat");
			String currentCode;
			char[] messChar = RES.toCharArray();
			char[] curr;
			for (int i = 0; i < messChar.length; i++) {
				currentCode = dictionnary.get(messChar[i]);
				System.out.println("Curr code: " + currentCode);
				if(currentCode != null) {
					curr = currentCode.toCharArray();
					for (int j = 0; j < curr.length; j++) {
						out.write(Byte.valueOf((byte) curr[j]));

					}

				}
				
			}

			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void printCode(HuffmanNode root, String s, Map<Character, Integer> sortedMapDesc) throws IOException {

		// base case; if the left and right are null
		// then its a leaf node and we print
		// the code s generated by traversing the tree.
		if (root.left == null && root.right == null && sortedMapDesc.containsKey(root.character)) {

			// c is the character in the node
			// System.out.println(root.character + ":" + s);
			key.write(root.character + ":" + s);
			key.newLine();

			return;
		}

		// if we go to left then add "0" to the code.
		// if we go to the right add"1" to the code.

		// recursive calls for left and
		// right sub-tree of the generated tree.
		printCode(root.left, s + "0", sortedMapDesc);
		printCode(root.right, s + "1", sortedMapDesc);
	}

	private static String generateUniqueCode(int counter) {
		String result = "0";
		while (counter > 0) {
			result = "1" + result;
			counter--;
		}
		return result;
	}

	/**
	 * 
	 * @param unsortMap
	 * @return sorted map by values
	 */
	public static Map<Character, Integer> sortByComparator(Map<Character, Integer> unsortMap) {

		List<Entry<Character, Integer>> list = new LinkedList<Entry<Character, Integer>>(unsortMap.entrySet());

		Collections.sort(list, new MyComparator());

		Map<Character, Integer> sortedMap = new LinkedHashMap<Character, Integer>();
		for (Entry<Character, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	/**
	 * Printing the map of keys
	 * 
	 * @param mp
	 */

	public static void printMap(Map<Character, Integer> mp) {
		Iterator it = mp.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}
	}

}

class MyComparator implements Comparator<Entry<Character, Integer>> {
	public int compare(Entry<Character, Integer> o1, Entry<Character, Integer> o2) {
		return o1.getValue().compareTo(o2.getValue());
	}
}
