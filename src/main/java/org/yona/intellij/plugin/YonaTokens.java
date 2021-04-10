package org.yona.intellij.plugin;

import com.intellij.psi.tree.TokenSet;
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import yona.parser.YonaLexer;

import java.util.List;

public class YonaTokens {
  static List<TokenIElementType> TOKEN_ELEMENT_TYPES = PSIElementTypeFactory.getTokenIElementTypes(YonaLanguage.INSTANCE);

  static final TokenSet COMMENTS = PSIElementTypeFactory.createTokenSet(YonaLanguage.INSTANCE, YonaLexer.COMMENT);
  static final TokenSet WHITESPACE = PSIElementTypeFactory.createTokenSet(YonaLanguage.INSTANCE, YonaLexer.WS);
  static final TokenSet STRING = PSIElementTypeFactory.createTokenSet(YonaLanguage.INSTANCE, YonaLexer.CHARACTER_LITERAL);
}
