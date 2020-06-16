package org.yatta.intellij.plugin;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import org.jetbrains.annotations.NotNull;
import org.yatta.intellij.plugin.parser.YattaLexer;
import org.yatta.intellij.plugin.parser.YattaParser;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class YattaSyntaxHighlighter extends SyntaxHighlighterBase {
  public static final TextAttributesKey ID =
      createTextAttributesKey("YATTA_ID", DefaultLanguageHighlighterColors.IDENTIFIER);
  public static final TextAttributesKey KEYWORD =
      createTextAttributesKey("YATTA_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
  public static final TextAttributesKey STRING =
      createTextAttributesKey("YATTA_STRING", DefaultLanguageHighlighterColors.STRING);
  public static final TextAttributesKey LINE_COMMENT =
      createTextAttributesKey("YATTA_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
  public static final TextAttributesKey BRACKETS =
      createTextAttributesKey("YATTA_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
  public static final TextAttributesKey PARENTHESES =
      createTextAttributesKey("YATTA_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);
  public static final TextAttributesKey BRACES =
      createTextAttributesKey("YATTA_BRACES", DefaultLanguageHighlighterColors.BRACES);
  public static final TextAttributesKey COMMA =
      createTextAttributesKey("YATTA_COMMA", DefaultLanguageHighlighterColors.COMMA);
  public static final TextAttributesKey DOT =
      createTextAttributesKey("YATTA_DOT", DefaultLanguageHighlighterColors.DOT);
  public static final TextAttributesKey NUMBER =
      createTextAttributesKey("YATTA_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
  public static final TextAttributesKey LABEL =
      createTextAttributesKey("YATTA_LABEL", DefaultLanguageHighlighterColors.LABEL);
  public static final TextAttributesKey OPERATION =
      createTextAttributesKey("YATTA_OPERATION", DefaultLanguageHighlighterColors.OPERATION_SIGN);
  public static final TextAttributesKey FUNCTION_DECLARATION =
      createTextAttributesKey("YATTA_FUNCTION_DECLARATION", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);
  public static final TextAttributesKey INTERFACE_NAME =
      createTextAttributesKey("YATTA_INTERFACE_NAME", DefaultLanguageHighlighterColors.INTERFACE_NAME);

  static {
    PSIElementTypeFactory.defineLanguageIElementTypes(YattaLanguage.INSTANCE, YattaParser.tokenNames, YattaParser.ruleNames);
  }

  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    YattaLexer lexer = new YattaLexer(null);
    return new ANTLRLexerAdaptor(YattaLanguage.INSTANCE, lexer);
  }

  @NotNull
  @Override
  public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
    if (!(tokenType instanceof TokenIElementType)) return EMPTY_KEYS;
    TokenIElementType myType = (TokenIElementType) tokenType;
    int ttype = myType.getANTLRTokenType();
    TextAttributesKey attrKey;
    switch (ttype) {
      case YattaLexer.UPPERCASE_NAME:
      case YattaLexer.LOWERCASE_NAME:
        attrKey = ID;
        break;
      case YattaLexer.KW_LET:
      case YattaLexer.KW_IN:
      case YattaLexer.KW_IF:
      case YattaLexer.KW_THEN:
      case YattaLexer.KW_ELSE:
      case YattaLexer.KW_TRUE:
      case YattaLexer.KW_FALSE:
      case YattaLexer.KW_MODULE:
      case YattaLexer.KW_EXPORTS:
      case YattaLexer.KW_AS:
      case YattaLexer.KW_CASE:
      case YattaLexer.KW_OF:
      case YattaLexer.KW_IMPORT:
      case YattaLexer.KW_FROM:
      case YattaLexer.KW_END:
      case YattaLexer.KW_DO:
      case YattaLexer.KW_TRY:
      case YattaLexer.KW_CATCH:
      case YattaLexer.KW_RAISE:
      case YattaLexer.KW_RECORD:
      case YattaLexer.KW_WITH:
        attrKey = KEYWORD;
        break;
      case YattaLexer.REGULAR_CHAR_INSIDE:
      case YattaLexer.REGULAR_STRING_INSIDE:
        attrKey = STRING;
        break;
      case YattaLexer.COMMENT:
        attrKey = LINE_COMMENT;
        break;
      case YattaLexer.BRACKET_L:
      case YattaLexer.BRACKET_R:
        attrKey = BRACKETS;
        break;
      case YattaLexer.CURLY_L:
      case YattaLexer.CURLY_R:
        attrKey = BRACES;
        break;
      case YattaLexer.PARENS_L:
      case YattaLexer.PARENS_R:
      case YattaLexer.UNIT:
        attrKey = PARENTHESES;
        break;
      case YattaLexer.COMMA:
        attrKey = COMMA;
        break;
      case YattaLexer.DOT:
      case YattaLexer.DCOLON:
      case YattaLexer.COLON:
        attrKey = DOT;
        break;
      case YattaLexer.INTEGER:
      case YattaLexer.FLOAT:
      case YattaLexer.FLOAT_INTEGER:
      case YattaLexer.BYTE:
        attrKey = NUMBER;
        break;
      case YattaLexer.UNDERSCORE:
        attrKey = LABEL;
        break;
      case YattaLexer.OP_ASSIGN:
      case YattaLexer.OP_EQ:
      case YattaLexer.OP_NEQ:
      case YattaLexer.OP_LT:
      case YattaLexer.OP_LTE:
      case YattaLexer.OP_GT:
      case YattaLexer.OP_GTE:
      case YattaLexer.OP_RIGHT_ARROW:
      case YattaLexer.OP_LEFT_ARROW:
      case YattaLexer.OP_POWER:
      case YattaLexer.OP_MULTIPLY:
      case YattaLexer.OP_DIVIDE:
      case YattaLexer.OP_MODULO:
      case YattaLexer.OP_PLUS:
      case YattaLexer.OP_MINUS:
      case YattaLexer.OP_LEFTSHIFT:
      case YattaLexer.OP_RIGHTSHIFT:
      case YattaLexer.OP_ZEROFILL_RIGHTSHIFT:
      case YattaLexer.OP_BIN_AND:
      case YattaLexer.OP_BIN_XOR:
      case YattaLexer.OP_BIN_NOT:
      case YattaLexer.OP_LOGIC_AND:
      case YattaLexer.OP_LOGIC_OR:
      case YattaLexer.OP_LOGIC_NOT:
      case YattaLexer.OP_CONS_L:
      case YattaLexer.OP_CONS_R:
      case YattaLexer.OP_JOIN:
      case YattaLexer.OP_PIPE_L:
      case YattaLexer.OP_PIPE_R:
        attrKey = OPERATION;
        break;
      case YattaLexer.VLINE:
        attrKey = OPERATION;
        break;
      case YattaLexer.BACKSLASH:
        attrKey = INTERFACE_NAME;
        break;
      default:
        return TextAttributesKey.EMPTY_ARRAY;
    }
    return new TextAttributesKey[]{attrKey};
  }
}
