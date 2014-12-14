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
    : tag_map
    | quote
    | backtick
    | unquote
    | unquote_splicing
    | deref
    | tag
    // | discard - FIXME: discard is hard, not sure it's a good idea
    | dispatch
    ;

literal
    : string
    | regex
    | set
    | nil
    | lit_symbol
    | macro_keyword
    | lit_keyword
    | n_IntegerLiteral
    | n_FloatingPointLiteral
    | character
    | boolean
    | KEYWORD
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

discard
    : '#_' form
    ;

dispatch
    : '#' lit_symbol form
    ;

// From the JDK8 spec
// §3.10.1 Integer Literals
n_IntegerLiteral
    : n_DecimalIntegerLiteral
    | n_HexIntegerLiteral
    | n_OctalIntegerLiteral
    | n_BinaryIntegerLiteral
    ;

n_DecimalIntegerLiteral
    : DecimalIntegerLiteral;

n_HexIntegerLiteral
    : HexIntegerLiteral;

n_OctalIntegerLiteral
    : OctalIntegerLiteral;

n_BinaryIntegerLiteral
    : BinaryIntegerLiteral;

DecimalIntegerLiteral
    : DecimalNumeral IntegerTypeSuffix?
    ;

HexIntegerLiteral
    : HexNumeral IntegerTypeSuffix?
    ;

OctalIntegerLiteral
    : OctalNumeral IntegerTypeSuffix?
    ;

BinaryIntegerLiteral
    : BinaryNumeral IntegerTypeSuffix?
    ;

fragment
IntegerTypeSuffix
    : [lL]
    ;

fragment
DecimalNumeral
    : '0'
    | NonZeroDigit Digits?
    ;

fragment
Digits
    : Digit+
    ;

fragment
Digit
    : '0'
    | NonZeroDigit
    ;

fragment
NonZeroDigit
    : [1-9]
    ;

fragment
HexNumeral
    : '0' [xX] HexDigits
    ;

fragment
HexDigits
    : HexDigit+
    ;

fragment
HexDigit
    : [0-9a-fA-F]
    ;

fragment
OctalNumeral
    : '0' OctalDigits
    ;

fragment
OctalDigits
    : OctalDigit+
    ;

fragment
OctalDigit
    : [0-7]
    ;

fragment
BinaryNumeral
    : '0' [bB] BinaryDigit BinaryDigits
    ;

fragment
BinaryDigits
    : BinaryDigit*
    ;

fragment
BinaryDigit
    : [01]
    ;

// §3.10.2 Floating-Point Literals
n_FloatingPointLiteral
    : n_DecimalFloatingPointLiteral
    | n_HexadecimalFloatingPointLiteral
    ;

nan: NaN;
NaN: Sign? 'NaN';

inf: Inf;
Inf: Sign? 'Infinity';

n_DecimalFloatingPointLiteral
    : DecimalFloatingPointLiteral
    | nan
    | inf
    ;

n_HexadecimalFloatingPointLiteral
    : HexadecimalFloatingPointLiteral ;

DecimalFloatingPointLiteral
    : Digits '.' Digits? ExponentPart? FloatTypeSuffix?
    | '.' Digits ExponentPart? FloatTypeSuffix?
    | Digits ExponentPart FloatTypeSuffix?
    | Digits FloatTypeSuffix
    ;

fragment
ExponentPart
    : ExponentIndicator SignedInteger
    ;

fragment
ExponentIndicator
    : [eE]
    ;

fragment
SignedInteger
    : Sign? Digits
    ;

fragment
Sign
    : [+-]
    ;

fragment
FloatTypeSuffix
    : [fFdD]
    ;

HexadecimalFloatingPointLiteral
    : HexSignificand BinaryExponent FloatTypeSuffix?
    ;

fragment
HexSignificand
    : HexNumeral '.'?
    | '0' [xX] HexDigits? '.' HexDigits
    ;

fragment
BinaryExponent
    : BinaryExponentIndicator SignedInteger
    ;

fragment
BinaryExponentIndicator
    : [pP]
    ;

named_char
    : '\\' NAMED_CHAR ;

any_char
    : '\\.' ;

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

u_hex_quad
    : U_HEX_QUAD ;

character
    : named_char
    | u_hex_quad
    | any_char
    ;

NIL
    : 'nil'
    ;

nil
    : NIL
    ;

boolean
    : BOOLEAN
    ;

BOOLEAN
    : 'true'
    | 'false'
    ;

lit_symbol
    : ns_symbol
    | raw_symbol
    ;

raw_symbol
    : special_sym
    | gen_sym
    | simple_sym
    ;

ns_symbol
    : simple_sym '/' ('.' | '/' | simple_sym)
    ;


special_sym
    : SPECIAL_SYM ;

SPECIAL_SYM
    : '.' | '/'
    ;

gen_sym
    : GEN_SYMBOL ;

simple_sym
    : ANAME ;

ANAME
    : NAME ;

GEN_SYMBOL
    : ANAME '#'
    ;

fragment
NAME: SYMBOL_HEAD SYMBOL_REST* (':' SYMBOL_REST+)* ;

fragment
SYMBOL_HEAD
    : ~('0'..'9'
        | ':'
        | '/'
        | '#'
        | '\\'
        | '('
        | ')'
        | '['
        | ']'
        | '{'
        | '}'
        | '\n'
        | '\r'
        | '\t'
        | ' '
        | ','
        | '%'
        | '~'
        | '@'
        | '\''
        | '^'
        )
    ;

fragment
SYMBOL_REST
    : SYMBOL_HEAD
    | '0'..'9'
    ;

lit_keyword
    : KEYWORD lit_symbol
    ;

macro_keyword
    : KEYWORD KEYWORD lit_symbol
    ;

KEYWORD
    : ':'
    ;

WS
    : [ \n\r\t\,] -> channel(HIDDEN);

COMMENT
    : ';' ~[\r\n]* -> channel(HIDDEN);
