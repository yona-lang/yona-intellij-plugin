package org.yona.intellij.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.util.IncorrectOperationException;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

public abstract class NamedDeclaration extends ANTLRPsiNode implements PsiNamedElement {
  public NamedDeclaration(@NotNull ASTNode node) {
    super(node);
  }

  public abstract @NotNull String getName();

  public abstract @NotNull String getType();

  @Override
  public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
    throw new IncorrectOperationException();
  }
}
