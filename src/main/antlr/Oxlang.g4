grammar Oxlang;

@header {
    package org.oxlang.parser;
}

ox_file
    : ox_form*
    ;

ox_form
    : ox_reader_macro
    | ox_literal
    | ox_list
    | ox_vector
    | ox_map
    | ox_set
    ;

ox_reader_macro
    : ox_meta_data
    | ox_reader_cond_splicing
    | ox_reader_cond
    | ox_discard
    | ox_dispatch
    | ox_quote
    ;

ox_list
    : '(' ox_form* ')'
    ;

ox_vector
    : '[' ox_form* ']'
    ;

ox_map
    : '{' (ox_form ox_form)* '}'
    ;

ox_set
    : '#{' ox_form* '}'
    ;

ox_literal
    : ox_keyword
    | ox_number
    | ox_symbol
    ;

ox_meta_data
    : '^' ox_form ox_form
    ;

ox_dispatch
    : ox_symbol ox_form
    ;

ox_discard
    : '#_' ox_form
    ;

ox_quote
    : '\'' ox_form
    ;

ox_reader_cond_splicing
    : '#?@' '(' (ox_keyword ox_form) * ')'
    ;

ox_reader_cond
    : '#?' '(' (ox_keyword ox_form) * ')'
    ;

ox_keyword
    : '::' NAMESPACE '/' NAME
    | '::' NAME
    | ':' NAMESPACE '/' NAME
    | ':' NAME
    ;

ox_symbol
    : NAMESPACE '/' NAME
    | NAME
    ;

ox_float
    : FLOAT
    ;

ox_hex
    : HEX
    ;

ox_bin
    : BIN
    ;

ox_bign
    : BIGN
    ;

ox_long
    : LONG
    ;

ox_number
    : ox_float
    | ox_hex
    | ox_bin
    | ox_bign
    | ox_long
    ;

//----------------------------------------------------------------------------------------------------------------------

NAME
    : [^\^;:#"'\[\]\(\)\{\}\d\s][^;:#"'\[\]\(\)\{\}\s]+
    ;

NAMESPACE
    : (([^\^;:#\"\'\[\]\(\)\{\}\d\s/][^;:#\"\'\[\]\(\)\{\}\s/]+)'.'?)+
    ;

NIL
    : 'nil'
    ;

BOOLEAN
    : 'true'
    | 'false'
    ;

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

fragment
WS : [ \n\r\t\,] ;

fragment
COMMENT: ';' ~[\r\n]* ;

TRASH
    : ( WS | COMMENT ) -> channel(HIDDEN)
    ;