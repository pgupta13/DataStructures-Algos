

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
/**
 * @author Prachi Gupta G01025257
 * INFS 519
 * Fall 2016
 */

/**
 * Huffman compression and decompression is a type of encoding and decoding
 * algorithm where input can be assigned a code each character wise in a trie
 * and then can be retrieved following the same code
 */
public class Huffman {
	// first part is to determine the frequency of each character in the given
	// input
	public static int[] determineFrequencies(char[] text) {
		// create an array of size of extended ASCII characters
		int[] frequency = new int[RADIX];

		// now for every charcter according to its order in ASCII table,
		// increment the value in table for index as the order of ASCII char
		for (int i = 0; i < text.length; i++) {

			char current = text[i];

			frequency[(int) current] += 1;

		}
		return frequency;
	}

	/**
	 * for a given char array, compress the input accordin tto HUffman encoding
	 * algo
	 */
	public static byte[] compress(char[] text) {
		// create a out stream to write bit
		BitStreamOutput out = new BitStreamOutput();
		// determine frequency of each character of given text
		int[] frequencyOfChars = determineFrequencies(text);
		// create a trie of frequency array
		Node node = makeTrie(frequencyOfChars);
		// now build code for each char
		String[] st = new String[RADIX];
		// assign code for every char in the input string
		makeCodingTable(st, node, "");

		// write the trie to out stream
		writeTrie(node, out);

		out.writeBits(text.length, 31);

		for (int i = 0; i < text.length; i++) {
			String code = st[text[i]];
			for (int j = 0; j < code.length(); j++) {

				if (code.charAt(j) == '0') {

					out.writeBit(false);
				}

				else if (code.charAt(j) == '1') {

					out.writeBit(true);
				} else
					throw new IllegalStateException("Illegal state");
			}
		}

		byte[] byt = out.toArray();
		// System.out.println(byt);
		return byt;
	}

	/**
	 * after passing a trie node and a table of size RADIX, we create a coding
	 * table where each code is assigned a code
	 */
	public static void makeCodingTable(String[] table, Node x, String code) {
		// check if the node is leaf, then ASCII value of that symbol gets the
		// value of code
		if (x.isLeaf()) {
			table[x.symbol] = code;

		} // if not a leaf, call recursively, if for a left(0) or a right(1) by
			// the function
		else if (!x.isLeaf()) {
			makeCodingTable(table, x.left, code + '0');
			makeCodingTable(table, x.right, code + '1');
		}
	}

	/**
	 * a trie is made after calculating the frequency of of the chars in input.
	 * this function uses min Heap context to merge two trees of least weight
	 * together
	 */
	public static Node makeTrie(int[] freq) {
		// create a minHeap of type Node
		MinHeap<Node> pqelement = new MinHeap<>(RADIX);
		for (char i = 0; i < RADIX; i++)
			// for every char that has a freq greater than 1, gets inserted in
			// minHeap
			if (freq[i] > 0)
				pqelement.insert(new Node(i, freq[i]));

		// when only one character with a nonzero frequency
		if (pqelement.size() == 1) {
			if (freq['\0'] == 0)
				pqelement.insert(new Node('\0', 0));
			else
				pqelement.insert(new Node('\1', 0));
		}

		// merging two smallest weighting trees
		while (pqelement.size() > 1) {
			Node left = pqelement.delMin();
			Node right = pqelement.delMin();
			Node parent = new Node('\0', left, right,

					left.freq + right.freq);
			pqelement.insert(parent);
		}
		return pqelement.delMin();

	}

	/**
	 * decmpression of the input bit stream is other part of Huffman coding,
	 * using the sequence of each bit, the char is found out
	 */
	public static char[] decompress(BitStreamInput in) {
		// MODIFY CODE
		
		Node actualnode = readTrie(in);

		// size of bytes to be printed
		int size = in.readBits(31);
		//string in which out of decompressed input will come
		String afterDecompression = "";
		BitStreamOutput out = new BitStreamOutput();
		//check if a leaf of not
		for (int i = 0; i < size; i++) {
			Node node = actualnode;
			while (!node.isLeaf()) {

				//reads bit as 1, then its a right node of parent node
				if (in.readBit()) {
					node = node.right;
				} else {
					// else if the read bit is 0, then left node of parent node
					node = node.left;
				}
			}
			// insert the symbol to string
			afterDecompression = afterDecompression + node.symbol;
		}
		//convert to return type of function , char array and return
		return afterDecompression.toCharArray();
	}

