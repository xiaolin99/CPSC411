import java.util.ArrayList;

/**
 * CPSC411 - Assignment 4
 * Represent a function symbol
 * @author xlin
 *
 */
public class FunSymbol extends MySymbol {
	public String lable;
	public ArrayList<Param> param_list;
	public int return_type;
	
	/**
	 * Constructor - pass in function name, lable, return type
	 * @param name
	 * @param lable
	 * @param type
	 */
	public FunSymbol(String name, String lable, int type) {
		super(name);
		this.lable = lable.toLowerCase();
		this.return_type = type;
		param_list = new ArrayList<Param>();
	}
	
	/**
	 * Add a parameter
	 * @param p
	 */
	public void addParam(Param p) {
		param_list.add(p);
	}
	
	/**
	 * Get a specific parameter
	 * @param index
	 * @return
	 * @throws SymbolException
	 */
	public Param getParam(int index) throws SymbolException {
		if (index >= param_list.size()) throw new SymbolException("Symbol Error: function "+name+" does not have the "+(index+1)+" arguments");
		return param_list.get(index);
	}

}
