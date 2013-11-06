package Lexer;

%%

%class JflexScanner
%unicode
%line
%column
%type Token

%{
Token getToken(String tag) { 
	return new Token(tag, yyline+1, yycolumn+1, yytext());
}

void throwLexicalException(String msg) throws LexicalException {
	throw new LexicalException(yyline+1, yycolumn+1, msg);
}
%}

// Keywords
Class = class
Extends = extends
Static = static
Void = void
Int = int
Boolean = boolean
StringKW = string
Return = return
If = if
Else = else
While = while
Break = break
Continue = continue
This = this
New = new
Length = length
True = true
False = false
Null = null

Keyword = 	{Class}|{Extends}|{Static}|{Void}|{Int}|{Boolean}|{StringKW}|{Return}|{If}|{Else}|{While}|{Break}|
			{Continue}|{This}|{New}|{Length}|{True}|{False}|{Null}	

NewLine = (\n|\r|\r\n)
WhiteSpace = {NewLine} | [ \t]

Integer = 0 | [1-9][0-9]*
ZLEADING_INTENGER = 0[0-9]?

OP =	"\[" | "\]" | "(" | ")" | "\." | "-" | "!" | "new" | "*" | "/" | "%" | "+" | "-" | 
		"<" | ">" | "<=" | ">=" | "==" | "!=" | "&&" | "\|\|" | "="
STRUCTURE = [{};,]

ID_LETTERS = [A-Za-z0-9_]
ID = [a-z]{ID_LETTERS}*
CLASS_ID = [A-Z]{ID_LETTERS}*
BAD_START_ID = [0-9_]{ID_LETTERS}*

DQUOTE = "\""
STRING_LETTERS = ([\x20-\x21\x23-\x5B\x5D-\x7E] | "\\\"" | "\\\\" | "\\t" | "\\n")
STRING = {DQUOTE}{STRING_LETTERS}*{DQUOTE}
BAD_STRING = {DQUOTE} [^\"]*

MULTI_LINE_COMMENT = "/*"~"*/"
LINE_COMMENT = "//" .* {NewLine}
COMMENTS = {MULTI_LINE_COMMENT} | {LINE_COMMENT}
UNCLOSED_COMMENT = "/*" ([^\*] | (\*[^/]))*

%%

{COMMENTS}			{ /* skipping comments */ }
{WhiteSpace}		{ /* skipping whitespaces */ }

{Class}				{ return getToken("class"); }
{Extends}			{ return getToken("extends"); }
{Static}			{ return getToken("static"); }
{Void}				{ return getToken("void"); }
{Int}				{ return getToken("int"); }
{Boolean}			{ return getToken("boolean"); }
{StringKW}			{ return getToken("string"); }
{Return}			{ return getToken("return"); }
{If}				{ return getToken("if"); }
{Else}				{ return getToken("else"); }
{While}				{ return getToken("while"); }
{Break}				{ return getToken("break"); }
{Continue}			{ return getToken("continue"); }
{This}				{ return getToken("this"); }
{New}				{ return getToken("new"); }
{Length}			{ return getToken("length"); }
{True}				{ return getToken("true"); }
{False}				{ return getToken("false"); }
{Null}				{ return getToken("null"); }
{Integer}			{ return getToken("INTEGER"); }
{OP}				{ return getToken(yytext()); }
{STRUCTURE}			{ return getToken(yytext()); }
{ID}				{ return getToken("ID"); }
{CLASS_ID}			{ return getToken("CLASS_ID"); }
{STRING}			{ return getToken("STRING"); }

{ZLEADING_INTENGER}	{ throwLexicalException("numbers should not have leading zeros"); }
{UNCLOSED_COMMENT}	{ throwLexicalException("unterminated comment"); }
{BAD_STRING}		{ throwLexicalException("malformed string literal"); }
{BAD_START_ID}		{ throwLexicalException("an identifier cannot start with '" + yytext().charAt(0) + "'"); }
.					{ throwLexicalException("invalid character '" + yytext() + "'"); }