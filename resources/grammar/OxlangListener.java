// Generated from Oxlang.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link OxlangParser}.
 */
public interface OxlangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxFile}.
	 * @param ctx the parse tree
	 */
	void enterOxFile(OxlangParser.OxFileContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxFile}.
	 * @param ctx the parse tree
	 */
	void exitOxFile(OxlangParser.OxFileContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxForm}.
	 * @param ctx the parse tree
	 */
	void enterOxForm(OxlangParser.OxFormContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxForm}.
	 * @param ctx the parse tree
	 */
	void exitOxForm(OxlangParser.OxFormContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxForms}.
	 * @param ctx the parse tree
	 */
	void enterOxForms(OxlangParser.OxFormsContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxForms}.
	 * @param ctx the parse tree
	 */
	void exitOxForms(OxlangParser.OxFormsContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxList}.
	 * @param ctx the parse tree
	 */
	void enterOxList(OxlangParser.OxListContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxList}.
	 * @param ctx the parse tree
	 */
	void exitOxList(OxlangParser.OxListContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxVector}.
	 * @param ctx the parse tree
	 */
	void enterOxVector(OxlangParser.OxVectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxVector}.
	 * @param ctx the parse tree
	 */
	void exitOxVector(OxlangParser.OxVectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxRecord}.
	 * @param ctx the parse tree
	 */
	void enterOxRecord(OxlangParser.OxRecordContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxRecord}.
	 * @param ctx the parse tree
	 */
	void exitOxRecord(OxlangParser.OxRecordContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxMap}.
	 * @param ctx the parse tree
	 */
	void enterOxMap(OxlangParser.OxMapContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxMap}.
	 * @param ctx the parse tree
	 */
	void exitOxMap(OxlangParser.OxMapContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxSet}.
	 * @param ctx the parse tree
	 */
	void enterOxSet(OxlangParser.OxSetContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxSet}.
	 * @param ctx the parse tree
	 */
	void exitOxSet(OxlangParser.OxSetContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxReaderMacro}.
	 * @param ctx the parse tree
	 */
	void enterOxReaderMacro(OxlangParser.OxReaderMacroContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxReaderMacro}.
	 * @param ctx the parse tree
	 */
	void exitOxReaderMacro(OxlangParser.OxReaderMacroContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxQuote}.
	 * @param ctx the parse tree
	 */
	void enterOxQuote(OxlangParser.OxQuoteContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxQuote}.
	 * @param ctx the parse tree
	 */
	void exitOxQuote(OxlangParser.OxQuoteContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxBacktick}.
	 * @param ctx the parse tree
	 */
	void enterOxBacktick(OxlangParser.OxBacktickContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxBacktick}.
	 * @param ctx the parse tree
	 */
	void exitOxBacktick(OxlangParser.OxBacktickContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxUnquote}.
	 * @param ctx the parse tree
	 */
	void enterOxUnquote(OxlangParser.OxUnquoteContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxUnquote}.
	 * @param ctx the parse tree
	 */
	void exitOxUnquote(OxlangParser.OxUnquoteContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxUnquoteSplicing}.
	 * @param ctx the parse tree
	 */
	void enterOxUnquoteSplicing(OxlangParser.OxUnquoteSplicingContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxUnquoteSplicing}.
	 * @param ctx the parse tree
	 */
	void exitOxUnquoteSplicing(OxlangParser.OxUnquoteSplicingContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxTag}.
	 * @param ctx the parse tree
	 */
	void enterOxTag(OxlangParser.OxTagContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxTag}.
	 * @param ctx the parse tree
	 */
	void exitOxTag(OxlangParser.OxTagContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxDeref}.
	 * @param ctx the parse tree
	 */
	void enterOxDeref(OxlangParser.OxDerefContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxDeref}.
	 * @param ctx the parse tree
	 */
	void exitOxDeref(OxlangParser.OxDerefContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxGensym}.
	 * @param ctx the parse tree
	 */
	void enterOxGensym(OxlangParser.OxGensymContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxGensym}.
	 * @param ctx the parse tree
	 */
	void exitOxGensym(OxlangParser.OxGensymContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxMetaData}.
	 * @param ctx the parse tree
	 */
	void enterOxMetaData(OxlangParser.OxMetaDataContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxMetaData}.
	 * @param ctx the parse tree
	 */
	void exitOxMetaData(OxlangParser.OxMetaDataContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxHostExpr}.
	 * @param ctx the parse tree
	 */
	void enterOxHostExpr(OxlangParser.OxHostExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxHostExpr}.
	 * @param ctx the parse tree
	 */
	void exitOxHostExpr(OxlangParser.OxHostExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxRegex}.
	 * @param ctx the parse tree
	 */
	void enterOxRegex(OxlangParser.OxRegexContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxRegex}.
	 * @param ctx the parse tree
	 */
	void exitOxRegex(OxlangParser.OxRegexContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxLiteral}.
	 * @param ctx the parse tree
	 */
	void enterOxLiteral(OxlangParser.OxLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxLiteral}.
	 * @param ctx the parse tree
	 */
	void exitOxLiteral(OxlangParser.OxLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxString}.
	 * @param ctx the parse tree
	 */
	void enterOxString(OxlangParser.OxStringContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxString}.
	 * @param ctx the parse tree
	 */
	void exitOxString(OxlangParser.OxStringContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxFloat}.
	 * @param ctx the parse tree
	 */
	void enterOxFloat(OxlangParser.OxFloatContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxFloat}.
	 * @param ctx the parse tree
	 */
	void exitOxFloat(OxlangParser.OxFloatContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxHex}.
	 * @param ctx the parse tree
	 */
	void enterOxHex(OxlangParser.OxHexContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxHex}.
	 * @param ctx the parse tree
	 */
	void exitOxHex(OxlangParser.OxHexContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxBin}.
	 * @param ctx the parse tree
	 */
	void enterOxBin(OxlangParser.OxBinContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxBin}.
	 * @param ctx the parse tree
	 */
	void exitOxBin(OxlangParser.OxBinContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxBign}.
	 * @param ctx the parse tree
	 */
	void enterOxBign(OxlangParser.OxBignContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxBign}.
	 * @param ctx the parse tree
	 */
	void exitOxBign(OxlangParser.OxBignContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxLong}.
	 * @param ctx the parse tree
	 */
	void enterOxLong(OxlangParser.OxLongContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxLong}.
	 * @param ctx the parse tree
	 */
	void exitOxLong(OxlangParser.OxLongContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxRint}.
	 * @param ctx the parse tree
	 */
	void enterOxRint(OxlangParser.OxRintContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxRint}.
	 * @param ctx the parse tree
	 */
	void exitOxRint(OxlangParser.OxRintContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxNumber}.
	 * @param ctx the parse tree
	 */
	void enterOxNumber(OxlangParser.OxNumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxNumber}.
	 * @param ctx the parse tree
	 */
	void exitOxNumber(OxlangParser.OxNumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxCharacter}.
	 * @param ctx the parse tree
	 */
	void enterOxCharacter(OxlangParser.OxCharacterContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxCharacter}.
	 * @param ctx the parse tree
	 */
	void exitOxCharacter(OxlangParser.OxCharacterContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxNamedChar}.
	 * @param ctx the parse tree
	 */
	void enterOxNamedChar(OxlangParser.OxNamedCharContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxNamedChar}.
	 * @param ctx the parse tree
	 */
	void exitOxNamedChar(OxlangParser.OxNamedCharContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxAnyChar}.
	 * @param ctx the parse tree
	 */
	void enterOxAnyChar(OxlangParser.OxAnyCharContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxAnyChar}.
	 * @param ctx the parse tree
	 */
	void exitOxAnyChar(OxlangParser.OxAnyCharContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxUHexQuad}.
	 * @param ctx the parse tree
	 */
	void enterOxUHexQuad(OxlangParser.OxUHexQuadContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxUHexQuad}.
	 * @param ctx the parse tree
	 */
	void exitOxUHexQuad(OxlangParser.OxUHexQuadContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxNil}.
	 * @param ctx the parse tree
	 */
	void enterOxNil(OxlangParser.OxNilContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxNil}.
	 * @param ctx the parse tree
	 */
	void exitOxNil(OxlangParser.OxNilContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxBoolean}.
	 * @param ctx the parse tree
	 */
	void enterOxBoolean(OxlangParser.OxBooleanContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxBoolean}.
	 * @param ctx the parse tree
	 */
	void exitOxBoolean(OxlangParser.OxBooleanContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxKeyword}.
	 * @param ctx the parse tree
	 */
	void enterOxKeyword(OxlangParser.OxKeywordContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxKeyword}.
	 * @param ctx the parse tree
	 */
	void exitOxKeyword(OxlangParser.OxKeywordContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxSimpleKeyword}.
	 * @param ctx the parse tree
	 */
	void enterOxSimpleKeyword(OxlangParser.OxSimpleKeywordContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxSimpleKeyword}.
	 * @param ctx the parse tree
	 */
	void exitOxSimpleKeyword(OxlangParser.OxSimpleKeywordContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxMacroKeyword}.
	 * @param ctx the parse tree
	 */
	void enterOxMacroKeyword(OxlangParser.OxMacroKeywordContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxMacroKeyword}.
	 * @param ctx the parse tree
	 */
	void exitOxMacroKeyword(OxlangParser.OxMacroKeywordContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxSymbol}.
	 * @param ctx the parse tree
	 */
	void enterOxSymbol(OxlangParser.OxSymbolContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxSymbol}.
	 * @param ctx the parse tree
	 */
	void exitOxSymbol(OxlangParser.OxSymbolContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#simpleSym}.
	 * @param ctx the parse tree
	 */
	void enterSimpleSym(OxlangParser.SimpleSymContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#simpleSym}.
	 * @param ctx the parse tree
	 */
	void exitSimpleSym(OxlangParser.SimpleSymContext ctx);
	/**
	 * Enter a parse tree produced by {@link OxlangParser#oxNsSymbol}.
	 * @param ctx the parse tree
	 */
	void enterOxNsSymbol(OxlangParser.OxNsSymbolContext ctx);
	/**
	 * Exit a parse tree produced by {@link OxlangParser#oxNsSymbol}.
	 * @param ctx the parse tree
	 */
	void exitOxNsSymbol(OxlangParser.OxNsSymbolContext ctx);
}