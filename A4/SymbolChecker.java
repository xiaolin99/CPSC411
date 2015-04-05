/**
 * CPSC411 - Assignment 4
 * Semantics (Symbols) checking for M+ language
 * Spec: http://pages.cpsc.ucalgary.ca/~robin/class/411/M+/m+data.spec
 * @author xlin
 *
 */
public class SymbolChecker {
	
	/**
	 * the start AST node
	 */
	Node start;
	boolean debug = true;
	
	/**
	 * block counter for code blocks
	 */
	public static int blockCounter;
	
	/**
	 * Constructor - pass in the start AST node
	 * @param start
	 */
	public SymbolChecker(Node start) {
		this.start = start;
	}

	/**
	 * Check Semantics
	 * @return true if semantics are good
	 */
	public boolean checkSyntax() {
		try {
			System.out.println("Checking Semantics ...");
			if (debug) System.out.println("Starting from M_prog");
			SymbolTable st = new SymbolTable("M_prog");
			Node n = (Node)start.children.get(0);
			st = check_M_decl(st, (Node)n.children.get(0));
			check_M_stmt(st, (Node)n.children.get(1));
		}
		catch(SymbolException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Get type from a variable name
	 * @param st SymbolTable
	 * @param name String
	 * @return variable type
	 * @throws SymbolException
	 */
	private int check_ID(SymbolTable st, String name) throws SymbolException {
		return SymbolTable.getType(st, name);
	}
	
	/**
	 * Checks array index from current node (it must be int). curr_node must be "array_dimensions" non-terminal
	 * @param st Symbol Table
	 * @param curr_node Node
	 * @return sym.INT type
	 * @throws SymbolException
	 */
	private int check_Array_Index(SymbolTable st, Node curr_node) throws SymbolException {
		if (debug) System.out.println("Checking array index" + curr_node.name);
		Node n = curr_node;
		if (n.children.size() < 1) return -1;
		while (n.children.size() > 0) { 
			if (check_M_expr(st, (Node)curr_node.children.get(0)) != sym.INT) throw new SymbolException("Symbol Error: array index must int");
			n = (Node)n.children.get(1);
		}
		return sym.INT;
	}
	
	/**
	 * Checks type. curr_node must be "M_type" non-terminal
	 * @param curr_node Node
	 * @return type
	 */
	private int check_M_type(Node curr_node) {
		Node n = curr_node;
		if (n.name.equals("M_int")) return sym.INT;
		if (n.name.equals("M_real")) return sym.REAL;
		if (n.name.equals("M_bool")) return sym.BOOL;
		return -1;
	}
	
	/**
	 * Verify M_expr. curr_node must be one of "M_expr" non-terminal
	 * @param st Symbol Table
	 * @param curr_node Node
	 * @return type of the evaluated expr
	 * @throws SymbolException
	 */
	private int check_M_expr(SymbolTable st, Node curr_node) throws SymbolException  {
		Node n = curr_node;
		if (n.name.equals("M_ival")) return sym.INT;
		if (n.name.equals("M_rval")) return sym.REAL;
		if (n.name.equals("M_bval")) return sym.BOOL;
		// check M_size
		if (n.name.equals("M_size")) {
			if (debug) System.out.println("Checking M_size");
			String id = n.children.get(0).toString();
			MySymbol s = SymbolTable.getSymbol(st, id);
			if (! (s instanceof VarSymbol)) throw new SymbolException("Symbol Error: "+id+" is not var");
			VarSymbol v = (VarSymbol)s;
			n = (Node)n.children.get(1);
			int array_dim = 0;
			while (n.children.size() > 0) {
				array_dim ++;
				n = (Node)n.children.get(0);
			}
			if (array_dim == 0) throw new SymbolException("Symbol Error: "+id+" is not an array");
			if (array_dim != v.dimentions) throw new SymbolException("Symbol Error: "+id+" does not have dimension "+array_dim);
			return sym.INT;
			
		}
		// check M_id. could be either a function call or a variable
		if (n.name.equals("M_id")){
			
			String id = n.children.get(0).toString();
			n = (Node)n.children.get(1);
			// check function call
			if (n.name.equals("M_fn")) {
				if (debug) System.out.println("Checking M_fn");
				int returnType = SymbolTable.getReturnType(st, id);
				// check function param
				FunSymbol s = (FunSymbol)SymbolTable.getSymbol(st, id);
				n = (Node)n.children.get(0);
				int i = 0;
				while (i < s.param_list.size()) {
					if (n.children.size() < 1) throw new SymbolException("Symbol Error: not enough args in function "+id);
					int type = check_M_expr(st, (Node)n.children.get(0));
					if (type != s.getParam(i).getType()) throw new SymbolException("Symbol Error: wrong type args in function "+id);
					n = (Node)n.children.get(1);
					i ++;
				}
				return returnType;
			}
			// check variable
			else {
				if (debug) System.out.println("Checking M_id");
				int type = SymbolTable.getType(st, id);
				n = (Node)n.children.get(0);
				// variable may be an array
				check_Array_Index(st, n);
				return type;
				
			}
		}
		// check add/sub
		if (n.name.equals("M_addop")) {
			if (debug) System.out.println("Checking M_addop");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			int type2 = check_M_expr(st, (Node)n.children.get(2));
			if (type1 == sym.REAL && type2 == sym.REAL) {
				return sym.REAL;
			}
			else if (type1 == sym.INT && type2 == sym.INT) {
				return sym.INT;
			}
			else  throw new SymbolException("Symbol Error: ADDOP cannot be used for "+n.toString());
		}
		// check mul/div
		if (n.name.equals("M_mulop")) {
			if (debug) System.out.println("Checking M_mulop");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			int type2 = check_M_expr(st, (Node)n.children.get(2));
			if (type1 == sym.REAL && type2 == sym.REAL) {
				return sym.REAL;
			}
			else if (type1 == sym.INT && type2 == sym.INT) {
				return sym.INT;
			}
			else  throw new SymbolException("Symbol Error: MULOP cannot be used for  "+n.toString());
		}
		// check negation
		if (n.name.equals("M_neg")) {
			if (debug) System.out.println("Checking M_neg");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			if (type1 == sym.REAL || type1 == sym.INT) {
				return type1;
			}
			else  throw new SymbolException("Symbol Error: NEGATION cannot be used for "+n.toString());
		}
		// check comparison
		if (n.name.equals("M_comp")) {
			if (debug) System.out.println("Checking M_comp");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			int type2 = check_M_expr(st, (Node)n.children.get(2));
			if (type1 == sym.REAL && type2 == sym.REAL) {
				return sym.BOOL;
			}
			else if (type1 == sym.INT && type2 == sym.INT) {
				return sym.BOOL;
			}
			else  throw new SymbolException("Symbol Error: COMPARISION cannot be used for "+n.toString());
		}
		// check NOT
		if (n.name.equals("M_not")) {
			if (debug) System.out.println("Checking M_not");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			if (type1 == sym.BOOL) {
				return type1;
			}
			else  throw new SymbolException("Symbol Error: NOT cannot be used for "+n.toString());
		}
		// check OR
		if (n.name.equals("M_or")) {
			if (debug) System.out.println("Checking M_or");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			int type2 = check_M_expr(st, (Node)n.children.get(1));
			if (type1 == sym.BOOL && type2 == sym.BOOL) {
				return sym.BOOL;
			}
			else  throw new SymbolException("Symbol Error: OR cannot be used for "+n.toString());
		}
		// check AND
		if (n.name.equals("M_and")) {
			if (debug) System.out.println("Checking M_and");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			int type2 = check_M_expr(st, (Node)n.children.get(1));
			if (type1 == sym.BOOL && type2 == sym.BOOL) {
				return sym.BOOL;
			}
			else  throw new SymbolException("Symbol Error: AND cannot be used for "+n.toString());
		}
		// check float
		if (n.name.equals("M_float")) {
			if (debug) System.out.println("Checking M_float");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			if (type1 == sym.INT) {
				return sym.FLOAT;
			}
			else  throw new SymbolException("Symbol Error: FLOAT cannot be used for "+n.toString());
		}
		// check floor
		if (n.name.equals("M_floor")) {
			if (debug) System.out.println("Checking M_floor");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			if (type1 == sym.FLOAT) {
				return sym.INT;
			}
			else  throw new SymbolException("Symbol Error: FLOOR cannot be used for "+n.toString());
		}
		// check ceil
		if (n.name.equals("M_ceil")) {
			if (debug) System.out.println("Checking M_ceil");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			if (type1 == sym.FLOAT) {
				return sym.INT;
			}
			else  throw new SymbolException("Symbol Error: CEIL cannot be used for "+n.toString());
		}
		// if not of the above, check current node's child
		if (n.name.equals("M_expr")) return check_M_expr(st, (Node)n.children.get(0)); 
		if (debug) System.out.println(n);
		throw new SymbolException("Symbol Error: unable to check expr node");
	}
	
	/**
	 * Check M_stmt. curr_node must be one of "M_stmt" non-terminal
	 * @param st SymbolTable
	 * @param curr_node Node
	 * @return int (but this doesn't matter)
	 * @throws SymbolException
	 */
	private int check_M_stmt(SymbolTable st, Node curr_node) throws SymbolException {
		Node n = curr_node;
		// P_body
		if (n.name.equals("P_body")) {
			if (debug) System.out.println("Checking P_body");
			return check_M_stmt(st, (Node)n.children.get(0));
		}
		// P_stmts
		if (n.name.equals("P_stmts")) {
			if (debug) System.out.println("Checking P_stmt");
			check_M_stmt(st, (Node)n.children.get(0));
			check_M_stmt(st, (Node)n.children.get(1));
			return 0;
		}
		// Checking Assignment
		if (n.name.equals("M_ass")) {
			if (debug) System.out.println("Checking M_ass");
			int rightType = check_M_expr(st, (Node)n.children.get(1));
			n = (Node)n.children.get(0);
			String id = n.children.get(0).toString();
			int leftType = check_ID(st, id);
			check_Array_Index(st, (Node)n.children.get(1));
			if (leftType != rightType) throw new SymbolException("Symbol Error: type mismatch in assignment - "+curr_node.toString());
			return 0;
		}
		// Checking while stmt
		if (n.name.equals("M_while")) {
			if (debug) System.out.println("Checking M_while");
			if (check_M_expr(st, (Node)n.children.get(0)) != sym.BOOL)  throw new SymbolException("Symbol Error: while statement expects a bool - "+curr_node.toString());
			return check_M_stmt(st, (Node)n.children.get(1));
		}
		// Checking read stmt
		if (n.name.equals("M_read")) {
			if (debug) System.out.println("Checking M_read");
			n = (Node)n.children.get(0);
			String id = n.children.get(0).toString();
			check_Array_Index(st, (Node)n.children.get(1));
			check_ID(st, id);
			return 0;
		}
		// Checking if stmt
		if (n.name.equals("M_cond")) {
			if (debug) System.out.println("Checking M_cond");
			if (check_M_expr(st, (Node)n.children.get(0)) != sym.BOOL)  throw new SymbolException("Symbol Error: if statement expects a bool - "+curr_node.toString());
			return check_M_stmt(st, (Node)n.children.get(1)) + check_M_stmt(st, (Node)n.children.get(2));
		}
		// Checking code block (have its own separate SymbolTable)
		if (n.name.equals("M_stmt_block")) {
			if (debug) System.out.println("Checking M_block");
			n = (Node)n.children.get(0);
			blockCounter ++;
			SymbolTable blockSt = st.insertST("Block"+blockCounter);
			SymbolTable st1 = check_M_decl(blockSt, (Node)n.children.get(0));
			return check_M_stmt(st1, (Node)n.children.get(1));
		}
		// Checking print
		if (n.name.equals("M_print")) {
			if (debug) System.out.println("Checking M_print");
			check_M_expr(st, (Node)n.children.get(0));
			return 0;
		}
		// Checking function body (have to make sure the return type matches)
		if (n.name.equals("F_body")) {
			if (debug) System.out.println("Checking F_body");
			String id = st.scope_name;
			int returnType = check_M_expr(st, (Node)n.children.get(1));
			if (returnType != SymbolTable.getReturnType(st, id))  throw new SymbolException("Symbol Error: return type mismatch in function "+id);
			return 0;
		}
		// do nothing on episilon
		if (n.children.size() == 0) return 0;
		throw new SymbolException("Symbol Error: unable to check stmt node");
	}
	
	/**
	 * Checking M_decl. curr_node must be one of "M_decl" non-terminals
	 * @param st SymbolTable
	 * @return SymbolTable (may or may not be the same as input SymbolTable)
	 * @throws SymbolException 
	 */
	private SymbolTable check_M_decl(SymbolTable st, Node curr_node) throws SymbolException {
		Node n = curr_node;
		// M_decls
		if (n.name.equals("M_decls")) {
			if (debug) System.out.println("Checking M_decls");
			SymbolTable st1 = check_M_decl(st, (Node)n.children.get(0));
			st1 = check_M_decl(st1, (Node)n.children.get(1));
			return st1;
		}
		// M_var_decl
		if (n.name.equals("M_var_decl")) {
			if (debug) System.out.println("Checking M_var_decl");
			return check_M_decl(st, (Node)n.children.get(0));
		}
		// M_fun_decl
		if (n.name.equals("M_fun_decl")) {
			if (debug) System.out.println("Checking M_fun_decl");
			return check_M_decl(st, (Node)n.children.get(0));
		}
		// Inserting variables into current SymbolTable
		if (n.name.equals("M_var")) {
			if (debug) System.out.println("Checking M_var");
			String id = n.children.get(0).toString();
			int type = check_M_type((Node)n.children.get(2));
			int dim = 0;
			n = (Node)n.children.get(1);
			while (n.children.size() > 0) { 
				if (check_M_expr(st, (Node)curr_node.children.get(0)) != sym.INT) throw new SymbolException("Symbol Error: array index must int");
				dim ++;
				n = (Node)n.children.get(1);
			}
			if (debug) System.out.println("Adding "+id+" to st["+st.scope_name+"]");
			if (debug) System.out.println(st);
			SymbolTable.insertSymbol(st, id, type, dim);
			if (debug) System.out.println(st);
			return st;
		}
		// Inserting functions into current SymbolTable
		if (n.name.equals("M_fun")) {
			if (debug) System.out.println("Checking M_fun");
			String name = n.children.get(0).toString();
			int returnType = check_M_type((Node)n.children.get(2));
			if (st.symbols.contains(name)) throw new SymbolException("Symbol Error: "+name+" already declared");
			String fun_lable = "FUN"+SymbolTable.fun_lable_counter;
			FunSymbol s = new FunSymbol(name, fun_lable, returnType);
			SymbolTable.fun_lable_counter ++;
			
			// Each function must have it's own seperate SymbolTable
			SymbolTable funSt = st.insertST(name);
			funSt.return_type = returnType;
			
			// Parsing function params
			Node paramN = (Node)n.children.get(1);
			paramN = (Node)paramN.children.get(0);
			while (paramN.children.size() > 0) {
				if (debug) System.out.println("Checking M_fun params");
				Node next = (Node)paramN.children.get(1);
				paramN = (Node)paramN.children.get(0);
				String id = paramN.children.get(0).toString();
				int type = check_M_type((Node)paramN.children.get(2));
				int dims = 0;
				Node dimN = (Node)paramN.children.get(1);
				while (dimN.children.size() > 0) {
					dims ++;
					dimN = (Node)dimN.children.get(0);
				}
				Param p = new Param(type, dims);
				s.addParam(p);
				SymbolTable.insertSymbol(funSt, p, id);
				paramN = next;
			}
			
			SymbolTable.insertSymbol(st, s);
			// go inside the function body
			check_F_block(funSt, (Node)n.children.get(3));
			return st;
		}
		return st;
		
	}
	
	/**
	 * Check inside function body. curr_node must be "F_block" non-terminal
	 * @param st SymbolTable (must be function's own SymbolTable
	 * @param curr_node Node
	 * @throws SymbolException
	 */
	private void check_F_block(SymbolTable st, Node curr_node) throws SymbolException {
		if (debug) System.out.println("Checking F_block");
		st = check_M_decl(st, (Node)curr_node.children.get(0));
		check_M_stmt(st, (Node)curr_node.children.get(1));
	}

}
