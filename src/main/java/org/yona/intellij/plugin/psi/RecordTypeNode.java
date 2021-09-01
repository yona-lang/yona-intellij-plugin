package org.yona.intellij.plugin.psi;

import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class RecordTypeNode extends AbstractRefNode {
  public RecordTypeNode(@NotNull ASTNode node) {
    super(node);
  }
}
