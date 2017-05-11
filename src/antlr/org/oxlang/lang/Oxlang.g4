grammar Oxlang;

file
  : sexpr* EOF
  ;

list
  : LPAREN sexpr* RPAREN
  ;

vector
  : LBRACKET sexpr* RBRACKET
  ;

mapping
  : LBRACE pair* RBRACE
  ;

pair
  : sexpr sexpr
  ;

sexpr
  : atom
  | list
  | vector
  | mapping
  ;

atom
  : STRING
  | KEYWORD
  | SYMBOL
  | NUMBER
  | DOT
  ;

STRING
  : '"' ('\\' . | ~ ('\\' | '"'))* '"'
  ;

WHITESPACE
  : (' ' | ',' | '\n' | '\t' | '\r') + -> skip
  ;

NUMBER
  : ('+' | '-')? (DIGIT) + ('.' (DIGIT) +)?
  ;

SYMBOL
  : SYMBOL_START (SYMBOL_START | DIGIT)*
  ;

KEYWORD
  : ':' SYMBOL
  ;

LPAREN
  : '('
  ;

RPAREN
  : ')'
  ;

LBRACKET
  : '['
  ;

RBRACKET
  : ']'
  ;

fragment SYMBOL_START
  : ('a' .. 'z') | ('A' .. 'Z') | '+' | '-' | '*' | '.'
  ;

fragment DIGIT
  : ('0' .. '9')
  ;
