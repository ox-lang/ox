grammar Oxlang;

file
    : form *
    ;

form: reader_macro
    | literal
    | list
    | vector
    | map
    ;

reader_macro
    : lambda
    | tag_map
    | symbol
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
    | keyword
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
fragment STR
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

CHARACTER
    : '\\' ( 'newline'
        | 'space'
        | 'tab'
        | 'formfeed'
        | 'backspace'
        | 'return'
        | 'u' [a-dA-D]?[a-fA-F]?[a-fA-F]?[a-fA-F]?
        | . )
    ;

fragment NIL
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

// FIXME: fully qualified symbols not actually supported
gensym
    : NAME '#'
    ;

symbol
    : NAME '/' NAME
    | gensym
    | sym
    ;

keyword
    : ':' symbol
    ;

sym
    : '.'
    | '/'
    | NAME
    ;

NAME
    : SYMBOL_HEAD SYMBOL_REST * ( ':' SYMBOL_REST+ ) *
    ;

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

WS
    : [ \n\r\t\,] -> channel(HIDDEN);

COMMENT
    : ';' ~[\r\n]* -> channel(HIDDEN);
