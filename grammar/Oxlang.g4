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
    | n_IntegerLiteral
    | n_FloatingPointLiteral
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
    | NonZeroDigit (Digits? | Underscores Digits)
    ;

fragment
Digits
    : Digit (DigitsAndUnderscores? Digit)?
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
DigitsAndUnderscores
    : DigitOrUnderscore+
    ;

fragment
DigitOrUnderscore
    : Digit
    | '_'
    ;

fragment
Underscores
    : '_'+
    ;

fragment
HexNumeral
    : '0' [xX] HexDigits
    ;

fragment
HexDigits
    : HexDigit (HexDigitsAndUnderscores? HexDigit)?
    ;

fragment
HexDigit
    : [0-9a-fA-F]
    ;

fragment
HexDigitsAndUnderscores
    : HexDigitOrUnderscore+
    ;

fragment
HexDigitOrUnderscore
    : HexDigit
    | '_'
    ;

fragment
OctalNumeral
    : '0' Underscores? OctalDigits
    ;

fragment
OctalDigits
    : OctalDigit (OctalDigitsAndUnderscores? OctalDigit)?
    ;

fragment
OctalDigit
    : [0-7]
    ;

fragment
OctalDigitsAndUnderscores
    : OctalDigitOrUnderscore+
    ;

fragment
OctalDigitOrUnderscore
    : OctalDigit
    | '_'
    ;

fragment
BinaryNumeral
    : '0' [bB] BinaryDigits
    ;

fragment
BinaryDigits
    : BinaryDigit (BinaryDigitsAndUnderscores? BinaryDigit)?
    ;

fragment
BinaryDigit
    : [01]
    ;

fragment
BinaryDigitsAndUnderscores
    : BinaryDigitOrUnderscore+
    ;

fragment
BinaryDigitOrUnderscore
    : BinaryDigit
    | '_'
    ;

// §3.10.2 Floating-Point Literals
n_FloatingPointLiteral
    : n_DecimalFloatingPointLiteral
    | n_HexadecimalFloatingPointLiteral
    ;

n_DecimalFloatingPointLiteral
    : DecimalFloatingPointLiteral ;

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
