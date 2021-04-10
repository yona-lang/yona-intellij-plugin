package org.yona.intellij.plugin;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yona.parser.YonaLexer;

public class YonaBraceMatcher implements PairedBraceMatcher {
  @Override
  @NotNull
  public BracePair[] getPairs() {
    return new BracePair[]{
        new BracePair(YonaTokens.TOKEN_ELEMENT_TYPES.get(YonaLexer.PARENS_L), YonaTokens.TOKEN_ELEMENT_TYPES.get(YonaLexer.PARENS_R), false),
        new BracePair(YonaTokens.TOKEN_ELEMENT_TYPES.get(YonaLexer.BRACKET_L), YonaTokens.TOKEN_ELEMENT_TYPES.get(YonaLexer.BRACKET_R), false),
        new BracePair(YonaTokens.TOKEN_ELEMENT_TYPES.get(YonaLexer.OPEN_BRACE_INSIDE), YonaTokens.TOKEN_ELEMENT_TYPES.get(YonaLexer.CLOSE_BRACE_INSIDE), false),
        new BracePair(YonaTokens.TOKEN_ELEMENT_TYPES.get(YonaLexer.DOUBLE_CURLY_INSIDE), YonaTokens.TOKEN_ELEMENT_TYPES.get(YonaLexer.DOUBLE_CURLY_CLOSE_INSIDE), false)
    };
  }

  @Override
  public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
    return false;
  }

  @Override
  public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
    return openingBraceOffset;
  }
}
