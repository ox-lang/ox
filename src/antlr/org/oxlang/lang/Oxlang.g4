grammar Oxlang;

file
  : sexpr* EOF
  ;

list
  : '(' sexpr* ')'
  ;

vector
  : '[' sexpr* ']'
  ;

set
  : '#{' sexpr* '}'
  ;

mapping
  : '{' pair* '}'
  ;

pair
  : sexpr sexpr
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
  | vector
  | mapping
  | set
  ;

atom
  : string
  | number
  | symbol
  ;

string: STRING;
STRING
  : '"' ('\\' . | ~ ('\\' | '"'))* '"'
  ;

number
  : FLOAT
  | INTEGER
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

symbol: SYMBOL;
SYMBOL
  : '/'
  | SYMBOL_START (SYMBOL_START | DIGIT | '/')*
  ;

SYMBOL_START
  : ~(// Not numbers
      '0' .. '9'
      // Not whitespace
      | ' ' | ',' | '\n' | '\t' | '\r'
      // Not punctuation
      | '/' | '^' | '(' | ')' | '[' | ']' | '{' | '}' | '#'
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
