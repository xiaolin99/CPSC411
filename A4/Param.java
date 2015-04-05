
/**
 * CPSC411 - Assignment 4
 * This class represent a function param 
 * (which is different than a regular var, so I made a seperate class)
 * @author xlin
 *
 */
public class Param {
	private int type;
	private int dimentions;
	
	// getters
	public int getType() {
		return type;
	}

	public int getDimentions() {
		return dimentions;
	}

	/**
	 * Constructor - pass in type and dimensions
	 * @param type
	 * @param dim
	 */
	public Param(int type, int dim) {
		this.type = type;
		this.dimentions = dim;
	}

	/**
	 * check for equality
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Param)) return false;
		Param p = (Param)o;
		return (this.type == p.type && this.dimentions == p.dimentions);
	}

}
