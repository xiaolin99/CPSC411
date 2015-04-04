
public class SymbolChecker {
	
	Node start;
	String s;
	
	public static int blockCounter;
	

	public SymbolChecker(Node start) {
		this.start = start;
	}

	public boolean checkSyntax() {
		try {
			SymbolTable st = new SymbolTable("M_prog");
		}
		catch(SymbolException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private int check_ID(SymbolTable st, String name) throws SymbolException {
		return SymbolTable.getType(st, name);
	}
	
	private int check_Array_Index(SymbolTable st, Node curr_node) throws SymbolException {
		Node n = curr_node;
		if (n.children.size() < 1) return -1;
		while (n.children.size() > 0) { 
			if (check_M_expr(st, (Node)curr_node.children.get(0)) != sym.INT) throw new SymbolException("Symbol Error: array index must int");
			n = (Node)n.children.get(1);
		}
		return sym.INT;
	}
	
	private int check_M_type(Node curr_node) {
		Node n = curr_node;
		if (n.name.equals("M_int")) return sym.INT;
		if (n.name.equals("M_real")) return sym.REAL;
		if (n.name.equals("M_bool")) return sym.BOOL;
		return -1;
	}
	

	
	private int check_M_expr(SymbolTable st, Node curr_node) throws SymbolException  {
		Node n = curr_node;
		if (n.name.equals("M_ival")) return sym.INT;
		if (n.name.equals("M_rval")) return sym.REAL;
		if (n.name.equals("M_bval")) return sym.BOOL;
		if (n.name.equals("M_size")) {
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
		if (n.name.equals("M_id")){
			String id = n.children.get(0).toString();
			n = (Node)n.children.get(1);
			if (n.name.equals("M_fn")) {
				// check function
				int returnType = SymbolTable.getReturnType(st, id);
				// check param
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
			else {
				// check array
				int type = SymbolTable.getType(st, id);
				n = (Node)n.children.get(0);
				check_Array_Index(st, n);
				return type;
			}
		}
		if (n.name.equals("M_addop")) {
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
		
		if (n.name.equals("M_mulop")) {
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
		
		if (n.name.equals("M_neg")) {
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			if (type1 == sym.REAL || type1 == sym.INT) {
				return type1;
			}
			else  throw new SymbolException("Symbol Error: NEGATION cannot be used for "+n.toString());
		}
		
		if (n.name.equals("M_comp")) {
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
		
		if (n.name.equals("M_not")) {
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			if (type1 == sym.BOOL) {
				return type1;
			}
			else  throw new SymbolException("Symbol Error: NOT cannot be used for "+n.toString());
		}
		
		if (n.name.equals("M_or")) {
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			int type2 = check_M_expr(st, (Node)n.children.get(1));
			if (type1 == sym.BOOL && type2 == sym.BOOL) {
				return sym.BOOL;
			}
			else  throw new SymbolException("Symbol Error: OR cannot be used for "+n.toString());
		}
		if (n.name.equals("M_and")) {
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			int type2 = check_M_expr(st, (Node)n.children.get(1));
			if (type1 == sym.BOOL && type2 == sym.BOOL) {
				return sym.BOOL;
			}
			else  throw new SymbolException("Symbol Error: AND cannot be used for "+n.toString());
		}
		if (n.name.equals("M_float")) {
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			if (type1 == sym.INT) {
				return sym.FLOAT;
			}
			else  throw new SymbolException("Symbol Error: FLOAT cannot be used for "+n.toString());
		}
		
		if (n.name.equals("M_floor")) {
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			if (type1 == sym.FLOAT) {
				return sym.INT;
			}
			else  throw new SymbolException("Symbol Error: FLOOR cannot be used for "+n.toString());
		}
		
		if (n.name.equals("M_ceil")) {
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			if (type1 == sym.FLOAT) {
				return sym.INT;
			}
			else  throw new SymbolException("Symbol Error: CEIL cannot be used for "+n.toString());
		}
		
		if (n.name.equals("M_expr")) return check_M_expr(st, (Node)n.children.get(0)); 
		
		throw new SymbolException("Symbol Error: unable to check expr node");
	}
	
	private int check_M_stmt(SymbolTable st, Node curr_node) throws SymbolException {
		Node n = curr_node;
		if (n.name.equals("P_body")) {
			return check_M_stmt(st, (Node)n.children.get(0));
		}
		if (n.name.equals("P_stmts")) {
			check_M_stmt(st, (Node)n.children.get(0));
			check_M_stmt(st, (Node)n.children.get(1));
			return 0;
		}
		if (n.name.equals("M_ass")) {
			int rightType = check_M_expr(st, (Node)n.children.get(1));
			n = (Node)n.children.get(0);
			String id = n.children.get(0).toString();
			int leftType = check_ID(st, id);
			check_Array_Index(st, (Node)n.children.get(1));
			if (leftType != rightType) throw new SymbolException("Symbol Error: type mismatch in assignment - "+curr_node.toString());
			return 0;
		}
		if (n.name.equals("M_while")) {
			if (check_M_expr(st, (Node)n.children.get(0)) != sym.BOOL)  throw new SymbolException("Symbol Error: while statement expects a bool - "+curr_node.toString());
			return check_M_stmt(st, (Node)n.children.get(1));
		}
		if (n.name.equals("M_read")) {
			n = (Node)n.children.get(0);
			String id = n.children.get(0).toString();
			check_Array_Index(st, (Node)n.children.get(1));
			check_ID(st, id);
			return 0;
		}
		if (n.name.equals("M_cond")) {
			if (check_M_expr(st, (Node)n.children.get(0)) != sym.BOOL)  throw new SymbolException("Symbol Error: if statement expects a bool - "+curr_node.toString());
			return check_M_stmt(st, (Node)n.children.get(1)) + check_M_stmt(st, (Node)n.children.get(2));
		}
		if (n.name.equals("M_stmt_block")) {
			n = (Node)n.children.get(0);
			blockCounter ++;
			SymbolTable blockSt = st.insertST("Block"+blockCounter);
			SymbolTable st1 = check_M_decl(blockSt, (Node)n.children.get(0));
			return check_M_stmt(st1, (Node)n.children.get(1));
		}
		if (n.name.equals("M_print")) {
			check_M_expr(st, (Node)n.children.get(0));
			return 0;
		}
		if (n.name.equals("F_body")) {
			String id = st.scope_name;
			int returnType = check_M_expr(st, (Node)n.children.get(1));
			if (returnType != SymbolTable.getReturnType(st, id))  throw new SymbolException("Symbol Error: return type mismatch in function "+id);
			return 0;
		}
		if (n.children.size() == 0) return 0;
		throw new SymbolException("Symbol Error: unable to check stmt node");
	}
	
	/**
	 * todo
	 * @param st
	 * @return
	 * @throws SymbolException 
	 */
	private SymbolTable check_M_decl(SymbolTable st, Node curr_node) throws SymbolException {
		Node n = curr_node;
		if (n.name.equals("M_decls")) {
			SymbolTable st1 = check_M_decl(st, (Node)n.children.get(0));
			st1 = check_M_decl(st1, (Node)n.children.get(1));
			return st1;
		}
		if (n.name.equals("M_var_decl")) {
			return check_M_decl(st, (Node)n.children.get(0));
		}
		if (n.name.equals("M_fun_decl")) {
			return check_M_decl(st, (Node)n.children.get(0));
		}
		if (n.name.equals("M_var")) {
			String id = n.children.get(0).toString();
			int type = check_M_type((Node)n.children.get(2));
			int dim = 0;
			n = (Node)n.children.get(1);
			while (n.children.size() > 0) { 
				if (check_M_expr(st, (Node)curr_node.children.get(0)) != sym.INT) throw new SymbolException("Symbol Error: array index must int");
				dim ++;
				n = (Node)n.children.get(1);
			}
			SymbolTable.insertSymbol(st, id, type, dim);
			return st;
		}
		if (n.name.equals("M_fun")) {
			String name = n.children.get(0).toString();
			int returnType = check_M_type((Node)n.children.get(2));
			if (st.symbols.contains(name)) throw new SymbolException("Symbol Error: "+name+" already declared");
			String fun_lable = "FUN"+SymbolTable.fun_lable_counter;
			FunSymbol s = new FunSymbol(name, fun_lable, returnType);
			SymbolTable.fun_lable_counter ++;
			
			SymbolTable funSt = st.insertST(name);
			funSt.return_type = returnType;
			Node paramN = (Node)n.children.get(1);
			paramN = (Node)paramN.children.get(0);
			
			while (paramN.children.size() > 0) {
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
			
			check_F_block(funSt, (Node)n.children.get(3));
			
			SymbolTable.insertSymbol(st, s);
			
			
			return st;
			
		}
		return st;
		
	}
	
	private void check_F_block(SymbolTable st, Node curr_node) throws SymbolException {
		st = check_M_decl(st, (Node)curr_node.children.get(0));
		check_M_stmt(st, (Node)curr_node.children.get(1));
	}

}
