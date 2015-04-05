import java.util.ArrayList;


/**
 * CPSC411 - Assignment 4
 * This class represent a SymbolTable
 * @author xlin
 *
 */
public class SymbolTable {
	public String scope_name;
	public int num_vars;
	public int num_args;
	public int num_funs;
	// keeping track of return_type if scope is a function
	public int return_type;
	public ArrayList<MySymbol> symbols;
	
	public int var_offset = 1;
	public int arg_offset = -4;
	
	public static int fun_lable_counter = 1;
	// nextLevel points to the parent SymbolTable of this table
	public SymbolTable nextLevel;
	
	/**
	 * Constructor - pass in scope name
	 * @param name
	 */
	public SymbolTable(String name) {
		scope_name = name;
		symbols = new ArrayList<MySymbol>();
		nextLevel = null;
		num_vars = 0;
		num_args = 0;
	}
	
	/**
	 * Constructor - passing scope name and parent table
	 * @param name
	 * @param parent
	 */
	public SymbolTable(String name, SymbolTable parent) {
		scope_name = name;
		symbols = new ArrayList<MySymbol>();
		nextLevel = parent;
		num_vars = 0;
		num_args = 0;
	}
	
	/**
	 * Creates a child SymbolTable from this table. pass in the child's scope name
	 * @param name
	 * @return
	 */
	public SymbolTable insertST(String name) {
		return new SymbolTable(name, this);
	}
	
	/**
	 * Insert a variable symbol into table
	 * @param st SymbolTable
	 * @param s VarSymbol
	 * @throws SymbolException if symbol already declared
	 */
	public static void insertSymbol(SymbolTable st, VarSymbol s)  throws SymbolException {
		if (st.symbols.contains(s)) throw new SymbolException("Symbol Error: "+s.name+" already declared");
		st.num_vars ++;
		st.symbols.add(s);
	}
	
	/**
	 * Insert a function symbol into table
	 * @param st SymbolTable
	 * @param s FunSymbol
	 * @throws SymbolException if symbol already declared
	 */
	public static void insertSymbol(SymbolTable st, FunSymbol s)  throws SymbolException {
		if (st.symbols.contains(s)) throw new SymbolException("Symbol Error: "+s.name+" already declared");
		st.num_funs ++;
		st.symbols.add(s);
	}
	
	/**
	 * Insert a function parameter into the symbol table
	 * @param st SymbolTable (must be function's symbol table)
	 * @param p Param
	 * @param name String - p's name
	 * @throws SymbolException if symbol already declared
	 */
	public static void insertSymbol(SymbolTable st, Param p, String name)  throws SymbolException {
		if (st.symbols.contains(new MySymbol(name))) throw new SymbolException("Symbol Error: "+name+" already declared");
		st.num_args ++;
		VarSymbol s = new VarSymbol(name, st.arg_offset, p.getType(), p.getDimentions());
		st.arg_offset --;
		st.symbols.add(s);
	}
	
	/**
	 * Create a var symbol, and then insert it into table
	 * @param st SymbolTable
	 * @param name String - var's name
	 * @param type int - var's type
	 * @param dims int - var's dimensions
	 * @throws SymbolException if symbol already declared
	 */
	public static void insertSymbol(SymbolTable st, String name, int type, int dims)  throws SymbolException {
		if (st.symbols.contains(new MySymbol(name))) throw new SymbolException("Symbol Error: "+name+" already declared");
		st.num_vars ++;
		VarSymbol s = new VarSymbol(name, st.var_offset, type, dims);
		st.var_offset ++;
		st.symbols.add(s);
	}
	
	/**
	 * Return's a symbol (var or fun) with specific name
	 * @param st SymbolTable
	 * @param id symbol's name
	 * @return Symbol
	 * @throws SymbolException
	 */
	public static MySymbol getSymbol(SymbolTable st, String id) throws SymbolException {
		int level = 0;
		SymbolTable cur_st = st;
		while (cur_st != null) {
			int index = cur_st.symbols.indexOf(new MySymbol(id));
			if (index >= 0) {
				cur_st.symbols.get(index).level = level;
				return cur_st.symbols.get(index);
			}
			cur_st = cur_st.nextLevel;
			level ++;
		}
		throw new SymbolException("Symbol Error: "+id+" not found");
	}
	
	/**
	 * Find a function's return type
	 * @param st SymbolTable
	 * @param fun_name String - fun's name
	 * @return int - the return type
	 * @throws SymbolException
	 */
	public static int getReturnType(SymbolTable st, String fun_name) throws SymbolException {
		SymbolTable cur_st = st;
		while (cur_st != null) {
			//System.out.println(cur_st);
			int index = cur_st.symbols.indexOf(new MySymbol(fun_name));
			if (index >= 0) {
				MySymbol s = cur_st.symbols.get(index);
				if (! (s instanceof FunSymbol)) throw new SymbolException("Symbol Error: "+fun_name+" is not a function");
				return ((FunSymbol)s).return_type;
			}
			cur_st = cur_st.nextLevel;
		}
		throw new SymbolException("Symbol Error: "+fun_name+" not found");
	}
	
	/**
	 * Find a variable's type
	 * @param st SymbolTable
	 * @param var_name String - var's name
	 * @return int - var's type
	 * @throws SymbolException
	 */
	public static int getType(SymbolTable st, String var_name) throws SymbolException {
		SymbolTable cur_st = st;
		while (cur_st != null) {
			int index = cur_st.symbols.indexOf(new MySymbol(var_name));
			if (index >= 0) {
				MySymbol s = cur_st.symbols.get(index);
				if (! (s instanceof VarSymbol)) throw new SymbolException("Symbol Error: "+var_name+" is not a variable");
				return ((VarSymbol)s).type;
			}
			cur_st = cur_st.nextLevel;
		}
		throw new SymbolException("Symbol Error: "+var_name+" not found");
	}
	
	/**
	 * Get one param from a function
	 * @param st SymbolTable
	 * @param fun_name String - fun's name
	 * @param param_index int - which param (starts from zero)
	 * @return Param
	 * @throws SymbolException
	 */
	public static Param getParam(SymbolTable st, String fun_name, int param_index)  throws SymbolException{
		SymbolTable cur_st = st;
		while (cur_st != null) {
			int index = cur_st.symbols.indexOf(new MySymbol(fun_name));
			if (index >= 0) {
				MySymbol s = cur_st.symbols.get(index);
				if (! (s instanceof FunSymbol)) throw new SymbolException("Symbol Error: "+fun_name+" is not a function");
				return ((FunSymbol)s).getParam(param_index);
			}
			cur_st = cur_st.nextLevel;
		}
		throw new SymbolException("Symbol Error: "+fun_name+" not found");
	}
	
	/**
	 * Method to find all arrays in a symbol table
	 * @param st SymbolTable
	 * @return ArrayList
	 */
	public static ArrayList<VarSymbol> getArrays(SymbolTable st) {
		ArrayList<VarSymbol> l = new ArrayList<VarSymbol>();
		if (st.symbols.size() < 1) return l;
		for (int i = 0; i < st.symbols.size(); i ++){
			MySymbol s = st.symbols.get(i);
			if (s instanceof VarSymbol) {
				VarSymbol v = (VarSymbol)s;
				if (v.dimentions > 0) l.add(v);
			}
		}
		return l;
	}
	
	/**
	 * Just to print out some info about the current table
	 */
	@Override
	public String toString() {
		String s = "[" + this.scope_name;
		s += ", "+this.num_args + ", " + this.num_vars + ", " +this.num_funs+"]";
		return s;
	}
	
	

}
