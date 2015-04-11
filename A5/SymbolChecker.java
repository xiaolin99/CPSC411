import java.io.PrintWriter;
import java.util.ArrayList;

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
	boolean debug = false;
	String str;
	String tmpS;
	boolean pushToTmpS = false;;
	PrintWriter AM;
	int lCounter = 0;
	
	/**
	 * block counter for code blocks
	 */
	public static int blockCounter;
	
	/**
	 * Constructor - pass in the start AST node
	 * @param start
	 */
	public SymbolChecker(Node start, PrintWriter f) {
		this.start = start;
		str = "";
		tmpS += "";
		AM = f;
	}

	/**
	 * Check Semantics and print out the IR
	 * @return true if semantics are good
	 */
	public boolean checkSyntax() {
		try {
			System.out.println("Checking Semantics ...");
			if (debug) System.out.println("Starting from M_prog");
			SymbolTable st = new SymbolTable("M_prog");
			Node n = (Node)start.children.get(0);
			AM.println("\tJUMP mprog");
			addToString("IPROG (\n");
			addToString("[");
			st = check_M_decl(st, (Node)n.children.get(0));
			
			addToString("]\n,");
			addToString(st.num_vars + "\n,[");
			ArrayList<VarSymbol> l = SymbolTable.getArrays(st);
			int i = 0;
			while (i < l.size()) {
				addToString("(");
				addToString(l.get(i).offset + ",");
				for (int j = 0; j < l.get(i).dimentions; j ++) {
					addToString("[");
					addToString(l.get(i).dimensionsIR[j]);
					addToString("]");
				}
				addToString(")");
				i ++;
			}
			addToString("]\n,");
			AM.println("mprog:");
			AM.println("\tLOAD_R %sp");
			AM.println("\tLOAD_R %sp");
			AM.println("\tSTORE_R %fp");
			AM.println("\tALLOC "+st.num_vars);
			allocArrays(st);
			check_M_stmt(st, (Node)n.children.get(1));
			addToString(")\n");
			AM.println("\tALLOC -"+(st.num_vars+1));
			AM.println("\tHALT");
			System.out.println("Intermediate Representation: ");
			System.out.println(str);
		}
		catch(SymbolException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Function to increment and return label counter
	 * @return
	 */
	private int getLCounter() {
		lCounter ++;
		return lCounter;
	}
	
	/**
	 * Helper function to add to string
	 */
	private void addToString(String s) {
		if (s == null || s == "null") return;
		if (!pushToTmpS) str += s;
		else tmpS += s;
	}
	
	/**
	 * Print am code for loading variable v
	 * @param v
	 */
	private void AMLoadVar(VarSymbol v) {
		AM.println("\tLOAD_R %fp");
		int l = 0;
		while (l < v.level) {
			AM.println("\tLOAD_O -2");
			l ++;
		}
		AM.println("\tLOAD_O "+v.offset);
	}
	
	/**
	 * Print am code for storing to variable v
	 * @param v
	 */
	private void AMStoreVar(VarSymbol v) {
		AM.println("\tLOAD_R %fp");
		int l = 0;
		while (l < v.level) {
			AM.println("\tLOAD_O -2");
			l ++;
		}
		AM.println("\tSTORE_O "+v.offset);
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
	 * @return int dimensions
	 * @throws SymbolException
	 */
	private int check_Array_Index(SymbolTable st, Node curr_node, VarSymbol v) throws SymbolException {
		if (debug) System.out.println("Checking array index" + curr_node.name);
		addToString("[");
		int dim = 0;
		Node n = curr_node;
		if (n.children.size() < 1) {
			addToString("]");
			return dim;
		}
		while (n.children.size() > 0) { 
			if (check_M_expr(st, (Node)curr_node.children.get(0)) != sym.INT) throw new SymbolException("Symbol Error: array index must int");
			// trying to calculate the total offset of the array item
			int i = dim+1;
			while (i < v.dimentions) {
				AMLoadVar(v);
				AM.println("\tLOAD_O "+i);
				AM.println("\tAPP MUL");
				i ++;
			}
			if (dim > 0) AM.println("\tAPP ADD");
			addToString(",");
			n = (Node)n.children.get(1);
			dim ++;
		}
		AM.println("\tLOAD_I "+v.dimentions);
		AM.println("\tAPP ADD");
		addToString("]");
		return dim;
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
		if (n.name.equals("M_ival")) {
			addToString("IINT "+n.children.get(0).toString());
			AM.println("\tLOAD_I "+n.children.get(0).toString());
			return sym.INT;
		}
		if (n.name.equals("M_rval")) {
			addToString("IREAL "+n.children.get(0).toString());
			AM.println("\tLOAD_F "+n.children.get(0).toString());
			return sym.REAL;
		}
		if (n.name.equals("M_bval")) {
			addToString("IBOOL "+n.children.get(0).toString());
			AM.println("\tLOAD_B "+n.children.get(0).toString());
			return sym.BOOL;
		}
		// check M_size
		if (n.name.equals("M_size")) {
			if (debug) System.out.println("Checking M_size");
			String id = n.children.get(0).toString();
			MySymbol s = SymbolTable.getSymbol(st, id);
			if (! (s instanceof VarSymbol)) throw new SymbolException("Symbol Error: "+id+" is not var");
			VarSymbol v = (VarSymbol)s;
			if (v.dimentions == 0)  throw new SymbolException("Symbol Error: M_size "+id+" is not an array");
			n = (Node)n.children.get(1);
			int array_dim = 0;
			while (n.children.size() > 0) {
				array_dim ++;
				n = (Node)n.children.get(0);
			}
			
			if (array_dim == 0) throw new SymbolException("Symbol Error: M_size "+id+" needs an dimension");
			if (array_dim > v.dimentions) throw new SymbolException("Symbol Error: "+id+" does not have dimension "+array_dim);
			addToString("ISIZE(" + v.level+","+v.offset+","+array_dim+")");
			array_dim --;
			AMLoadVar(v);
			AM.println("\tLOAD_I "+array_dim);
			AM.println("\tLOAD_OS");
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
				addToString(" IAPP(ICALL (\""+s.lable+"\","+s.level+"),");
				n = (Node)n.children.get(0);
				int i = 0;
				while (i < s.param_list.size()) {
					if (n.children.size() < 1) throw new SymbolException("Symbol Error: not enough args in function "+id);
					int type = check_M_expr(st, (Node)n.children.get(0));
					if (type != s.getParam(i).getType()) throw new SymbolException("Symbol Error: wrong type args in function "+id);
					n = (Node)n.children.get(1);
					i ++;
				}
				if (n.children.size() > 0) throw new SymbolException("Symbol Error: too many args in function "+id);
				addToString(")");
				AM.println("\tALLOC 1");
				AM.println("\tLOAD_R %fp");
				int l = 0;
				while(l < s.level) {
					AM.println("\tLOAD_O -2");
					l ++;
				}
				AM.println("\tLOAD_R %fp");
				AM.println("\tLOAD_R %cp");
				AM.println("\tJUMP "+s.lable);
				return returnType;
			}
			// check variable
			else {
				if (debug) System.out.println("Checking M_id");
				MySymbol s = SymbolTable.getSymbol(st, id);
				if (! (s instanceof VarSymbol)) throw new SymbolException("Symbol Error: "+id+" is not var");
				VarSymbol v = (VarSymbol)s;
				addToString(" IID("+v.level+","+v.offset+",");
				n = (Node)n.children.get(0);
				// variable may be an array
				AMLoadVar(v);
				int dim = check_Array_Index(st, n, v);
				// if (dim != v.dimentions) throw new SymbolException("Symbol Error: "+id+" dimensions mismatch");
				if (dim > 0) AM.println("\tLOAD_OS");
				
				addToString(")");
				return v.type;
				
			}
		}
		// check add/sub
		if (n.name.equals("M_addop")) {
			if (debug) System.out.println("Checking M_addop");
			boolean savedState = pushToTmpS;
			if (!pushToTmpS) tmpS = "";
			pushToTmpS = true;
			tmpS += " IAPP(";
			if (n.children.get(1).toString().contains("sub")) tmpS += "ISUB,[";
			else tmpS += "IADD,[";
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			tmpS += "],[";
			int type2 = check_M_expr(st, (Node)n.children.get(2));
			tmpS += "])";
			if (type1 == sym.REAL && type2 == sym.REAL) {
				if (n.children.get(1).toString().contains("sub")) {
					tmpS = tmpS.replaceFirst("ISUB", "ISUB_F");
					AM.println("\tAPP SUB_F");
				}
				else {
					tmpS = tmpS.replaceFirst("IADD", "IADD_F");
					AM.println("\tAPP ADD_F");
				}
				pushToTmpS = savedState;
				if (!pushToTmpS) { addToString(tmpS); tmpS = "";}
				return sym.REAL;
			}
			else if (type1 == sym.INT && type2 == sym.INT) {
				if (n.children.get(1).toString().contains("sub")) {
					AM.println("\tAPP SUB");
				}
				else {
					AM.println("\tAPP ADD");
				}
				pushToTmpS = savedState;
				if (!pushToTmpS) { addToString(tmpS); tmpS = "";}
				return sym.INT;
			}
			else  throw new SymbolException("Symbol Error: ADDOP cannot be used for "+n.toString());
		}
		// check mul/div
		if (n.name.equals("M_mulop")) {
			boolean savedState = pushToTmpS;
			if (!pushToTmpS) tmpS = "";
			pushToTmpS = true;
			tmpS += " IAPP(";
			if (n.children.get(1).toString().contains("div")) tmpS += "IDIV,[";
			else tmpS += "IMUL,[";
			if (debug) System.out.println("Checking M_mulop");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			tmpS += "],[";
			int type2 = check_M_expr(st, (Node)n.children.get(2));
			tmpS += "])";
			if (type1 == sym.REAL && type2 == sym.REAL) {
				if (n.children.get(1).toString().contains("div")) {
					tmpS = tmpS.replaceFirst("IDIV", "IDIV_F");
					AM.println("\tAPP DIV_F");
				}
				else {
					tmpS = tmpS.replaceFirst("IMUL", "IMUL_F");
					AM.println("\tAPP MUL_F");
				}
				pushToTmpS = savedState;
				if (!pushToTmpS) { addToString(tmpS); tmpS = "";}
				return sym.REAL;
			}
			else if (type1 == sym.INT && type2 == sym.INT) {
				if (n.children.get(1).toString().contains("div")) {
					AM.println("\tAPP DIV");
				}
				else {
					AM.println("\tAPP MUL");
				}
				pushToTmpS = savedState;
				if (!pushToTmpS) { addToString(tmpS); tmpS = "";}
				return sym.INT;
			}
			else  throw new SymbolException("Symbol Error: MULOP cannot be used for  "+n.toString());
		}
		// check negation
		if (n.name.equals("M_neg")) {
			boolean savedState = pushToTmpS;
			if (!pushToTmpS) tmpS = "";
			pushToTmpS = true;
			tmpS += " IAPP(INEG,[";
			if (debug) System.out.println("Checking M_neg");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			tmpS += "])";
			if (type1 == sym.REAL) {
				tmpS = tmpS.replaceFirst("INEG", "INEG_F");
				AM.println("\tAPP NEG_F");
				pushToTmpS = savedState;
				if (!pushToTmpS) { addToString(tmpS); tmpS = "";}
				return type1;
			}
			else if (type1 == sym.INT) {
				AM.println("\tAPP NEG");
				pushToTmpS = savedState;
				if (!pushToTmpS) { addToString(tmpS); tmpS = "";}
				return type1;
			}
			else throw new SymbolException("Symbol Error: NEGATION cannot be used for "+n.toString());
		}
		// check comparison
		if (n.name.equals("M_comp")) {
			boolean savedState = pushToTmpS;
			if (!pushToTmpS) tmpS = "";
			pushToTmpS = true;
			tmpS += " IAPP(";
			if (n.children.get(1).toString().contains("lt")) tmpS += "ILT,[";
			else if (n.children.get(1).toString().contains("le")) tmpS += "ILE,[";
			else if (n.children.get(1).toString().contains("gt")) tmpS += "IGT,[";
			else if (n.children.get(1).toString().contains("ge")) tmpS += "IGE,[";
			else tmpS += "IEQ,[";
			if (debug) System.out.println("Checking M_comp");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			tmpS += "],[";
			int type2 = check_M_expr(st, (Node)n.children.get(2));
			tmpS += "])";
			if (type1 == sym.REAL && type2 == sym.REAL) {
				if (n.children.get(1).toString().contains("lt")) {
					tmpS = tmpS.replaceFirst("ILT", "ILT_F");
					AM.println("\tAPP LT_F");
				}
				else if (n.children.get(1).toString().contains("le")){
					tmpS = tmpS.replaceFirst("ILE", "ILE_F");
					AM.println("\tAPP LE_F");
				}
				else if (n.children.get(1).toString().contains("gt")) {
					tmpS = tmpS.replaceFirst("IGT", "IGT_F");
					AM.println("\tAPP GT_F");
				}
				else if (n.children.get(1).toString().contains("ge")) {
					tmpS = tmpS.replaceFirst("IGE", "IGE_F");
					AM.println("\tAPP GE_F");
				}
				else {
					tmpS = tmpS.replaceFirst("IEQ", "IEQ_F");
					AM.println("\tAPP EQ_F");
				}
				pushToTmpS = savedState;
				if (!pushToTmpS) { addToString(tmpS); tmpS = "";}
				return sym.BOOL;
			}
			else if (type1 == sym.INT && type2 == sym.INT) {
				if (n.children.get(1).toString().contains("lt")) {
					AM.println("\tAPP LT");
				}
				else if (n.children.get(1).toString().contains("le")){
					AM.println("\tAPP LE");
				}
				else if (n.children.get(1).toString().contains("gt")) {
					AM.println("\tAPP GT");
				}
				else if (n.children.get(1).toString().contains("ge")) {
					AM.println("\tAPP GE");
				}
				else {
					AM.println("\tAPP EQ");
				}
				pushToTmpS = savedState;
				if (!pushToTmpS) { addToString(tmpS); tmpS = "";}
				return sym.BOOL;
			}
			else  throw new SymbolException("Symbol Error: COMPARISION cannot be used for "+n.toString());
		}
		// check NOT
		if (n.name.equals("M_not")) {
			addToString(" IAPP(INOT,[");
			if (debug) System.out.println("Checking M_not");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			addToString("])");
			if (type1 == sym.BOOL) {
				AM.println("\tAPP NOT");
				return type1;
			}
			else  throw new SymbolException("Symbol Error: NOT cannot be used for "+n.toString());
		}
		// check OR
		if (n.name.equals("M_or")) {
			addToString(" IAPP(IOR,[");
			if (debug) System.out.println("Checking M_or");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			addToString("],[");
			int type2 = check_M_expr(st, (Node)n.children.get(1));
			addToString("])");
			if (type1 == sym.BOOL && type2 == sym.BOOL) {
				AM.println("\tAPP OR");
				return sym.BOOL;
			}
			else  throw new SymbolException("Symbol Error: OR cannot be used for "+n.toString());
		}
		// check AND
		if (n.name.equals("M_and")) {
			addToString(" IAPP(IAND,[");
			if (debug) System.out.println("Checking M_and");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			addToString("],[");
			int type2 = check_M_expr(st, (Node)n.children.get(1));
			addToString("])");
			if (type1 == sym.BOOL && type2 == sym.BOOL) {
				AM.println("\tAPP AND");
				return sym.BOOL;
			}
			else  throw new SymbolException("Symbol Error: AND cannot be used for "+n.toString());
		}
		// check float
		if (n.name.equals("M_float")) {
			addToString(" IAPP(IFLOAT,[");
			if (debug) System.out.println("Checking M_float");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			addToString("])");
			if (type1 == sym.INT) {
				AM.println("\tAPP FLOAT");
				return sym.FLOAT;
			}
			else  throw new SymbolException("Symbol Error: FLOAT cannot be used for "+n.toString());
		}
		// check floor
		if (n.name.equals("M_floor")) {
			addToString(" IAPP(IFLOOR,[");
			if (debug) System.out.println("Checking M_floor");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			addToString("])");
			if (type1 == sym.FLOAT) {
				AM.println("\tAPP FLOOR");
				return sym.INT;
			}
			else  throw new SymbolException("Symbol Error: FLOOR cannot be used for "+n.toString());
		}
		// check ceil
		if (n.name.equals("M_ceil")) {
			addToString(" IAPP(ICEIL,[");
			if (debug) System.out.println("Checking M_ceil");
			int type1 = check_M_expr(st, (Node)n.children.get(0));
			addToString("])");
			if (type1 == sym.FLOAT) {
				AM.println("\tAPP CEIL");
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
			addToString("\n");
			if (st.nextLevel != null) addToString("\t");
			addToString(",");
			check_M_stmt(st, (Node)n.children.get(1));
			return 0;
		}
		// Checking Assignment
		if (n.name.equals("M_ass")) {
			if (debug) System.out.println("Checking M_ass");
			addToString("IASS(");
			Node tmpN = (Node)n.children.get(0);
			String id = tmpN.children.get(0).toString();
			MySymbol s = SymbolTable.getSymbol(st, id);
			if (! (s instanceof VarSymbol)) throw new SymbolException("Symbol Error: "+id+" is not var");
			VarSymbol v = (VarSymbol)s;
			addToString(v.level + ","+v.offset+",");
			
			addToString(",");
			int rightType = check_M_expr(st, (Node)n.children.get(1));
			int leftType = v.type;
			
			if (leftType != rightType) throw new SymbolException("Symbol Error: type mismatch in assignment - "+curr_node.toString());
			if (v.dimentions < 1) AMStoreVar(v);
			else {
				AMLoadVar(v);
				int dim = check_Array_Index(st, (Node)tmpN.children.get(1), v);
				if (dim != v.dimentions) throw new SymbolException("Symbol Error: "+id+" dimensions mismatch");
				AM.println("\tSTORE_OS");
			}
			addToString(")");
			return 0;
		}
		// Checking while stmt
		if (n.name.equals("M_while")) {
			addToString("ICOND(");
			if (debug) System.out.println("Checking M_while");
			String l1 = "loop"+getLCounter();
			String l2 = "loop"+getLCounter();
			AM.println(l1+":");
			if (check_M_expr(st, (Node)n.children.get(0)) != sym.BOOL)  throw new SymbolException("Symbol Error: while statement expects a bool - "+curr_node.toString());
			AM.println("\tJUMP_C "+l2);
			addToString(", ");
			check_M_stmt(st, (Node)n.children.get(1));
			AM.println("\tJUMP "+l1);
			AM.println(l2+":");
			addToString(")");
			return 0;
		}
		// Checking read stmt
		if (n.name.equals("M_read")) {
			addToString("IREAD");
			if (debug) System.out.println("Checking M_read");
			n = (Node)n.children.get(0);
			String id = n.children.get(0).toString();
			MySymbol s = SymbolTable.getSymbol(st, id);
			if (! (s instanceof VarSymbol)) throw new SymbolException("Symbol Error: "+id+" is not var");
			VarSymbol v = (VarSymbol)s;
			if (v.type == sym.INT) {
				addToString("_I");
				AM.println("\tREAD_I");
			}
			else if (v.type == sym.REAL) {
				addToString("_F");
				AM.println("\tREAD_F");
			}
			else {
				addToString("_B");
				AM.println("\tREAD_B");
			}
			addToString("("+v.level+","+v.offset+", ");
			if (v.dimentions < 1) AMStoreVar(v);
			else {
				AMLoadVar(v);
				int dim = check_Array_Index(st, (Node)n.children.get(1), v);
				if (dim != v.dimentions) throw new SymbolException("Symbol Error: "+id+" dimensions mismatch");
				AM.println("\tSTORE_OS");
			}
			addToString(")");
			return 0;
		}
		// Checking if stmt
		if (n.name.equals("M_cond")) {
			if (debug) System.out.println("Checking M_cond");
			String l1 = "cond"+getLCounter();
			String l2 = "cond"+getLCounter();
			addToString("ICOND(");
			if (check_M_expr(st, (Node)n.children.get(0)) != sym.BOOL)  throw new SymbolException("Symbol Error: if statement expects a bool - "+curr_node.toString());
			addToString(", ");
			AM.println("\tJUMP_C "+l1);
			check_M_stmt(st, (Node)n.children.get(1));
			AM.println("\tJUMP "+l2);
			addToString(", ");
			AM.println(l1+":");
			check_M_stmt(st, (Node)n.children.get(2));
			AM.println(l2+":");
			addToString(")");
			return 0;
		}
		// Checking code block (have its own separate SymbolTable)
		if (n.name.equals("M_stmt_block")) {
			if (debug) System.out.println("Checking M_block");
			addToString("\nIBLOCK(");
			n = (Node)n.children.get(0);
			blockCounter ++;
			SymbolTable blockSt = st.insertST("Block"+blockCounter);
			addToString("\n\t[");
			SymbolTable st1 = check_M_decl(blockSt, (Node)n.children.get(0));
			addToString("]\n\t,");
			addToString(st1.num_vars + "\n\t,");
			addToString(st1.num_args + "\n\t,[");
			ArrayList<VarSymbol> l = SymbolTable.getArrays(st1);
			int i = 0;
			while (i < l.size()) {
				addToString("(");
				addToString(l.get(i).offset + ",");
				for (int j = 0; j < l.get(i).dimentions; j ++) {
					addToString("[");
					addToString(l.get(i).dimensionsIR[j]);
					addToString("]");
				}
				addToString(")");
				i ++;
			}
			addToString("]\n\t,");
			AM.println("\tLOAD_R %fp");
			AM.println("\tALLOC 2");
			AM.println("\tLOAD_R %sp");
			AM.println("\tSTORE_R %fp");
			AM.println("\tALLOC "+st1.num_vars);
			AM.println("\tLOAD_I "+(st1.num_vars+3));
			// todo alloc arrays
			allocArrays(st1);
			check_M_stmt(st1, (Node)n.children.get(1));
			AM.println("\tLOAD_R %fp");
			AM.println("\tLOAD_O "+(st1.num_vars+1));
			AM.println("\tAPP NEG");
			AM.println("\tALLOC_S");
			AM.println("\tSTORE_R %fp");
			addToString(")");
			return 0;
		}
		// Checking print
		if (n.name.equals("M_print")) {
			boolean savedState = pushToTmpS;
			if (!pushToTmpS) tmpS = "";
			pushToTmpS = true;
			tmpS += "IPRINT(";
			if (debug) System.out.println("Checking M_print");
			int type = check_M_expr(st, (Node)n.children.get(0));
			tmpS += ")";
			if (type == sym.INT) {
				tmpS = tmpS.replaceFirst("IPRINT", "IPRINT_I");
				AM.println("\tPRINT_I");
			}
			else if (type == sym.REAL) {
				tmpS = tmpS.replaceFirst("IPRINT", "IPRINT_F");
				AM.println("\tPRINT_F");
			}
			else {
				tmpS = tmpS.replaceFirst("IPRINT", "IPRINT_B");
				AM.println("\tPRINT_B");
			}
			pushToTmpS = savedState;
			if (!pushToTmpS) { addToString(tmpS); tmpS = "";}
			return 0;
		}
		// Checking function body (have to make sure the return type matches)
		if (n.name.equals("F_body")) {
			if (debug) System.out.println("Checking F_body");
			String id = st.scope_name;
			check_M_stmt(st, (Node)n.children.get(0));
			addToString("\n\t,"+ "IRETURN(");
			int returnType = check_M_expr(st, (Node)n.children.get(1));
			addToString(")");
			if (returnType != SymbolTable.getReturnType(st, id))  throw new SymbolException("Symbol Error: return type mismatch in function "+id);
			AM.println("\tLOAD_R %fp");
			AM.println("\tSTORE_O "+(-st.num_args-3));
			return 0;
		}
		// do nothing on episilon
		if (n.children.size() == 0) return 0;
		throw new SymbolException("Symbol Error: unable to check stmt node");
	}
	
	/**
	 * Insert a var defined in node to symbol table
	 * @param st SymbolTable
	 * @param n Node - must have name "M_var"
	 * @throws SymbolException
	 */
	private void insertVar(SymbolTable st, Node n) throws SymbolException {
		if (debug) System.out.println("Checking M_var");
		String id = n.children.get(0).toString();
		int type = check_M_type((Node)n.children.get(2));
		int dim = 0;
		n = (Node)n.children.get(1);
		ArrayList<String> l = new ArrayList<String>();
		ArrayList<Node> nodeL = new ArrayList<Node>();
		while (n.children.size() > 0) { 
			tmpS = "";
			if (!pushToTmpS) tmpS = "";
			pushToTmpS = true;
			//if (check_M_expr(st, (Node)n.children.get(0)) != sym.INT) throw new SymbolException("Symbol Error: array index must int");
			nodeL.add((Node)n.children.get(0));
			dim ++;
			n = (Node)n.children.get(1);
			pushToTmpS = false;
			l.add(tmpS);
			tmpS = "";
		}
		if (debug) System.out.println("Adding "+id+" to st["+st.scope_name+"]");
		if (debug) System.out.println(st);
		SymbolTable.insertSymbol(st, id, type, dim);
		VarSymbol v = (VarSymbol)SymbolTable.getSymbol(st, id);
		for (int i = 0; i < dim; i ++) v.dimensionsIR[i] = l.get(i);
		v.dimentionsNode = nodeL;
		if (debug) System.out.println(st);
	}
	
	/**
	 * Insert a fun symbol defined in node n
	 * @param st SymbolTable
	 * @param n Node - must have name "M_fun"
	 * @throws SymbolException
	 */
	private void insertFun(SymbolTable st, Node n) throws SymbolException {
		if (debug) System.out.println("Checking M_fun");
		String name = n.children.get(0).toString();
		int returnType = check_M_type((Node)n.children.get(2));
		if (st.symbols.contains(name)) throw new SymbolException("Symbol Error: "+name+" already declared");
		String fun_lable = "FUN"+SymbolTable.fun_lable_counter;
		FunSymbol s = new FunSymbol(name, fun_lable, returnType);
		SymbolTable.fun_lable_counter ++;		
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
			paramN = next;
		}
		SymbolTable.insertSymbol(st, s);

	}
	
	/**
	 * AM code to allocate arrays (this function should be called right before checking P_stmts
	 * @param st SymbolTable
	 * @throws SymbolException 
	 */
	private void allocArrays(SymbolTable st) throws SymbolException {
		ArrayList<VarSymbol> arrays = SymbolTable.getArrays(st);
		int i = 0;
		while (i < arrays.size()) {
			VarSymbol v = arrays.get(i);
			System.out.println(v.name);
			if (check_M_expr(st, v.dimentionsNode.get(0)) != sym.INT) throw new SymbolException("Symbol Error: array size must int");
			AM.println("\tLOAD_R %sp");
			AM.println("\tLOAD_R %fp");
			AM.println("\tSTORE_O "+v.offset);
			int dim = 1;
			while (dim < v.dimentionsNode.size()) {
				if (check_M_expr(st, v.dimentionsNode.get(dim)) != sym.INT) throw new SymbolException("Symbol Error: array size must int");
				dim ++;
			}
			for (dim = 0; dim < v.dimentions; dim ++) {
				AM.println("\tLOAD_R %fp");
				AM.println("\tLOAD_O "+v.offset);
				AM.println("\tLOAD_O "+dim);
			}
			dim = 1;
			while (dim < v.dimentions) {
				AM.println("\tAPP MUL");
				dim ++;
			}
			AM.println("\tLOAD_R %fp");
			AM.println("\tLOAD_O "+(st.num_vars+1));
			AM.println("\tLOAD_I "+v.dimentions);
			AM.println("\tLOAD_R %fp");
			AM.println("\tLOAD_O "+v.offset);
			AM.println("\tLOAD_O "+(v.dimentions+1));
			AM.println("\tAPP ADD");
			AM.println("\tAPP ADD");
			AM.println("\tLOAD_R %fp");
			AM.println("\tSTORE_O "+(st.num_vars+1));
			AM.println("\tALLOC_S");
			i ++;
		}
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
			Node declN = (Node)n.children.get(0);
			Node decls = (Node)n.children.get(1);
			declN = (Node)declN.children.get(0);
			// I have to insert all symbols defined in current block first
			// in order to make "mutually recursive functions" work
			if (declN.name == "M_var") {
				if (!st.completed) insertVar(st, declN);
			}
			else {
				if (!st.completed)insertFun(st, declN);
			}
			while (decls.children.size()> 0) {
				declN = (Node)decls.children.get(0);
				decls = (Node)decls.children.get(1);
				declN = (Node)declN.children.get(0);
				if (declN.name == "M_var") {
					if (!st.completed)insertVar(st, declN);
				}
				else {
					if (!st.completed)insertFun(st, declN);
				}
			}
			st.completed = true;
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
			SymbolTable st1 = check_M_decl(st, (Node)n.children.get(0));
			return st1;
			
		}
		// Function already inserted into symbol table st at this point, now go into the function block
		if (n.name.equals("M_fun")) {
			if (debug) System.out.println("Checking M_fun");
			addToString("IFUN \n\t(");
			String name = n.children.get(0).toString();
			int returnType = check_M_type((Node)n.children.get(2));
			FunSymbol s = (FunSymbol)SymbolTable.getSymbol(st, name);
			String fun_lable = s.lable;
			addToString("\""+fun_lable+"\"\n\t,");
			// Each function must have it's own seperate SymbolTable
			SymbolTable funSt = st.insertST(name);
			funSt.return_type = returnType;
			
			// Parsing function params
			Node paramN = (Node)n.children.get(1);
			paramN = (Node)paramN.children.get(0);
			// have to reverse the order of params
			ArrayList<Param> pList = new ArrayList<Param>();
			ArrayList<String> pNameList = new ArrayList<String>();
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
				pList.add(new Param(type, dims));
				pNameList.add(new String(id));
				paramN = next;
			}
			for (int i = pList.size()-1; i >= 0; i --) {
				SymbolTable.insertSymbol(funSt, pList.get(i), pNameList.get(i));
			}
			// go inside the function body
			AM.println(s.lable+":");
			check_F_block(funSt, (Node)n.children.get(3));
			addToString("\n\t)\n");
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
		addToString("[");
		st = check_M_decl(st, (Node)curr_node.children.get(0));
		addToString("]\n\t,");
		addToString(st.num_vars + "\n\t,");
		addToString(st.num_args + "\n\t,[");
		ArrayList<VarSymbol> l = SymbolTable.getArrays(st);
		int i = 0;
		while (i < l.size()) {
			addToString("(");
			addToString(l.get(i).offset + ",");
			for (int j = 0; j < l.get(i).dimentions; j ++) {
				addToString("[");
				addToString(l.get(i).dimensionsIR[j]);
				addToString("]");
			}
			addToString(")");
			i ++;
		}
		addToString("]\n\t,");
		AM.println("\tLOAD_R %sp");
		AM.println("\tSTORE_R %fp");
		AM.println("\tALLOC "+st.num_vars);
		AM.println("\tLOAD_I "+(st.num_vars+2)); // de-alloc, should be negative
		// now deal with arrays
		allocArrays(st);
					
		check_M_stmt(st, (Node)curr_node.children.get(1));
		
		AM.println("\tLOAD_R %fp");
		AM.println("\tLOAD_O 0");
		AM.println("\tLOAD_R %fp");
		AM.println("\tSTORE_O "+(-st.num_args-2));
		
		AM.println("\tLOAD_R %fp");
		AM.println("\tLOAD_O "+(st.num_vars+1));
		AM.println("\tAPP NEG");
		AM.println("\tALLOC_S");
		
		AM.println("\tSTORE_R %fp");
		
		AM.println("\tALLOC -"+st.num_args);
		AM.println("\tJUMP_S");		
		
		
	}

}
