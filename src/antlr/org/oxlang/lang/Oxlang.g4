grammar Oxlang;

file
  : sexpr* eof
  ;

eof
  : EOF
  ;

list
  : '(' data=sexpr* ')'
  ;

sqlist
  : '[' data=sexpr* ']'
  ;

mapping
  : '{' pair* '}'
  ;

pair
  : key=sexpr value=sexpr
  ;

set
  : '#{' sexpr* '}'
  ;

tagexpr
  : META (symbol | list) sexpr
  ;

quote
  : QUOTE sexpr
  ;

sexpr
  : quote
  | tagexpr
  | atom
  | list
  | sqlist
  | mapping
  | set
  ;

atom
  : string
  | number
  | symbol
  | keyword
  ;

string: STRING;
STRING
  : '"' ('\\' . | ~ ('\\' | '"'))* '"'
  ;

number
  : floating
  | integer
  ;

floating
  : FLOAT
  ;

integer
  : INTEGER
  ;

FLOAT
  : SIGN? INTEGER ('.' INTEGER)
  | SIGN? INTEGER ('e' SIGN? INTEGER)
  | SIGN? INTEGER ('.' INTEGER) ('e' SIGN? INTEGER)
  ;

INTEGER
  : SIGN? (DIGIT ('_'? DIGIT)*)
  ;

fragment SIGN
  : '+' | '-'
  ;

keyword: KEYWORD;
KEYWORD
  : ':' SYMBOL
  ;

symbol: SYMBOL;
SYMBOL
  : '/'
  | SIMPLE_SYMBOL
  | SIMPLE_SYMBOL '/' ('/' | SIMPLE_SYMBOL)
  ;

SIMPLE_SYMBOL
  : SYMBOL_START (SYMBOL_START | DIGIT)*
  ;

SYMBOL_START
  : ~(// Not numbers
      '0' .. '9'
      // Not whitespace
      | ' ' | ',' | '\n' | '\t' | '\r'
      // Not a backslash
      | '\\'
      // Not punctuation
      | '/' | '^' | '(' | ')' | '[' | ']' | '{' | '}' | '#' | ';' | ':'
      )
  ;

DIGIT: '0' .. '9';

META: '^';

QUOTE: '\'';

// For now, comments are discarded.
//
// This is less than ideal, but is sufficient for a bootstrap reader.
COMMENT
  : ';' . *? '\n' -> skip
  ;

// For now, whitespace is discarded.
//
// This is less than ideal, but is sufficient for a bootstrap reader.
WHITESPACE
  : (' ' | ',' | '\n' | '\t' | '\r')+ -> skip
  ;
