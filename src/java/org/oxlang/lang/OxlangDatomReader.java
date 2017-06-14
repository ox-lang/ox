package org.oxlang.lang;

import org.oxlang.data.Keyword;
import org.oxlang.data.Symbol;

import io.lacuna.bifurcan.IList;
import io.lacuna.bifurcan.IMap;
import io.lacuna.bifurcan.ISet;
import io.lacuna.bifurcan.Lists;
import io.lacuna.bifurcan.Maps;
import io.lacuna.bifurcan.Sets;

/**
 * Created by arrdem on 5/13/17.
 */
class OxlangDatomReader
    extends OxlangBaseVisitor<Object>
{

  @Override
  public Object visitString(OxlangParser.StringContext ctx) {
    StringBuilder buff = new StringBuilder();

    // Get the raw text of the node
    String text = ctx.STRING().getText();

    // Strip the open and close quotes
    text = text.substring(1, text.length() - 1);

    // FIXME: Decode character escapes.
    return text;
  }

  /**
   * Parsing symbols
   * @param ctx
   * @return
   */
  @Override
  public Object visitSymbol(OxlangParser.SymbolContext ctx) {
    return Symbol.of(ctx.SYMBOL().getText());
  }

  /**
   * Parsing keywords
   * @param ctx
   * @return
   */
  @Override
  public Object visitKeyword(OxlangParser.KeywordContext ctx) {
    return Keyword.of(ctx.KEYWORD().getText());
  }

  /**
   * Parsing floats
   * @param ctx
   * @return
   */
  @Override
  public Object visitFloating(OxlangParser.FloatingContext ctx) {
    return Double.parseDouble(ctx.FLOAT().getText());
  }

  /**
   * Parsing integers
   * @param ctx
   * @return
   */
  @Override
  public Object visitInteger(OxlangParser.IntegerContext ctx) {
    return Long.parseLong(ctx.INTEGER().getText());
  }

  @Override
  public Object visitList(OxlangParser.ListContext ctx) {
    IList coll = Lists.EMPTY;
    for (OxlangParser.SexprContext sctx: ctx.sexpr()) {
      coll = coll.addLast(visit(sctx));
    }
    return coll;
  }

  @Override
  public Object visitQuote(OxlangParser.QuoteContext ctx) {
    // FIXME: Technically what quote means, but it's not super useful.
    return Lists.from(new Object[]{Symbol.of("quote"), visit(ctx.sexpr())});
  }

  @Override
  public Object visitTagexpr(OxlangParser.TagexprContext ctx) {
    // FIXME: Not discarding tags would be great
    return visit(ctx.sexpr());
  }

  @Override
  public Object visitSqlist(OxlangParser.SqlistContext ctx) {
    IList coll = Lists.EMPTY;
    for (OxlangParser.SexprContext sctx: ctx.sexpr()) {
      coll = coll.addLast(visit(sctx));
    }
    return coll;
  }

  @Override
  public Object visitSet(OxlangParser.SetContext ctx) {
    ISet coll = Sets.EMPTY;
    for (OxlangParser.SexprContext sctx : ctx.sexpr()) {
      coll = coll.add(visit(sctx));
    }
    return coll;
  }

  @Override
  public Object visitMapping(OxlangParser.MappingContext ctx) {
    IMap coll = Maps.EMPTY;
    for (OxlangParser.PairContext pctx: ctx.pair()) {
      coll = coll.put(visit(pctx.key), visit(pctx.value));
    }
    return coll;
  }

  @Override
  public Object visitFile(OxlangParser.FileContext ctx) {
    return visit(ctx.sexpr(0));
  }
}
