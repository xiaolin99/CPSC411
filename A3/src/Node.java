import java.util.ArrayList;

import java_cup.runtime.*;

/**
 * This class represent an AST Node, it will be generated when parser parses some code
 * @author xlin
 *
 */
public class Node{
	  /**
	   * Children Nodes
	   */
	  public ArrayList<Object> children = new ArrayList<Object>();
	  /**
	   * Just an ID for the current Node
	   */
	  public String name = "";
	  
	  public String s = "";
	  
	  public Object parent = null;
	  
	  /**
	   * Constructors - can add up to 8 children
	   */
	  public Node() {
	  }
	  public Node(Object s1) {
		  children.add(s1);
	  }
	  public Node(Object s1, Object s2) {
		  children.add(s1);
		  children.add(s2);
	  }
	  public Node(Object s1, Object s2, Object s3) {
		  children.add(s1);
		  children.add(s2);
		  children.add(s3);
	  }
	  public Node(Object s1, Object s2, Object s3, Object s4) {
		  children.add(s1);
		  children.add(s2);
		  children.add(s3);
		  children.add(s4);

	  }
	  public Node(Object s1, Object s2, Object s3, Object s4, 
			  Object s5) {
		  children.add(s1);
		  children.add(s2);
		  children.add(s3);
		  children.add(s4);
		  children.add(s5);
	  }
	  public Node(Object s1, Object s2, Object s3, Object s4, 
			  Object s5, Object s6) {
		  children.add(s1);
		  children.add(s2);
		  children.add(s3);
		  children.add(s4);
		  children.add(s5);
		  children.add(s6);
	  }
	  public Node(Object s1, Object s2, Object s3, Object s4, 
			  Object s5, Object s6, Object s7) {
		  children.add(s1);
		  children.add(s2);
		  children.add(s3);
		  children.add(s4);
		  children.add(s5);
		  children.add(s6);
		  children.add(s7);
	  }
	  public Node(Object s1, Object s2, Object s3, Object s4, 
			  Object s5, Object s6, Object s7, Object s8) {
		  children.add(s1);
		  children.add(s2);
		  children.add(s3);
		  children.add(s4);
		  children.add(s5);
		  children.add(s6);
		  children.add(s7);
		  children.add(s8);
	  }
	  
	  /**
	   * Helper function to get the string representation of ith child
	   * @param i
	   * @return
	   */
	  private String childToString(int i) {
		  String tmp = "";
		  if (i >= children.size()) return tmp;
		  if (children.get(i) instanceof String) tmp += (String) children.get(i);
		  else {
			  Node v = (Node) children.get(i);
			  tmp += v.toString();
		  }
		  if (tmp.equals(";")) tmp = ", ";
		  return tmp;
	  }
	  
	  /**
	   * Print current node plus all of its childrent (thus print the AST)
	   */
	  @Override
	  public String toString() {
		  if (children.size() < 1) return s;
		  if (name.equals("M_prog")) {
			  s += "M_prog\n  (";
			  s += childToString(0);
			  s += ")\n";
		  }
		  if (name.equals("M_block")) {
			  s += "[";
			  s += childToString(0);
			  s += "]\n";
			  s += ",[";
			  s += childToString(1);
			  s += "]\n";
		  }
		  else if (name.equals("M_var")){
			  s += childToString(0);
			  s += " ("+childToString(1);
			  s += ",";
			  s += childToString(3);
			  s += ")";
		  }
		  else if (name.equals("M_fun")){
			  s += childToString(0);
			  s += " (" + childToString(1) + ",[";
			  s += childToString(2) + "],";
			  // s += childToString(3);
			  s += childToString(4) + ",";
			  s += childToString(5);
			  s += childToString(6);
			  s += childToString(7);
			  s += ")\n";
		  }
		  else if (name.equals("M_data")){
			  s += childToString(0);
			  s += " (" + childToString(1);
			  s += childToString(2);
			  s += ", ["+childToString(3)+"]";
			  s += ")\n";
		  }
		  else if (name.equals("M_ass")){
			  s += childToString(1);
			  s += " ("+childToString(0)+",";
			  s += childToString(2)+")";
		  }
		  else if (name.equals("M_while")){
			  s += childToString(0);
			  s += " ("+childToString(1)+",";
			  //s += childToString(2);
			  s += childToString(3)+")";
		  }
		  else if (name.equals("M_cond")){
			  s += "M_cond (";
			  //s += childToString(0);
			  s += childToString(1)+",";
			  //s += childToString(2);
			  s += childToString(3)+",";
			  //s += childToString(4);
			  s += childToString(5);
			  s += ")";
		  }
		  else if (name.equals("M_read")){
			  s += childToString(0);
			  s += " ("+childToString(1)+")";
		  }
		  else if (name.equals("M_case")){
			  s += childToString(0)+" (";
			  s += childToString(1)+",";
			  // s += childToString(2);
			  //s += childToString(3);
			  s += "["+childToString(4)+"]";
			  //s += childToString(5);
			  s += ")";
		  }
		  else if (name.equals("M_app")){
			  s += "M_app (";
			  s += childToString(1) + ",";
			  s += childToString(0) + ",";
			  s += childToString(2) + ")";
		  }
		  else if (name.equals("M_expr1")){
			  s += "[";
			  s += childToString(0);
			  s += childToString(1);
			  s += childToString(2);
			  s += "]";
		  }
		  else if (name.equals("M_expr2")){
			  s += "[";
			  s += childToString(0);
			  s += "]";
		  }
		  else {
			  for (int i = 0; i < children.size(); i ++) {
				  if (children.get(i) == null) continue;
				  //if (!s.startsWith(" ")) s += " ";
				  s += childToString(i);
			  }
		  }
		  return s;
	  }
  }