package org.yona.intellij.plugin;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import org.jetbrains.annotations.NotNull;
import yona.parser.YonaLexer;
import yona.parser.YonaParser;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class YonaSyntaxHighlighter extends SyntaxHighlighterBase {
  public static final TextAttributesKey ID =
      createTextAttributesKey("YONA_ID", DefaultLanguageHighlighterColors.IDENTIFIER);
  public static final TextAttributesKey KEYWORD =
      createTextAttributesKey("YONA_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
  public static final TextAttributesKey STRING =
      createTextAttributesKey("YONA_STRING", DefaultLanguageHighlighterColors.STRING);
  public static final TextAttributesKey LINE_COMMENT =
      createTextAttributesKey("YONA_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
  public static final TextAttributesKey BRACKETS =
      createTextAttributesKey("YONA_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
  public static final TextAttributesKey PARENTHESES =
      createTextAttributesKey("YONA_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);
  public static final TextAttributesKey BRACES =
      createTextAttributesKey("YONA_BRACES", DefaultLanguageHighlighterColors.BRACES);
  public static final TextAttributesKey COMMA =
      createTextAttributesKey("YONA_COMMA", DefaultLanguageHighlighterColors.COMMA);
  public static final TextAttributesKey DOT =
      createTextAttributesKey("YONA_DOT", DefaultLanguageHighlighterColors.DOT);
  public static final TextAttributesKey NUMBER =
      createTextAttributesKey("YONA_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
  public static final TextAttributesKey LABEL =
      createTextAttributesKey("YONA_LABEL", DefaultLanguageHighlighterColors.LABEL);
  public static final TextAttributesKey OPERATION =
      createTextAttributesKey("YONA_OPERATION", DefaultLanguageHighlighterColors.OPERATION_SIGN);

  static {
    PSIElementTypeFactory.defineLanguageIElementTypes(YonaLanguage.INSTANCE, YonaParser.tokenNames, YonaParser.ruleNames);
  }

  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    YonaLexer lexer = new YonaLexer(null);
    return new ANTLRLexerAdaptor(YonaLanguage.INSTANCE, lexer);
  }

  @NotNull
  @Override
  public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
    if (!(tokenType instanceof TokenIElementType)) return TextAttributesKey.EMPTY_ARRAY;
    TokenIElementType myType = (TokenIElementType) tokenType;
    int ttype = myType.getANTLRTokenType();
    TextAttributesKey attrKey;
    switch (ttype) {
      case YonaLexer.UPPERCASE_NAME:
      case YonaLexer.LOWERCASE_NAME:
        attrKey = ID;
        break;
      case YonaLexer.KW_LET:
      case YonaLexer.KW_IN:
      case YonaLexer.KW_IF:
      case YonaLexer.KW_THEN:
      case YonaLexer.KW_ELSE:
      case YonaLexer.KW_TRUE:
      case YonaLexer.KW_FALSE:
      case YonaLexer.KW_MODULE:
      case YonaLexer.KW_EXPORTS:
      case YonaLexer.KW_AS:
      case YonaLexer.KW_CASE:
      case YonaLexer.KW_OF:
      case YonaLexer.KW_IMPORT:
      case YonaLexer.KW_FROM:
      case YonaLexer.KW_END:
      case YonaLexer.KW_DO:
      case YonaLexer.KW_TRY:
      case YonaLexer.KW_CATCH:
      case YonaLexer.KW_RAISE:
      case YonaLexer.KW_RECORD:
      case YonaLexer.KW_WITH:
        attrKey = KEYWORD;
        break;
      case YonaLexer.STRING_START:
      case YonaLexer.STRING_STOP:
      case YonaLexer.REGULAR_STRING_INSIDE:
      case YonaLexer.CHARACTER_LITERAL:
      case YonaLexer.DOUBLE_CURLY_OPEN_INSIDE:
      case YonaLexer.DOUBLE_CURLY_CLOSE_INSIDE:
        attrKey = STRING;
        break;
      case YonaLexer.COMMENT:
        attrKey = LINE_COMMENT;
        break;
      case YonaLexer.BRACKET_L:
      case YonaLexer.BRACKET_R:
        attrKey = BRACKETS;
        break;
      case YonaLexer.OPEN_INTERP:
      case YonaLexer.CLOSE_INTERP:
      case YonaLexer.CURLY_L:
      case YonaLexer.CURLY_R:
        attrKey = BRACES;
        break;
      case YonaLexer.PARENS_L:
      case YonaLexer.PARENS_R:
      case YonaLexer.UNIT:
        attrKey = PARENTHESES;
        break;
      case YonaLexer.COMMA:
        attrKey = COMMA;
        break;
      case YonaLexer.DOT:
      case YonaLexer.DCOLON:
      case YonaLexer.COLON:
        attrKey = DOT;
        break;
      case YonaLexer.INTEGER:
      case YonaLexer.FLOAT:
      case YonaLexer.FLOAT_INTEGER:
      case YonaLexer.BYTE:
        attrKey = NUMBER;
        break;
      case YonaLexer.UNDERSCORE:
      case YonaLexer.SYMBOL:
        attrKey = LABEL;
        break;
      case YonaLexer.OP_ASSIGN:
      case YonaLexer.OP_EQ:
      case YonaLexer.OP_NEQ:
      case YonaLexer.OP_LT:
      case YonaLexer.OP_LTE:
      case YonaLexer.OP_GT:
      case YonaLexer.OP_GTE:
      case YonaLexer.OP_RIGHT_ARROW:
      case YonaLexer.OP_LEFT_ARROW:
      case YonaLexer.OP_POWER:
      case YonaLexer.OP_MULTIPLY:
      case YonaLexer.OP_DIVIDE:
      case YonaLexer.OP_MODULO:
      case YonaLexer.OP_PLUS:
      case YonaLexer.OP_MINUS:
      case YonaLexer.OP_LEFTSHIFT:
      case YonaLexer.OP_RIGHTSHIFT:
      case YonaLexer.OP_ZEROFILL_RIGHTSHIFT:
      case YonaLexer.OP_BIN_AND:
      case YonaLexer.OP_BIN_XOR:
      case YonaLexer.OP_BIN_NOT:
      case YonaLexer.OP_LOGIC_AND:
      case YonaLexer.OP_LOGIC_OR:
      case YonaLexer.OP_LOGIC_NOT:
      case YonaLexer.OP_CONS_L:
      case YonaLexer.OP_CONS_R:
      case YonaLexer.OP_JOIN:
      case YonaLexer.OP_PIPE_L:
      case YonaLexer.OP_PIPE_R:
      case YonaLexer.VLINE:
      case YonaLexer.BACKSLASH:
        attrKey = OPERATION;
        break;
      default:
        return TextAttributesKey.EMPTY_ARRAY;
    }
    return new TextAttributesKey[]{attrKey};
  }
}
