package org.yona.intellij.plugin.psi;

import com.intellij.lang.ASTNode;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

public class IdentifierNode extends ANTLRPsiNode {
  public IdentifierNode(@NotNull ASTNode node) {
    super(node);
  }
}
