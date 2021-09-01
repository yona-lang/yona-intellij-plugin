package org.yona.intellij.plugin.psi;

import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class FunctionNameNode extends AbstractRefNode {
  public FunctionNameNode(@NotNull ASTNode node) {
    super(node);
  }
}
