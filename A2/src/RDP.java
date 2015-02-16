import java.util.ArrayList;

/* Grammar (after removing left recursion)
prog -> stmt.
stmt -> IF expr THEN stmt ELSE stmt
      | WHILE expr DO stmt
      | INPUT ID
      | ID ASSIGN expr
      | WRITE expr
      | BEGIN stmtlist END.
stmtlist -> stmtlistP
stmtlistP -> stmt SEMICOLON stmtlistP
           |.
expr -> term exprP
exprP -> addop term exprP
       | .
addop -> ADD
       | SUB.
term -> factor termP
termP -> mulop factor termP
       | .
mulop -> MUL
       | DIV.
factor -> LPAR expr RPAR
        | ID
        | NUM
        | SUB NUM.

*/


/**
 * CPSC411 Assignment 2 - Recursive Descent Parser for minisculus
 * @author Xiao Lin
 *
 */
public class RDP {
	private int index;
	private ArrayList<String> tokenList;
	
	public RDP(ArrayList<String> tl) {
		index = 0;
		tokenList = tl;
	}
	
	// helper funcitons
	
	/**
	 * expect String s to be the next token. Exit with errors if not found
	 * @param s
	 */
	public void expect(String s) {
		if (!s.equals(tokenList.get(index))) {
			System.out.printf("Error: expected %s, got %s", s, tokenList.get(index));
			System.exit(1);
		}
	}
	
	/**
	 * check if String s is a number
	 * @param s
	 * @return
	 */
	public boolean isNUM(String s) {
		try {
			double d = Double.parseDouble(s);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * check if String s is ID
	 * @param s
	 * @return
	 */
	public boolean isID(String s) {
		if (s.startsWith("ID")) return true;
		return false;
	}
	
	/**
	 * expect next token to be a number. Exit with error if failed
	 */
	public void expectNUM() {
		if(!isNUM(tokenList.get(index))) {
			System.out.printf("Error: expected a number, got %s", tokenList.get(index));
			System.exit(1);
		}
		
	}
	
	/**
	 * expect next token to be an ID. Exit with error if failed
	 */
	public void expectID() {
		if(!isID(tokenList.get(index))){
			System.out.printf("Error: expected ID, got %s", tokenList.get(index));
			System.exit(1);
		}
	}
	
	/**
	 * start
	 */
	public void start() {
		prog();
	}
	
	/**
	 * prog -> stmt
	 */
	public void prog() {
		System.out.println("prog" + ": "+ tokenList.get(index) + "...");
		stmt();
	}
	
	/**
	 * stmt -> IF expr THEN stmt ELSE stmt
      | WHILE expr DO stmt
      | INPUT ID
      | ID ASSIGN expr
      | WRITE expr
      | BEGIN stmtlist END.
	 */
	public void stmt() {
		System.out.println("stmt" + ": "+ tokenList.get(index) + "...");
		if (tokenList.get(index).equals("IF")) {
			index ++;
			expr();
			expect("THEN");
			index ++;
			stmt();
			expect("ELSE");
			index ++;
			stmt();
		}
		else if (tokenList.get(index).equals("WHILE")) {
			index ++;
			expr();
			expect("DO");
			index ++;
			stmt();
		}
		else if (tokenList.get(index).equals("INPUT")) {
			index ++;
			expectID();
			index ++;
		}
		else if (isID(tokenList.get(index))) {
			index ++;
			expect("ASSIGN");
			index ++;
			expr();
		}
		else if (tokenList.get(index).equals("WRITE")) {
			index ++;
			expr();
		}
		else if (tokenList.get(index).equals("BEGIN")) {
			index ++;
			stmtlist();
			expect("END");
			index ++;
		}
		else {
			System.out.println("ERROR!");
			System.exit(1);
		}
	}
	
	/**
	 * stmtlist -> stmtlistP
	 */
	public void stmtlist(){
		System.out.println("stmtlist" + ": "+ tokenList.get(index) + "...");
		stmtlistP();
	}
	
	/**
	 * stmtlistP -> stmt SEMICOLON stmtlistP
           |.
	 */
	public void stmtlistP() {
		System.out.println("stmtlistP" + ": "+ tokenList.get(index) + "...");
		if (tokenList.get(index).equals("IF") || 
				tokenList.get(index).equals("WHILE") ||
				tokenList.get(index).equals("INPUT") ||
				isID(tokenList.get(index)) ||
				tokenList.get(index).equals("WRITE") ||
				tokenList.get(index).equals("BEGIN")) {
			stmt();
			expect("SEMICOLON");
			index ++;
			stmtlistP();
		}
		else {
			// .
		}
	}
	
	/**
	 * expr -> term exprP
	 */
	public void expr() {
		System.out.println("expr" + ": "+ tokenList.get(index) + "...");
		term();
		exprP();
	}
	
	/**
	 * exprP -> addop term exprP
       | .
	 */
	public void exprP() {
		System.out.println("exprP" + ": "+ tokenList.get(index) + "...");
		if (tokenList.get(index).equals("ADD") || 
				tokenList.get(index).equals("SUB")) {
			addop();
			term();
			exprP();
		}
	}
	
	/**
	 * addop -> ADD
       | SUB.
	 */
	public void addop() {
		System.out.println("addop" + ": "+ tokenList.get(index) + "...");
		if (tokenList.get(index).equals("ADD") || 
				tokenList.get(index).equals("SUB"))
			index ++;
		else {
			expect("ADD or SUB");
		}
	}
	
	/**
	 * term -> factor termP
	 */
	public void term() {
		System.out.println("term" + ": "+ tokenList.get(index) + "...");
		factor();
		termP();
	}

	/**
	 * termP -> mulop factor termP
       | .
	 */
	public void termP() {
		System.out.println("termP" + ": "+ tokenList.get(index) + "...");
		if (tokenList.get(index).equals("MUL") || 
				tokenList.get(index).equals("DIV")) {
			mulop();
			factor();
			termP();
		}
		else {
			// .
		}
	}
	
	/**
	 * mulop -> MUL
       | DIV.
	 */
	public void mulop() {
		System.out.println("mulop" + ": "+ tokenList.get(index) + "...");
		if (tokenList.get(index).equals("MUL") || 
				tokenList.get(index).equals("DIV"))
			index ++;
		else {
			expect("MUL or DIV");
		}
	}
	
	/**
	 * factor -> LPAR expr RPAR
        | ID
        | NUM
        | SUB NUM.
	 */
	public void factor() {
		System.out.println("factor" + ": "+ tokenList.get(index) + "...");
		if (tokenList.get(index).equals("LPAR")) {
			index ++;
			expr();
			expect("RPAR");
			index ++;
		}
		else if (isID(tokenList.get(index))) {
			index ++;
		}
		else if (isNUM(tokenList.get(index))) {
			index ++;
		}
		else if (tokenList.get(index).equals("SUB")) {
			index ++;
			expectNUM();
			index ++;
		}
		else {
			System.out.println("ERROR!");
			System.exit(1);
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage : java RDP <inputfile>");
	    }
	    else {
	    	ArrayList<String> tokenList = mm_lexor.getTokenList(args[0]);
	    	RDP r = new RDP(tokenList);
	    	r.start();
	    	System.out.println("Success");
	    }

	}

}
