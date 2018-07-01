import java.util.Formatter;

/**
 * Stream that reads bits from a byte array.
 * <p>
 * When writing bits, be sure to call flush when finished to clear
 * the last, if any, remaining bits in the buffer.
 * @author Zeehasham Rasheed
 */
public class BitStreamOutput
{
    public static final int BITS_PER_BYTE = 8;
    public static final int DEFAULT_CAPACITY = 16;
    
    // Byte array variables
    private byte[] buffer; // a.k.a. byteBuffer
    private int position;
    
    // For bit operations
    private int bitBuffer;    // 8-bit bitBuffer of bits to write out
    private int bitsInBuffer; // number of bits remaining in bitBuffer
    
    public BitStreamOutput()
    {
        this(DEFAULT_CAPACITY);
    }
    
    public BitStreamOutput( int initialCapacity )
    {
        this( new byte[initialCapacity] );
    }
    
    public BitStreamOutput( byte[] buffer )
    {
        this( buffer, 0 );
    }
    
    public BitStreamOutput( byte[] buffer, int position )
    {
        this.buffer      = buffer;
        this.position    = position;
    }
    
    
    public byte[] getBuffer()
    {
        return this.buffer;
    }
    
    public byte[] toArray()
    {
        byte[] temp = new byte[this.getPosition()];
        System.arraycopy(buffer, 0, temp, 0, temp.length);
        return temp;
    }
    
    public int getCapacity()
    {
        return this.buffer.length;
    }
    
    public int getPosition()
    {
        return this.position;
    }
    
    //----- Bit related output methods -----//
    
    public void writeBit( boolean bit )
    {
        this.bitBuffer = this.bitBuffer << 1; // make room for next bit
        if( bit ) this.bitBuffer = this.bitBuffer | 0x01;
        bitsInBuffer++;
        if( bitsInBuffer == BITS_PER_BYTE )
        {
            this.flush();
        }
    }
    
    public void writeBits( int v, int r )
    {
        if( r > 31 ) throw new IllegalArgumentException("r must be less than 32");
        if( v < 0  ) throw new IllegalArgumentException("v must be positive"    );
        for (int i = 0; i < r; i++)
        {
            // Most significant bit first
            boolean bit = ( (v >> (r - i - 1) ) & 0x01) == 0x01;
            this.writeBit(bit);
        }
    }
    
    public void flush()
    {
        if( bitsInBuffer == 0 )
        {
            return;
        }
        else
        {
            // Pad remaining bits with zeroes
            this.bitBuffer = this.bitBuffer << (BITS_PER_BYTE - bitsInBuffer);
            this.write(bitBuffer);
        }
        
        this.bitsInBuffer = 0;
        this.bitBuffer    = 0;
    }
    
    
    //----- Byte related output methods -----//
    
    private void write( int b )
    {
        if( this.isFull() )
        {
            this.grow();
        }
        this.buffer[position++] = (byte)b;
    }
    
    private boolean isFull()
    {
        return this.position == this.getCapacity();
    }
    
    private void grow( )
    {
        int newSize = this.getCapacity() * 2;
        
        byte[] temp = new byte[newSize];
        System.arraycopy(this.buffer, 0, temp, 0, this.buffer.length);
        this.buffer = temp; // garbage collect old buffer
    }
    
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        Formatter out = new Formatter(s);
        out.format("len=%d pos=%d", this.getCapacity(), this.getPosition());
        out.format(" [");
        for (int i = 0; i < this.buffer.length; i++)
        {
            if(i!=0) out.format(", ");
            out.format("%02x", this.buffer[i]);
        }
        out.format("]");
        return s.toString();
    }
    
    
    /**
     * Unit test for both input and output
     * @param args 
     */
    public static void main( String[] args )
    {
        boolean b1 = true;
        boolean b2 = false;
        boolean b3 = true;
        boolean b4 = false;
        
        int g1 = 0x0e;
        int g2 = 0x0f;
        int g3 = 0x06;
        
        boolean b5 = true;
        boolean b6 = false;
        boolean b7 = true;
        
        int g4 = 0x6c;
        
        BitStreamOutput out = new BitStreamOutput();
        out.writeBit(b1);
        out.writeBit(b2);
        out.writeBit(b3);
        out.writeBit(b4);
        out.writeBits(g1, 4);
        out.writeBits(g2, 4);
        out.writeBits(g3, 4);
        
        out.writeBit(b5);
        out.writeBit(b6);
        out.writeBit(b7);
        out.flush();
        
        out.writeBits(g4, 7);
        out.flush();
        
        
        byte[] data = out.toArray();
        
        System.out.println( toString(data) );
        
        BitStreamInput in = new BitStreamInput(data);
        
        System.out.println( b1 == in.readBit()   );
        System.out.println( b2 == in.readBit()   );
        System.out.println( b3 == in.readBit()   );
        System.out.println( b4 == in.readBit()   );
        System.out.println( g1 == in.readBits(4) );
        System.out.println( g2 == in.readBits(4) );
        System.out.println( g3 == in.readBits(4) );
        
        System.out.println( b5 == in.readBit()   );
        System.out.println( b6 == in.readBit()   );
        System.out.println( b7 == in.readBit()   );
        
        in.fillBuffer();
        
        System.out.println( g4 == in.readBits(7) );
        
        System.out.println( "Empty? "+ in.isEmpty() );
    }
    
    public static String toString( byte[] data )
    {
        StringBuilder s = new StringBuilder();
        Formatter out = new Formatter(s);
        out.format(" [");
        for (int i = 0; i < data.length; i++)
        {
            if(i!=0) out.format(", ");
            out.format("%02x", data[i]);
        }
        out.format("]");
        return s.toString();
    }
    
}
