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

mapping
  : '{' pair* '}'
  ;

pair
  : sexpr sexpr
  ;

metaexpr
  : META (SYMBOL | list) sexpr
  ;

quote
  : QUOTE sexpr
  ;

sexpr
  : quote
  | metaexpr
  | atom
  | list
  | vector
  | mapping
  ;

atom
  : STRING
  | NUMBER
  | SYMBOL
  ;

STRING
  : '"' ('\\' . | ~ ('\\' | '"'))* '"'
  ;

NUMBER
  : ('+' | '-')? DIGIT+ ('.' DIGIT +)?
  ;

SYMBOL
  : '/'
  | SYMBOL_START (SYMBOL_START | DIGIT | '/')*
  ;

SYMBOL_START
  : ~('0' .. '9' | '/' | '^' | '(' | ')' | '[' | ']' | '{' | '}')
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
