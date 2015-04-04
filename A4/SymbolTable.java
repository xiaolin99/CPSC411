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
	
	public int var_offset = 1;
	public int arg_offset = -4;
	
	public static int fun_lable_counter = 1;
	public SymbolTable nextLevel;
	
	public SymbolTable(String name) {
		scope_name = name;
		symbols = new ArrayList<MySymbol>();
		nextLevel = null;
		num_vars = 0;
		num_args = 0;
	}
	
	public SymbolTable(String name, SymbolTable parent) {
		scope_name = name;
		symbols = new ArrayList<MySymbol>();
		nextLevel = parent;
		num_vars = 0;
		num_args = 0;
	}
	
	public SymbolTable insertST(String name) {
		return new SymbolTable(name, this);
	}
	
	public static void insertSymbol(SymbolTable st, VarSymbol s)  throws SymbolException {
		st.num_vars ++;
		if (st.symbols.contains(s)) throw new SymbolException("Symbol Error: "+s.name+" already declared");
		st.symbols.add(s);
	}
	
	public static void insertSymbol(SymbolTable st, FunSymbol s)  throws SymbolException {
		if (st.symbols.contains(s)) throw new SymbolException("Symbol Error: "+s.name+" already declared");
		st.symbols.add(s);
	}
	
	public static void insertSymbol(SymbolTable st, Param p, String name)  throws SymbolException {
		if (st.symbols.contains(name)) throw new SymbolException("Symbol Error: "+name+" already declared");
		st.num_args ++;
		VarSymbol s = new VarSymbol(name, st.arg_offset, p.getType(), p.getDimentions());
		st.arg_offset --;
		st.symbols.add(s);
	}
	
	public static void insertSymbol(SymbolTable st, String name, int type, int dims)  throws SymbolException {
		if (st.symbols.contains(name)) throw new SymbolException("Symbol Error: "+name+" already declared");
		VarSymbol s = new VarSymbol(name, st.var_offset, type, dims);
		st.var_offset --;
		st.symbols.add(s);
	}
	
	
	
	public static MySymbol getSymbol(SymbolTable st, String id) throws SymbolException {
		while (true) {
			SymbolTable cur_st = st;
			int index = cur_st.symbols.indexOf(id);
			if (index >= 0) return cur_st.symbols.get(index);
			if (cur_st.nextLevel == null) break;
			cur_st = cur_st.nextLevel;
		}
		throw new SymbolException("Symbol Error: "+id+" not found");
	}
	
	public static int getReturnType(SymbolTable st, String fun_name) throws SymbolException {
		while (true) {
			SymbolTable cur_st = st;
			int index = cur_st.symbols.indexOf(fun_name);
			if (index >= 0) {
				MySymbol s = cur_st.symbols.get(index);
				if (! (s instanceof FunSymbol)) throw new SymbolException("Symbol Error: "+fun_name+" is not a function");
				return ((FunSymbol)s).return_type;
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
				return ((VarSymbol)s).type;
			}
			if (cur_st.nextLevel == null) break;
			cur_st = cur_st.nextLevel;
		}
		throw new SymbolException("Symbol Error: "+var_name+" not found");
	}
	
	public static Param getParam(SymbolTable st, String fun_name, int param_index)  throws SymbolException{
		while (true) {
			SymbolTable cur_st = st;
			int index = cur_st.symbols.indexOf(fun_name);
			if (index >= 0) {
				MySymbol s = cur_st.symbols.get(index);
				if (! (s instanceof FunSymbol)) throw new SymbolException("Symbol Error: "+fun_name+" is not a function");
				return ((FunSymbol)s).getParam(param_index);
			}
			if (cur_st.nextLevel == null) break;
			cur_st = cur_st.nextLevel;
		}
		throw new SymbolException("Symbol Error: "+fun_name+" not found");
	}
	
	

}
