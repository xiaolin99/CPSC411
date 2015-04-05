/**
 * CPSC411 - Assignment 4
 * This class represent a Symbol in symbol table
 * @author xlin
 *
 */
public class MySymbol{
	protected String name;
	public int level = 0;
	
	/**
	 * Constructor
	 * @param name
	 */
	public MySymbol(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * A method to check for equality (used for searching in ArrayList)
	 */
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof String) return name.equals((String)arg0);
		if (arg0 instanceof MySymbol) return name.equals(((MySymbol)arg0).getName());
		return false;
	}
	
	
}
