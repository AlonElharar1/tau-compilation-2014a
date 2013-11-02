/* The following code was generated by JFlex 1.4.3 on 14:49 02/11/13 */

package Lexer;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 14:49 02/11/13 from the specification file
 * <tt>rules.lex</tt>
 */
class JflexScanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\36\1\34\2\0\1\35\22\0\1\36\1\0\1\31\7\0"+
    "\1\33\4\27\1\32\1\25\11\26\42\0\1\0\1\0\1\27\2\0"+
    "\1\3\1\15\1\1\1\11\1\5\1\21\1\17\1\23\1\12\1\30"+
    "\1\24\1\2\1\30\1\10\1\14\2\30\1\16\1\4\1\7\1\20"+
    "\1\13\1\22\1\6\2\30\uff85\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\15\1\2\2\1\3\1\0\1\3\2\4\13\1"+
    "\1\5\6\1\1\6\1\0\11\1\1\7\1\1\1\10"+
    "\6\1\1\6\1\0\5\1\1\11\1\1\1\12\1\13"+
    "\1\14\1\15\5\1\1\4\1\16\6\1\1\17\1\1"+
    "\1\20\1\21\1\1\1\22\1\23\1\24\2\1\1\25"+
    "\1\1\1\26\1\27\1\30";

  private static int [] zzUnpackAction() {
    int [] result = new int[100];
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
    "\0\0\0\37\0\76\0\135\0\174\0\233\0\272\0\331"+
    "\0\370\0\u0117\0\u0136\0\u0155\0\u0174\0\u0193\0\u01b2\0\u01d1"+
    "\0\u01b2\0\u01f0\0\u020f\0\u01b2\0\u022e\0\u024d\0\u026c\0\u028b"+
    "\0\u02aa\0\u02c9\0\u02e8\0\u0307\0\u0326\0\u0345\0\u0364\0\u0383"+
    "\0\135\0\u03a2\0\u03c1\0\u03e0\0\u03ff\0\u041e\0\u043d\0\u045c"+
    "\0\u047b\0\u049a\0\u04b9\0\u04d8\0\u04f7\0\u0516\0\u0535\0\u0554"+
    "\0\u0573\0\u0592\0\135\0\u05b1\0\135\0\u05d0\0\u05ef\0\u060e"+
    "\0\u062d\0\u064c\0\u066b\0\u01b2\0\u068a\0\u06a9\0\u06c8\0\u06e7"+
    "\0\u0706\0\u0725\0\135\0\u0744\0\135\0\135\0\135\0\135"+
    "\0\u0763\0\u0782\0\u07a1\0\u07c0\0\u07df\0\u047b\0\135\0\u07fe"+
    "\0\u081d\0\u083c\0\u085b\0\u087a\0\u0899\0\135\0\u08b8\0\135"+
    "\0\135\0\u08d7\0\135\0\135\0\135\0\u08f6\0\u0915\0\135"+
    "\0\u0934\0\135\0\135\0\135";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[100];
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
    "\1\0\1\2\1\3\1\4\1\5\1\6\1\4\1\7"+
    "\1\10\1\4\1\11\1\12\1\4\1\13\1\14\2\4"+
    "\1\15\1\16\2\4\1\17\1\20\1\21\1\4\1\22"+
    "\1\23\1\21\1\24\1\25\1\24\1\0\1\4\1\26"+
    "\11\4\1\27\12\4\1\0\1\4\7\0\4\4\1\30"+
    "\21\4\1\0\1\4\7\0\26\4\1\0\1\4\7\0"+
    "\6\4\1\31\17\4\1\0\1\4\7\0\1\4\1\32"+
    "\3\4\1\33\20\4\1\0\1\4\7\0\15\4\1\34"+
    "\4\4\1\35\3\4\1\0\1\4\7\0\4\4\1\36"+
    "\12\4\1\37\6\4\1\0\1\4\7\0\7\4\1\40"+
    "\10\4\1\41\5\4\1\0\1\4\7\0\13\4\1\42"+
    "\12\4\1\0\1\4\7\0\13\4\1\43\1\4\1\44"+
    "\10\4\1\0\1\4\7\0\4\4\1\45\21\4\1\0"+
    "\1\4\7\0\2\4\1\46\23\4\1\0\1\4\7\0"+
    "\22\4\1\47\3\4\1\0\1\4\72\0\2\20\10\0"+
    "\31\22\1\50\5\22\32\0\1\24\1\51\37\0\1\24"+
    "\3\0\2\4\1\52\23\4\1\0\1\4\7\0\7\4"+
    "\1\53\16\4\1\0\1\4\7\0\7\4\1\54\16\4"+
    "\1\0\1\4\7\0\2\4\1\55\12\4\1\56\10\4"+
    "\1\0\1\4\7\0\3\4\1\57\22\4\1\0\1\4"+
    "\7\0\6\4\1\60\17\4\1\0\1\4\7\0\17\4"+
    "\1\61\6\4\1\0\1\4\7\0\11\4\1\62\14\4"+
    "\1\0\1\4\7\0\21\4\1\63\4\4\1\0\1\4"+
    "\7\0\1\4\1\64\24\4\1\0\1\4\7\0\6\4"+
    "\1\65\17\4\1\0\1\4\7\0\11\4\1\66\14\4"+
    "\1\0\1\4\7\0\13\4\1\67\12\4\1\0\1\4"+
    "\7\0\4\4\1\70\21\4\1\0\1\4\7\0\6\4"+
    "\1\71\17\4\1\0\1\4\7\0\1\4\1\72\24\4"+
    "\1\0\1\4\7\0\11\4\1\73\14\4\1\0\1\4"+
    "\37\0\1\74\5\0\33\51\1\75\3\51\1\0\3\4"+
    "\1\76\22\4\1\0\1\4\7\0\6\4\1\77\17\4"+
    "\1\0\1\4\7\0\16\4\1\100\7\4\1\0\1\4"+
    "\7\0\6\4\1\101\17\4\1\0\1\4\7\0\11\4"+
    "\1\102\14\4\1\0\1\4\7\0\4\4\1\103\21\4"+
    "\1\0\1\4\7\0\4\4\1\104\21\4\1\0\1\4"+
    "\7\0\4\4\1\105\21\4\1\0\1\4\7\0\3\4"+
    "\1\106\22\4\1\0\1\4\7\0\1\4\1\107\24\4"+
    "\1\0\1\4\7\0\10\4\1\110\15\4\1\0\1\4"+
    "\7\0\1\4\1\111\24\4\1\0\1\4\7\0\2\4"+
    "\1\112\23\4\1\0\1\4\7\0\17\4\1\113\6\4"+
    "\1\0\1\4\7\0\3\4\1\114\22\4\1\0\1\4"+
    "\7\0\1\4\1\115\24\4\1\0\1\4\6\0\32\51"+
    "\1\116\1\75\3\51\1\0\3\4\1\117\22\4\1\0"+
    "\1\4\7\0\11\4\1\120\14\4\1\0\1\4\7\0"+
    "\6\4\1\121\17\4\1\0\1\4\7\0\11\4\1\122"+
    "\14\4\1\0\1\4\7\0\7\4\1\123\16\4\1\0"+
    "\1\4\7\0\7\4\1\124\16\4\1\0\1\4\7\0"+
    "\4\4\1\125\21\4\1\0\1\4\7\0\23\4\1\126"+
    "\2\4\1\0\1\4\7\0\15\4\1\127\10\4\1\0"+
    "\1\4\7\0\4\4\1\130\21\4\1\0\1\4\7\0"+
    "\4\4\1\131\21\4\1\0\1\4\7\0\7\4\1\132"+
    "\16\4\1\0\1\4\7\0\22\4\1\133\3\4\1\0"+
    "\1\4\7\0\1\134\25\4\1\0\1\4\7\0\16\4"+
    "\1\135\7\4\1\0\1\4\7\0\10\4\1\136\15\4"+
    "\1\0\1\4\7\0\2\4\1\137\23\4\1\0\1\4"+
    "\7\0\7\4\1\140\16\4\1\0\1\4\7\0\17\4"+
    "\1\141\6\4\1\0\1\4\7\0\3\4\1\142\22\4"+
    "\1\0\1\4\7\0\7\4\1\143\16\4\1\0\1\4"+
    "\7\0\4\4\1\144\21\4\1\0\1\4\6\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[2387];
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
    "\1\0\15\1\1\11\1\1\1\11\1\0\1\1\1\11"+
    "\24\1\1\0\22\1\1\11\1\0\47\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[100];
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
Token getToken(String tag) { 
	return new Token(tag, yyline+1, yycolumn+1, yytext());
}


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  JflexScanner(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  JflexScanner(java.io.InputStream in) {
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
    while (i < 90) {
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
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public Token yylex() throws java.io.IOException {
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
        case 3: 
          { return getToken("Operation");
          }
        case 25: break;
        case 8: 
          { return getToken("int");
          }
        case 26: break;
        case 20: 
          { return getToken("string");
          }
        case 27: break;
        case 18: 
          { return getToken("length");
          }
        case 28: break;
        case 23: 
          { return getToken("boolean");
          }
        case 29: break;
        case 12: 
          { return getToken("null");
          }
        case 30: break;
        case 6: 
          { return getToken("String");
          }
        case 31: break;
        case 4: 
          { /* nothing; skip */
          }
        case 32: break;
        case 17: 
          { return getToken("while");
          }
        case 33: break;
        case 22: 
          { return getToken("extends");
          }
        case 34: break;
        case 14: 
          { return getToken("class");
          }
        case 35: break;
        case 16: 
          { return getToken("false");
          }
        case 36: break;
        case 10: 
          { return getToken("true");
          }
        case 37: break;
        case 19: 
          { return getToken("static");
          }
        case 38: break;
        case 24: 
          { return getToken("continue");
          }
        case 39: break;
        case 21: 
          { return getToken("return");
          }
        case 40: break;
        case 5: 
          { return getToken("if");
          }
        case 41: break;
        case 7: 
          { return getToken("new");
          }
        case 42: break;
        case 15: 
          { return getToken("break");
          }
        case 43: break;
        case 1: 
          { return getToken("ID");
          }
        case 44: break;
        case 11: 
          { return getToken("this");
          }
        case 45: break;
        case 2: 
          { return getToken("INTEGER");
          }
        case 46: break;
        case 13: 
          { return getToken("void");
          }
        case 47: break;
        case 9: 
          { return getToken("else");
          }
        case 48: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            return null;
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
