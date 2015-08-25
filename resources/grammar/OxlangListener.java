// Generated from Oxlang.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link OxlangParser}.
 */
public interface OxlangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_file}.
	 * @param ctx the parse tree
	 */
	void enterOx_file(OxlangParser.Ox_fileContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_file}.
	 * @param ctx the parse tree
	 */
	void exitOx_file(OxlangParser.Ox_fileContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_form}.
	 * @param ctx the parse tree
	 */
	void enterOx_form(OxlangParser.Ox_formContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_form}.
	 * @param ctx the parse tree
	 */
	void exitOx_form(OxlangParser.Ox_formContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_forms}.
	 * @param ctx the parse tree
	 */
	void enterOx_forms(OxlangParser.Ox_formsContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_forms}.
	 * @param ctx the parse tree
	 */
	void exitOx_forms(OxlangParser.Ox_formsContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_list}.
	 * @param ctx the parse tree
	 */
	void enterOx_list(OxlangParser.Ox_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_list}.
	 * @param ctx the parse tree
	 */
	void exitOx_list(OxlangParser.Ox_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_vector}.
	 * @param ctx the parse tree
	 */
	void enterOx_vector(OxlangParser.Ox_vectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_vector}.
	 * @param ctx the parse tree
	 */
	void exitOx_vector(OxlangParser.Ox_vectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_record}.
	 * @param ctx the parse tree
	 */
	void enterOx_record(OxlangParser.Ox_recordContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_record}.
	 * @param ctx the parse tree
	 */
	void exitOx_record(OxlangParser.Ox_recordContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_map}.
	 * @param ctx the parse tree
	 */
	void enterOx_map(OxlangParser.Ox_mapContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_map}.
	 * @param ctx the parse tree
	 */
	void exitOx_map(OxlangParser.Ox_mapContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_set}.
	 * @param ctx the parse tree
	 */
	void enterOx_set(OxlangParser.Ox_setContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_set}.
	 * @param ctx the parse tree
	 */
	void exitOx_set(OxlangParser.Ox_setContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_reader_macro}.
	 * @param ctx the parse tree
	 */
	void enterOx_reader_macro(OxlangParser.Ox_reader_macroContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_reader_macro}.
	 * @param ctx the parse tree
	 */
	void exitOx_reader_macro(OxlangParser.Ox_reader_macroContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_quote}.
	 * @param ctx the parse tree
	 */
	void enterOx_quote(OxlangParser.Ox_quoteContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_quote}.
	 * @param ctx the parse tree
	 */
	void exitOx_quote(OxlangParser.Ox_quoteContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_backtick}.
	 * @param ctx the parse tree
	 */
	void enterOx_backtick(OxlangParser.Ox_backtickContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_backtick}.
	 * @param ctx the parse tree
	 */
	void exitOx_backtick(OxlangParser.Ox_backtickContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_unquote}.
	 * @param ctx the parse tree
	 */
	void enterOx_unquote(OxlangParser.Ox_unquoteContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_unquote}.
	 * @param ctx the parse tree
	 */
	void exitOx_unquote(OxlangParser.Ox_unquoteContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_unquote_splicing}.
	 * @param ctx the parse tree
	 */
	void enterOx_unquote_splicing(OxlangParser.Ox_unquote_splicingContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_unquote_splicing}.
	 * @param ctx the parse tree
	 */
	void exitOx_unquote_splicing(OxlangParser.Ox_unquote_splicingContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_tag}.
	 * @param ctx the parse tree
	 */
	void enterOx_tag(OxlangParser.Ox_tagContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_tag}.
	 * @param ctx the parse tree
	 */
	void exitOx_tag(OxlangParser.Ox_tagContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_deref}.
	 * @param ctx the parse tree
	 */
	void enterOx_deref(OxlangParser.Ox_derefContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_deref}.
	 * @param ctx the parse tree
	 */
	void exitOx_deref(OxlangParser.Ox_derefContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_gensym}.
	 * @param ctx the parse tree
	 */
	void enterOx_gensym(OxlangParser.Ox_gensymContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_gensym}.
	 * @param ctx the parse tree
	 */
	void exitOx_gensym(OxlangParser.Ox_gensymContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_meta_data}.
	 * @param ctx the parse tree
	 */
	void enterOx_meta_data(OxlangParser.Ox_meta_dataContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_meta_data}.
	 * @param ctx the parse tree
	 */
	void exitOx_meta_data(OxlangParser.Ox_meta_dataContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_host_expr}.
	 * @param ctx the parse tree
	 */
	void enterOx_host_expr(OxlangParser.Ox_host_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_host_expr}.
	 * @param ctx the parse tree
	 */
	void exitOx_host_expr(OxlangParser.Ox_host_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_regex}.
	 * @param ctx the parse tree
	 */
	void enterOx_regex(OxlangParser.Ox_regexContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_regex}.
	 * @param ctx the parse tree
	 */
	void exitOx_regex(OxlangParser.Ox_regexContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_literal}.
	 * @param ctx the parse tree
	 */
	void enterOx_literal(OxlangParser.Ox_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_literal}.
	 * @param ctx the parse tree
	 */
	void exitOx_literal(OxlangParser.Ox_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_string}.
	 * @param ctx the parse tree
	 */
	void enterOx_string(OxlangParser.Ox_stringContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_string}.
	 * @param ctx the parse tree
	 */
	void exitOx_string(OxlangParser.Ox_stringContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_float}.
	 * @param ctx the parse tree
	 */
	void enterOx_float(OxlangParser.Ox_floatContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_float}.
	 * @param ctx the parse tree
	 */
	void exitOx_float(OxlangParser.Ox_floatContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_hex}.
	 * @param ctx the parse tree
	 */
	void enterOx_hex(OxlangParser.Ox_hexContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_hex}.
	 * @param ctx the parse tree
	 */
	void exitOx_hex(OxlangParser.Ox_hexContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_bin}.
	 * @param ctx the parse tree
	 */
	void enterOx_bin(OxlangParser.Ox_binContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_bin}.
	 * @param ctx the parse tree
	 */
	void exitOx_bin(OxlangParser.Ox_binContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_bign}.
	 * @param ctx the parse tree
	 */
	void enterOx_bign(OxlangParser.Ox_bignContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_bign}.
	 * @param ctx the parse tree
	 */
	void exitOx_bign(OxlangParser.Ox_bignContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_long}.
	 * @param ctx the parse tree
	 */
	void enterOx_long(OxlangParser.Ox_longContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_long}.
	 * @param ctx the parse tree
	 */
	void exitOx_long(OxlangParser.Ox_longContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_rint}.
	 * @param ctx the parse tree
	 */
	void enterOx_rint(OxlangParser.Ox_rintContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_rint}.
	 * @param ctx the parse tree
	 */
	void exitOx_rint(OxlangParser.Ox_rintContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_number}.
	 * @param ctx the parse tree
	 */
	void enterOx_number(OxlangParser.Ox_numberContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_number}.
	 * @param ctx the parse tree
	 */
	void exitOx_number(OxlangParser.Ox_numberContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_character}.
	 * @param ctx the parse tree
	 */
	void enterOx_character(OxlangParser.Ox_characterContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_character}.
	 * @param ctx the parse tree
	 */
	void exitOx_character(OxlangParser.Ox_characterContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_named_char}.
	 * @param ctx the parse tree
	 */
	void enterOx_named_char(OxlangParser.Ox_named_charContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_named_char}.
	 * @param ctx the parse tree
	 */
	void exitOx_named_char(OxlangParser.Ox_named_charContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_any_char}.
	 * @param ctx the parse tree
	 */
	void enterOx_any_char(OxlangParser.Ox_any_charContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_any_char}.
	 * @param ctx the parse tree
	 */
	void exitOx_any_char(OxlangParser.Ox_any_charContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_u_hex_quad}.
	 * @param ctx the parse tree
	 */
	void enterOx_u_hex_quad(OxlangParser.Ox_u_hex_quadContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_u_hex_quad}.
	 * @param ctx the parse tree
	 */
	void exitOx_u_hex_quad(OxlangParser.Ox_u_hex_quadContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_nil}.
	 * @param ctx the parse tree
	 */
	void enterOx_nil(OxlangParser.Ox_nilContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_nil}.
	 * @param ctx the parse tree
	 */
	void exitOx_nil(OxlangParser.Ox_nilContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_boolean}.
	 * @param ctx the parse tree
	 */
	void enterOx_boolean(OxlangParser.Ox_booleanContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_boolean}.
	 * @param ctx the parse tree
	 */
	void exitOx_boolean(OxlangParser.Ox_booleanContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_keyword}.
	 * @param ctx the parse tree
	 */
	void enterOx_keyword(OxlangParser.Ox_keywordContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_keyword}.
	 * @param ctx the parse tree
	 */
	void exitOx_keyword(OxlangParser.Ox_keywordContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_simple_keyword}.
	 * @param ctx the parse tree
	 */
	void enterOx_simple_keyword(OxlangParser.Ox_simple_keywordContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_simple_keyword}.
	 * @param ctx the parse tree
	 */
	void exitOx_simple_keyword(OxlangParser.Ox_simple_keywordContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_macro_keyword}.
	 * @param ctx the parse tree
	 */
	void enterOx_macro_keyword(OxlangParser.Ox_macro_keywordContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_macro_keyword}.
	 * @param ctx the parse tree
	 */
	void exitOx_macro_keyword(OxlangParser.Ox_macro_keywordContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_symbol}.
	 * @param ctx the parse tree
	 */
	void enterOx_symbol(OxlangParser.Ox_symbolContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_symbol}.
	 * @param ctx the parse tree
	 */
	void exitOx_symbol(OxlangParser.Ox_symbolContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#simple_sym}.
	 * @param ctx the parse tree
	 */
	void enterSimple_sym(OxlangParser.Simple_symContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#simple_sym}.
	 * @param ctx the parse tree
	 */
	void exitSimple_sym(OxlangParser.Simple_symContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#ox_ns_symbol}.
	 * @param ctx the parse tree
	 */
	void enterOx_ns_symbol(OxlangParser.Ox_ns_symbolContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#ox_ns_symbol}.
	 * @param ctx the parse tree
	 */
	void exitOx_ns_symbol(OxlangParser.Ox_ns_symbolContext ctx);
}