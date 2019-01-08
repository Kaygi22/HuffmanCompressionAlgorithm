package com.zip;

import java.util.Comparator;

public class Complarator implements Comparator<HuffmanNode> {
	
	public int compare(HuffmanNode x, HuffmanNode y) {

		return x.data - y.data;
	}
	
}
