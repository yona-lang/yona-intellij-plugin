package org.yatta.intellij.plugin.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class AliasRef extends ElementRef {
  public AliasRef(@NotNull Identifier element) {
    super(element);
  }

  @Override
  public boolean isDefSubtree(PsiElement def) {
    return def instanceof AliasSubtree;
  }
}
