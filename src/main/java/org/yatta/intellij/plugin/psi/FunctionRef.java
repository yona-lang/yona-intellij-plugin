package org.yatta.intellij.plugin.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * A reference object associated with (referring to) a Identifier
 * underneath a call_expr rule subtree root.
 */
public class FunctionRef extends ElementRef {
  public FunctionRef(@NotNull Identifier element) {
    super(element);
  }

  @Override
  public boolean isDefSubtree(PsiElement def) {
    return def instanceof FunctionSubtree;
  }
}
