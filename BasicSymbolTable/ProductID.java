//package PA9;
/**

 * ProductID uniquely identifies a product.  Should be IMMUTABLE!!!
 */

/**
 * @author Prachi Gupta G01025257 user: pgupta13 INFS 519 Fall 2016
 * INFS 519
 * Fall 2016
 */

public class ProductID {
	private final String epc;
	private final int serialNumber;

	public ProductID(String epc, int serialNumber) {
		this.epc = epc;
		this.serialNumber = serialNumber;
	}

	@Override
	//return hashcode of a given object of type ProductID
	public int hashCode() {
		int hash = 17; // pick prime constants
		hash = 31 * hash + epc.hashCode();// hash value for epc member of ProductID
		hash = 31 * hash + ((Integer) serialNumber).hashCode(); //addition of hashvalue of serial number with factor 31 which is base-R in our funciton

		return hash;
		// MODIFY CODE
	}

	@Override
	public boolean equals(Object obj) {
		// performance trick, typical to check super.equals also
		if (obj == this)
			return true;
		//type check, handles null,
		if (!(obj instanceof ProductID))
			return false;
		//safe cast so can check each attribute
		ProductID prod_id_Obj = (ProductID) obj;
		//check each attribute for equality, should handle nulls
		return (this.epc.equals(prod_id_Obj.epc) && this.serialNumber == prod_id_Obj.serialNumber);
	}

	// MODIFY CODE

	@Override
	public String toString() {
		return epc + "," + serialNumber;
	}
}
