grammar Oxlang;

sexpr
   : item* EOF
   ;

item
   : atom
   | list
   | LPAREN item DOT item RPAREN
   ;

list
   : LPAREN item* RPAREN
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
   : (' ' | '\n' | '\t' | '\r') + -> skip
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


DOT
   : '.'
   ;


fragment SYMBOL_START
   : ('a' .. 'z') | ('A' .. 'Z') | '+' | '-' | '*' | '.'
   ;


fragment DIGIT
   : ('0' .. '9')
   ;
