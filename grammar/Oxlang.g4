/* Reworked for grammar specificity by Reid Mckenzie. Did a bunch of
   work so that rather than reading "a bunch of crap in parens" some
   syntactic information is preserved and recovered. Dec. 14 2014.

   Converted to ANTLR 4 by Terence Parr. Unsure of provence. I see
   it commited by matthias.koester for clojure-eclipse project on
   Oct 5, 2009:

   https://code.google.com/p/clojure-eclipse/

   Seems to me Laurent Petit had a version of this. I also see
   Jingguo Yao submitting a link to a now-dead github project on
   Jan 1, 2011.

   https://github.com/laurentpetit/ccw/tree/master/clojure-antlr-grammar

   Regardless, there are some issues perhaps related to "sugar";
   I've tried to fix them.

   This parses https://github.com/weavejester/compojure project.

   I also note this is hardly a grammar; more like "match a bunch of
   crap in parens" but I guess that is LISP for you ;)
 */

grammar Oxlang;

file: form*;

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

set: '#{' form* '}' ;

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
    | set
    | SYMBOL '#' // TJP added (auto-gensym)
    ;

literal
    : string
    | number
    | character
    | nil
    | boolean
    | keyword
    | symbol
    | param_name
    ;

string: STRING;
float: FLOAT;
hex: HEX;
bin: BIN;
bign: BIGN;
long: LONG;
number
    : float
    | hex
    | bin
    | bign
    | long
    ;

character: CHARACTER;
nil: NIL;
boolean: BOOLEAN;

keyword: macro_keyword | simple_keyword;
simple_keyword: ':' symbol;
macro_keyword: ':' ':' symbol;

symbol: ns_symbol | simple_sym;
simple_sym: SYMBOL;
ns_symbol: NS_SYMBOL;

param_name: PARAM_NAME;

// Lexers
//--------------------------------------------------------------------

STRING : '"' ( ~'"' | '\\' '"' )* '"' ;

// FIXME: Doesn't deal with arbitrary read radixes, BigNums
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

HEX: '0' [xX] [0-9a-fA-F]+ ;
BIN: '0' [bB] [10]+ ;
LONG: '-'? [0-9]+[lL]?;
BIGN: '-'? [0-9]+[nN];

CHARACTER : '\\' . ;

NIL : 'nil';

BOOLEAN : 'true' | 'false' ;

SYMBOL
    : '.'
    | '/'
    | NAME
    ;

NS_SYMBOL
    : NAME '/' SYMBOL
    ;

PARAM_NAME: '%' (('1'..'9')('0'..'9')*)? ;

// Fragments
//--------------------------------------------------------------------

fragment
NAME: SYMBOL_HEAD SYMBOL_REST* (':' SYMBOL_REST+)* ;

fragment
SYMBOL_HEAD
    : ~('0' .. '9'
        | ':' | '/' | '%' | '(' | ')' | '[' | ']' | '{' | '}' // FIXME: could be one group
        | [ \n\r\t\,] // FIXME: could be WS
        )
    ;

fragment
SYMBOL_REST
    : SYMBOL_HEAD
    | '&' // apparently this is legal in an ID: "(defn- assoc-&-binding ..." TJP
    | '0'..'9'
    | '.'
    ;

// Discard
//--------------------------------------------------------------------

fragment
WS : [ \n\r\t\,] ;

fragment
COMMENT: ';' ~[\r\n]* ;

TRASH
    : ( WS | COMMENT ) -> channel(HIDDEN)
    ;
