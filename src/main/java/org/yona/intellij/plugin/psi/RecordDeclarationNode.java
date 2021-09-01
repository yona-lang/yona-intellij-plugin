package org.yona.intellij.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.PlatformIcons;
import org.jetbrains.annotations.NotNull;
import org.yona.intellij.plugin.YonaFQN;
import org.yona.intellij.plugin.YonaTokenTypes;
import org.yona.intellij.plugin.structview.YonaItemPresentation;
import yona.parser.YonaParser;

import java.util.Objects;

public class RecordDeclarationNode extends AbstractDeclarationNode implements YonaScopeNode {
  public RecordDeclarationNode(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public IElementType getRuleRefType() {
    return YonaTokenTypes.RULE_ELEMENT_TYPES.get(YonaParser.RULE_recordType);
  }

  @Override
  public YonaFQN getYonaFQN() {
    return null;
  }

  @Override
  public @NotNull String getType() {
    return "record";
  }

  @Override
  @NotNull
  public AbstractRefNode getNameIdentifier() {
    return Objects.requireNonNull(PsiTreeUtil.getChildOfType(this, RecordTypeNode.class));
  }

  @Override
  public ItemPresentation getPresentation() {
    return new YonaItemPresentation(this, PlatformIcons.RECORD_ICON);
  }
}
