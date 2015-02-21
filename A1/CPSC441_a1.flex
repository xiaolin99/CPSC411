/* CPSC441 - Assignment 1 */
/* by Xiao Lin */
/* Reference: http://jflex.de/manual.html */

%%

%class mm_lexor
%standalone
%unicode
%line
%column

%{
  /* can define helper functions here */
  private int sym_if = 0;
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


IF = "if"
THEN = "then"
WHILE = "while"
DO = "do"
INPUT = "input"
ELSE = "else"
BEGIN = "begin"
END = "end"
WRITE = "write"
ID = [:jletter:] [:jletterdigit:]*
NUM = 0 | [1-9][0-9]*
ADD = "+"
ASSIGN = ":="
SUB = "-"
MUL = "*"
DIV = "/"
LPAR = "("
RPAR = ")"
SEMICOLON = ";"

%state STRING

%%


<YYINITIAL> {
  {IF}                   { System.out.print("IF "); return 0; }
  {THEN}                 { System.out.print("THEN "); return 0; }
  {WHILE}                { System.out.print("WHILE "); return 0; }
  {DO}                   { System.out.print("DO "); return 0; }
  {INPUT}                { System.out.print("INPUT "); return 0; }
  {ELSE}                 { System.out.print("ELSE "); return 0; }
  {BEGIN}                { System.out.print("BEGIN "); return 0; }
  {END}                  { System.out.print("END "); return 0; }
  {WRITE}                { System.out.print("WRITE "); return 0; }
  {ID}                   { System.out.print("ID(\""+yytext()+"\")  "); return 0; }
  {NUM}                  { System.out.print("NUM(\""+yytext()+"\") "); return 0; }
  {ADD}                  { System.out.print("ADD "); return 0; }
  {ASSIGN}               { System.out.print("ASSIGN "); return 0; }
  {SUB}                  { System.out.print("SUB "); return 0; }
  {MUL}                  { System.out.print("MUL "); return 0; }
  {DIV}                  { System.out.print("DIV "); return 0; }
  {LPAR}                 { System.out.print("LPAR "); return 0; }
  {RPAR}                 { System.out.print("RPAR "); return 0; }
  {SEMICOLON}            { System.out.print("SEMICOLON "); return 0; } 

  /* comments */
  {Comment}                      { /* ignore */ }
 
  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
}

/* error fallback */
[^]                              { System.out.println("Illegal character <"+
                                                    yytext()+">"); }

