//USER CODE
package syntaxanalysis;
import java_cup.runtime.*;
import java.io.IOException;
%%

//OPTION & DECLARATIONS
%public
%class Lexer
%unicode
%cup
%line
%column

%eofval{
    return new Symbol(sym.EOF);
%eofval}

%{
    StringBuffer string = new StringBuffer();

    private Symbol generateToken(int type){
        return new Symbol(type);
    }

    private Symbol generateToken(int type, Object value){
        return new Symbol(type, value);
    }
%}

//MACRO
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]
Letter = [A-Za-z]
Digit = [0-9]
Identifier = ({Letter}|_)({Letter}|{Digit}|_)*
Integer = [1-9][0-9]*|[0]
//Float = (0|[1-9][0-9]*) (\.([0]*[1-9]+([0-9]*[1-9])?) ((e|E)(\+|-)?[0-9]+)?)
Float = (0|[1-9][0-9]*)(\.([0-9]*)((e|E)(\+|-)?[0-9]+)?)
CommentString = [^\*\/]*

//dichiarazione degli state per la gestione degli errori per stringhe e commenti
%state STRING
%state COMMENT
%state COMMENT2

//fine delle directives e inizio delle regular expression rules (LEXICAL RULES)
%%
<YYINITIAL>{
     "true"     {return generateToken(sym.TRUE);}
    "false"     {return generateToken(sym.FALSE);}
    /* NULL */
     "null"     {return generateToken(sym.NULL);}
    /* identifiers */
    {Identifier} { //questa variabile contiene un intero che è il riferimento
                   //alla keyword se yytext() è una keyword, -1 altrimenti
                   int isKeyword = SymbolTable.retrieveKeyword(yytext());
                   if( isKeyword != -1){
                       return generateToken(isKeyword);
                   } else {
                       return generateToken(sym.ID, yytext());
                   }
    }

    /* literals */
    {Integer}   {return generateToken(sym.INT_CONST, yytext());}
    {Float}     {return generateToken(sym.FLOAT_CONST, yytext());}
    /* operators */
        "="     {return generateToken(sym.EQ);}
        ":="    {return generateToken(sym.ASSIGN);}
        "<"     {return generateToken(sym.LT);}
        "<="    {return generateToken(sym.LE);}
        ">"     {return generateToken(sym.GT);}
        ">="    {return generateToken(sym.GE);}
        "<>"    {return generateToken(sym.NE);}
        "+"     {return generateToken(sym.PLUS);}
        "-"     {return generateToken(sym.MINUS);}
        "*"     {return generateToken(sym.TIMES);}
        "/"     {return generateToken(sym.DIV);}
        "->"    {return generateToken(sym.RETURN);}
    /*parentesi*/
        "("     {return generateToken(sym.LPAR);}
        ")"     {return generateToken(sym.RPAR);}
    /*virgole*/
        ","     {return generateToken(sym.COMMA);}
        ";"     {return generateToken(sym.SEMI);}
        ":"     {return generateToken(sym.COLON);}
    /* booleani */
       "&&"     {return generateToken(sym.AND);}
       "||"     {return generateToken(sym.OR);}
        "!"     {return generateToken(sym.NOT);}
         \"     {yybegin(STRING);  /*controllo stringhe - cerchiamo la fine della stringa*/}
        \/\*    {yybegin(COMMENT); /*controllo commenti - cerchiamo la fine del commento */}
{WhiteSpace}    {/* ignore */}
}

<STRING> <<EOF>>     {throw new IOException("Stringa costante non completata");}
<STRING> {
    \"          { yybegin(YYINITIAL);
                  Symbol s = generateToken(sym.STRING_CONST, string.toString());
                  string = new StringBuffer();
                  return s;
                }
   [^\n\r\"\\]* {string.append(yytext());}
    \\t         { string.append('\t'); }
    \\n         { string.append('\n');}
    \\r         { string.append('\r');}
    \\\"        { string.append('\"'); }
    \\          { string.append('\\'); }
}

<COMMENT,COMMENT2> <<EOF>> {throw new IOException("Commento non chiuso");}

<COMMENT> {
    \*                  {yybegin(COMMENT2);}
    {CommentString}     {/* ignore */}
    \/                  {/* ignore */}
}
<COMMENT2>{
    \/                  {yybegin(YYINITIAL);}
    [^\/]               {yybegin(COMMENT);}
}
/* error fallback */
[^] 			{throw new IOException("<Carattere Illegale (L: "+ (yyline+1)+" C: " + (yycolumn+1)+"): "
                 + yytext() + ">");}
