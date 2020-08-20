package org.yona.intellij.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.psi.IdentifierDefSubtree;
import org.jetbrains.annotations.NotNull;

public class AliasSubtree extends IdentifierDefSubtree {
  public AliasSubtree(@NotNull ASTNode node, @NotNull IElementType idElementTyp) {
    super(node, idElementTyp);
  }
}