	// ----- DO NOT MODIFY ANYTHING BELOW THIS LINE -----//

	public static final int RADIX = 256; // number of symbols for extended ascii

	private static class Node implements Comparable<Node> {
		private Node left;
		private Node right;
		private char symbol;
		private int freq;

		public Node(char c, int freq) {
			this(c, null, null, freq);
		}

		public Node(char symbol, Node left, Node right, int freq) {
			this.symbol = symbol;
			this.left = left;
			this.right = right;
			this.freq = freq;
		}

		public Node getLeft() {
			return this.left;
		}

		public Node getRight() {
			return this.right;
		}

		public char getSymbol() {
			return this.symbol;
		}

		public int getFreq() {
			return this.freq;
		}

		public boolean isLeaf() {
			return this.left == null && this.right

			== null;
		}

		public int compareTo(Node other) {
			return this.freq - other.freq;
		}

		@Override
		public String toString() {
			return "(" + this.symbol + " " + freq + ")";
		}
	}

	public static void writeTrie(Node x, BitStreamOutput out) {
		// Use preorder traversal to encode the trie
		if (x.isLeaf()) {
			out.writeBit(true);
			out.writeBits(x.symbol, 8);
			return;
		}
		out.writeBit(false);
		writeTrie(x.left, out);
		writeTrie(x.right, out);
	}

	public static Node readTrie(BitStreamInput in) {
		boolean bit = in.readBit();
		if (bit) {
			char symbol = (char) in.readBits(8);
			return new Node(symbol, 0);
		}
		Node internalNode = new Node('\0', 0);
		internalNode.left = readTrie(in);
		internalNode.right = readTrie(in);

		return internalNode;
	}

	public static void printTable(String[] table) {
		for (int i = 0; i < table.length; i++) {
			String code = table[i];
			if (code != null)
				Stdio.println("" + ((char) i) + " = " + code);
		}
	}

	/**
	 * Unit tests the Huffman compression/decompression algorithm.
	 * 
	 * @param args
	 * @throws java.io.IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			String u = "Usage: Huffman <+|-> <filename>";
			Stdio.println(u);
			return;
		}

		String option = args[0];
		String filename = args[1];
		if (option.equals("-")) {
			BufferedReader fileIn = new BufferedReader(new FileReader

			(filename));
			StringBuilder buf = new StringBuilder();
			int nextChar = fileIn.read();
			while (nextChar != -1) {
				buf.append((char) nextChar);
				nextChar = fileIn.read();
			}
			fileIn.close();

			char[] text = buf.toString().toCharArray();

			byte[] compressedText = compress(text);

			FileOutputStream fos = new FileOutputStream(filename + ".zip"

			);
			BufferedOutputStream file = new BufferedOutputStream(fos);
			file.write(compressedText);
			file.close();
		} else if (option.equals("+")) {
			// READ IN THE FILE
			FileInputStream fis = new FileInputStream(filename);
			BufferedInputStream file = new BufferedInputStream(fis);

			byte[] compressedText = new byte[16];
			int size = 0;
			int byteRead = file.read();
			while (byteRead != -1) {
				if (size == compressedText.length) {
					byte[] newCompressedText = new byte

					[compressedText.length * 2];
					System.arraycopy(compressedText, 0,

							newCompressedText, 0, compressedText.length);
					compressedText = newCompressedText;
				}

				compressedText[size++] = (byte) byteRead;
				byteRead = file.read();
			}
			file.close();

			BitStreamInput in = new BitStreamInput(compressedText);
			char[] decompressedText = decompress(in);

			String text = new String(decompressedText);
			Stdio.println("Decompressed: " + text);
		} else {
			Stdio.println("Invalid option: " + option);
		}

	}
}
