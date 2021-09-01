package org.yona.intellij.plugin.psi;

import com.intellij.lang.ASTNode;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractRefNode extends ANTLRPsiNode {
  public AbstractRefNode(@NotNull ASTNode node) {
    super(node);
  }
}
