grammar Oxlang;

file
    : form *
    ;

form: literal
    | list
    | vector
    | map
    | reader_macro 
    ;

reader_macro
    : lambda
    | tag_map
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
    | lit_symbol
    | lit_keyword
    | NUMBER
    | character
    | BOOLEAN
    | KEYWORD
    | PARAM_NAME
    ;

quote
    : '\'' form
    ;

backtick
    : '`' form
    ;

unquote
    : '~' form
    ;

unquote_splicing
    : '~@' form
    ;

deref
    : '@' form
    ;

tag
    : '^' form form
    ;

list
    : '(' form* ')'
    ;

vector
    : '[' form* ']'
    ;

map
    : '{' (form form)* '}'
    ;

set
    : '#{' form* '}'
    ;

lambda
    : '#(' form* ')'
    ;

tag_map
    : '#^' map form
    ;

// FIXME: not complete, escape characters?
STR
    : '"' ( ~'"' | '\\' '"' )* '"'
    ;

regex
    : '#' STR
    ;

string
    : STR
    ;

NUMBER
    : '-'? [0-9]+ ('.' [0-9]+)? ([eE] '-'? [0-9]+)?
    ;

NAMED_CHAR
    : 'newline'
    | 'return'
    | 'space'
    | 'tab'
    | 'formfeed'
    | 'backspace'
    ;

U_HEX_QUAD
    : '\\u'
        [a-dA-D]?
        [a-fA-F]
        [a-fA-F]?
        [a-fA-F]?
    ;

character
    : '\\' NAMED_CHAR
    | U_HEX_QUAD
    | '\\' .
    ;

NIL
    : 'nil'
    ;

nil
    : NIL
    ;

BOOLEAN
    : 'true'
    | 'false'
    ;

PARAM_NAME
    : '%' (('1'..'9')('0'..'9')*)?
    ;

lit_symbol
    : SYMBOL
    ;

SYMBOL
    : '.'
    | '/'
    | NAME ('/' NAME)? '#'?
    ;

fragment
NAME: SYMBOL_HEAD SYMBOL_REST* ;

fragment
SYMBOL_HEAD
    : ~('0'..'9' | ':' | '/')
    ;
    
fragment
SYMBOL_REST
    : SYMBOL_HEAD
    | '0'..'9'
    | ':'~':'?
    ;   

lit_keyword
    : KEYWORD
    ;

KEYWORD
    : ':' ':'? SYMBOL
    ;

WS
    : [ \n\r\t\,] -> channel(HIDDEN);

COMMENT
    : ';' ~[\r\n]* -> channel(HIDDEN);
