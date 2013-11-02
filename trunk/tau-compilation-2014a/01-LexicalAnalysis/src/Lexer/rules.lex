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
%}

//Keywords
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

Integer = 0 | [1-9][0-9]*
OP = [+-/*\^]
ID = [a-z][a-z0-9]*
DQUOTE = "\""
String = {DQUOTE}(.|{NewLine})* [^\\] {DQUOTE}
Comment = \/\*(.|{NewLine})*\*\/
LineComment = \/\/.*
NewLine = \n|\r|\r\n
WhiteSpace = {NewLine} | [ \t\f]


%%

{Class}			{ return getToken("class"); }
{Extends}		{ return getToken("extends"); }
{Static}		{ return getToken("static"); }
{Void}			{ return getToken("void"); }
{Int}			{ return getToken("int"); }
{Boolean}		{ return getToken("boolean"); }
{StringKW}		{ return getToken("string"); }
{Return}		{ return getToken("return"); }
{If}			{ return getToken("if"); }
{Else}			{ return getToken("else"); }
{While}			{ return getToken("while"); }
{Break}			{ return getToken("break"); }
{Continue}		{ return getToken("continue"); }
{This}			{ return getToken("this"); }
{New}			{ return getToken("new"); }
{Length}		{ return getToken("length"); }
{True}			{ return getToken("true"); }
{False}			{ return getToken("false"); }
{Null}			{ return getToken("null"); }
{Integer}		{ return getToken("INTEGER"); }
{OP}			{ return getToken("Operation"); }
{String}		{ return getToken("String"); }
{ID}			{ return getToken("ID"); }

{Comment}		|
{LineComment}	|
{WhiteSpace}				{ /* nothing; skip */ }