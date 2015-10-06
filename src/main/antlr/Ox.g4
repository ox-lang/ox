grammar Ox;

oxFile
    : oxForms
    ;

oxForms
    : oxForm *
    ;

oxForm
    : oxNil
    | oxBoolean
    | oxSymbol
    | oxKeyword
    | oxString
    | oxNumber
    | oxCharacter
    | oxQuote
    | oxList
    | oxTuple
    | oxVector
    | oxSomething
    | oxMap
    | oxSet
    | oxTag
    | oxReader
    ;

oxList
    : '(' oxForms ')'
    ;

oxTuple
    : '#(' oxForms ')'
    ;

oxVector
    : '[' oxForms ']'
    ;

// FIXME: what is?
oxSomething
    : '#[' oxForms ']'
    ;

oxMap
    : '{' (oxForm oxForm)* '}'
    ;

oxSet
    : '#{' oxForms '}'
    ;

oxQuote
    : '\'' oxForm
    ;

oxTag
    : '^' oxForm oxForm
    ;

oxReader
    : '#' oxSymbol oxForm
    ;

oxString: STRING;
oxFloat : FLOAT;
oxHex   : HEX;
oxBin   : BIN;
oxBign  : BIGN;
oxLong  : LONG;
oxRint  : RINT;

oxNumber
    : oxFloat
    | oxHex
    | oxBin
    | oxBign
    | oxLong
    | oxRint
    ;

oxCharacter
    : oxNamedChar
    | oxUHexQuad
    | oxAnyChar
    ;

oxNamedChar: CHAR_NAMED ;
oxAnyChar  : CHAR_ANY ;
oxUHexQuad : CHAR_U ;

oxNil: NIL;
oxBoolean: BOOLEAN;

oxKeyword
    : oxMacroKeyword
    | oxSimpleKeyword
    ;

oxSimpleKeyword
    : ':' oxSymbol
    ;

oxMacroKeyword
    : '::' oxSymbol
    ;

oxSymbol
    : oxNsSymbol
    | SYMBOL
    ;

oxNsSymbol
    : NS_SYMBOL
    ;

// Lexers
//--------------------------------------------------------------------

// Java §3.10.5 String Literals
STRING
    : '"' StringCharacters? '"'
    ;

fragment
StringCharacters
    : StringCharacter+
    ;

fragment
StringCharacter
    : ~[""\\]
    | EscapeSequence
    ;

// Java §3.10.6 Escape Sequences for Character and String Literals
fragment
EscapeSequence
    : '\\' [btnfr"'\\]
    | CHAR_U
    | CHAR_NAMED
    ;

// FIXME: Doesn't deal with arbitrary read radixes, BigNums
FLOAT
    : '-'? [0-9]+ FLOAT_TAIL
    | '-'? 'Infinity'
    | '-'? 'NaN'
    ;

fragment
FLOAT_TAIL
    : FLOAT_DECIMAL FLOAT_EXP
    | FLOAT_DECIMAL
    | FLOAT_EXP
    ;

fragment
FLOAT_DECIMAL
    : '.' [0-9]+
    ;

fragment
FLOAT_EXP
    : [eE] '-'? [0-9]+
    ;

fragment
HEXD: [0-9a-fA-F] ;
HEX:  '0' [xX] HEXD+ ;
BIN:  '0' [bB] [10]+ ;
LONG: '-'? [0-9]+[lL]?;
BIGN: '-'? [0-9]+[nN];
RINT: [1-9][0-9]* 'r' ('0'..'9'|'a'..'z'|'A'..'Z')+;

CHAR_U
    : '\\' 'u'[0-9D-Fd-f] HEXD HEXD HEXD ;

CHAR_NAMED
    : '\\'
    ( 'newline'
    | 'return'
    | 'space'
    | 'tab'
    | 'formfeed'
    | 'backspace'
    )
    ;

CHAR_ANY: '\\' . ;

NIL : 'nil';

BOOLEAN : 'true' | 'false' ;

SYMBOL
    : '.'
    | '/'
    | ':'
    | NAME
    ;

NS_SYMBOL
    : NAME '/' SYMBOL
    ;

// Fragments
//--------------------------------------------------------------------

NAME: SYMBOL_HEAD SYMBOL_REST* (':' SYMBOL_REST+)* ;

SYMBOL_HEAD
    : ~('0' .. '9'
       | '(' | ')'
       | '[' | ']'
       | '{' | '}'
       | '^' | '`' | '\'' | '"' | '#' | '~' | '@' | ':' | '/'
       | [ \n\r\t\,]
       )
    ;

SYMBOL_REST
    : SYMBOL_HEAD
    | '0'..'9'
    | '.'
    ;

// Discard
//--------------------------------------------------------------------

fragment
WS : [ \n\r\t\,] ;

fragment
COMMENT: ';' ~[\r\n]* ;

TRASH
    : ( WS | COMMENT ) -> channel(HIDDEN)
    ;
