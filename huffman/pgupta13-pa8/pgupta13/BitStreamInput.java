import java.util.Formatter;

/**
 * Stream that reads bits from a byte array.
 * @author Zeehasham Rasheed
 */
public class BitStreamInput
{
    public static final int DEFAULT_MAX_LENGTH = Integer.MAX_VALUE;
    
    // Byte array variables
    private byte[] buffer; // a.k.a. byteBuffer
    private int position;
    
    // For bit operations
    private int bitBuffer;    // 8-bit bitBuffer of bits to write out
    private int bitsInBuffer; // number of bits remaining in bitBuffer
    
    public BitStreamInput( byte[] buffer )
    {
        this( buffer, 0 );
    }
    
    public BitStreamInput( byte[] buffer, int position )
    {
        this.buffer      = buffer;
        this.position    = position;
        
        //
        this.bitsInBuffer = -1;
    }
    
    
    public byte[] getBuffer()
    {
        return this.buffer;
    }
    
    public int getCapacity()
    {
        return this.buffer.length;
    }
    
    public int getPosition()
    {
        return this.position;
    }
    
    //----- Input  StreamOutput -----//
    public boolean readBit()
    {
        // Fill when necessary
        if( this.bitsInBuffer <= 0 ) this.fillBuffer();
        
        this.bitsInBuffer--;
        
        boolean bit = ((this.bitBuffer >> bitsInBuffer) & 0x01) == 0x01;
        
        return bit;
    }
    
    public int readBits( int r )
    {
        if( r > 31 ) throw new IllegalArgumentException("r must be less than 32");
        int v = 0;
        for (int i = 0; i < r; i++)
        {
            // Most significant bit first
            boolean bit = readBit();
            
            v = v << 1;
            if( bit ) v = v | 0x01;
        }
        return v;
    }
    
    public void fillBuffer()
    {
        this.bitBuffer = this.read();
        this.bitsInBuffer = 8;
        
        //System.out.println("Filling buffer "+ Integer.toHexString( this.bitBuffer ));
    }
    
    // Byte opeation
    private int read()
    {
        return this.buffer[position++] & 0xff;
    }
    
    public boolean isEmpty()
    {
        return  this.getPosition() == this.getCapacity() &&
                this.bitsInBuffer <= 0;
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
}
