grammar Oxlang;

file: list*;
  
form: literal
    | list 
    | vector 
    | map 
    | reader_macro
    | '#\'' SYMBOL // TJP added (get Var object instead of the value of a symbol)
    ;
  
list: '(' form* ')' ;
  
vector: '[' form* ']' ;
  
map: '{' (form form)* '}' ;

// TJP added '&' (gather a variable number of arguments)
special_form: ('\'' | '`' | '~' | '~@' | '^' | '@' | '&') form ;
  
lambda: '#(' form* ')' ;
  
meta_data: '#^' map form ;
  
var_quote: '\'' '#' SYMBOL ;
  
regex: '#' STRING  ;
  
reader_macro
    : lambda 
    | meta_data 
    | special_form 
    | regex 
    | var_quote
    | SYMBOL '#' // TJP added (auto-gensym)
    ; 
    
literal
    : STRING
    | NUMBER 
    | CHARACTER
    | NIL
    | BOOLEAN
    | KEYWORD
    | SYMBOL
    | PARAM_NAME
    ;   
  
STRING : '"' ( ~'"' | '\\' '"' )* '"' ;
  
NUMBER : '-'? [0-9]+ ('.' [0-9]+)? ([eE] '-'? [0-9]+)? ;

CHARACTER : '\\' . ;

NIL : 'nil';
  
BOOLEAN : 'true' | 'false' ;

KEYWORD : ':' SYMBOL ;

SYMBOL: '.' | '/' | NAME ('/' NAME)? ;

PARAM_NAME: '%' (('1'..'9')('0'..'9')*)? ;
  
fragment
NAME: SYMBOL_HEAD SYMBOL_REST* (':' SYMBOL_REST+)* ;

fragment
SYMBOL_HEAD
    :   'a'..'z' | 'A'..'Z' | '*' | '+' | '!' | '-' | '_' | '?' | '>' | '<' | '=' | '$'
    ;
    
fragment
SYMBOL_REST
    : SYMBOL_HEAD
    | '&' // apparently this is legal in an ID: "(defn- assoc-&-binding ..." TJP
    | '0'..'9'
    | '.'
    ;   

WS : [ \n\r\t\,] -> channel(HIDDEN) ;

COMMENT : ';' ~[\r\n]* -> channel(HIDDEN) ;
