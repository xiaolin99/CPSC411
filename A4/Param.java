/**
 * 
 */

/**
 * @author xlin
 *
 */
public class Param implements Comparable<Param> {
	private int type;
	private int dimentions;
	
	public int getType() {
		return type;
	}

	public int getDimentions() {
		return dimentions;
	}

	public Param(int type, int dim) {
		this.type = type;
		this.dimentions = dim;
	}

	@Override
	public int compareTo(Param o) {
		if (this.type != o.type) return -1;
		if (this.dimentions != o.dimentions) return -1;
		return 0;
	}

}
