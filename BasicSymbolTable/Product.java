/**
 * Product with product key and other attributes.
 */
public class Product
{
    private final ProductID key;
    private final String description;
    private final int quantity;
    private final double cost;
    
    public Product( ProductID key, String description, int quantity, double cost )
    {
        this.key         = key;
        this.description = description;
        this.quantity    = quantity;
        this.cost        = cost;
    }
    
    @Override
    public String toString()
    {
        StringBuilder buf = new StringBuilder();
        buf.append(key);         buf.append(",");
        buf.append(description); buf.append(",");
        buf.append(quantity);    buf.append(",");
        buf.append(cost);
        return buf.toString();
    }
}
