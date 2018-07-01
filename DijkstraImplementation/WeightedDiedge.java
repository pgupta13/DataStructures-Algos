package PA11;
/**
 * Edge represents an directed, weighted link between two vertices v and w.
 * @author Zeehasham Rasheed
 */
public class WeightedDiedge implements Comparable<WeightedDiedge>
{   
    private final int from;
    private final int to;
    private final double weight;

    /**
     * Creates a new DirectedEdge with specified vertices and weight.
     * @param from
     * @param to
     * @param weight
     */
    public WeightedDiedge(int from, int to, double weight)
    {
        this.from   = from;
        this.to     = to;
        this.weight = weight;
    }

    /**
     * Returns from vertex
     * @return from
     */
    public int from()
    {
        return this.from;
    }

    /**
     * Returns to vertex
     * @return to
     */
    public int to()
    {
        return to;
    }

    /**
     * Gets the weight of the edge.
     * @return 
     */
    public double weight()
    {
        return this.weight;
    }

    /**
     * Returns hash of this object.
     * @return 
     */
    @Override
    public int hashCode()
    {
        int hash = 17;
        hash = 31*hash + ((Integer)from).hashCode();
        hash = 31*hash + ((Integer)to).hashCode();
        hash = 31*hash + ((Double)weight).hashCode();
        return hash;
    }

    /**
     * Determines if this object is equivalent to object.
     * @param object
     * @return true if equivalent, false otherwise
     */
    @Override
    public boolean equals( Object object )
    {
        if( this == object ) return true;
        if( ! (object instanceof WeightedDiedge) ) return false;
        WeightedDiedge that = (WeightedDiedge) object;

        return( this.from   == that.from &&
                this.to     == that.to   &&
                this.weight == that.weight );
    }

    /**
     * Compares this edge to specified edge.
     * @param that
     * @return -1 if this less than that, +1 of this more than than, 0 if equal
     */
    public int compareTo(WeightedDiedge that)
    {
        if(      this.weight < that.weight ) return -1;
        else if( this.weight > that.weight ) return +1;
        return 0;
    }

    /**
     * Gets String facsimile of this object.
     * @return 
     */
    @Override
    public String toString()
    {
        return String.format("(%d->%d=%.2f)", from, to, weight);
    }

}
