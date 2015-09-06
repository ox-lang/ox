grammar Oxlang;

oxFile: oxForm *;

oxForm
  : oxLiteral
  | oxList
  | oxVector
  | oxMap
  | oxRecord
  | oxReaderMacro
  ;

oxForms: oxForm * ;

oxList: '(' oxForms ')' ;

oxVector: '[' oxForms ']' ;

oxRecord: '#[' oxNsSymbol oxMap ']' ;

oxMap: '{' (oxForm oxForm)* '}' ;

oxSet: '#{' oxForms '}' ;

oxReaderMacro
    : oxMetaData
    | oxRegex
    | oxHostExpr
    | oxSet
    | oxTag
    | oxDeref
    | oxQuote
    | oxBacktick
    | oxUnquote
    | oxUnquoteSplicing
    | oxGensym
    ;

oxQuote
    : '\'' oxForm
    ;

oxBacktick
    : '`' oxForm
    ;

oxUnquote
    : '~' oxForm
    ;

oxUnquoteSplicing
    : '~@' oxForm
    ;

oxTag
    : '^' (oxSymbol | oxString) oxForm
    ;

oxDeref
    : '@' oxForm
    ;

oxGensym
    : SYMBOL '#'
    ;

oxMetaData
    : '#^' oxMap oxForm
    ;

oxHostExpr
    : '#+' oxForm oxForm
    ;

oxRegex
    : '#' oxString
    ;

oxLiteral
    : oxString
    | oxNumber
    | oxCharacter
    | oxNil
    | oxBoolean
    | oxKeyword
    | oxSymbol
    ;

oxString: STRING;
oxFloat: FLOAT;
oxHex: HEX;
oxBin: BIN;
oxBign: BIGN;
oxLong: LONG;
oxRint: RINT;
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
oxAnyChar: CHAR_ANY ;
oxUHexQuad: CHAR_U ;

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
  : ':' ':' oxSymbol
  ;

oxSymbol
  : oxNsSymbol
  | simpleSym
  ;

simpleSym
  : SYMBOL
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
HEX: '0' [xX] HEXD+ ;
BIN: '0' [bB] [10]+ ;
LONG: '-'? [0-9]+[lL]?;
BIGN: '-'? [0-9]+[nN];
RINT: [1-9][0-9]* 'r' ('0'..'9'|'a'..'z'|'A'..'Z')+;

CHAR_U
    : '\\' 'u'[0-9D-Fd-f] HEXD HEXD HEXD ;
CHAR_NAMED
    : '\\' ( 'newline'
     | 'return'
     | 'space'
     | 'tab'
     | 'formfeed'
     | 'backspace' ) ;
CHAR_ANY
    : '\\' . ;

NIL : 'nil';

BOOLEAN : 'true' | 'false' ;

SYMBOL
    : '.'
    | '/'
    | NAME
    ;

NS_SYMBOL
    : NAME '/' SYMBOL
    ;

// Fragments
//--------------------------------------------------------------------

fragment
NAME: SYMBOL_HEAD SYMBOL_REST* (':' SYMBOL_REST+)* ;

fragment
SYMBOL_HEAD
    : ~('0' .. '9'
       | '〈' | '〉'
       | '⁅' | '⁆'
       | '⌊' | '⌋'
       | '⌈' | '⌉'
       | '⟦' | '⟧'
       | '〈' | '〉'
       | '⟪' | '⟫'
       | '⟬' | '⟭'
       | '⦃' | '⦄'
       | '⦇' | '⦈'
       | '⦉' | '⦊'
       | '⦑' | '⦒'
       | '⧘' | '⧙'
       | '⧚' | '⧛'
       | '⸨' | '⸩'
       | '﹙' | '﹚'
       | '⁽' | '⁾'
       | '₍' | '₎'
       | '(' | ')'
       | '[' | ']'
       | '{' | '}'
       | '^' | '`' | '\'' | '"' | '#' | '~' | '@' | ':' | '/' // FIXME: could be one group
       | [ \n\r\t\,] // FIXME: could be WS
       )
    ;

fragment
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
