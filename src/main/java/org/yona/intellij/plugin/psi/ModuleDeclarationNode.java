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

public class ModuleDeclarationNode extends AbstractDeclarationNode implements YonaScopeNode {
  private YonaFQN yonaFQN;

  public ModuleDeclarationNode(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public IElementType getRuleRefType() {
    return YonaTokenTypes.RULE_ELEMENT_TYPES.get(YonaParser.RULE_fqn);
  }

  @Override
  public YonaFQN getYonaFQN() {
    if (yonaFQN == null) {
      this.yonaFQN = YonaFQN.parse(getNameIdentifier().getText());
    }

    return yonaFQN;
  }

  @Override
  public @NotNull String getType() {
    return "module";
  }

  @Override
  @NotNull
  public FQNNode getNameIdentifier() {
    return Objects.requireNonNull(PsiTreeUtil.getChildOfType(this, FQNNode.class));
  }

  @Override
  public ItemPresentation getPresentation() {
    return new YonaItemPresentation(this, PlatformIcons.CLASS_ICON);
  }
}
