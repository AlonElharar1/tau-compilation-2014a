/* Compile me with:  jflex mini.lex  */
package Lexer;

%%

%class JflexScanner
%unicode

%type Token

%{
Token getNextToken(String tag) { 
	return new Token(tag, yycolumn, yytext());
}
%}
	
%column

INT = [0-9]+
NUM = {INT} ("." {INT})?
OP = [+-/*\^]
ID = [a-z][a-z0-9]*

DQUOTE = "\""
STR = {DQUOTE} ~([^\\]?{DQUOTE})

COMMENT = "#" .*
	
%%

{NUM}       { return getNextToken("INTEGER"); }
{OP}        { return getNextToken("◇"); }
{STR}       { return getNextToken("A"); }
{ID}        { return getNextToken("$"); }

{COMMENT} |
[ ]         { /* nothing; skip */ }

.           { throw new Error("Lexical error"); }
