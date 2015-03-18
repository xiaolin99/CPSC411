/* CPSC441 - Assignment 3 */
/* by Xiao Lin */
/* Reference: http://jflex.de/manual.html */
import java_cup.runtime.Symbol;

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

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}

%eof{
  System.out.println();
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
{ADD}				{ return symbol(sym.ADD); }
{SUB}				{ return symbol(sym.SUB); }
{MUL}				{ return symbol(sym.MUL); }
{DIV}				{ return symbol(sym.DIV); }
{ARROW}				{ return symbol(sym.ARROW); }
{AND}				{ return symbol(sym.AND); }
{OR}				{ return symbol(sym.OR); }
{NOT}				{ return symbol(sym.NOT); }
{EQUAL}				{ return symbol(sym.EQUAL); }
{LT}				{ return symbol(sym.LT); }
{GT}				{ return symbol(sym.GT); }
{LE}				{ return symbol(sym.LE); }
{GE}				{ return symbol(sym.GE); }
{ASSIGN}			{ return symbol(sym.ASSIGN); }
{LPAR}				{ return symbol(sym.LPAR); }
{RPAR}				{ return symbol(sym.RPAR); }
{CLPAR}				{ return symbol(sym.CLPAR); }
{CRPAR}				{ return symbol(sym.CRPAR); }
{SLPAR}				{ return symbol(sym.SLPAR); }
{SRPAR}				{ return symbol(sym.SRPAR); }
{SLASH}				{ return symbol(sym.SLASH); }
{COLON}				{ return symbol(sym.COLON); }
{SEMICLON}			{ return symbol(sym.SEMICLON); }
{COMMA}				{ return symbol(sym.COMMA); }
{IF}				{ return symbol(sym.IF); }
{THEN}				{ return symbol(sym.THEN); }
{WHILE}				{ return symbol(sym.WHILE); }
{DO}				{ return symbol(sym.DO); }
{READ}				{ return symbol(sym.READ); }
{ELSE}				{ return symbol(sym.ELSE); }
{BEGIN}				{ return symbol(sym.BEGIN); }
{END}				{ return symbol(sym.END); }
{CASE}				{ return symbol(sym.CASE); }
{OF}				{ return symbol(sym.OF); }
{PRINT}				{ return symbol(sym.PRINT); }
{INT}				{ return symbol(sym.INT); }
{BOOL}				{ return symbol(sym.BOOL); }
{CHAR}				{ return symbol(sym.CHAR); }
{REAL}				{ return symbol(sym.REAL); }
{VAR}				{ return symbol(sym.VAR); }
{DATA}				{ return symbol(sym.DATA); }
{SIZE}				{ return symbol(sym.SIZE); }
{FLOAT}				{ return symbol(sym.FLOAT); }
{FLOOR}				{ return symbol(sym.FLOOR); }
{CEIL}				{ return symbol(sym.CEIL); }
{FUN}				{ return symbol(sym.FUN); }
{RETURN}			{ return symbol(sym.RETURN); }

// (booleans)
{BVAL}				{ return symbol(sym.BVAL, new String(yytext())); }
// (constructor)
{CID}				{ return symbol(sym.CID, new String(yytext())); }
// (identifier)
{ID}				{ return symbol(sym.ID, new String(yytext())); }
// (real/float)
{RVAL}				{ return symbol(sym.RVAL, new Double(yytext())); }
// (integer)
{IVAL}				{ return symbol(sym.IVAL, new Integer(yytext())); }
//(character)
{CVAL}				{ return symbol(sym.CVAL, new String(yytext())); }

 /* comments */
{Comment}                      { /* ignore */ }

/* whitespace */
{WhiteSpace}                   { /* ignore */ }
}


/* error fallback */
[^]                              { System.out.println("Illegal character <"+
                                                    yytext()+">"); }
