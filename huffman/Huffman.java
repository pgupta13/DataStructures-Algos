import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
/**
 * @author Your Name Here
 * INFS 519
 * Fall 2016
 */
 
/**
 * ADD DOCUMENTATION
 */
public class Huffman
{
    /**
     * ADD DOCUMENTATION
     */
	public static int[] determineFrequencies(char[] text) {

		int[] frequency = new int[RADIX];

		for (int i = 0; i < text.length; i++) {

			char current = text[i];

			frequency[(int) current] += 1;

		}
		return frequency;
	}

	/**
	 * ADD DOCUMENTATION
	 */
	public static byte[] compress(char[] text) {
		// MODIFY CODE
		BitStreamOutput out = new BitStreamOutput();
		// determine frequency of each charactewr of given text
		int[] frequencyOfChars = determineFrequencies(text);
		// create a trie of frequency array
		Node node = makeTrie(frequencyOfChars);
		// now build code for each char
		String[] st = new String[RADIX];
		makeCodingTable(st, node, "");

		writeTrie(node, out);

		out.writeBits(text.length, 31);
		// encoding algo
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
		return byt;
	}

	/**
	 * ADD DOCUMENTATION
	 */
	public static void makeCodingTable(String[] table, Node x, String code) {
		// ADD CODE
		if (x.isLeaf()) {
			table[x.symbol] = code;

		} else if (!x.isLeaf()) {
			makeCodingTable(table, x.left, code + '0');
			makeCodingTable(table, x.right, code + '1');
		}
	}

	/**
	 * ADD DOCUMENTATION
	 */
	public static Node makeTrie(int[] freq) {
		// MODIFY CODE
		MinHeap<Node> pqelement = new MinHeap<>(RADIX);
		for (char i = 0; i < RADIX; i++)
			if (freq[i] > 0)
				pqelement.insert(new Node(i, freq[i]));

		// when one character with a nonzero frequency
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
			Node parent = new Node('\0', left, right, left.freq + right.freq);
			pqelement.insert(parent);
		}
		return pqelement.delMin();

	}

	/**
	 * ADD DOCUMENTATION
	 */
	public static char[] decompress(BitStreamInput in) {
		// MODIFY CODE

		Node node = readTrie(in);

		// size of bytes
		int size = in.readBits(31);
		char[] afterDecompression = new char[size];
		BitStreamOutput out = new BitStreamOutput();

		// decoding loop
		for (int i = 0; i < size; i++) {
			Node node1 = node;
			while (node1.isLeaf() == !true) {

				if (in.readBit()) {

					node1 = node1.right;

				} else if (!in.readBit()) {

					node1 = node1.left;
				}

			}
			afterDecompression[i] = node1.symbol;
			// out.writeBits(node1.symbol, 8);
		}
		return afterDecompression;
	}


    //----- DO NOT MODIFY ANYTHING BELOW THIS LINE -----//

    public static final int RADIX = 256; // number of symbols for extended ascii

    private static class Node implements Comparable<Node>
    {
        private Node left;
        private Node right;
        private char symbol;
        private int freq;    

        public Node( char c, int freq )
        {
            this(c, null, null, freq);
        }

        public Node( char symbol, Node left, Node right, int freq )
        {
            this.symbol = symbol;
            this.left   = left;
            this.right  = right;
            this.freq   = freq;
        }

        public Node getLeft() { return this.left; }
        public Node getRight() { return this.right; }
        public char getSymbol() { return this.symbol; }
        public int getFreq() { return this.freq; }
        public boolean isLeaf() { return this.left == null && this.right == null; }

        public int compareTo( Node other )
        {
            return this.freq - other.freq;
        }
        
        @Override
        public String toString()
        {
            return "("+this.symbol+" " + freq+")";
        }
    }
    

    public static void writeTrie(Node x, BitStreamOutput out)
    {
        // Use preorder traversal to encode the trie
        if (x.isLeaf())
        {
            out.writeBit(true);
            out.writeBits(x.symbol, 8);
            return;
        }
        out.writeBit(false);
        writeTrie(x.left,  out);
        writeTrie(x.right, out);
    }

    public static Node readTrie( BitStreamInput in )
    {
        boolean bit = in.readBit();
        if( bit )
        {
            char symbol = (char)in.readBits(8);
            return new Node(symbol, 0);
        }
        Node internalNode = new Node('\0', 0);
        internalNode.left  = readTrie( in );
        internalNode.right = readTrie( in );

        return internalNode;
    }
    
    public static void printTable(String[] table)
    {
        for( int i = 0; i < table.length; i++ )
        {
            String code = table[i];
            if(code != null) Stdio.println(""+((char)i) + " = " +code );
        }
    }
    
    
    /**
     * Unit tests the Huffman compression/decompression algorithm.
     * @param args 
     * @throws java.io.IOException 
     */
    public static void main( String[] args ) throws IOException
    {
        if( args.length != 2 )
        {
            String u = "Usage: Huffman <+|-> <filename>";
            Stdio.println(u);
            return;
        }
        
        String option   = args[0];
        String filename = args[1];
        if( option.equals("-") )
        {
            BufferedReader fileIn = new BufferedReader( new FileReader(filename) );
            StringBuilder buf = new StringBuilder();
            int nextChar = fileIn.read();
            while( nextChar != -1 )
            {
                buf.append((char)nextChar);
                nextChar = fileIn.read();
            }
            fileIn.close();
            
            char[] text = buf.toString().toCharArray();
            
            byte[] compressedText = compress(text);
            
            FileOutputStream fos = new FileOutputStream( filename+".zip" );
            BufferedOutputStream file = new BufferedOutputStream(fos);
            file.write(compressedText);
            file.close();
        }
        else if( option.equals("+") )
        {
            // READ IN THE FILE
            FileInputStream fis = new FileInputStream( filename );
            BufferedInputStream file = new BufferedInputStream(fis);
            
            byte[] compressedText = new byte[16];
            int size = 0;
            int byteRead = file.read();
            while( byteRead != -1 )
            {
                if( size == compressedText.length )
                {
                    byte[] newCompressedText = new byte[compressedText.length*2];
                    System.arraycopy(compressedText, 0, newCompressedText, 0, compressedText.length);
                    compressedText = newCompressedText;
                }
                
                compressedText[size++] = (byte)byteRead;
                byteRead = file.read();
            }            
            file.close();
            
            BitStreamInput in = new BitStreamInput(compressedText);
            char[] decompressedText = decompress( in );
            
            String text = new String(decompressedText);
            Stdio.println("Decompressed: "+text);
        }
        else
        {
            Stdio.println("Invalid option: "+option);
        }
        
    }
}
