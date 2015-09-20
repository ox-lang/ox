// Generated from Oxlang.g4 by ANTLR 4.5.1

package ox.lang;

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
    static {
        RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION);
    }

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
    RULE_oxFile = 0, RULE_oxForm = 1, RULE_oxForms = 2, RULE_oxList = 3, RULE_oxVector = 4,
    RULE_oxRecord = 5, RULE_oxMap = 6, RULE_oxSet = 7, RULE_oxReaderMacro = 8,
    RULE_oxQuote = 9, RULE_oxBacktick = 10, RULE_oxUnquote = 11, RULE_oxUnquoteSplicing = 12,
    RULE_oxTag = 13, RULE_oxDeref = 14, RULE_oxGensym = 15, RULE_oxMetaData = 16,
    RULE_oxHostExpr = 17, RULE_oxRegex = 18, RULE_oxLiteral = 19, RULE_oxString = 20,
    RULE_oxFloat = 21, RULE_oxHex = 22, RULE_oxBin = 23, RULE_oxBign = 24,
    RULE_oxLong = 25, RULE_oxRint = 26, RULE_oxNumber = 27, RULE_oxCharacter = 28,
    RULE_oxNamedChar = 29, RULE_oxAnyChar = 30, RULE_oxUHexQuad = 31, RULE_oxNil = 32,
    RULE_oxBoolean = 33, RULE_oxKeyword = 34, RULE_oxSimpleKeyword = 35, RULE_oxMacroKeyword = 36,
    RULE_oxSymbol = 37, RULE_simpleSym = 38, RULE_oxNsSymbol = 39;
    public static final String[] ruleNames = {
        "oxFile", "oxForm", "oxForms", "oxList", "oxVector", "oxRecord", "oxMap",
        "oxSet", "oxReaderMacro", "oxQuote", "oxBacktick", "oxUnquote", "oxUnquoteSplicing",
        "oxTag", "oxDeref", "oxGensym", "oxMetaData", "oxHostExpr", "oxRegex",
        "oxLiteral", "oxString", "oxFloat", "oxHex", "oxBin", "oxBign", "oxLong",
        "oxRint", "oxNumber", "oxCharacter", "oxNamedChar", "oxAnyChar", "oxUHexQuad",
        "oxNil", "oxBoolean", "oxKeyword", "oxSimpleKeyword", "oxMacroKeyword",
        "oxSymbol", "simpleSym", "oxNsSymbol"
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
    public String getGrammarFileName() {
        return "Oxlang.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public OxlangParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
    }
    public static class OxFileContext extends ParserRuleContext {
        public List<OxFormContext> oxForm() {
            return getRuleContexts(OxFormContext.class);
        }
        public OxFormContext oxForm(int i) {
            return getRuleContext(OxFormContext.class,i);
        }
        public OxFileContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxFile;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxFile(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxFile(this);
        }
    }

    public final OxFileContext oxFile() throws RecognitionException {
        OxFileContext _localctx = new OxFileContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_oxFile);
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
                            oxForm();
                        }
                    }
                    setState(85);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxFormContext extends ParserRuleContext {
        public OxLiteralContext oxLiteral() {
            return getRuleContext(OxLiteralContext.class,0);
        }
        public OxListContext oxList() {
            return getRuleContext(OxListContext.class,0);
        }
        public OxVectorContext oxVector() {
            return getRuleContext(OxVectorContext.class,0);
        }
        public OxMapContext oxMap() {
            return getRuleContext(OxMapContext.class,0);
        }
        public OxRecordContext oxRecord() {
            return getRuleContext(OxRecordContext.class,0);
        }
        public OxReaderMacroContext oxReaderMacro() {
            return getRuleContext(OxReaderMacroContext.class,0);
        }
        public OxFormContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxForm;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxForm(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxForm(this);
        }
    }

    public final OxFormContext oxForm() throws RecognitionException {
        OxFormContext _localctx = new OxFormContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_oxForm);
        try {
            setState(92);
            switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
            case 1:
                enterOuterAlt(_localctx, 1);
                {
                    setState(86);
                    oxLiteral();
                }
                break;
            case 2:
                enterOuterAlt(_localctx, 2);
                {
                    setState(87);
                    oxList();
                }
                break;
            case 3:
                enterOuterAlt(_localctx, 3);
                {
                    setState(88);
                    oxVector();
                }
                break;
            case 4:
                enterOuterAlt(_localctx, 4);
                {
                    setState(89);
                    oxMap();
                }
                break;
            case 5:
                enterOuterAlt(_localctx, 5);
                {
                    setState(90);
                    oxRecord();
                }
                break;
            case 6:
                enterOuterAlt(_localctx, 6);
                {
                    setState(91);
                    oxReaderMacro();
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxFormsContext extends ParserRuleContext {
        public List<OxFormContext> oxForm() {
            return getRuleContexts(OxFormContext.class);
        }
        public OxFormContext oxForm(int i) {
            return getRuleContext(OxFormContext.class,i);
        }
        public OxFormsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxForms;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxForms(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxForms(this);
        }
    }

    public final OxFormsContext oxForms() throws RecognitionException {
        OxFormsContext _localctx = new OxFormsContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_oxForms);
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
                            oxForm();
                        }
                    }
                    setState(99);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxListContext extends ParserRuleContext {
        public OxFormsContext oxForms() {
            return getRuleContext(OxFormsContext.class,0);
        }
        public OxListContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxList;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxList(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxList(this);
        }
    }

    public final OxListContext oxList() throws RecognitionException {
        OxListContext _localctx = new OxListContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_oxList);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(100);
                match(T__0);
                setState(101);
                oxForms();
                setState(102);
                match(T__1);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxVectorContext extends ParserRuleContext {
        public OxFormsContext oxForms() {
            return getRuleContext(OxFormsContext.class,0);
        }
        public OxVectorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxVector;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxVector(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxVector(this);
        }
    }

    public final OxVectorContext oxVector() throws RecognitionException {
        OxVectorContext _localctx = new OxVectorContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_oxVector);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(104);
                match(T__2);
                setState(105);
                oxForms();
                setState(106);
                match(T__3);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxRecordContext extends ParserRuleContext {
        public OxNsSymbolContext oxNsSymbol() {
            return getRuleContext(OxNsSymbolContext.class,0);
        }
        public OxMapContext oxMap() {
            return getRuleContext(OxMapContext.class,0);
        }
        public OxRecordContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxRecord;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxRecord(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxRecord(this);
        }
    }

    public final OxRecordContext oxRecord() throws RecognitionException {
        OxRecordContext _localctx = new OxRecordContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_oxRecord);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(108);
                match(T__4);
                setState(109);
                oxNsSymbol();
                setState(110);
                oxMap();
                setState(111);
                match(T__3);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxMapContext extends ParserRuleContext {
        public List<OxFormContext> oxForm() {
            return getRuleContexts(OxFormContext.class);
        }
        public OxFormContext oxForm(int i) {
            return getRuleContext(OxFormContext.class,i);
        }
        public OxMapContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxMap;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxMap(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxMap(this);
        }
    }

    public final OxMapContext oxMap() throws RecognitionException {
        OxMapContext _localctx = new OxMapContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_oxMap);
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
                            oxForm();
                            setState(115);
                            oxForm();
                        }
                    }
                    setState(121);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(122);
                match(T__6);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxSetContext extends ParserRuleContext {
        public OxFormsContext oxForms() {
            return getRuleContext(OxFormsContext.class,0);
        }
        public OxSetContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxSet;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxSet(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxSet(this);
        }
    }

    public final OxSetContext oxSet() throws RecognitionException {
        OxSetContext _localctx = new OxSetContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_oxSet);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(124);
                match(T__7);
                setState(125);
                oxForms();
                setState(126);
                match(T__6);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxReaderMacroContext extends ParserRuleContext {
        public OxMetaDataContext oxMetaData() {
            return getRuleContext(OxMetaDataContext.class,0);
        }
        public OxRegexContext oxRegex() {
            return getRuleContext(OxRegexContext.class,0);
        }
        public OxHostExprContext oxHostExpr() {
            return getRuleContext(OxHostExprContext.class,0);
        }
        public OxSetContext oxSet() {
            return getRuleContext(OxSetContext.class,0);
        }
        public OxTagContext oxTag() {
            return getRuleContext(OxTagContext.class,0);
        }
        public OxDerefContext oxDeref() {
            return getRuleContext(OxDerefContext.class,0);
        }
        public OxQuoteContext oxQuote() {
            return getRuleContext(OxQuoteContext.class,0);
        }
        public OxBacktickContext oxBacktick() {
            return getRuleContext(OxBacktickContext.class,0);
        }
        public OxUnquoteContext oxUnquote() {
            return getRuleContext(OxUnquoteContext.class,0);
        }
        public OxUnquoteSplicingContext oxUnquoteSplicing() {
            return getRuleContext(OxUnquoteSplicingContext.class,0);
        }
        public OxGensymContext oxGensym() {
            return getRuleContext(OxGensymContext.class,0);
        }
        public OxReaderMacroContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxReaderMacro;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxReaderMacro(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxReaderMacro(this);
        }
    }

    public final OxReaderMacroContext oxReaderMacro() throws RecognitionException {
        OxReaderMacroContext _localctx = new OxReaderMacroContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_oxReaderMacro);
        try {
            setState(139);
            switch (_input.LA(1)) {
            case T__15:
                enterOuterAlt(_localctx, 1);
                {
                    setState(128);
                    oxMetaData();
                }
                break;
            case T__14:
                enterOuterAlt(_localctx, 2);
                {
                    setState(129);
                    oxRegex();
                }
                break;
            case T__16:
                enterOuterAlt(_localctx, 3);
                {
                    setState(130);
                    oxHostExpr();
                }
                break;
            case T__7:
                enterOuterAlt(_localctx, 4);
                {
                    setState(131);
                    oxSet();
                }
                break;
            case T__12:
                enterOuterAlt(_localctx, 5);
                {
                    setState(132);
                    oxTag();
                }
                break;
            case T__13:
                enterOuterAlt(_localctx, 6);
                {
                    setState(133);
                    oxDeref();
                }
                break;
            case T__8:
                enterOuterAlt(_localctx, 7);
                {
                    setState(134);
                    oxQuote();
                }
                break;
            case T__9:
                enterOuterAlt(_localctx, 8);
                {
                    setState(135);
                    oxBacktick();
                }
                break;
            case T__10:
                enterOuterAlt(_localctx, 9);
                {
                    setState(136);
                    oxUnquote();
                }
                break;
            case T__11:
                enterOuterAlt(_localctx, 10);
                {
                    setState(137);
                    oxUnquoteSplicing();
                }
                break;
            case SYMBOL:
                enterOuterAlt(_localctx, 11);
                {
                    setState(138);
                    oxGensym();
                }
                break;
            default:
                throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxQuoteContext extends ParserRuleContext {
        public OxFormContext oxForm() {
            return getRuleContext(OxFormContext.class,0);
        }
        public OxQuoteContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxQuote;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxQuote(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxQuote(this);
        }
    }

    public final OxQuoteContext oxQuote() throws RecognitionException {
        OxQuoteContext _localctx = new OxQuoteContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_oxQuote);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(141);
                match(T__8);
                setState(142);
                oxForm();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxBacktickContext extends ParserRuleContext {
        public OxFormContext oxForm() {
            return getRuleContext(OxFormContext.class,0);
        }
        public OxBacktickContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxBacktick;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxBacktick(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxBacktick(this);
        }
    }

    public final OxBacktickContext oxBacktick() throws RecognitionException {
        OxBacktickContext _localctx = new OxBacktickContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_oxBacktick);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(144);
                match(T__9);
                setState(145);
                oxForm();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxUnquoteContext extends ParserRuleContext {
        public OxFormContext oxForm() {
            return getRuleContext(OxFormContext.class,0);
        }
        public OxUnquoteContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxUnquote;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxUnquote(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxUnquote(this);
        }
    }

    public final OxUnquoteContext oxUnquote() throws RecognitionException {
        OxUnquoteContext _localctx = new OxUnquoteContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_oxUnquote);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(147);
                match(T__10);
                setState(148);
                oxForm();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxUnquoteSplicingContext extends ParserRuleContext {
        public OxFormContext oxForm() {
            return getRuleContext(OxFormContext.class,0);
        }
        public OxUnquoteSplicingContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxUnquoteSplicing;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxUnquoteSplicing(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxUnquoteSplicing(this);
        }
    }

    public final OxUnquoteSplicingContext oxUnquoteSplicing() throws RecognitionException {
        OxUnquoteSplicingContext _localctx = new OxUnquoteSplicingContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_oxUnquoteSplicing);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(150);
                match(T__11);
                setState(151);
                oxForm();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxTagContext extends ParserRuleContext {
        public OxFormContext oxForm() {
            return getRuleContext(OxFormContext.class,0);
        }
        public OxSymbolContext oxSymbol() {
            return getRuleContext(OxSymbolContext.class,0);
        }
        public OxStringContext oxString() {
            return getRuleContext(OxStringContext.class,0);
        }
        public OxTagContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxTag;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxTag(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxTag(this);
        }
    }

    public final OxTagContext oxTag() throws RecognitionException {
        OxTagContext _localctx = new OxTagContext(_ctx, getState());
        enterRule(_localctx, 26, RULE_oxTag);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(153);
                match(T__12);
                setState(156);
                switch (_input.LA(1)) {
                case SYMBOL:
                case NS_SYMBOL: {
                    setState(154);
                    oxSymbol();
                }
                break;
                case STRING: {
                    setState(155);
                    oxString();
                }
                break;
                default:
                    throw new NoViableAltException(this);
                }
                setState(158);
                oxForm();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxDerefContext extends ParserRuleContext {
        public OxFormContext oxForm() {
            return getRuleContext(OxFormContext.class,0);
        }
        public OxDerefContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxDeref;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxDeref(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxDeref(this);
        }
    }

    public final OxDerefContext oxDeref() throws RecognitionException {
        OxDerefContext _localctx = new OxDerefContext(_ctx, getState());
        enterRule(_localctx, 28, RULE_oxDeref);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(160);
                match(T__13);
                setState(161);
                oxForm();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxGensymContext extends ParserRuleContext {
        public TerminalNode SYMBOL() {
            return getToken(OxlangParser.SYMBOL, 0);
        }
        public OxGensymContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxGensym;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxGensym(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxGensym(this);
        }
    }

    public final OxGensymContext oxGensym() throws RecognitionException {
        OxGensymContext _localctx = new OxGensymContext(_ctx, getState());
        enterRule(_localctx, 30, RULE_oxGensym);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(163);
                match(SYMBOL);
                setState(164);
                match(T__14);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxMetaDataContext extends ParserRuleContext {
        public OxMapContext oxMap() {
            return getRuleContext(OxMapContext.class,0);
        }
        public OxFormContext oxForm() {
            return getRuleContext(OxFormContext.class,0);
        }
        public OxMetaDataContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxMetaData;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxMetaData(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxMetaData(this);
        }
    }

    public final OxMetaDataContext oxMetaData() throws RecognitionException {
        OxMetaDataContext _localctx = new OxMetaDataContext(_ctx, getState());
        enterRule(_localctx, 32, RULE_oxMetaData);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(166);
                match(T__15);
                setState(167);
                oxMap();
                setState(168);
                oxForm();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxHostExprContext extends ParserRuleContext {
        public List<OxFormContext> oxForm() {
            return getRuleContexts(OxFormContext.class);
        }
        public OxFormContext oxForm(int i) {
            return getRuleContext(OxFormContext.class,i);
        }
        public OxHostExprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxHostExpr;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxHostExpr(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxHostExpr(this);
        }
    }

    public final OxHostExprContext oxHostExpr() throws RecognitionException {
        OxHostExprContext _localctx = new OxHostExprContext(_ctx, getState());
        enterRule(_localctx, 34, RULE_oxHostExpr);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(170);
                match(T__16);
                setState(171);
                oxForm();
                setState(172);
                oxForm();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxRegexContext extends ParserRuleContext {
        public OxStringContext oxString() {
            return getRuleContext(OxStringContext.class,0);
        }
        public OxRegexContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxRegex;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxRegex(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxRegex(this);
        }
    }

    public final OxRegexContext oxRegex() throws RecognitionException {
        OxRegexContext _localctx = new OxRegexContext(_ctx, getState());
        enterRule(_localctx, 36, RULE_oxRegex);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(174);
                match(T__14);
                setState(175);
                oxString();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxLiteralContext extends ParserRuleContext {
        public OxStringContext oxString() {
            return getRuleContext(OxStringContext.class,0);
        }
        public OxNumberContext oxNumber() {
            return getRuleContext(OxNumberContext.class,0);
        }
        public OxCharacterContext oxCharacter() {
            return getRuleContext(OxCharacterContext.class,0);
        }
        public OxNilContext oxNil() {
            return getRuleContext(OxNilContext.class,0);
        }
        public OxBooleanContext oxBoolean() {
            return getRuleContext(OxBooleanContext.class,0);
        }
        public OxKeywordContext oxKeyword() {
            return getRuleContext(OxKeywordContext.class,0);
        }
        public OxSymbolContext oxSymbol() {
            return getRuleContext(OxSymbolContext.class,0);
        }
        public OxLiteralContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxLiteral;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxLiteral(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxLiteral(this);
        }
    }

    public final OxLiteralContext oxLiteral() throws RecognitionException {
        OxLiteralContext _localctx = new OxLiteralContext(_ctx, getState());
        enterRule(_localctx, 38, RULE_oxLiteral);
        try {
            setState(184);
            switch (_input.LA(1)) {
            case STRING:
                enterOuterAlt(_localctx, 1);
                {
                    setState(177);
                    oxString();
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
                    oxNumber();
                }
                break;
            case CHAR_U:
            case CHAR_NAMED:
            case CHAR_ANY:
                enterOuterAlt(_localctx, 3);
                {
                    setState(179);
                    oxCharacter();
                }
                break;
            case NIL:
                enterOuterAlt(_localctx, 4);
                {
                    setState(180);
                    oxNil();
                }
                break;
            case BOOLEAN:
                enterOuterAlt(_localctx, 5);
                {
                    setState(181);
                    oxBoolean();
                }
                break;
            case T__17:
                enterOuterAlt(_localctx, 6);
                {
                    setState(182);
                    oxKeyword();
                }
                break;
            case SYMBOL:
            case NS_SYMBOL:
                enterOuterAlt(_localctx, 7);
                {
                    setState(183);
                    oxSymbol();
                }
                break;
            default:
                throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxStringContext extends ParserRuleContext {
        public TerminalNode STRING() {
            return getToken(OxlangParser.STRING, 0);
        }
        public OxStringContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxString;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxString(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxString(this);
        }
    }

    public final OxStringContext oxString() throws RecognitionException {
        OxStringContext _localctx = new OxStringContext(_ctx, getState());
        enterRule(_localctx, 40, RULE_oxString);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(186);
                match(STRING);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxFloatContext extends ParserRuleContext {
        public TerminalNode FLOAT() {
            return getToken(OxlangParser.FLOAT, 0);
        }
        public OxFloatContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxFloat;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxFloat(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxFloat(this);
        }
    }

    public final OxFloatContext oxFloat() throws RecognitionException {
        OxFloatContext _localctx = new OxFloatContext(_ctx, getState());
        enterRule(_localctx, 42, RULE_oxFloat);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(188);
                match(FLOAT);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxHexContext extends ParserRuleContext {
        public TerminalNode HEX() {
            return getToken(OxlangParser.HEX, 0);
        }
        public OxHexContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxHex;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxHex(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxHex(this);
        }
    }

    public final OxHexContext oxHex() throws RecognitionException {
        OxHexContext _localctx = new OxHexContext(_ctx, getState());
        enterRule(_localctx, 44, RULE_oxHex);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(190);
                match(HEX);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxBinContext extends ParserRuleContext {
        public TerminalNode BIN() {
            return getToken(OxlangParser.BIN, 0);
        }
        public OxBinContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxBin;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxBin(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxBin(this);
        }
    }

    public final OxBinContext oxBin() throws RecognitionException {
        OxBinContext _localctx = new OxBinContext(_ctx, getState());
        enterRule(_localctx, 46, RULE_oxBin);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(192);
                match(BIN);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxBignContext extends ParserRuleContext {
        public TerminalNode BIGN() {
            return getToken(OxlangParser.BIGN, 0);
        }
        public OxBignContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxBign;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxBign(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxBign(this);
        }
    }

    public final OxBignContext oxBign() throws RecognitionException {
        OxBignContext _localctx = new OxBignContext(_ctx, getState());
        enterRule(_localctx, 48, RULE_oxBign);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(194);
                match(BIGN);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxLongContext extends ParserRuleContext {
        public TerminalNode LONG() {
            return getToken(OxlangParser.LONG, 0);
        }
        public OxLongContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxLong;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxLong(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxLong(this);
        }
    }

    public final OxLongContext oxLong() throws RecognitionException {
        OxLongContext _localctx = new OxLongContext(_ctx, getState());
        enterRule(_localctx, 50, RULE_oxLong);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(196);
                match(LONG);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxRintContext extends ParserRuleContext {
        public TerminalNode RINT() {
            return getToken(OxlangParser.RINT, 0);
        }
        public OxRintContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxRint;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxRint(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxRint(this);
        }
    }

    public final OxRintContext oxRint() throws RecognitionException {
        OxRintContext _localctx = new OxRintContext(_ctx, getState());
        enterRule(_localctx, 52, RULE_oxRint);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(198);
                match(RINT);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxNumberContext extends ParserRuleContext {
        public OxFloatContext oxFloat() {
            return getRuleContext(OxFloatContext.class,0);
        }
        public OxHexContext oxHex() {
            return getRuleContext(OxHexContext.class,0);
        }
        public OxBinContext oxBin() {
            return getRuleContext(OxBinContext.class,0);
        }
        public OxBignContext oxBign() {
            return getRuleContext(OxBignContext.class,0);
        }
        public OxLongContext oxLong() {
            return getRuleContext(OxLongContext.class,0);
        }
        public OxRintContext oxRint() {
            return getRuleContext(OxRintContext.class,0);
        }
        public OxNumberContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxNumber;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxNumber(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxNumber(this);
        }
    }

    public final OxNumberContext oxNumber() throws RecognitionException {
        OxNumberContext _localctx = new OxNumberContext(_ctx, getState());
        enterRule(_localctx, 54, RULE_oxNumber);
        try {
            setState(206);
            switch (_input.LA(1)) {
            case FLOAT:
                enterOuterAlt(_localctx, 1);
                {
                    setState(200);
                    oxFloat();
                }
                break;
            case HEX:
                enterOuterAlt(_localctx, 2);
                {
                    setState(201);
                    oxHex();
                }
                break;
            case BIN:
                enterOuterAlt(_localctx, 3);
                {
                    setState(202);
                    oxBin();
                }
                break;
            case BIGN:
                enterOuterAlt(_localctx, 4);
                {
                    setState(203);
                    oxBign();
                }
                break;
            case LONG:
                enterOuterAlt(_localctx, 5);
                {
                    setState(204);
                    oxLong();
                }
                break;
            case RINT:
                enterOuterAlt(_localctx, 6);
                {
                    setState(205);
                    oxRint();
                }
                break;
            default:
                throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxCharacterContext extends ParserRuleContext {
        public OxNamedCharContext oxNamedChar() {
            return getRuleContext(OxNamedCharContext.class,0);
        }
        public OxUHexQuadContext oxUHexQuad() {
            return getRuleContext(OxUHexQuadContext.class,0);
        }
        public OxAnyCharContext oxAnyChar() {
            return getRuleContext(OxAnyCharContext.class,0);
        }
        public OxCharacterContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxCharacter;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxCharacter(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxCharacter(this);
        }
    }

    public final OxCharacterContext oxCharacter() throws RecognitionException {
        OxCharacterContext _localctx = new OxCharacterContext(_ctx, getState());
        enterRule(_localctx, 56, RULE_oxCharacter);
        try {
            setState(211);
            switch (_input.LA(1)) {
            case CHAR_NAMED:
                enterOuterAlt(_localctx, 1);
                {
                    setState(208);
                    oxNamedChar();
                }
                break;
            case CHAR_U:
                enterOuterAlt(_localctx, 2);
                {
                    setState(209);
                    oxUHexQuad();
                }
                break;
            case CHAR_ANY:
                enterOuterAlt(_localctx, 3);
                {
                    setState(210);
                    oxAnyChar();
                }
                break;
            default:
                throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxNamedCharContext extends ParserRuleContext {
        public TerminalNode CHAR_NAMED() {
            return getToken(OxlangParser.CHAR_NAMED, 0);
        }
        public OxNamedCharContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxNamedChar;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxNamedChar(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxNamedChar(this);
        }
    }

    public final OxNamedCharContext oxNamedChar() throws RecognitionException {
        OxNamedCharContext _localctx = new OxNamedCharContext(_ctx, getState());
        enterRule(_localctx, 58, RULE_oxNamedChar);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(213);
                match(CHAR_NAMED);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxAnyCharContext extends ParserRuleContext {
        public TerminalNode CHAR_ANY() {
            return getToken(OxlangParser.CHAR_ANY, 0);
        }
        public OxAnyCharContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxAnyChar;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxAnyChar(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxAnyChar(this);
        }
    }

    public final OxAnyCharContext oxAnyChar() throws RecognitionException {
        OxAnyCharContext _localctx = new OxAnyCharContext(_ctx, getState());
        enterRule(_localctx, 60, RULE_oxAnyChar);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(215);
                match(CHAR_ANY);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxUHexQuadContext extends ParserRuleContext {
        public TerminalNode CHAR_U() {
            return getToken(OxlangParser.CHAR_U, 0);
        }
        public OxUHexQuadContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxUHexQuad;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxUHexQuad(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxUHexQuad(this);
        }
    }

    public final OxUHexQuadContext oxUHexQuad() throws RecognitionException {
        OxUHexQuadContext _localctx = new OxUHexQuadContext(_ctx, getState());
        enterRule(_localctx, 62, RULE_oxUHexQuad);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(217);
                match(CHAR_U);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxNilContext extends ParserRuleContext {
        public TerminalNode NIL() {
            return getToken(OxlangParser.NIL, 0);
        }
        public OxNilContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxNil;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxNil(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxNil(this);
        }
    }

    public final OxNilContext oxNil() throws RecognitionException {
        OxNilContext _localctx = new OxNilContext(_ctx, getState());
        enterRule(_localctx, 64, RULE_oxNil);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(219);
                match(NIL);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxBooleanContext extends ParserRuleContext {
        public TerminalNode BOOLEAN() {
            return getToken(OxlangParser.BOOLEAN, 0);
        }
        public OxBooleanContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxBoolean;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxBoolean(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxBoolean(this);
        }
    }

    public final OxBooleanContext oxBoolean() throws RecognitionException {
        OxBooleanContext _localctx = new OxBooleanContext(_ctx, getState());
        enterRule(_localctx, 66, RULE_oxBoolean);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(221);
                match(BOOLEAN);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxKeywordContext extends ParserRuleContext {
        public OxMacroKeywordContext oxMacroKeyword() {
            return getRuleContext(OxMacroKeywordContext.class,0);
        }
        public OxSimpleKeywordContext oxSimpleKeyword() {
            return getRuleContext(OxSimpleKeywordContext.class,0);
        }
        public OxKeywordContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxKeyword;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxKeyword(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxKeyword(this);
        }
    }

    public final OxKeywordContext oxKeyword() throws RecognitionException {
        OxKeywordContext _localctx = new OxKeywordContext(_ctx, getState());
        enterRule(_localctx, 68, RULE_oxKeyword);
        try {
            setState(225);
            switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
            case 1:
                enterOuterAlt(_localctx, 1);
                {
                    setState(223);
                    oxMacroKeyword();
                }
                break;
            case 2:
                enterOuterAlt(_localctx, 2);
                {
                    setState(224);
                    oxSimpleKeyword();
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxSimpleKeywordContext extends ParserRuleContext {
        public OxSymbolContext oxSymbol() {
            return getRuleContext(OxSymbolContext.class,0);
        }
        public OxSimpleKeywordContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxSimpleKeyword;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxSimpleKeyword(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxSimpleKeyword(this);
        }
    }

    public final OxSimpleKeywordContext oxSimpleKeyword() throws RecognitionException {
        OxSimpleKeywordContext _localctx = new OxSimpleKeywordContext(_ctx, getState());
        enterRule(_localctx, 70, RULE_oxSimpleKeyword);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(227);
                match(T__17);
                setState(228);
                oxSymbol();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxMacroKeywordContext extends ParserRuleContext {
        public OxSymbolContext oxSymbol() {
            return getRuleContext(OxSymbolContext.class,0);
        }
        public OxMacroKeywordContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxMacroKeyword;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxMacroKeyword(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxMacroKeyword(this);
        }
    }

    public final OxMacroKeywordContext oxMacroKeyword() throws RecognitionException {
        OxMacroKeywordContext _localctx = new OxMacroKeywordContext(_ctx, getState());
        enterRule(_localctx, 72, RULE_oxMacroKeyword);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(230);
                match(T__17);
                setState(231);
                match(T__17);
                setState(232);
                oxSymbol();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxSymbolContext extends ParserRuleContext {
        public OxNsSymbolContext oxNsSymbol() {
            return getRuleContext(OxNsSymbolContext.class,0);
        }
        public SimpleSymContext simpleSym() {
            return getRuleContext(SimpleSymContext.class,0);
        }
        public OxSymbolContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxSymbol;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxSymbol(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxSymbol(this);
        }
    }

    public final OxSymbolContext oxSymbol() throws RecognitionException {
        OxSymbolContext _localctx = new OxSymbolContext(_ctx, getState());
        enterRule(_localctx, 74, RULE_oxSymbol);
        try {
            setState(236);
            switch (_input.LA(1)) {
            case NS_SYMBOL:
                enterOuterAlt(_localctx, 1);
                {
                    setState(234);
                    oxNsSymbol();
                }
                break;
            case SYMBOL:
                enterOuterAlt(_localctx, 2);
                {
                    setState(235);
                    simpleSym();
                }
                break;
            default:
                throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class SimpleSymContext extends ParserRuleContext {
        public TerminalNode SYMBOL() {
            return getToken(OxlangParser.SYMBOL, 0);
        }
        public SimpleSymContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_simpleSym;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterSimpleSym(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitSimpleSym(this);
        }
    }

    public final SimpleSymContext simpleSym() throws RecognitionException {
        SimpleSymContext _localctx = new SimpleSymContext(_ctx, getState());
        enterRule(_localctx, 76, RULE_simpleSym);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(238);
                match(SYMBOL);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class OxNsSymbolContext extends ParserRuleContext {
        public TerminalNode NS_SYMBOL() {
            return getToken(OxlangParser.NS_SYMBOL, 0);
        }
        public OxNsSymbolContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() {
            return RULE_oxNsSymbol;
        }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).enterOxNsSymbol(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof OxlangListener ) ((OxlangListener)listener).exitOxNsSymbol(this);
        }
    }

    public final OxNsSymbolContext oxNsSymbol() throws RecognitionException {
        OxNsSymbolContext _localctx = new OxNsSymbolContext(_ctx, getState());
        enterRule(_localctx, 78, RULE_oxNsSymbol);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(240);
                match(NS_SYMBOL);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
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