/* The following code was generated by JFlex 1.4.3 on 21/03/15 5:07 PM */

/* CPSC441 - Assignment 3 */
/* by Xiao Lin */
/* Reference: http://jflex.de/manual.html */
import java_cup.runtime.*;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 21/03/15 5:07 PM from the specification file
 * <tt>mpp_lexor.flex</tt>
 */
class mpp_lexor implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int STRING = 2;
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\60\1\3\1\2\1\0\1\3\1\1\16\60\4\0\1\3\1\0"+
    "\1\55\1\57\1\60\1\6\1\13\1\0\1\22\1\23\1\5\1\7"+
    "\1\31\1\10\1\61\1\4\1\62\11\54\1\21\1\30\1\20\1\11"+
    "\1\12\2\0\32\53\1\26\1\0\1\27\1\0\1\56\1\0\1\42"+
    "\1\44\1\46\1\40\1\35\1\33\1\45\1\34\1\32\2\53\1\37"+
    "\1\53\1\15\1\16\1\47\1\53\1\41\1\43\1\17\1\52\1\50"+
    "\1\36\2\53\1\51\1\24\1\14\1\25\1\0\41\60\2\0\4\60"+
    "\4\0\1\60\2\0\1\60\7\0\1\60\4\0\1\60\5\0\27\60"+
    "\1\0\37\60\1\0\u01ca\60\4\0\14\60\16\0\5\60\7\0\1\60"+
    "\1\0\1\60\21\0\165\60\1\0\2\60\2\0\4\60\10\0\1\60"+
    "\1\0\3\60\1\0\1\60\1\0\24\60\1\0\123\60\1\0\213\60"+
    "\1\0\5\60\2\0\236\60\11\0\46\60\2\0\1\60\7\0\47\60"+
    "\11\0\55\60\1\0\1\60\1\0\2\60\1\0\2\60\1\0\1\60"+
    "\10\0\33\60\5\0\3\60\15\0\4\60\7\0\1\60\4\0\13\60"+
    "\5\0\112\60\4\0\146\60\1\0\11\60\1\0\12\60\1\0\23\60"+
    "\2\0\1\60\17\0\74\60\2\0\145\60\16\0\66\60\4\0\1\60"+
    "\5\0\56\60\22\0\34\60\244\0\144\60\2\0\12\60\1\0\7\60"+
    "\1\0\7\60\1\0\3\60\1\0\10\60\2\0\2\60\2\0\26\60"+
    "\1\0\7\60\1\0\1\60\3\0\4\60\2\0\11\60\2\0\2\60"+
    "\2\0\4\60\10\0\1\60\4\0\2\60\1\0\5\60\2\0\16\60"+
    "\7\0\1\60\5\0\3\60\1\0\6\60\4\0\2\60\2\0\26\60"+
    "\1\0\7\60\1\0\2\60\1\0\2\60\1\0\2\60\2\0\1\60"+
    "\1\0\5\60\4\0\2\60\2\0\3\60\3\0\1\60\7\0\4\60"+
    "\1\0\1\60\7\0\20\60\13\0\3\60\1\0\11\60\1\0\3\60"+
    "\1\0\26\60\1\0\7\60\1\0\2\60\1\0\5\60\2\0\12\60"+
    "\1\0\3\60\1\0\3\60\2\0\1\60\17\0\4\60\2\0\12\60"+
    "\1\0\1\60\17\0\3\60\1\0\10\60\2\0\2\60\2\0\26\60"+
    "\1\0\7\60\1\0\2\60\1\0\5\60\2\0\11\60\2\0\2\60"+
    "\2\0\3\60\10\0\2\60\4\0\2\60\1\0\5\60\2\0\12\60"+
    "\1\0\1\60\20\0\2\60\1\0\6\60\3\0\3\60\1\0\4\60"+
    "\3\0\2\60\1\0\1\60\1\0\2\60\3\0\2\60\3\0\3\60"+
    "\3\0\14\60\4\0\5\60\3\0\3\60\1\0\4\60\2\0\1\60"+
    "\6\0\1\60\16\0\12\60\11\0\1\60\7\0\3\60\1\0\10\60"+
    "\1\0\3\60\1\0\27\60\1\0\12\60\1\0\5\60\3\0\10\60"+
    "\1\0\3\60\1\0\4\60\7\0\2\60\1\0\2\60\6\0\4\60"+
    "\2\0\12\60\22\0\2\60\1\0\10\60\1\0\3\60\1\0\27\60"+
    "\1\0\12\60\1\0\5\60\2\0\11\60\1\0\3\60\1\0\4\60"+
    "\7\0\2\60\7\0\1\60\1\0\4\60\2\0\12\60\1\0\2\60"+
    "\17\0\2\60\1\0\10\60\1\0\3\60\1\0\51\60\2\0\10\60"+
    "\1\0\3\60\1\0\5\60\10\0\1\60\10\0\4\60\2\0\12\60"+
    "\12\0\6\60\2\0\2\60\1\0\22\60\3\0\30\60\1\0\11\60"+
    "\1\0\1\60\2\0\7\60\3\0\1\60\4\0\6\60\1\0\1\60"+
    "\1\0\10\60\22\0\2\60\15\0\72\60\4\0\20\60\1\0\12\60"+
    "\47\0\2\60\1\0\1\60\2\0\2\60\1\0\1\60\2\0\1\60"+
    "\6\0\4\60\1\0\7\60\1\0\3\60\1\0\1\60\1\0\1\60"+
    "\2\0\2\60\1\0\15\60\1\0\3\60\2\0\5\60\1\0\1\60"+
    "\1\0\6\60\2\0\12\60\2\0\2\60\42\0\1\60\27\0\2\60"+
    "\6\0\12\60\13\0\1\60\1\0\1\60\1\0\1\60\4\0\12\60"+
    "\1\0\44\60\4\0\24\60\1\0\22\60\1\0\44\60\11\0\1\60"+
    "\71\0\112\60\6\0\116\60\2\0\46\60\12\0\53\60\1\0\1\60"+
    "\3\0\u0149\60\1\0\4\60\2\0\7\60\1\0\1\60\1\0\4\60"+
    "\2\0\51\60\1\0\4\60\2\0\41\60\1\0\4\60\2\0\7\60"+
    "\1\0\1\60\1\0\4\60\2\0\17\60\1\0\71\60\1\0\4\60"+
    "\2\0\103\60\2\0\3\60\40\0\20\60\20\0\125\60\14\0\u026c\60"+
    "\2\0\21\60\1\0\32\60\5\0\113\60\3\0\3\60\17\0\15\60"+
    "\1\0\7\60\13\0\25\60\13\0\24\60\14\0\15\60\1\0\3\60"+
    "\1\0\2\60\14\0\124\60\3\0\1\60\3\0\3\60\2\0\12\60"+
    "\41\0\3\60\2\0\12\60\6\0\130\60\10\0\53\60\5\0\106\60"+
    "\12\0\35\60\3\0\14\60\4\0\14\60\12\0\50\60\2\0\5\60"+
    "\13\0\54\60\4\0\32\60\6\0\12\60\46\0\34\60\4\0\77\60"+
    "\1\0\35\60\2\0\13\60\6\0\12\60\15\0\1\60\130\0\114\60"+
    "\4\0\12\60\21\0\11\60\14\0\53\60\3\0\14\60\6\0\64\60"+
    "\14\0\70\60\10\0\12\60\3\0\61\60\122\0\3\60\1\0\37\60"+
    "\15\0\347\60\25\0\u011a\60\2\0\6\60\2\0\46\60\2\0\6\60"+
    "\2\0\10\60\1\0\1\60\1\0\1\60\1\0\1\60\1\0\37\60"+
    "\2\0\65\60\1\0\7\60\1\0\1\60\3\0\3\60\1\0\7\60"+
    "\3\0\4\60\2\0\6\60\4\0\15\60\5\0\3\60\1\0\7\60"+
    "\16\0\5\60\32\0\5\60\20\0\2\60\23\0\1\60\13\0\5\60"+
    "\5\0\6\60\1\0\1\60\15\0\1\60\20\0\15\60\3\0\32\60"+
    "\26\0\15\60\4\0\1\60\3\0\14\60\21\0\1\60\4\0\1\60"+
    "\2\0\12\60\1\0\1\60\3\0\5\60\6\0\1\60\1\0\1\60"+
    "\1\0\1\60\1\0\4\60\1\0\13\60\2\0\4\60\5\0\5\60"+
    "\4\0\1\60\21\0\51\60\u0a77\0\57\60\1\0\57\60\1\0\205\60"+
    "\6\0\7\60\16\0\46\60\12\0\66\60\11\0\1\60\17\0\30\60"+
    "\11\0\7\60\1\0\7\60\1\0\7\60\1\0\7\60\1\0\7\60"+
    "\1\0\7\60\1\0\7\60\1\0\7\60\1\0\40\60\57\0\1\60"+
    "\u01d5\0\3\60\31\0\17\60\1\0\5\60\2\0\5\60\4\0\126\60"+
    "\2\0\2\60\2\0\3\60\1\0\132\60\1\0\4\60\5\0\51\60"+
    "\3\0\136\60\21\0\33\60\65\0\20\60\u0200\0\u19b6\60\112\0\u51cc\60"+
    "\64\0\u048d\60\103\0\56\60\2\0\u010d\60\3\0\34\60\24\0\60\60"+
    "\14\0\2\60\1\0\31\60\10\0\122\60\45\0\11\60\2\0\147\60"+
    "\2\0\4\60\1\0\2\60\16\0\12\60\120\0\56\60\20\0\1\60"+
    "\7\0\64\60\14\0\105\60\13\0\12\60\6\0\30\60\3\0\1\60"+
    "\4\0\56\60\2\0\44\60\14\0\35\60\3\0\101\60\16\0\13\60"+
    "\46\0\67\60\11\0\16\60\2\0\12\60\6\0\27\60\3\0\2\60"+
    "\4\0\103\60\30\0\3\60\43\0\6\60\2\0\6\60\2\0\6\60"+
    "\11\0\7\60\1\0\7\60\221\0\53\60\1\0\2\60\2\0\12\60"+
    "\6\0\u2ba4\60\14\0\27\60\4\0\61\60\u2104\0\u012e\60\2\0\76\60"+
    "\2\0\152\60\46\0\7\60\14\0\5\60\5\0\14\60\1\0\15\60"+
    "\1\0\5\60\1\0\1\60\1\0\2\60\1\0\2\60\1\0\154\60"+
    "\41\0\u016b\60\22\0\100\60\2\0\66\60\50\0\15\60\3\0\20\60"+
    "\20\0\7\60\14\0\2\60\30\0\3\60\31\0\1\60\6\0\5\60"+
    "\1\0\207\60\2\0\1\60\4\0\1\60\13\0\12\60\7\0\32\60"+
    "\4\0\1\60\1\0\32\60\13\0\131\60\3\0\6\60\2\0\6\60"+
    "\2\0\6\60\2\0\3\60\3\0\2\60\3\0\2\60\22\0\3\60"+
    "\4\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\2\2\1\3\1\4\1\1\1\5\1\6"+
    "\1\7\1\10\1\1\1\11\3\12\1\13\1\14\1\15"+
    "\1\16\1\17\1\20\1\21\1\22\1\23\1\24\14\12"+
    "\1\25\1\1\1\26\1\25\1\2\1\0\1\27\1\30"+
    "\1\31\1\32\1\33\1\12\1\34\2\12\1\35\1\12"+
    "\1\36\6\12\1\37\12\12\3\0\1\2\2\0\1\40"+
    "\2\12\1\41\2\12\1\42\1\43\14\12\1\44\1\45"+
    "\1\46\1\0\1\47\1\50\2\12\1\51\1\12\1\52"+
    "\1\12\1\53\1\54\1\55\1\56\1\12\1\57\1\60"+
    "\1\61\1\12\1\62\1\63\1\64\1\12\1\65\1\66"+
    "\1\67";

  private static int [] zzUnpackAction() {
    int [] result = new int[128];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\63\0\146\0\231\0\146\0\314\0\146\0\377"+
    "\0\146\0\146\0\u0132\0\u0165\0\u0198\0\u01cb\0\u01fe\0\u0231"+
    "\0\u0264\0\146\0\u0297\0\146\0\146\0\146\0\146\0\146"+
    "\0\146\0\146\0\146\0\u02ca\0\u02fd\0\u0330\0\u0363\0\u0396"+
    "\0\u03c9\0\u03fc\0\u042f\0\u0462\0\u0495\0\u04c8\0\u04fb\0\u052e"+
    "\0\u0561\0\u0594\0\u05c7\0\u05fa\0\377\0\146\0\146\0\146"+
    "\0\146\0\146\0\u062d\0\u0330\0\u0660\0\u0693\0\146\0\u06c6"+
    "\0\u0330\0\u06f9\0\u072c\0\u075f\0\u0792\0\u07c5\0\u07f8\0\u0330"+
    "\0\u082b\0\u085e\0\u0891\0\u08c4\0\u08f7\0\u092a\0\u095d\0\u0990"+
    "\0\u09c3\0\u09f6\0\u0a29\0\u0a5c\0\u05c7\0\u0a8f\0\u0ac2\0\u0af5"+
    "\0\u0330\0\u0b28\0\u0b5b\0\u0330\0\u0b8e\0\u0bc1\0\u0330\0\u0330"+
    "\0\u0bf4\0\u0c27\0\u0c5a\0\u0c8d\0\u0cc0\0\u0cf3\0\u0d26\0\u0d59"+
    "\0\u0d8c\0\u0dbf\0\u0df2\0\u0e25\0\u0330\0\u0a29\0\146\0\u0e58"+
    "\0\u0330\0\u0330\0\u0e8b\0\u0ebe\0\u0330\0\u0ef1\0\u0330\0\u0f24"+
    "\0\u0330\0\u0330\0\u0330\0\u0330\0\u0f57\0\u0330\0\u0330\0\u0330"+
    "\0\u0f8a\0\u0330\0\u0330\0\u0330\0\u0fbd\0\u0330\0\u0330\0\u0330";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[128];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\1\4\2\5\1\6\1\7\1\10\1\11\1\12"+
    "\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22"+
    "\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32"+
    "\1\33\1\34\1\35\1\36\1\37\1\40\1\36\1\41"+
    "\1\42\1\36\1\43\1\44\1\36\1\45\1\46\1\47"+
    "\3\36\1\50\1\51\1\3\1\52\2\3\1\53\63\3"+
    "\65\0\1\5\65\0\1\54\55\0\1\55\1\4\1\5"+
    "\60\55\12\0\1\56\5\0\1\57\53\0\1\60\64\0"+
    "\1\61\63\0\1\62\63\0\1\36\1\63\1\36\12\0"+
    "\23\36\1\0\1\36\1\0\1\36\1\0\1\36\15\0"+
    "\3\36\12\0\1\36\1\64\21\36\1\0\1\36\1\0"+
    "\1\36\1\0\1\36\15\0\3\36\12\0\2\36\1\65"+
    "\4\36\1\66\13\36\1\0\1\36\1\0\1\36\1\0"+
    "\1\36\11\0\1\67\66\0\1\70\2\36\12\0\1\36"+
    "\1\71\21\36\1\0\1\36\1\0\1\36\1\0\1\36"+
    "\15\0\3\36\12\0\5\36\1\72\2\36\1\73\7\36"+
    "\1\74\2\36\1\0\1\36\1\0\1\36\1\0\1\36"+
    "\15\0\3\36\12\0\23\36\1\0\1\36\1\0\1\36"+
    "\1\0\1\36\15\0\1\75\2\36\12\0\5\36\1\76"+
    "\15\36\1\0\1\36\1\0\1\36\1\0\1\36\15\0"+
    "\3\36\12\0\2\36\1\77\20\36\1\0\1\36\1\0"+
    "\1\36\1\0\1\36\15\0\1\36\1\100\1\36\12\0"+
    "\10\36\1\101\12\36\1\0\1\36\1\0\1\36\1\0"+
    "\1\36\15\0\3\36\12\0\3\36\1\102\17\36\1\0"+
    "\1\36\1\0\1\36\1\0\1\36\15\0\3\36\12\0"+
    "\1\103\22\36\1\0\1\36\1\0\1\36\1\0\1\36"+
    "\15\0\1\36\1\104\1\36\12\0\3\36\1\105\17\36"+
    "\1\0\1\36\1\0\1\36\1\0\1\36\15\0\3\36"+
    "\12\0\2\36\1\106\1\107\4\36\1\110\12\36\1\0"+
    "\1\36\1\0\1\36\1\0\1\36\15\0\3\36\12\0"+
    "\7\36\1\111\13\36\1\0\1\36\1\0\1\36\1\0"+
    "\1\36\15\0\3\36\12\0\10\36\1\112\12\36\1\0"+
    "\1\36\1\0\1\36\1\0\1\36\54\0\1\50\4\0"+
    "\1\113\1\50\15\114\3\0\12\114\23\0\1\114\1\0"+
    "\3\114\16\0\3\52\12\0\23\52\1\0\1\52\1\0"+
    "\1\52\1\0\1\52\54\0\1\115\4\0\1\113\1\115"+
    "\4\116\1\117\1\120\55\116\15\0\2\36\1\121\12\0"+
    "\23\36\1\0\1\36\1\0\1\36\1\0\1\36\15\0"+
    "\3\36\12\0\3\36\1\122\17\36\1\0\1\36\1\0"+
    "\1\36\1\0\1\36\15\0\3\36\12\0\20\36\1\123"+
    "\2\36\1\0\1\36\1\0\1\36\1\0\1\36\15\0"+
    "\2\36\1\124\12\0\23\36\1\0\1\36\1\0\1\36"+
    "\1\0\1\36\15\0\1\36\1\125\1\36\12\0\23\36"+
    "\1\0\1\36\1\0\1\36\1\0\1\36\15\0\3\36"+
    "\12\0\5\36\1\126\15\36\1\0\1\36\1\0\1\36"+
    "\1\0\1\36\15\0\1\127\2\36\12\0\23\36\1\0"+
    "\1\36\1\0\1\36\1\0\1\36\15\0\3\36\12\0"+
    "\6\36\1\130\14\36\1\0\1\36\1\0\1\36\1\0"+
    "\1\36\15\0\3\36\12\0\11\36\1\131\11\36\1\0"+
    "\1\36\1\0\1\36\1\0\1\36\15\0\3\36\12\0"+
    "\1\132\22\36\1\0\1\36\1\0\1\36\1\0\1\36"+
    "\15\0\2\36\1\133\12\0\23\36\1\0\1\36\1\0"+
    "\1\36\1\0\1\36\15\0\2\36\1\134\12\0\10\36"+
    "\1\135\12\36\1\0\1\36\1\0\1\36\1\0\1\36"+
    "\15\0\3\36\12\0\17\36\1\136\3\36\1\0\1\36"+
    "\1\0\1\36\1\0\1\36\15\0\1\36\1\137\1\36"+
    "\12\0\23\36\1\0\1\36\1\0\1\36\1\0\1\36"+
    "\15\0\3\36\12\0\13\36\1\140\7\36\1\0\1\36"+
    "\1\0\1\36\1\0\1\36\15\0\3\36\12\0\10\36"+
    "\1\141\12\36\1\0\1\36\1\0\1\36\1\0\1\36"+
    "\15\0\3\36\12\0\1\142\22\36\1\0\1\36\1\0"+
    "\1\36\1\0\1\36\15\0\3\36\12\0\11\36\1\143"+
    "\11\36\1\0\1\36\1\0\1\36\1\0\1\36\15\0"+
    "\3\36\12\0\1\144\22\36\1\0\1\36\1\0\1\36"+
    "\1\0\1\36\15\0\3\36\12\0\7\36\1\145\13\36"+
    "\1\0\1\36\1\0\1\36\1\0\1\36\54\0\1\146"+
    "\5\0\1\146\55\0\1\147\5\0\4\116\1\117\1\150"+
    "\55\116\5\117\1\150\55\117\4\0\1\5\1\120\72\0"+
    "\1\151\2\36\12\0\23\36\1\0\1\36\1\0\1\36"+
    "\1\0\1\36\15\0\3\36\12\0\3\36\1\152\17\36"+
    "\1\0\1\36\1\0\1\36\1\0\1\36\15\0\1\36"+
    "\1\153\1\36\12\0\10\36\1\154\12\36\1\0\1\36"+
    "\1\0\1\36\1\0\1\36\15\0\3\36\12\0\11\36"+
    "\1\123\11\36\1\0\1\36\1\0\1\36\1\0\1\36"+
    "\15\0\3\36\12\0\3\36\1\155\17\36\1\0\1\36"+
    "\1\0\1\36\1\0\1\36\15\0\3\36\12\0\5\36"+
    "\1\156\15\36\1\0\1\36\1\0\1\36\1\0\1\36"+
    "\15\0\3\36\12\0\10\36\1\157\12\36\1\0\1\36"+
    "\1\0\1\36\1\0\1\36\15\0\3\36\12\0\20\36"+
    "\1\160\2\36\1\0\1\36\1\0\1\36\1\0\1\36"+
    "\15\0\3\36\12\0\5\36\1\161\1\162\14\36\1\0"+
    "\1\36\1\0\1\36\1\0\1\36\15\0\3\36\12\0"+
    "\3\36\1\163\17\36\1\0\1\36\1\0\1\36\1\0"+
    "\1\36\15\0\3\36\12\0\5\36\1\164\15\36\1\0"+
    "\1\36\1\0\1\36\1\0\1\36\15\0\3\36\12\0"+
    "\1\165\22\36\1\0\1\36\1\0\1\36\1\0\1\36"+
    "\15\0\3\36\12\0\7\36\1\166\13\36\1\0\1\36"+
    "\1\0\1\36\1\0\1\36\15\0\3\36\12\0\5\36"+
    "\1\167\15\36\1\0\1\36\1\0\1\36\1\0\1\36"+
    "\15\0\3\36\12\0\3\36\1\170\17\36\1\0\1\36"+
    "\1\0\1\36\1\0\1\36\15\0\1\171\2\36\12\0"+
    "\23\36\1\0\1\36\1\0\1\36\1\0\1\36\4\117"+
    "\1\5\1\150\55\117\15\0\3\36\12\0\7\36\1\172"+
    "\13\36\1\0\1\36\1\0\1\36\1\0\1\36\15\0"+
    "\2\36\1\173\12\0\23\36\1\0\1\36\1\0\1\36"+
    "\1\0\1\36\15\0\3\36\12\0\3\36\1\174\17\36"+
    "\1\0\1\36\1\0\1\36\1\0\1\36\15\0\3\36"+
    "\12\0\7\36\1\175\13\36\1\0\1\36\1\0\1\36"+
    "\1\0\1\36\15\0\1\176\2\36\12\0\23\36\1\0"+
    "\1\36\1\0\1\36\1\0\1\36\15\0\2\36\1\177"+
    "\12\0\23\36\1\0\1\36\1\0\1\36\1\0\1\36"+
    "\15\0\1\200\2\36\12\0\23\36\1\0\1\36\1\0"+
    "\1\36\1\0\1\36";

  private static int [] zzUnpackTrans() {
    int [] result = new int[4080];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\1\11\1\1\1\11\1\1\1\11\1\1\2\11"+
    "\7\1\1\11\1\1\10\11\21\1\1\0\5\11\4\1"+
    "\1\11\23\1\3\0\1\1\2\0\26\1\1\11\1\0"+
    "\30\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[128];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
  /* can define helper functions here */
  StringBuffer string = new StringBuffer();
	  
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


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  mpp_lexor(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  mpp_lexor(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 1902) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      System.out.println();
  yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 2: 
          { /* ignore */
          }
        case 56: break;
        case 30: 
          { return symbol("M_if", sym.IF);
          }
        case 57: break;
        case 53: 
          { return symbol("M_begin", sym.BEGIN);
          }
        case 58: break;
        case 15: 
          { return symbol("(", sym.CLPAR);
          }
        case 59: break;
        case 36: 
          { return symbol("M_var", sym.VAR);
          }
        case 60: break;
        case 34: 
          { return symbol("M_fun", sym.FUN);
          }
        case 61: break;
        case 9: 
          { return symbol("|", sym.SLASH);
          }
        case 62: break;
        case 41: 
          { return symbol("M_else", sym.ELSE);
          }
        case 63: break;
        case 51: 
          { return symbol("M_float", sym.FLOAT);
          }
        case 64: break;
        case 14: 
          { return symbol(")", sym.RPAR);
          }
        case 65: break;
        case 5: 
          { return symbol("M_add", sym.ADD);
          }
        case 66: break;
        case 39: 
          { return symbol("M_then", sym.THEN);
          }
        case 67: break;
        case 43: 
          { return symbol("M_real", sym.REAL);
          }
        case 68: break;
        case 23: 
          { return symbol("M_arrow", sym.ARROW);
          }
        case 69: break;
        case 31: 
          { return symbol("M_do", sym.DO);
          }
        case 70: break;
        case 11: 
          { return symbol("M_lt", sym.LT);
          }
        case 71: break;
        case 17: 
          { return symbol("[", sym.SLPAR);
          }
        case 72: break;
        case 4: 
          { return symbol("M_mul", sym.MUL);
          }
        case 73: break;
        case 44: 
          { return symbol("M_read", sym.READ);
          }
        case 74: break;
        case 13: 
          { return symbol("(", sym.LPAR);
          }
        case 75: break;
        case 29: 
          { return symbol("M_ass", sym.ASSIGN);
          }
        case 76: break;
        case 50: 
          { return symbol("M_floor", sym.FLOOR);
          }
        case 77: break;
        case 47: 
          { return symbol("M_char", sym.CHAR);
          }
        case 78: break;
        case 25: 
          { return symbol("M_ge", sym.GE);
          }
        case 79: break;
        case 54: 
          { return symbol("M_print", sym.PRINT);
          }
        case 80: break;
        case 19: 
          { return symbol(";", sym.SEMICLON);
          }
        case 81: break;
        case 3: 
          { return symbol("M_div", sym.DIV);
          }
        case 82: break;
        case 32: 
          { return symbol("M_not", sym.NOT);
          }
        case 83: break;
        case 22: 
          { return symbol("M_cid", sym.CID, new String(yytext()));
          }
        case 84: break;
        case 6: 
          { return symbol("M_sub", sym.SUB);
          }
        case 85: break;
        case 7: 
          { return symbol("M_equal", sym.EQUAL);
          }
        case 86: break;
        case 46: 
          { return symbol("M_bool", sym.BOOL);
          }
        case 87: break;
        case 45: 
          { return symbol("M_size", sym.SIZE);
          }
        case 88: break;
        case 55: 
          { return symbol("M_return", sym.RETURN);
          }
        case 89: break;
        case 26: 
          { return symbol("M_and", sym.AND);
          }
        case 90: break;
        case 21: 
          { return symbol("M_ival", sym.IVAL, new Integer(yytext()));
          }
        case 91: break;
        case 18: 
          { return symbol("]", sym.SRPAR);
          }
        case 92: break;
        case 33: 
          { return symbol("M_int", sym.INT);
          }
        case 93: break;
        case 28: 
          { return symbol("M_of", sym.OF);
          }
        case 94: break;
        case 40: 
          { return symbol("M_bval", sym.BVAL, new String(yytext()));
          }
        case 95: break;
        case 8: 
          { return symbol("M_gt", sym.GT);
          }
        case 96: break;
        case 52: 
          { return symbol("M_while", sym.WHILE);
          }
        case 97: break;
        case 16: 
          { return symbol(")", sym.CRPAR);
          }
        case 98: break;
        case 20: 
          { return symbol(",", sym.COMMA);
          }
        case 99: break;
        case 27: 
          { return symbol("M_or", sym.OR);
          }
        case 100: break;
        case 38: 
          { return symbol("M_cval", sym.CVAL, new String(yytext()));
          }
        case 101: break;
        case 1: 
          { System.out.println("Illegal character <"+
                                                    yytext()+">");
          }
        case 102: break;
        case 48: 
          { return symbol("M_ceil", sym.CEIL);
          }
        case 103: break;
        case 42: 
          { return symbol("M_data", sym.DATA);
          }
        case 104: break;
        case 12: 
          { return symbol(":", sym.COLON);
          }
        case 105: break;
        case 24: 
          { return symbol("M_le", sym.LE);
          }
        case 106: break;
        case 35: 
          { return symbol("M_end", sym.END);
          }
        case 107: break;
        case 10: 
          { return symbol("", sym.ID, new String("\""+yytext()+"\""));
          }
        case 108: break;
        case 49: 
          { return symbol("M_case", sym.CASE);
          }
        case 109: break;
        case 37: 
          { return symbol("M_rval", sym.RVAL, new Double(yytext()));
          }
        case 110: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { return new java_cup.runtime.Symbol(sym.EOF); }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }

  /**
   * Runs the scanner on input files.
   *
   * This is a standalone scanner, it will print any unmatched
   * text to System.out unchanged.
   *
   * @param argv   the command line, contains the filenames to run
   *               the scanner on.
   */
  public static void main(String argv[]) {
    if (argv.length == 0) {
      System.out.println("Usage : java mpp_lexor <inputfile>");
    }
    else {
      for (int i = 0; i < argv.length; i++) {
        mpp_lexor scanner = null;
        try {
          scanner = new mpp_lexor( new java.io.FileReader(argv[i]) );
          while ( !scanner.zzAtEOF ) scanner.next_token();
        }
        catch (java.io.FileNotFoundException e) {
          System.out.println("File not found : \""+argv[i]+"\"");
        }
        catch (java.io.IOException e) {
          System.out.println("IO error scanning file \""+argv[i]+"\"");
          System.out.println(e);
        }
        catch (Exception e) {
          System.out.println("Unexpected exception:");
          e.printStackTrace();
        }
      }
    }
  }


}
