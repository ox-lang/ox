// Generated from Oxlang.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class OxlangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, STRING=19, FLOAT=20, HEX=21, BIN=22, LONG=23, BIGN=24, RINT=25, 
		CHAR_U=26, CHAR_NAMED=27, CHAR_ANY=28, NIL=29, BOOLEAN=30, SYMBOL=31, 
		NS_SYMBOL=32, TRASH=33;
	public static final int
		RULE_ox_file = 0, RULE_ox_form = 1, RULE_ox_forms = 2, RULE_ox_list = 3, 
		RULE_ox_vector = 4, RULE_ox_record = 5, RULE_ox_map = 6, RULE_ox_set = 7, 
		RULE_ox_reader_macro = 8, RULE_ox_quote = 9, RULE_ox_backtick = 10, RULE_ox_unquote = 11, 
		RULE_ox_unquote_splicing = 12, RULE_ox_tag = 13, RULE_ox_deref = 14, RULE_ox_gensym = 15, 
		RULE_ox_meta_data = 16, RULE_ox_host_expr = 17, RULE_ox_regex = 18, RULE_ox_literal = 19, 
		RULE_ox_string = 20, RULE_ox_float = 21, RULE_ox_hex = 22, RULE_ox_bin = 23, 
		RULE_ox_bign = 24, RULE_ox_long = 25, RULE_ox_rint = 26, RULE_ox_number = 27, 
		RULE_ox_character = 28, RULE_ox_named_char = 29, RULE_ox_any_char = 30, 
		RULE_ox_u_hex_quad = 31, RULE_ox_nil = 32, RULE_ox_boolean = 33, RULE_ox_keyword = 34, 
		RULE_ox_simple_keyword = 35, RULE_ox_macro_keyword = 36, RULE_ox_symbol = 37, 
		RULE_simple_sym = 38, RULE_ox_ns_symbol = 39;
	public static final String[] ruleNames = {
		"ox_file", "ox_form", "ox_forms", "ox_list", "ox_vector", "ox_record", 
		"ox_map", "ox_set", "ox_reader_macro", "ox_quote", "ox_backtick", "ox_unquote", 
		"ox_unquote_splicing", "ox_tag", "ox_deref", "ox_gensym", "ox_meta_data", 
		"ox_host_expr", "ox_regex", "ox_literal", "ox_string", "ox_float", "ox_hex", 
		"ox_bin", "ox_bign", "ox_long", "ox_rint", "ox_number", "ox_character", 
		"ox_named_char", "ox_any_char", "ox_u_hex_quad", "ox_nil", "ox_boolean", 
		"ox_keyword", "ox_simple_keyword", "ox_macro_keyword", "ox_symbol", "simple_sym", 
		"ox_ns_symbol"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'['", "']'", "'#['", "'{'", "'}'", "'#{'", "'''", 
		"'`'", "'~'", "'~@'", "'^'", "'@'", "'#'", "'#^'", "'#+'", "':'", null, 
		null, null, null, null, null, null, null, null, null, "'nil'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, "STRING", "FLOAT", "HEX", "BIN", 
		"LONG", "BIGN", "RINT", "CHAR_U", "CHAR_NAMED", "CHAR_ANY", "NIL", "BOOLEAN", 
		"SYMBOL", "NS_SYMBOL", "TRASH"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Oxlang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public OxlangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class Ox_fileContext extends ParserRuleContext {
		public List<Ox_formContext> ox_form() {
			return getRuleContexts(Ox_formContext.class);
		}
		public Ox_formContext ox_form(int i) {
			return getRuleContext(Ox_formContext.class,i);
		}
		public Ox_fileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_file; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_file(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_file(this);
		}
	}

	public final Ox_fileContext ox_file() throws RecognitionException {
		Ox_fileContext _localctx = new Ox_fileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_ox_file);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(83);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__4) | (1L << T__5) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << STRING) | (1L << FLOAT) | (1L << HEX) | (1L << BIN) | (1L << LONG) | (1L << BIGN) | (1L << RINT) | (1L << CHAR_U) | (1L << CHAR_NAMED) | (1L << CHAR_ANY) | (1L << NIL) | (1L << BOOLEAN) | (1L << SYMBOL) | (1L << NS_SYMBOL))) != 0)) {
				{
				{
				setState(80);
				ox_form();
				}
				}
				setState(85);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_formContext extends ParserRuleContext {
		public Ox_literalContext ox_literal() {
			return getRuleContext(Ox_literalContext.class,0);
		}
		public Ox_listContext ox_list() {
			return getRuleContext(Ox_listContext.class,0);
		}
		public Ox_vectorContext ox_vector() {
			return getRuleContext(Ox_vectorContext.class,0);
		}
		public Ox_mapContext ox_map() {
			return getRuleContext(Ox_mapContext.class,0);
		}
		public Ox_recordContext ox_record() {
			return getRuleContext(Ox_recordContext.class,0);
		}
		public Ox_reader_macroContext ox_reader_macro() {
			return getRuleContext(Ox_reader_macroContext.class,0);
		}
		public Ox_formContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_form; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_form(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_form(this);
		}
	}

	public final Ox_formContext ox_form() throws RecognitionException {
		Ox_formContext _localctx = new Ox_formContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_ox_form);
		try {
			setState(92);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(86);
				ox_literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(87);
				ox_list();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(88);
				ox_vector();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(89);
				ox_map();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(90);
				ox_record();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(91);
				ox_reader_macro();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_formsContext extends ParserRuleContext {
		public List<Ox_formContext> ox_form() {
			return getRuleContexts(Ox_formContext.class);
		}
		public Ox_formContext ox_form(int i) {
			return getRuleContext(Ox_formContext.class,i);
		}
		public Ox_formsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_forms; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_forms(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_forms(this);
		}
	}

	public final Ox_formsContext ox_forms() throws RecognitionException {
		Ox_formsContext _localctx = new Ox_formsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_ox_forms);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__4) | (1L << T__5) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << STRING) | (1L << FLOAT) | (1L << HEX) | (1L << BIN) | (1L << LONG) | (1L << BIGN) | (1L << RINT) | (1L << CHAR_U) | (1L << CHAR_NAMED) | (1L << CHAR_ANY) | (1L << NIL) | (1L << BOOLEAN) | (1L << SYMBOL) | (1L << NS_SYMBOL))) != 0)) {
				{
				{
				setState(94);
				ox_form();
				}
				}
				setState(99);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_listContext extends ParserRuleContext {
		public Ox_formsContext ox_forms() {
			return getRuleContext(Ox_formsContext.class,0);
		}
		public Ox_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_list(this);
		}
	}

	public final Ox_listContext ox_list() throws RecognitionException {
		Ox_listContext _localctx = new Ox_listContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_ox_list);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			match(T__0);
			setState(101);
			ox_forms();
			setState(102);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_vectorContext extends ParserRuleContext {
		public Ox_formsContext ox_forms() {
			return getRuleContext(Ox_formsContext.class,0);
		}
		public Ox_vectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_vector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_vector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_vector(this);
		}
	}

	public final Ox_vectorContext ox_vector() throws RecognitionException {
		Ox_vectorContext _localctx = new Ox_vectorContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_ox_vector);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(T__2);
			setState(105);
			ox_forms();
			setState(106);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_recordContext extends ParserRuleContext {
		public Ox_ns_symbolContext ox_ns_symbol() {
			return getRuleContext(Ox_ns_symbolContext.class,0);
		}
		public Ox_mapContext ox_map() {
			return getRuleContext(Ox_mapContext.class,0);
		}
		public Ox_recordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_record; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_record(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_record(this);
		}
	}

	public final Ox_recordContext ox_record() throws RecognitionException {
		Ox_recordContext _localctx = new Ox_recordContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_ox_record);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			match(T__4);
			setState(109);
			ox_ns_symbol();
			setState(110);
			ox_map();
			setState(111);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_mapContext extends ParserRuleContext {
		public List<Ox_formContext> ox_form() {
			return getRuleContexts(Ox_formContext.class);
		}
		public Ox_formContext ox_form(int i) {
			return getRuleContext(Ox_formContext.class,i);
		}
		public Ox_mapContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_map; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_map(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_map(this);
		}
	}

	public final Ox_mapContext ox_map() throws RecognitionException {
		Ox_mapContext _localctx = new Ox_mapContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_ox_map);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(T__5);
			setState(119);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__4) | (1L << T__5) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << STRING) | (1L << FLOAT) | (1L << HEX) | (1L << BIN) | (1L << LONG) | (1L << BIGN) | (1L << RINT) | (1L << CHAR_U) | (1L << CHAR_NAMED) | (1L << CHAR_ANY) | (1L << NIL) | (1L << BOOLEAN) | (1L << SYMBOL) | (1L << NS_SYMBOL))) != 0)) {
				{
				{
				setState(114);
				ox_form();
				setState(115);
				ox_form();
				}
				}
				setState(121);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(122);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_setContext extends ParserRuleContext {
		public Ox_formsContext ox_forms() {
			return getRuleContext(Ox_formsContext.class,0);
		}
		public Ox_setContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_set; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_set(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_set(this);
		}
	}

	public final Ox_setContext ox_set() throws RecognitionException {
		Ox_setContext _localctx = new Ox_setContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_ox_set);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			match(T__7);
			setState(125);
			ox_forms();
			setState(126);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_reader_macroContext extends ParserRuleContext {
		public Ox_meta_dataContext ox_meta_data() {
			return getRuleContext(Ox_meta_dataContext.class,0);
		}
		public Ox_regexContext ox_regex() {
			return getRuleContext(Ox_regexContext.class,0);
		}
		public Ox_host_exprContext ox_host_expr() {
			return getRuleContext(Ox_host_exprContext.class,0);
		}
		public Ox_setContext ox_set() {
			return getRuleContext(Ox_setContext.class,0);
		}
		public Ox_tagContext ox_tag() {
			return getRuleContext(Ox_tagContext.class,0);
		}
		public Ox_derefContext ox_deref() {
			return getRuleContext(Ox_derefContext.class,0);
		}
		public Ox_quoteContext ox_quote() {
			return getRuleContext(Ox_quoteContext.class,0);
		}
		public Ox_backtickContext ox_backtick() {
			return getRuleContext(Ox_backtickContext.class,0);
		}
		public Ox_unquoteContext ox_unquote() {
			return getRuleContext(Ox_unquoteContext.class,0);
		}
		public Ox_unquote_splicingContext ox_unquote_splicing() {
			return getRuleContext(Ox_unquote_splicingContext.class,0);
		}
		public Ox_gensymContext ox_gensym() {
			return getRuleContext(Ox_gensymContext.class,0);
		}
		public Ox_reader_macroContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_reader_macro; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_reader_macro(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_reader_macro(this);
		}
	}

	public final Ox_reader_macroContext ox_reader_macro() throws RecognitionException {
		Ox_reader_macroContext _localctx = new Ox_reader_macroContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_ox_reader_macro);
		try {
			setState(139);
			switch (_input.LA(1)) {
			case T__15:
				enterOuterAlt(_localctx, 1);
				{
				setState(128);
				ox_meta_data();
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(129);
				ox_regex();
				}
				break;
			case T__16:
				enterOuterAlt(_localctx, 3);
				{
				setState(130);
				ox_host_expr();
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 4);
				{
				setState(131);
				ox_set();
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 5);
				{
				setState(132);
				ox_tag();
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 6);
				{
				setState(133);
				ox_deref();
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 7);
				{
				setState(134);
				ox_quote();
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 8);
				{
				setState(135);
				ox_backtick();
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 9);
				{
				setState(136);
				ox_unquote();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 10);
				{
				setState(137);
				ox_unquote_splicing();
				}
				break;
			case SYMBOL:
				enterOuterAlt(_localctx, 11);
				{
				setState(138);
				ox_gensym();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_quoteContext extends ParserRuleContext {
		public Ox_formContext ox_form() {
			return getRuleContext(Ox_formContext.class,0);
		}
		public Ox_quoteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_quote; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_quote(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_quote(this);
		}
	}

	public final Ox_quoteContext ox_quote() throws RecognitionException {
		Ox_quoteContext _localctx = new Ox_quoteContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_ox_quote);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			match(T__8);
			setState(142);
			ox_form();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_backtickContext extends ParserRuleContext {
		public Ox_formContext ox_form() {
			return getRuleContext(Ox_formContext.class,0);
		}
		public Ox_backtickContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_backtick; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_backtick(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_backtick(this);
		}
	}

	public final Ox_backtickContext ox_backtick() throws RecognitionException {
		Ox_backtickContext _localctx = new Ox_backtickContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_ox_backtick);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			match(T__9);
			setState(145);
			ox_form();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_unquoteContext extends ParserRuleContext {
		public Ox_formContext ox_form() {
			return getRuleContext(Ox_formContext.class,0);
		}
		public Ox_unquoteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_unquote; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_unquote(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_unquote(this);
		}
	}

	public final Ox_unquoteContext ox_unquote() throws RecognitionException {
		Ox_unquoteContext _localctx = new Ox_unquoteContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_ox_unquote);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			match(T__10);
			setState(148);
			ox_form();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_unquote_splicingContext extends ParserRuleContext {
		public Ox_formContext ox_form() {
			return getRuleContext(Ox_formContext.class,0);
		}
		public Ox_unquote_splicingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_unquote_splicing; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_unquote_splicing(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_unquote_splicing(this);
		}
	}

	public final Ox_unquote_splicingContext ox_unquote_splicing() throws RecognitionException {
		Ox_unquote_splicingContext _localctx = new Ox_unquote_splicingContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_ox_unquote_splicing);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
			match(T__11);
			setState(151);
			ox_form();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_tagContext extends ParserRuleContext {
		public Ox_formContext ox_form() {
			return getRuleContext(Ox_formContext.class,0);
		}
		public Ox_symbolContext ox_symbol() {
			return getRuleContext(Ox_symbolContext.class,0);
		}
		public Ox_stringContext ox_string() {
			return getRuleContext(Ox_stringContext.class,0);
		}
		public Ox_tagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_tag; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_tag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_tag(this);
		}
	}

	public final Ox_tagContext ox_tag() throws RecognitionException {
		Ox_tagContext _localctx = new Ox_tagContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_ox_tag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			match(T__12);
			setState(156);
			switch (_input.LA(1)) {
			case SYMBOL:
			case NS_SYMBOL:
				{
				setState(154);
				ox_symbol();
				}
				break;
			case STRING:
				{
				setState(155);
				ox_string();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(158);
			ox_form();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_derefContext extends ParserRuleContext {
		public Ox_formContext ox_form() {
			return getRuleContext(Ox_formContext.class,0);
		}
		public Ox_derefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_deref; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_deref(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_deref(this);
		}
	}

	public final Ox_derefContext ox_deref() throws RecognitionException {
		Ox_derefContext _localctx = new Ox_derefContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_ox_deref);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			match(T__13);
			setState(161);
			ox_form();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_gensymContext extends ParserRuleContext {
		public TerminalNode SYMBOL() { return getToken(OxlangParser.SYMBOL, 0); }
		public Ox_gensymContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_gensym; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_gensym(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_gensym(this);
		}
	}

	public final Ox_gensymContext ox_gensym() throws RecognitionException {
		Ox_gensymContext _localctx = new Ox_gensymContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_ox_gensym);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			match(SYMBOL);
			setState(164);
			match(T__14);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_meta_dataContext extends ParserRuleContext {
		public Ox_mapContext ox_map() {
			return getRuleContext(Ox_mapContext.class,0);
		}
		public Ox_formContext ox_form() {
			return getRuleContext(Ox_formContext.class,0);
		}
		public Ox_meta_dataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_meta_data; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_meta_data(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_meta_data(this);
		}
	}

	public final Ox_meta_dataContext ox_meta_data() throws RecognitionException {
		Ox_meta_dataContext _localctx = new Ox_meta_dataContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_ox_meta_data);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			match(T__15);
			setState(167);
			ox_map();
			setState(168);
			ox_form();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_host_exprContext extends ParserRuleContext {
		public List<Ox_formContext> ox_form() {
			return getRuleContexts(Ox_formContext.class);
		}
		public Ox_formContext ox_form(int i) {
			return getRuleContext(Ox_formContext.class,i);
		}
		public Ox_host_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_host_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_host_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_host_expr(this);
		}
	}

	public final Ox_host_exprContext ox_host_expr() throws RecognitionException {
		Ox_host_exprContext _localctx = new Ox_host_exprContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_ox_host_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			match(T__16);
			setState(171);
			ox_form();
			setState(172);
			ox_form();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_regexContext extends ParserRuleContext {
		public Ox_stringContext ox_string() {
			return getRuleContext(Ox_stringContext.class,0);
		}
		public Ox_regexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_regex; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_regex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_regex(this);
		}
	}

	public final Ox_regexContext ox_regex() throws RecognitionException {
		Ox_regexContext _localctx = new Ox_regexContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_ox_regex);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
			match(T__14);
			setState(175);
			ox_string();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_literalContext extends ParserRuleContext {
		public Ox_stringContext ox_string() {
			return getRuleContext(Ox_stringContext.class,0);
		}
		public Ox_numberContext ox_number() {
			return getRuleContext(Ox_numberContext.class,0);
		}
		public Ox_characterContext ox_character() {
			return getRuleContext(Ox_characterContext.class,0);
		}
		public Ox_nilContext ox_nil() {
			return getRuleContext(Ox_nilContext.class,0);
		}
		public Ox_booleanContext ox_boolean() {
			return getRuleContext(Ox_booleanContext.class,0);
		}
		public Ox_keywordContext ox_keyword() {
			return getRuleContext(Ox_keywordContext.class,0);
		}
		public Ox_symbolContext ox_symbol() {
			return getRuleContext(Ox_symbolContext.class,0);
		}
		public Ox_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_literal(this);
		}
	}

	public final Ox_literalContext ox_literal() throws RecognitionException {
		Ox_literalContext _localctx = new Ox_literalContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_ox_literal);
		try {
			setState(184);
			switch (_input.LA(1)) {
			case STRING:
				enterOuterAlt(_localctx, 1);
				{
				setState(177);
				ox_string();
				}
				break;
			case FLOAT:
			case HEX:
			case BIN:
			case LONG:
			case BIGN:
			case RINT:
				enterOuterAlt(_localctx, 2);
				{
				setState(178);
				ox_number();
				}
				break;
			case CHAR_U:
			case CHAR_NAMED:
			case CHAR_ANY:
				enterOuterAlt(_localctx, 3);
				{
				setState(179);
				ox_character();
				}
				break;
			case NIL:
				enterOuterAlt(_localctx, 4);
				{
				setState(180);
				ox_nil();
				}
				break;
			case BOOLEAN:
				enterOuterAlt(_localctx, 5);
				{
				setState(181);
				ox_boolean();
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 6);
				{
				setState(182);
				ox_keyword();
				}
				break;
			case SYMBOL:
			case NS_SYMBOL:
				enterOuterAlt(_localctx, 7);
				{
				setState(183);
				ox_symbol();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_stringContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(OxlangParser.STRING, 0); }
		public Ox_stringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_string(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_string(this);
		}
	}

	public final Ox_stringContext ox_string() throws RecognitionException {
		Ox_stringContext _localctx = new Ox_stringContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_ox_string);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_floatContext extends ParserRuleContext {
		public TerminalNode FLOAT() { return getToken(OxlangParser.FLOAT, 0); }
		public Ox_floatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_float; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_float(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_float(this);
		}
	}

	public final Ox_floatContext ox_float() throws RecognitionException {
		Ox_floatContext _localctx = new Ox_floatContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_ox_float);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			match(FLOAT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_hexContext extends ParserRuleContext {
		public TerminalNode HEX() { return getToken(OxlangParser.HEX, 0); }
		public Ox_hexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_hex; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_hex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_hex(this);
		}
	}

	public final Ox_hexContext ox_hex() throws RecognitionException {
		Ox_hexContext _localctx = new Ox_hexContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_ox_hex);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			match(HEX);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_binContext extends ParserRuleContext {
		public TerminalNode BIN() { return getToken(OxlangParser.BIN, 0); }
		public Ox_binContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_bin; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_bin(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_bin(this);
		}
	}

	public final Ox_binContext ox_bin() throws RecognitionException {
		Ox_binContext _localctx = new Ox_binContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_ox_bin);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			match(BIN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_bignContext extends ParserRuleContext {
		public TerminalNode BIGN() { return getToken(OxlangParser.BIGN, 0); }
		public Ox_bignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_bign; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_bign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_bign(this);
		}
	}

	public final Ox_bignContext ox_bign() throws RecognitionException {
		Ox_bignContext _localctx = new Ox_bignContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_ox_bign);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			match(BIGN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_longContext extends ParserRuleContext {
		public TerminalNode LONG() { return getToken(OxlangParser.LONG, 0); }
		public Ox_longContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_long; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_long(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_long(this);
		}
	}

	public final Ox_longContext ox_long() throws RecognitionException {
		Ox_longContext _localctx = new Ox_longContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_ox_long);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
			match(LONG);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_rintContext extends ParserRuleContext {
		public TerminalNode RINT() { return getToken(OxlangParser.RINT, 0); }
		public Ox_rintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_rint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_rint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_rint(this);
		}
	}

	public final Ox_rintContext ox_rint() throws RecognitionException {
		Ox_rintContext _localctx = new Ox_rintContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_ox_rint);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			match(RINT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_numberContext extends ParserRuleContext {
		public Ox_floatContext ox_float() {
			return getRuleContext(Ox_floatContext.class,0);
		}
		public Ox_hexContext ox_hex() {
			return getRuleContext(Ox_hexContext.class,0);
		}
		public Ox_binContext ox_bin() {
			return getRuleContext(Ox_binContext.class,0);
		}
		public Ox_bignContext ox_bign() {
			return getRuleContext(Ox_bignContext.class,0);
		}
		public Ox_longContext ox_long() {
			return getRuleContext(Ox_longContext.class,0);
		}
		public Ox_rintContext ox_rint() {
			return getRuleContext(Ox_rintContext.class,0);
		}
		public Ox_numberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_number(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_number(this);
		}
	}

	public final Ox_numberContext ox_number() throws RecognitionException {
		Ox_numberContext _localctx = new Ox_numberContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_ox_number);
		try {
			setState(206);
			switch (_input.LA(1)) {
			case FLOAT:
				enterOuterAlt(_localctx, 1);
				{
				setState(200);
				ox_float();
				}
				break;
			case HEX:
				enterOuterAlt(_localctx, 2);
				{
				setState(201);
				ox_hex();
				}
				break;
			case BIN:
				enterOuterAlt(_localctx, 3);
				{
				setState(202);
				ox_bin();
				}
				break;
			case BIGN:
				enterOuterAlt(_localctx, 4);
				{
				setState(203);
				ox_bign();
				}
				break;
			case LONG:
				enterOuterAlt(_localctx, 5);
				{
				setState(204);
				ox_long();
				}
				break;
			case RINT:
				enterOuterAlt(_localctx, 6);
				{
				setState(205);
				ox_rint();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_characterContext extends ParserRuleContext {
		public Ox_named_charContext ox_named_char() {
			return getRuleContext(Ox_named_charContext.class,0);
		}
		public Ox_u_hex_quadContext ox_u_hex_quad() {
			return getRuleContext(Ox_u_hex_quadContext.class,0);
		}
		public Ox_any_charContext ox_any_char() {
			return getRuleContext(Ox_any_charContext.class,0);
		}
		public Ox_characterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_character; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_character(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_character(this);
		}
	}

	public final Ox_characterContext ox_character() throws RecognitionException {
		Ox_characterContext _localctx = new Ox_characterContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_ox_character);
		try {
			setState(211);
			switch (_input.LA(1)) {
			case CHAR_NAMED:
				enterOuterAlt(_localctx, 1);
				{
				setState(208);
				ox_named_char();
				}
				break;
			case CHAR_U:
				enterOuterAlt(_localctx, 2);
				{
				setState(209);
				ox_u_hex_quad();
				}
				break;
			case CHAR_ANY:
				enterOuterAlt(_localctx, 3);
				{
				setState(210);
				ox_any_char();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_named_charContext extends ParserRuleContext {
		public TerminalNode CHAR_NAMED() { return getToken(OxlangParser.CHAR_NAMED, 0); }
		public Ox_named_charContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_named_char; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_named_char(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_named_char(this);
		}
	}

	public final Ox_named_charContext ox_named_char() throws RecognitionException {
		Ox_named_charContext _localctx = new Ox_named_charContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_ox_named_char);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			match(CHAR_NAMED);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_any_charContext extends ParserRuleContext {
		public TerminalNode CHAR_ANY() { return getToken(OxlangParser.CHAR_ANY, 0); }
		public Ox_any_charContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_any_char; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_any_char(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_any_char(this);
		}
	}

	public final Ox_any_charContext ox_any_char() throws RecognitionException {
		Ox_any_charContext _localctx = new Ox_any_charContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_ox_any_char);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			match(CHAR_ANY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_u_hex_quadContext extends ParserRuleContext {
		public TerminalNode CHAR_U() { return getToken(OxlangParser.CHAR_U, 0); }
		public Ox_u_hex_quadContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_u_hex_quad; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_u_hex_quad(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_u_hex_quad(this);
		}
	}

	public final Ox_u_hex_quadContext ox_u_hex_quad() throws RecognitionException {
		Ox_u_hex_quadContext _localctx = new Ox_u_hex_quadContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_ox_u_hex_quad);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217);
			match(CHAR_U);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_nilContext extends ParserRuleContext {
		public TerminalNode NIL() { return getToken(OxlangParser.NIL, 0); }
		public Ox_nilContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_nil; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_nil(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_nil(this);
		}
	}

	public final Ox_nilContext ox_nil() throws RecognitionException {
		Ox_nilContext _localctx = new Ox_nilContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_ox_nil);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219);
			match(NIL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_booleanContext extends ParserRuleContext {
		public TerminalNode BOOLEAN() { return getToken(OxlangParser.BOOLEAN, 0); }
		public Ox_booleanContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_boolean; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_boolean(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_boolean(this);
		}
	}

	public final Ox_booleanContext ox_boolean() throws RecognitionException {
		Ox_booleanContext _localctx = new Ox_booleanContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_ox_boolean);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			match(BOOLEAN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_keywordContext extends ParserRuleContext {
		public Ox_macro_keywordContext ox_macro_keyword() {
			return getRuleContext(Ox_macro_keywordContext.class,0);
		}
		public Ox_simple_keywordContext ox_simple_keyword() {
			return getRuleContext(Ox_simple_keywordContext.class,0);
		}
		public Ox_keywordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_keyword; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_keyword(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_keyword(this);
		}
	}

	public final Ox_keywordContext ox_keyword() throws RecognitionException {
		Ox_keywordContext _localctx = new Ox_keywordContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_ox_keyword);
		try {
			setState(225);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(223);
				ox_macro_keyword();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(224);
				ox_simple_keyword();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_simple_keywordContext extends ParserRuleContext {
		public Ox_symbolContext ox_symbol() {
			return getRuleContext(Ox_symbolContext.class,0);
		}
		public Ox_simple_keywordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_simple_keyword; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_simple_keyword(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_simple_keyword(this);
		}
	}

	public final Ox_simple_keywordContext ox_simple_keyword() throws RecognitionException {
		Ox_simple_keywordContext _localctx = new Ox_simple_keywordContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_ox_simple_keyword);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(227);
			match(T__17);
			setState(228);
			ox_symbol();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_macro_keywordContext extends ParserRuleContext {
		public Ox_symbolContext ox_symbol() {
			return getRuleContext(Ox_symbolContext.class,0);
		}
		public Ox_macro_keywordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_macro_keyword; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_macro_keyword(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_macro_keyword(this);
		}
	}

	public final Ox_macro_keywordContext ox_macro_keyword() throws RecognitionException {
		Ox_macro_keywordContext _localctx = new Ox_macro_keywordContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_ox_macro_keyword);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(230);
			match(T__17);
			setState(231);
			match(T__17);
			setState(232);
			ox_symbol();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_symbolContext extends ParserRuleContext {
		public Ox_ns_symbolContext ox_ns_symbol() {
			return getRuleContext(Ox_ns_symbolContext.class,0);
		}
		public Simple_symContext simple_sym() {
			return getRuleContext(Simple_symContext.class,0);
		}
		public Ox_symbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_symbol; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_symbol(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_symbol(this);
		}
	}

	public final Ox_symbolContext ox_symbol() throws RecognitionException {
		Ox_symbolContext _localctx = new Ox_symbolContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_ox_symbol);
		try {
			setState(236);
			switch (_input.LA(1)) {
			case NS_SYMBOL:
				enterOuterAlt(_localctx, 1);
				{
				setState(234);
				ox_ns_symbol();
				}
				break;
			case SYMBOL:
				enterOuterAlt(_localctx, 2);
				{
				setState(235);
				simple_sym();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Simple_symContext extends ParserRuleContext {
		public TerminalNode SYMBOL() { return getToken(OxlangParser.SYMBOL, 0); }
		public Simple_symContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simple_sym; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterSimple_sym(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitSimple_sym(this);
		}
	}

	public final Simple_symContext simple_sym() throws RecognitionException {
		Simple_symContext _localctx = new Simple_symContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_simple_sym);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			match(SYMBOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ox_ns_symbolContext extends ParserRuleContext {
		public TerminalNode NS_SYMBOL() { return getToken(OxlangParser.NS_SYMBOL, 0); }
		public Ox_ns_symbolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ox_ns_symbol; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOx_ns_symbol(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOx_ns_symbol(this);
		}
	}

	public final Ox_ns_symbolContext ox_ns_symbol() throws RecognitionException {
		Ox_ns_symbolContext _localctx = new Ox_ns_symbolContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_ox_ns_symbol);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(240);
			match(NS_SYMBOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3#\u00f5\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\3\2\7\2T\n\2\f"+
		"\2\16\2W\13\2\3\3\3\3\3\3\3\3\3\3\3\3\5\3_\n\3\3\4\7\4b\n\4\f\4\16\4e"+
		"\13\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b"+
		"\3\b\7\bx\n\b\f\b\16\b{\13\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u008e\n\n\3\13\3\13\3\13\3\f\3\f\3\f\3"+
		"\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\5\17\u009f\n\17\3\17\3\17\3\20"+
		"\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24"+
		"\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u00bb\n\25\3\26\3\26"+
		"\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\5\35\u00d1\n\35\3\36\3\36\3\36\5\36\u00d6\n\36\3"+
		"\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\5$\u00e4\n$\3%\3%\3%\3&\3&\3"+
		"&\3&\3\'\3\'\5\'\u00ef\n\'\3(\3(\3)\3)\3)\2\2*\2\4\6\b\n\f\16\20\22\24"+
		"\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNP\2\2\u00ee\2U\3\2\2\2"+
		"\4^\3\2\2\2\6c\3\2\2\2\bf\3\2\2\2\nj\3\2\2\2\fn\3\2\2\2\16s\3\2\2\2\20"+
		"~\3\2\2\2\22\u008d\3\2\2\2\24\u008f\3\2\2\2\26\u0092\3\2\2\2\30\u0095"+
		"\3\2\2\2\32\u0098\3\2\2\2\34\u009b\3\2\2\2\36\u00a2\3\2\2\2 \u00a5\3\2"+
		"\2\2\"\u00a8\3\2\2\2$\u00ac\3\2\2\2&\u00b0\3\2\2\2(\u00ba\3\2\2\2*\u00bc"+
		"\3\2\2\2,\u00be\3\2\2\2.\u00c0\3\2\2\2\60\u00c2\3\2\2\2\62\u00c4\3\2\2"+
		"\2\64\u00c6\3\2\2\2\66\u00c8\3\2\2\28\u00d0\3\2\2\2:\u00d5\3\2\2\2<\u00d7"+
		"\3\2\2\2>\u00d9\3\2\2\2@\u00db\3\2\2\2B\u00dd\3\2\2\2D\u00df\3\2\2\2F"+
		"\u00e3\3\2\2\2H\u00e5\3\2\2\2J\u00e8\3\2\2\2L\u00ee\3\2\2\2N\u00f0\3\2"+
		"\2\2P\u00f2\3\2\2\2RT\5\4\3\2SR\3\2\2\2TW\3\2\2\2US\3\2\2\2UV\3\2\2\2"+
		"V\3\3\2\2\2WU\3\2\2\2X_\5(\25\2Y_\5\b\5\2Z_\5\n\6\2[_\5\16\b\2\\_\5\f"+
		"\7\2]_\5\22\n\2^X\3\2\2\2^Y\3\2\2\2^Z\3\2\2\2^[\3\2\2\2^\\\3\2\2\2^]\3"+
		"\2\2\2_\5\3\2\2\2`b\5\4\3\2a`\3\2\2\2be\3\2\2\2ca\3\2\2\2cd\3\2\2\2d\7"+
		"\3\2\2\2ec\3\2\2\2fg\7\3\2\2gh\5\6\4\2hi\7\4\2\2i\t\3\2\2\2jk\7\5\2\2"+
		"kl\5\6\4\2lm\7\6\2\2m\13\3\2\2\2no\7\7\2\2op\5P)\2pq\5\16\b\2qr\7\6\2"+
		"\2r\r\3\2\2\2sy\7\b\2\2tu\5\4\3\2uv\5\4\3\2vx\3\2\2\2wt\3\2\2\2x{\3\2"+
		"\2\2yw\3\2\2\2yz\3\2\2\2z|\3\2\2\2{y\3\2\2\2|}\7\t\2\2}\17\3\2\2\2~\177"+
		"\7\n\2\2\177\u0080\5\6\4\2\u0080\u0081\7\t\2\2\u0081\21\3\2\2\2\u0082"+
		"\u008e\5\"\22\2\u0083\u008e\5&\24\2\u0084\u008e\5$\23\2\u0085\u008e\5"+
		"\20\t\2\u0086\u008e\5\34\17\2\u0087\u008e\5\36\20\2\u0088\u008e\5\24\13"+
		"\2\u0089\u008e\5\26\f\2\u008a\u008e\5\30\r\2\u008b\u008e\5\32\16\2\u008c"+
		"\u008e\5 \21\2\u008d\u0082\3\2\2\2\u008d\u0083\3\2\2\2\u008d\u0084\3\2"+
		"\2\2\u008d\u0085\3\2\2\2\u008d\u0086\3\2\2\2\u008d\u0087\3\2\2\2\u008d"+
		"\u0088\3\2\2\2\u008d\u0089\3\2\2\2\u008d\u008a\3\2\2\2\u008d\u008b\3\2"+
		"\2\2\u008d\u008c\3\2\2\2\u008e\23\3\2\2\2\u008f\u0090\7\13\2\2\u0090\u0091"+
		"\5\4\3\2\u0091\25\3\2\2\2\u0092\u0093\7\f\2\2\u0093\u0094\5\4\3\2\u0094"+
		"\27\3\2\2\2\u0095\u0096\7\r\2\2\u0096\u0097\5\4\3\2\u0097\31\3\2\2\2\u0098"+
		"\u0099\7\16\2\2\u0099\u009a\5\4\3\2\u009a\33\3\2\2\2\u009b\u009e\7\17"+
		"\2\2\u009c\u009f\5L\'\2\u009d\u009f\5*\26\2\u009e\u009c\3\2\2\2\u009e"+
		"\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u00a1\5\4\3\2\u00a1\35\3\2\2"+
		"\2\u00a2\u00a3\7\20\2\2\u00a3\u00a4\5\4\3\2\u00a4\37\3\2\2\2\u00a5\u00a6"+
		"\7!\2\2\u00a6\u00a7\7\21\2\2\u00a7!\3\2\2\2\u00a8\u00a9\7\22\2\2\u00a9"+
		"\u00aa\5\16\b\2\u00aa\u00ab\5\4\3\2\u00ab#\3\2\2\2\u00ac\u00ad\7\23\2"+
		"\2\u00ad\u00ae\5\4\3\2\u00ae\u00af\5\4\3\2\u00af%\3\2\2\2\u00b0\u00b1"+
		"\7\21\2\2\u00b1\u00b2\5*\26\2\u00b2\'\3\2\2\2\u00b3\u00bb\5*\26\2\u00b4"+
		"\u00bb\58\35\2\u00b5\u00bb\5:\36\2\u00b6\u00bb\5B\"\2\u00b7\u00bb\5D#"+
		"\2\u00b8\u00bb\5F$\2\u00b9\u00bb\5L\'\2\u00ba\u00b3\3\2\2\2\u00ba\u00b4"+
		"\3\2\2\2\u00ba\u00b5\3\2\2\2\u00ba\u00b6\3\2\2\2\u00ba\u00b7\3\2\2\2\u00ba"+
		"\u00b8\3\2\2\2\u00ba\u00b9\3\2\2\2\u00bb)\3\2\2\2\u00bc\u00bd\7\25\2\2"+
		"\u00bd+\3\2\2\2\u00be\u00bf\7\26\2\2\u00bf-\3\2\2\2\u00c0\u00c1\7\27\2"+
		"\2\u00c1/\3\2\2\2\u00c2\u00c3\7\30\2\2\u00c3\61\3\2\2\2\u00c4\u00c5\7"+
		"\32\2\2\u00c5\63\3\2\2\2\u00c6\u00c7\7\31\2\2\u00c7\65\3\2\2\2\u00c8\u00c9"+
		"\7\33\2\2\u00c9\67\3\2\2\2\u00ca\u00d1\5,\27\2\u00cb\u00d1\5.\30\2\u00cc"+
		"\u00d1\5\60\31\2\u00cd\u00d1\5\62\32\2\u00ce\u00d1\5\64\33\2\u00cf\u00d1"+
		"\5\66\34\2\u00d0\u00ca\3\2\2\2\u00d0\u00cb\3\2\2\2\u00d0\u00cc\3\2\2\2"+
		"\u00d0\u00cd\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d0\u00cf\3\2\2\2\u00d19\3"+
		"\2\2\2\u00d2\u00d6\5<\37\2\u00d3\u00d6\5@!\2\u00d4\u00d6\5> \2\u00d5\u00d2"+
		"\3\2\2\2\u00d5\u00d3\3\2\2\2\u00d5\u00d4\3\2\2\2\u00d6;\3\2\2\2\u00d7"+
		"\u00d8\7\35\2\2\u00d8=\3\2\2\2\u00d9\u00da\7\36\2\2\u00da?\3\2\2\2\u00db"+
		"\u00dc\7\34\2\2\u00dcA\3\2\2\2\u00dd\u00de\7\37\2\2\u00deC\3\2\2\2\u00df"+
		"\u00e0\7 \2\2\u00e0E\3\2\2\2\u00e1\u00e4\5J&\2\u00e2\u00e4\5H%\2\u00e3"+
		"\u00e1\3\2\2\2\u00e3\u00e2\3\2\2\2\u00e4G\3\2\2\2\u00e5\u00e6\7\24\2\2"+
		"\u00e6\u00e7\5L\'\2\u00e7I\3\2\2\2\u00e8\u00e9\7\24\2\2\u00e9\u00ea\7"+
		"\24\2\2\u00ea\u00eb\5L\'\2\u00ebK\3\2\2\2\u00ec\u00ef\5P)\2\u00ed\u00ef"+
		"\5N(\2\u00ee\u00ec\3\2\2\2\u00ee\u00ed\3\2\2\2\u00efM\3\2\2\2\u00f0\u00f1"+
		"\7!\2\2\u00f1O\3\2\2\2\u00f2\u00f3\7\"\2\2\u00f3Q\3\2\2\2\rU^cy\u008d"+
		"\u009e\u00ba\u00d0\u00d5\u00e3\u00ee";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}