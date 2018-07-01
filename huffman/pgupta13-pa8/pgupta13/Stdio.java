
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;

/**
 * Standard input/output methods.  The idea was borrowed from Sedgwick/Wayne.
 * However, all code was developed from scratch.  The thought is to keep
 * all minutia (not related to algorithm design) away from the implementations,
 * including input/output processing.  I/O Exceptions (not related to API)
 * are also captures here to make the unit tests (i.e. the main methods) free
 * from superfluous imports.
 * @author Zeehasham Rasheed
 */
public class Stdio
{
    private static Scanner scanner = null;
    
    public static void open( )
    {
        scanner = new Scanner(System.in);
    }
    
    
    public static Scanner open( String fileName )
    {
        scanner = null;
        try
        {
            scanner = new Scanner( new File(fileName) );
        }
        catch (FileNotFoundException ex)
        {
            println("Unable to open file "+fileName + ": "+ex.getMessage());
        }
        return scanner;
    }
    
    public static boolean hasNext()
    {
        return scanner.hasNext();
    }
    
    public static String readString( )
    {
        String entered = scanner.next();
        return entered;
    }
    
    public static int readInt( )
    {
        return scanner.nextInt();
    }

    public static double readDouble( )
    {
        return scanner.nextDouble();
    }

    public static String readString( String prompt )
    {
        print( prompt );
        String entered = scanner.next();
        return entered;
    }
    
    public static void close( )
    {
        scanner.close();
    }
    
    
    
    /**
     * Gets the option between user friendly [1,options.length].  Continues
     * to prompt user until a valid option is entered.
     * @param options
     * @return array friendly option in [0,options.length-1]
     */
    public static int getOption( String[] options )
    {
        int option = -1;
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < options.length; i++)
        {
            String anOption = options[i];
            buf.append( (i+1) );
            buf.append( ") " );
            buf.append( anOption );
            buf.append( "\n" );
            //if( i != 0 ) buf.append(", ");
        }
        buf.append("\n> ");
        
        while( option == -1 )
        {
            print(buf.toString());

            Scanner scanner = new Scanner(System.in);
            String entered = scanner.next();
            if( entered != null && entered.length() > 0 )
            {
                try
                {
                    int enteredOption = Integer.parseInt(entered);
                    if( enteredOption < 1 || enteredOption > options.length )
                    {
                        println("Invalid number, out of range: [1,"+options.length+"]");
                    }
                    else
                    {
                        option = enteredOption;
                    }
                }
                catch( NumberFormatException e )
                {
                    println("Invalid number entered: "+entered);
                }
            }
            else
            {
                println("Invalid number entered: null or zero length");
            }
        }
        return option-1; // return array friendly index
    }
    
    /**
     * Prints to standard out appending a newline.
     * @param display 
     */
    public static void println( Object display )
    {
        if( display != null )
        {
            System.out.println(display.toString());
        }
        else
        {
            System.out.println( "null" );
        }
    }

    
    
    public static void printf( String s, Object ... arguments )
    {
        StringBuilder b = new StringBuilder();
        Formatter f = new Formatter(b);
        f.format(s, arguments);
        print( b.toString() );
    }
    
    /**
     * Prints to standard out.
     * @param display 
     */
    public static void print( String display )
    {
        System.out.print(display);
    }
    
    
    
    //-----------------------------------------------------------------------//
    // Log-like API
    //-----------------------------------------------------------------------//
    private static boolean log = false;
    
    public static void setLogging( boolean l )
    {
        log = l;
    }

    public static void log( String s )
    {
        if( log ) println(s);
    }

    
    //-----------------------------------------------------------------------//
    // Generators that produce large input files
    //-----------------------------------------------------------------------//
    /**
     * Generates large number of integer objects, possibly duplicates.  Prepends
     * the beginning with the number generated (for created arrays).
     * @param filename
     * @param n
     * @param bounds
     * @throws java.io.IOException
     */
    public static void generate( String filename, int n, int bounds ) throws IOException
    {
        Comparable[] items = generate( n, bounds );
        
        FileWriter fw = new FileWriter(filename);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw);
        
        out.println( n );
        for (int i = 0; i < n; i++)
        {
            out.println( items[i] );
        }
        
        out.close();
        bw.close();
        fw.close();
    }
    
    /**
     * Determines if the specified String can be converted to an integer.
     * @param s
     * @return true if s can be an integer, false otherwise
     * @throws NullPointerException if s is null
     */
    public static boolean isInteger( String s )
    {
        boolean isInt = false;
        try
        {
            Integer.parseInt(s);
            isInt = true;
        }
        catch( NumberFormatException e ) {}
        return isInt;
    }
    
    /**
     * Generates large number of integer objects, possibly duplicates.
     * @param n
     * @param bounds
     * @return n items within [0,bounds)
     */
    public static Comparable[] generate( int n, int bounds )
    {
        Comparable[] items = new Comparable[n];
        Random rand = new Random(123456);
        for (int i = 0; i < n; i++)
        {
            // All positive?
            Integer v = rand.nextInt( bounds );
            items[i] = v;
        }
        return items;
    }


    public static void main( String[] args ) throws IOException
    {
        String filename = args[0];
        int generate    = Integer.parseInt( args[1] );
        int bounds      = Integer.parseInt( args[2] );
        
        generate( filename, generate, bounds );
    }

}
