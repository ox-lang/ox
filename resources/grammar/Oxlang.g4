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

ox_file: ox_form *;

ox_form
  : ox_literal
  | ox_list
  | ox_vector
  | ox_map
  | ox_record
  | ox_reader_macro
  ;

ox_forms: ox_form * ;

ox_list: '(' ox_forms ')' ;

ox_vector: '[' ox_forms ']' ;

ox_record: '#[' ox_ns_symbol ox_map ']' ;

ox_map: '{' (ox_form ox_form)* '}' ;

ox_set: '#{' ox_forms '}' ;

ox_reader_macro
    : ox_meta_data
    | ox_regex
    | ox_host_expr
    | ox_set
    | ox_tag
    | ox_deref
    | ox_quote
    | ox_backtick
    | ox_unquote
    | ox_unquote_splicing
    | ox_gensym
    ;

ox_quote
    : '\'' ox_form
    ;

ox_backtick
    : '`' ox_form
    ;

ox_unquote
    : '~' ox_form
    ;

ox_unquote_splicing
    : '~@' ox_form
    ;

ox_tag
    : '^' (ox_symbol | ox_string) ox_form
    ;

ox_deref
    : '@' ox_form
    ;

ox_gensym
    : SYMBOL '#'
    ;

ox_meta_data
    : '#^' ox_map ox_form
    ;

ox_host_expr
    : '#+' ox_form ox_form
    ;

ox_regex
    : '#' ox_string
    ;

ox_literal
    : ox_string
    | ox_number
    | ox_character
    | ox_nil
    | ox_boolean
    | ox_keyword
    | ox_symbol
    ;

ox_string: STRING;
ox_float: FLOAT;
ox_hex: HEX;
ox_bin: BIN;
ox_bign: BIGN;
ox_long: LONG;
ox_rint: RINT;
ox_number
    : ox_float
    | ox_hex
    | ox_bin
    | ox_bign
    | ox_long
    | ox_rint
    ;

ox_character
    : ox_named_char
    | ox_u_hex_quad
    | ox_any_char
    ;
ox_named_char: CHAR_NAMED ;
ox_any_char: CHAR_ANY ;
ox_u_hex_quad: CHAR_U ;

ox_nil: NIL;
ox_boolean: BOOLEAN;

ox_keyword
  : ox_macro_keyword
  | ox_simple_keyword
  ;

ox_simple_keyword
  : ':' ox_symbol
  ;

ox_macro_keyword
  : ':' ':' ox_symbol
  ;

ox_symbol
  : ox_ns_symbol
  | simple_sym
  ;

simple_sym
  : SYMBOL
  ;

ox_ns_symbol
  : NS_SYMBOL
  ;

// Lexers
//--------------------------------------------------------------------

// Java §3.10.5 String Literals
STRING
    : '"' StringCharacters? '"'
    ;
fragment
StringCharacters
    : StringCharacter+
    ;
fragment
StringCharacter
    : ~[""\\]
    | EscapeSequence
    ;

// Java §3.10.6 Escape Sequences for Character and String Literals
fragment
EscapeSequence
    : '\\' [btnfr"'\\]
    | CHAR_U
    | CHAR_NAMED
    ;

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
fragment
HEXD: [0-9a-fA-F] ;
HEX: '0' [xX] HEXD+ ;
BIN: '0' [bB] [10]+ ;
LONG: '-'? [0-9]+[lL]?;
BIGN: '-'? [0-9]+[nN];
RINT: [1-9][0-9]* 'r' ('0'..'9'|'a'..'z'|'A'..'Z')+;

CHAR_U
    : '\\' 'u'[0-9D-Fd-f] HEXD HEXD HEXD ;
CHAR_NAMED
    : '\\' ( 'newline'
     | 'return'
     | 'space'
     | 'tab'
     | 'formfeed'
     | 'backspace' ) ;
CHAR_ANY
    : '\\' . ;

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

// Fragments
//--------------------------------------------------------------------

fragment
NAME: SYMBOL_HEAD SYMBOL_REST* (':' SYMBOL_REST+)* ;

fragment
SYMBOL_HEAD
    : ~('0' .. '9'
       | '〈' | '〉'
       | '⁅' | '⁆'
       | '⌊' | '⌋'
       | '⌈' | '⌉'
       | '⟦' | '⟧'
       | '〈' | '〉'
       | '⟪' | '⟫'
       | '⟬' | '⟭'
       | '⦃' | '⦄'
       | '⦇' | '⦈'
       | '⦉' | '⦊'
       | '⦑' | '⦒'
       | '⧘' | '⧙'
       | '⧚' | '⧛'
       | '⸨' | '⸩'
       | '﹙' | '﹚'
       | '⁽' | '⁾'
       | '₍' | '₎'
       | '(' | ')'
       | '[' | ']'
       | '{' | '}'
       | '^' | '`' | '\'' | '"' | '#' | '~' | '@' | ':' | '/' // FIXME: could be one group
       | [ \n\r\t\,] // FIXME: could be WS
       )
    ;

fragment
SYMBOL_REST
    : SYMBOL_HEAD
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
