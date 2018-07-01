/**
 * @author Your Name Here
 * INFS 519
 * Fall 2016
 */

/**
 * ADD DOCUMENTATION
 */
public class DynamicArray <Type> implements List<Type>
{
    public static final int MIN_CAPACITY = 4;

    // ADD CODE

    public DynamicArray()
    {
        // ADD CODE
    }

    public Type get(int i)
    {
        return null; // MODIFY CODE
    }
    
    
    public void set(int i, Type item)
    {
        // ADD CODE
    }

    public void add(Type item)
    {
        // ADD CODE
    }

    
    public Type remove(int i)
    {
        return null; // MODIFY CODE
    }

    public void insert(int i, Type item)
    {
        // ADD CODE
    }

    public int size()
    {
        return -1; // MODIFY CODE
    }

    public int capacity()
    {
        return -1; // MODIFY CODE
    }


    // Place any utility methods here and make them private


    //--------------------- DO NOT MODIFY BELOW THIS -----------------------//
    @Override
    public String toString()
    {
        StringBuilder buf = new StringBuilder();
        //buf.append("cap="+this.items.length+"[");
        buf.append("[");
        for( int i = 0; i < this.size(); i++ )
        {
            Type item = this.get(i);
            if( i != 0 ) buf.append( ", " );
            buf.append( item.toString() );
        }
        buf.append("]");
        return buf.toString();
    }


    public static void main(String[] args)
    {
        if( args.length != 1 )
        {
            String u = "Usage: java DynamicArray <filename> \n"+
                       "  e.g: java DynamicArray operations.txt";
            Stdio.println(u); 
            return;
        }

        DynamicArray<String> list = new DynamicArray<String>();
        
        Stdio.open( args[0] );
        while( Stdio.hasNext() )
        {
            String method = Stdio.readString();
            if( method.equals("add") )
            {
                String param1 = Stdio.readString();
                Stdio.println( "adding "+ param1 );
                list.add( param1 );
            }
            else if( method.equals("get") )
            {
                int index = Stdio.readInt();
                Stdio.println( "get(" + index+")="+list.get(index) );
            }
            else if( method.equals("size") )
            {
                Stdio.println( "size="+list.size() );
            }
            else if( method.equals("capacity") )
            {
                Stdio.println( "capacity="+list.capacity() );
            }
            else if( method.equals("remove") )
            {
                int index = Stdio.readInt();
                Stdio.println( "remove "+  list.remove(index) );
            }
            else if( method.equals("set") )
            {
                int index     = Stdio.readInt();
                String item   = Stdio.readString();
                list.set(index, item);
                Stdio.println( "set "+ index + " to " + list.get(index) );
            }
            else
            {
                Stdio.println( "Unknown method: "+ method );
            }
        }
        Stdio.println( "" );
        Stdio.println( "Final list=" +list.toString() );
        Stdio.close();
    }

}
