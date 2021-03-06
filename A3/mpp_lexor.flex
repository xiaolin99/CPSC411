/* CPSC441 - Assignment 3 */
/* by Xiao Lin */
/* Reference: http://jflex.de/manual.html */
import java_cup.runtime.*;

%%

%class mpp_lexor
%standalone
%unicode
%line
%column
%cup

%{
  /* can define helper functions here */
  StringBuffer string = new StringBuffer();
  
  boolean success = true;
	  
  private Symbol symbol(String name, int type) {
    Symbol s = new Symbol(type, yyline, yycolumn);
    s.value = name;
    return s;
  }
  private Symbol symbol(String name, int type, Object value) {
    Symbol s = new Symbol(type, yyline, yycolumn, value);
    s.value = new String(name + " " + value.toString());
    return s;
  }
%}

%eof{
  System.out.println();

  if (!success) {System.out.println("Code contains errors. Cannot parse."); System.exit(1);}

%eof}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

/* comments */
Comment = {MultiLineComment} | {OneLineComment} 

MultiLineComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/" | "/*" [^"*/"]*
OneLineComment     = "%" {InputCharacter}* {LineTerminator}




ADD	= "+"
SUB	= "-"
MUL	= "*"
DIV	= "/"
ARROW	= "=>"
AND	= "&&"
OR	= "||"
NOT	= "not"
EQUAL	= "="
LT	= "<"
GT	= ">"
LE	= "=<"
GE	= ">="
ASSIGN	= ":="
LPAR	= "("
RPAR	= ")"
CLPAR	= "{"
CRPAR	= "}"
SLPAR	= "["
SRPAR	= "]"
SLASH	= "|"
COLON	= ":"
SEMICLON = ";"
COMMA	= ","
IF	= "if"
THEN	= "then"
WHILE	= "while"
DO	= "do"
READ	= "read"
ELSE	= "else"
BEGIN	= "begin"
END	= "end"
CASE	= "case"
OF	= "of" 
PRINT	= "print"
INT	= "int"
BOOL	= "bool"
CHAR	= "char"
REAL	= "real"
VAR	= "var"
DATA	= "data"
SIZE	= "size"
FLOAT	= "float"
FLOOR	= "floor"
CEIL	= "ceil"
FUN	= "fun"
RETURN	= "return"


alpha = [a-zA-Z]
digit = [0-9]
quote = [\"]
char = [^_A-Za-z0-9]

// (constructor)
CID = "#"[:jletterdigit:]*
// (identifier)
ID = {alpha}[:jletterdigit:]*
// (real/float)
RVAL = {digit}+"."{digit}+ 
// (integer)
IVAL = 0 | [1-9][0-9]*
// (booleans)
BVAL = "false"| "true"
//(character)
CVAL = {quote}{char}{quote} |  {quote}"\n" {quote} | {quote}"\t" {quote}


%state STRING

%%

<YYINITIAL> {
{ADD}				{ return symbol("M_add", sym.ADD); }
{SUB}				{ return symbol("M_sub", sym.SUB); }
{MUL}				{ return symbol("M_mul", sym.MUL); }
{DIV}				{ return symbol("M_div", sym.DIV); }
{ARROW}				{ return symbol("M_arrow", sym.ARROW); }
{AND}				{ return symbol("M_and", sym.AND); }
{OR}				{ return symbol("M_or", sym.OR); }
{NOT}				{ return symbol("M_not", sym.NOT); }
{EQUAL}				{ return symbol("M_equal", sym.EQUAL); }
{LT}				{ return symbol("M_lt", sym.LT); }
{GT}				{ return symbol("M_gt", sym.GT); }
{LE}				{ return symbol("M_le", sym.LE); }
{GE}				{ return symbol("M_ge", sym.GE); }
{ASSIGN}			{ return symbol("M_ass", sym.ASSIGN); }
{LPAR}				{ return symbol("(", sym.LPAR); }
{RPAR}				{ return symbol(")", sym.RPAR); }
{CLPAR}				{ return symbol("(", sym.CLPAR); }
{CRPAR}				{ return symbol(")", sym.CRPAR); }
{SLPAR}				{ return symbol("[", sym.SLPAR); }
{SRPAR}				{ return symbol("]", sym.SRPAR); }
{SLASH}				{ return symbol("|", sym.SLASH); }
{COLON}				{ return symbol(":", sym.COLON); }
{SEMICLON}			{ return symbol(";", sym.SEMICLON); }
{COMMA}				{ return symbol(",", sym.COMMA); }
{IF}				{ return symbol("M_if", sym.IF); }
{THEN}				{ return symbol("M_then", sym.THEN); }
{WHILE}				{ return symbol("M_while", sym.WHILE); }
{DO}				{ return symbol("M_do", sym.DO); }
{READ}				{ return symbol("M_read", sym.READ); }
{ELSE}				{ return symbol("M_else", sym.ELSE); }
{BEGIN}				{ return symbol("M_begin", sym.BEGIN); }
{END}				{ return symbol("M_end", sym.END); }
{CASE}				{ return symbol("M_case", sym.CASE); }
{OF}				{ return symbol("M_of", sym.OF); }
{PRINT}				{ return symbol("M_print", sym.PRINT); }
{INT}				{ return symbol("M_int", sym.INT); }
{BOOL}				{ return symbol("M_bool", sym.BOOL); }
{CHAR}				{ return symbol("M_char", sym.CHAR); }
{REAL}				{ return symbol("M_real", sym.REAL); }
{VAR}				{ return symbol("M_var", sym.VAR); }
{DATA}				{ return symbol("M_data", sym.DATA); }
{SIZE}				{ return symbol("M_size", sym.SIZE); }
{FLOAT}				{ return symbol("M_float", sym.FLOAT); }
{FLOOR}				{ return symbol("M_floor", sym.FLOOR); }
{CEIL}				{ return symbol("M_ceil", sym.CEIL); }
{FUN}				{ return symbol("M_fun", sym.FUN); }
{RETURN}			{ return symbol("M_return", sym.RETURN); }

// (booleans)
{BVAL}				{ return symbol("M_bval", sym.BVAL, new String(yytext())); }
// (constructor)
{CID}				{ return symbol("M_cid", sym.CID, new String(yytext())); }
// (identifier)
{ID}				{ return symbol("", sym.ID, new String("\""+yytext()+"\"")); }
// (real/float)
{RVAL}				{ return symbol("M_rval", sym.RVAL, new Double(yytext())); }
// (integer)
{IVAL}				{ return symbol("M_ival", sym.IVAL, new Integer(yytext())); }
//(character)
{CVAL}				{ return symbol("M_cval", sym.CVAL, new String(yytext())); }

 /* comments */
{Comment}                      { /* ignore */ }

/* whitespace */
{WhiteSpace}                   { /* ignore */ }
}


/* error fallback */
[^]                              { System.out.println("Illegal character <"+
                                                    yytext()+"> at line "+yyline); success = false;}

