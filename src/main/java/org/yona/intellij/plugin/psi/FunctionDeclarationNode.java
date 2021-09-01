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

public class FunctionDeclarationNode extends AbstractDeclarationNode implements YonaScopeNode {
  private YonaFQN yonaFQN;

  public FunctionDeclarationNode(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public IElementType getRuleRefType() {
    return YonaTokenTypes.RULE_ELEMENT_TYPES.get(YonaParser.RULE_functionName);
  }

  @Override
  public YonaFQN getYonaFQN() {
    if (yonaFQN == null) {
      ModuleDeclarationNode moduleDeclarationNode = (ModuleDeclarationNode) PsiTreeUtil.findFirstParent(this.getParent(), (el) -> el instanceof ModuleDeclarationNode);

      if (moduleDeclarationNode != null) {
        yonaFQN = YonaFQN.parse(moduleDeclarationNode.getNameIdentifier().getText() + "::" + getNameIdentifier().getText());
      } else {
        yonaFQN = new YonaFQN(new String[]{}, null, getNameIdentifier().getText());
      }
    }

    return yonaFQN;
  }

  @Override
  public @NotNull String getType() {
    return "function";
  }

  @Override
  @NotNull
  public AbstractRefNode getNameIdentifier() {
    return Objects.requireNonNull(PsiTreeUtil.getChildOfType(this, FunctionNameNode.class));
  }

  @Override
  public ItemPresentation getPresentation() {
    return new YonaItemPresentation(this, PlatformIcons.FUNCTION_ICON);
  }
}
