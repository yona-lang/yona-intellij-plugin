package org.yona.intellij.plugin.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.antlr.intellij.adaptor.psi.ScopeNode;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface YonaScopeNode extends ScopeNode {
  @Nullable
  @Override
  default PsiElement resolve(PsiNamedElement element) {
    PsiElement[] results = PsiTreeUtil.collectElements(this, input -> {
      if (input.getNode() instanceof PsiNamedElement) {
        return Objects.equals(element.getName(), ((PsiNamedElement) input.getNode()).getName());
      }
      return false;
    });

    if (results.length > 0) {
      return results[0];
    }

    return null;
  }
}
