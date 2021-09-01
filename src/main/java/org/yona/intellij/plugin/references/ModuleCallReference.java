package org.yona.intellij.plugin.references;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.yona.intellij.plugin.YonaFQN;
import org.yona.intellij.plugin.YonaUtil;
import org.yona.intellij.plugin.psi.AbstractDeclarationNode;
import org.yona.intellij.plugin.psi.FunctionDeclarationNode;
import org.yona.intellij.plugin.psi.YonaPsiUtil;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModuleCallReference extends YonaReferenceBase {
  public ModuleCallReference(@NotNull PsiElement element, TextRange rangeInElement) {
    super(element, rangeInElement);
  }

  @Override
  @SuppressWarnings("unchecked")
  protected Collection<AbstractDeclarationNode> getFirstNonEmptyDeclaringNode(PsiElement element) {
    Collection<AbstractDeclarationNode> projectTopLevelDeclarations = YonaUtil.findProjectTopLevelDeclarations(myElement.getProject());
    return projectTopLevelDeclarations.stream().filter(el -> el instanceof FunctionDeclarationNode).collect(Collectors.toList());
  }

  @Override
  protected boolean compareReference(YonaFQN reference, YonaFQN nodeFQN) {
    return reference.equals(nodeFQN);
  }

  @Override
  protected Function<PsiElement, Collection<FunctionDeclarationNode>> variantsGetter() {
    return YonaPsiUtil.finderOfDeclarationsOfType(FunctionDeclarationNode.class);
  }
}
