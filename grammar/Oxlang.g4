grammar Oxlang;

file: form*;

form: reader_macro
    | literal
    | list
    | vector
    | map
    ;

reader_macro
    : lambda
    | tag_map
    | gensym
    | quote
    | backtick
    | unquote
    | unquote_splicing
    | deref
    | tag
    ;

literal
    : string
    | regex
    | set
    | nil
    | NUMBER
    | CHARACTER
    | BOOLEAN
    | KEYWORD
    | SYMBOL
    | PARAM_NAME
    ;

quote: '\'' SYMBOL ;

backtick: '`' SYMBOL ;

unquote: '~' SYMBOL ;

unquote_splicing: '~@' SYMBOL ;

deref: '@' form ;

tag: '^' form form ;

// FIXME: fully qualified symbols not actually supported
gensym: SYMBOL '#' ;

list: '(' form* ')' ;

vector: '[' form* ']' ;

map: '{' (form form)* '}' ;

set: '#{' form* '}' ;

lambda: '#(' form* ')' ;

tag_map: '#^' map form ;

// FIXME: not complete, escape characters?
regex: '#"' ( ~'"' | '\\' '"' )* '"' ;

string: '"' ( ~'"' | '\\' '"' )* '"' ;

NUMBER : '-'? [0-9]+ ('.' [0-9]+)? ([eE] '-'? [0-9]+)? ;

CHARACTER : '\\' . ;

fragment NIL : 'nil';
nil : NIL ;

BOOLEAN : 'true' | 'false' ;

KEYWORD : ':' SYMBOL ;

SYMBOL: '.' | '/' | NAME ('/' NAME)? ;

PARAM_NAME: '%' (('1'..'9')('0'..'9')*)? ;

fragment NAME
    : SYMBOL_HEAD SYMBOL_REST* (':' SYMBOL_REST+)* ;

fragment SYMBOL_HEAD
    : 'a'..'z'
    | 'A'..'Z'
    | '*'
    | '+'
    | '!'
    | '-'
    | '_'
    | '?'
    | '>'
    | '<'
    | '='
    | '$'
    | '&'
    ;

fragment SYMBOL_REST
    : SYMBOL_HEAD
    | '0'..'9'
    | '.'
    ;

WS : [ \n\r\t\,] -> channel(HIDDEN) ;

COMMENT : ';' ~[\r\n]* -> channel(HIDDEN) ;
