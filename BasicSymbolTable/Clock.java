/**
 * Utility class to measure wall-clock time for tasks.
 * @author Zeehasham Rasheed
 */
public class Clock
{
    /*
     * Constants that divide nano-seconds, allow user to specify unit
     */
    public static class Timeunit
    {
        private final double timeunit;
        private final String abbreviation;
        public Timeunit( double timeunit, String abbreviation )
        {
            this.timeunit     = timeunit;
            this.abbreviation = abbreviation;
        }
        
        public double getTimeunit()     { return this.timeunit;     }
        public String getAbbreviation() { return this.abbreviation; }
    }
    
    public static final Timeunit SEC   = new Timeunit(1000000000.0, "sec");
    public static final Timeunit MILLI = new Timeunit(1000000.0, "ms");
    public static final Timeunit MICRO = new Timeunit(1000.0, "us");
    public static final Timeunit NANO  = new Timeunit(1.0, "ns");
    
    
    private final long started;
    private final Timeunit timeunit;

    /**
     * Creates a new clock with millisecond time unit.
     */
    public Clock()
    {
        this( MILLI );
    }
    
    /**
     * Creates a new clock with specified time unit.
     * @param timeunit 
     */
    public Clock(Timeunit timeunit)
    {
        this.started = System.nanoTime();
        this.timeunit= timeunit;
    }

    /**
     * Gets the time from when the task was created to now.
     * @return elapsed time in milliseconds
     */
    public double elapsed()
    {
        long stopped = System.nanoTime();
        long time = stopped-started;
        // if fast, nano time can have stop occur before stop
        if( time < 0 ) return 0.0;
        return time / timeunit.getTimeunit();
    }

    @Override
    public String toString()
    {
        return "" + elapsed() + this.timeunit.getAbbreviation();
    }
}