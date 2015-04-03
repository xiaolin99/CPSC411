import java.util.ArrayList;

/**
 * 
 */

/**
 * @author xlin
 *
 */
public class SymbolTable {
	public String scope_name;
	public int num_vars;
	public int num_args;
	public int return_type;
	public ArrayList<MySymbol> symbols;
	
	private static int var_offset = 1;
	private static int arg_offset = -4;
	
	public Integer fun_lable_counter;
	public SymbolTable nextLevel;
	
	public SymbolTable(String name, Integer flc) {
		scope_name = name;
		symbols = new ArrayList<MySymbol>();
		fun_lable_counter = flc;
		nextLevel = null;
		num_vars = 0;
		num_args = 0;
	}
	
	public SymbolTable(String name, Integer flc, SymbolTable parent) {
		scope_name = name;
		symbols = new ArrayList<MySymbol>();
		fun_lable_counter = flc;
		nextLevel = parent;
		num_vars = 0;
		num_args = 0;
	}
	
	public SymbolTable insertST(String name) {
		return new SymbolTable(name, fun_lable_counter, this);
	}
	
	public static void insertSymbol(SymbolTable st, VarSymbol s) {
		st.num_vars ++;
		st.symbols.add(s);
	}
	
	public static void insertSymbol(SymbolTable st, FunSymbol s) {
		st.symbols.add(s);
	}
	
	public static void insertSymbol(SymbolTable st, Param p, String name) {
		st.num_args ++;
		VarSymbol s = new VarSymbol(name, arg_offset, p.getType(), p.getDimentions());
		arg_offset --;
		st.symbols.add(s);
	}
	
	public static int getReturnType(SymbolTable st, String fun_name) throws SymbolException {
		while (true) {
			SymbolTable cur_st = st;
			int index = cur_st.symbols.indexOf(fun_name);
			if (index >= 0) {
				MySymbol s = cur_st.symbols.get(index);
				if (! (s instanceof FunSymbol)) throw new SymbolException("Symbol Error: "+fun_name+" is not a function");
				return ((FunSymbol)s).getReturn_type();
			}
			if (cur_st.nextLevel == null) break;
			cur_st = cur_st.nextLevel;
		}
		throw new SymbolException("Symbol Error: "+fun_name+" not found");
	}
	
	public static int getType(SymbolTable st, String var_name) throws SymbolException {
		while (true) {
			SymbolTable cur_st = st;
			int index = cur_st.symbols.indexOf(var_name);
			if (index >= 0) {
				MySymbol s = cur_st.symbols.get(index);
				if (! (s instanceof VarSymbol)) throw new SymbolException("Symbol Error: "+var_name+" is not a variable");
				return ((VarSymbol)s).getType();
			}
			if (cur_st.nextLevel == null) break;
			cur_st = cur_st.nextLevel;
		}
		throw new SymbolException("Symbol Error: "+var_name+" not found");
	}
	
	

}
