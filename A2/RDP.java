import java.util.ArrayList;
import java.io.*;

/* Grammar (after removing left recursion and expanding left-most non-terminals)
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
(I don't want to expand the leading stmt here because it would complicate the program)
expr -> term exprP
exprP -> ADD term exprP
	   | SUB term exprP
       | .
term -> factor termP
termP -> MUL factor termP
	   | DIV factor termP
       | .
factor -> LPAR expr RPAR
        | ID
        | NUM
        | SUB NUM.

*/


/**
 * CPSC411 Assignment 2 - Recursive Descent Parser for minisculus
 * This class will take a m- program, tokenize it, print the AST (in one line)
 * then create a .sc stack code file
 * @author Xiao Lin
 *
 */
public class RDP {
	private int index;
	private ArrayList<String> tokenList;
	private String prettyAST;
	private int labelCounter;
	private PrintWriter stackCode;
	private String stackCodeFileName;
	
	/**
	 * Constructor
	 * @param tl ArrayList<String> - Token List
	 * @param filename String - name of the stack code file
	 */
	public RDP(ArrayList<String> tl, String filename) {
		index = 0;
		tokenList = tl;
		prettyAST = new String();
		labelCounter = 1;
		stackCodeFileName = filename;
		try {
			stackCode = new PrintWriter(stackCodeFileName);
		} catch (IOException e) {
			System.out.println("ERROR: unable to create stack machine file");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Print the AST
	 */
	public void printAST() {
		System.out.println(prettyAST);
	}
	
	// helper funcitons
	
	/**
	 * Helper function to clean up and then exit
	 */
	public void exitWithError() {
		stackCode.flush();
		stackCode.close();
		File f = new File(stackCodeFileName);
		f.delete();
		System.exit(1);
	}
	
	/**
	 * expect String s to be the next token. Exit with errors if not found
	 * @param s
	 */
	public void expect(String s) {
		if (!s.equals(tokenList.get(index))) {
			System.out.printf("Error: expected %s, got %s%n", s, tokenList.get(index));
			exitWithError();
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
			System.out.printf("Error: expected a number, got %s%n", tokenList.get(index));
			exitWithError();
		}
		
	}
	
	/**
	 * expect next token to be an ID. Exit with error if failed
	 */
	public void expectID() {
		if(!isID(tokenList.get(index))){
			System.out.printf("Error: expected ID, got %s%n", tokenList.get(index));
			exitWithError();
		}
	}
	
	/**
	 * start
	 */
	public void start() {
		prog();
		stackCode.flush();
		stackCode.close();
	}
	
	/**
	 * prog -> stmt
	 */
	public void prog() {
//		System.out.println("Parsing: prog" + ": "+ tokenList.get(index) + "...");
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
		prettyAST += " Block [";
//		System.out.println("Parsing: stmt" + ": "+ tokenList.get(index) + "...");
		if (tokenList.get(index).equals("IF")) {
			String L1 = "L"+labelCounter;
			labelCounter ++;
			String L2 = "L"+labelCounter;
			labelCounter ++;
			prettyAST += " If ";
			index ++;
			expr();
			// note to self: expr must push result to stack
			stackCode.println("  cJUMP "+L1);
			expect("THEN");
			index ++;
			stmt();
			stackCode.println("  JUMP "+L2);
			expect("ELSE");
			index ++;
			stackCode.println(L1+":");
			stmt();
			stackCode.println(L2+":");
		}
		else if (tokenList.get(index).equals("WHILE")) {
			String L1 = "L"+labelCounter;
			labelCounter ++;
			String L2 = "L"+labelCounter;
			labelCounter ++;
			prettyAST += " while ";
			index ++;
			stackCode.println(L1+":");
			expr();
			stackCode.println("  cJUMP "+L2);
			expect("DO");
			index ++;
			stmt();
			stackCode.println("  JUMP "+L1);
			stackCode.println(L2+":");
		}
		else if (tokenList.get(index).equals("INPUT")) {
			prettyAST += " Input ";
			index ++;
			expectID();
			prettyAST += tokenList.get(index);
			stackCode.println("  READ "+ tokenList.get(index).substring(3));
			index ++;
			
		}
		else if (isID(tokenList.get(index))) {
			prettyAST += " Assign " + tokenList.get(index) + " ";
			String id = tokenList.get(index).substring(3);
			index ++;
			expect("ASSIGN");
			index ++;
			expr();
			stackCode.println("  LOAD "+ id);
		}
		else if (tokenList.get(index).equals("WRITE")) {
			prettyAST += " Print ";
			index ++;
			expr();
			stackCode.println("  PRINT");
		}
		else if (tokenList.get(index).equals("BEGIN")) {
			index ++;
			stmtlist();
			expect("END");
			index ++;
		}
		else {
			System.out.println("ERROR: Expected stmt, but got "+tokenList.get(index));
			exitWithError();
		}
		prettyAST += "]";
	}
	
	/**
	 * stmtlist -> stmtlistP
	 */
	public void stmtlist(){
//		System.out.println("Parsing: stmtlist" + ": "+ tokenList.get(index) + "...");
		stmtlistP();
	}
	
	/**
	 * stmtlistP -> stmt SEMICOLON stmtlistP
           |.
		(I don't want to expend the leading stmt here because it would complicate the program)
	 */
	public void stmtlistP() {
//		System.out.println("Parsing: stmtlistP" + ": "+ tokenList.get(index) + "...");
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
//		System.out.println("Parsing: expr" + ": "+ tokenList.get(index) + "...");
		prettyAST += "(";
		term();
		exprP();
		prettyAST += ")";
	}
	
	/**
	 * exprP -> ADD term exprP
	   | SUB term exprP
       | .
	 */
	public void exprP() {
//		System.out.println("Parsing: exprP" + ": "+ tokenList.get(index) + "...");
		if (tokenList.get(index).equals("ADD")) {
			prettyAST = new StringBuilder(prettyAST).insert(prettyAST.lastIndexOf("("), "ADD ").toString();
			index ++;
			term();
			stackCode.println("  OP2 +");
			exprP();
		}
		else if	(tokenList.get(index).equals("SUB")) {
			prettyAST = new StringBuilder(prettyAST).insert(prettyAST.lastIndexOf("("), "SUB ").toString();
			index ++;
			term();
			stackCode.println("  OP2 -");
			exprP();
		}
		else {
			// .
		}
	}
	
	
	/**
	 * term -> factor termP
	 */
	public void term() {
//		System.out.println("Parsing: term" + ": "+ tokenList.get(index) + "...");
		prettyAST += "(";
		factor();
		termP();
		prettyAST += ")";
	}

	/**
	 * termP -> MUL factor termP
	   | DIV factor termP
       | .
	 */
	public void termP() {
//		System.out.println("Parsing: termP" + ": "+ tokenList.get(index) + "...");
		if (tokenList.get(index).equals("MUL")) {
			prettyAST = new StringBuilder(prettyAST).insert(prettyAST.lastIndexOf("("), "MUL ").toString();
			index ++;
			factor();
			stackCode.println("  OP2 *");
			termP();
		}
		else if	(tokenList.get(index).equals("DIV")) {
			prettyAST = new StringBuilder(prettyAST).insert(prettyAST.lastIndexOf("("), "DIV ").toString();	
			index ++;
			factor();
			stackCode.println("  OP2 /");
			termP();
		}
		else {
			// .
		}
	}
	
	/**
	 * factor -> LPAR expr RPAR
        | ID
        | NUM
        | SUB NUM.
	 */
	public void factor() {
//		System.out.println("Parsing: factor" + ": "+ tokenList.get(index) + "...");
		if (tokenList.get(index).equals("LPAR")) {
			prettyAST += "(";
			index ++;
			expr();
			expect("RPAR");
			index ++;
			prettyAST += ")";
		}
		else if (isID(tokenList.get(index))) {
			prettyAST += " " + tokenList.get(index) + " ";
			stackCode.println("  rPUSH "+tokenList.get(index).substring(3));
			index ++;
		}
		else if (isNUM(tokenList.get(index))) {
			prettyAST += " " + tokenList.get(index) + " ";
			stackCode.println("  cPUSH "+tokenList.get(index));
			index ++;
		}
		else if (tokenList.get(index).equals("SUB")) {
			index ++;
			expectNUM();
			prettyAST += " -" + tokenList.get(index) + " ";
			stackCode.println("  cPUSH -"+tokenList.get(index));
			index ++;
		}
		else {
			System.out.println("ERROR: Expected factor, but got "+tokenList.get(index));
			exitWithError();
		}
	}
	
	
	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage : java RDP <inputfile>");
	    }
	    else {
	    	System.out.println("Token List:");
	    	ArrayList<String> tokenList = mm_lexor.getTokenList(args[0]);
	    	if (tokenList.size() < 1) {
	    		System.out.println("ERROR: Token list empty! Nothin to parse.");
	    		System.exit(1);
	    	}
	    	RDP r = new RDP(tokenList, args[0]+".sc");
	    	r.start();
	    	System.out.println("Parsing Success!!!");
	    	System.out.println("AST:");
	    	r.printAST();
	    	System.out.println("Stack Code:");
	    	System.out.println(args[0]+".sc");
	    }

	}

}
