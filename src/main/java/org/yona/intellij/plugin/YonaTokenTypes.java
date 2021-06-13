package org.yona.intellij.plugin;

import com.intellij.psi.tree.TokenSet;
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.intellij.adaptor.lexer.RuleIElementType;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import org.intellij.lang.annotations.MagicConstant;
import yona.parser.YonaLexer;

import java.util.List;

public class YonaTokenTypes {
    public static final List<TokenIElementType> TOKEN_ELEMENT_TYPES =
            PSIElementTypeFactory.getTokenIElementTypes(YonaLanguage.INSTANCE);
    public static final List<RuleIElementType> RULE_ELEMENT_TYPES =
            PSIElementTypeFactory.getRuleIElementTypes(YonaLanguage.INSTANCE);

    public static final TokenSet COMMENTS = PSIElementTypeFactory.createTokenSet(YonaLanguage.INSTANCE, YonaLexer.COMMENT);
    public static final TokenSet WHITESPACE = PSIElementTypeFactory.createTokenSet(YonaLanguage.INSTANCE, YonaLexer.WS);
    public static final TokenSet STRING = PSIElementTypeFactory.createTokenSet(YonaLanguage.INSTANCE, YonaLexer.STRING_START, YonaLexer.STRING_STOP, YonaLexer.REGULAR_STRING_INSIDE, YonaLexer.OPEN_INTERP, YonaLexer.CLOSE_INTERP);
    public static final TokenSet IDENTIFIERS = PSIElementTypeFactory.createTokenSet(YonaLanguage.INSTANCE, YonaLexer.LOWERCASE_NAME, YonaLexer.UPPERCASE_NAME);
    public static final TokenSet LITERALS = PSIElementTypeFactory.createTokenSet(YonaLanguage.INSTANCE, YonaLexer.CHARACTER_LITERAL, YonaLexer.INTEGER, YonaLexer.FLOAT, YonaLexer.FLOAT_INTEGER, YonaLexer.BYTE);

    public static RuleIElementType getRuleElementType(@MagicConstant(valuesFromClass = YonaLanguage.class)int ruleIndex){
        return RULE_ELEMENT_TYPES.get(ruleIndex);
    }
    public static TokenIElementType getTokenElementType(@MagicConstant(valuesFromClass = YonaLanguage.class)int ruleIndex) {
        return TOKEN_ELEMENT_TYPES.get(ruleIndex);
    }
}
