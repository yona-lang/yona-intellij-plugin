package org.yona.intellij.plugin.references;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.yona.intellij.plugin.YonaFQN;
import org.yona.intellij.plugin.psi.*;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunctionCallReference extends YonaReferenceBase {
  public FunctionCallReference(@NotNull PsiElement element, TextRange rangeInElement) {
    super(element, rangeInElement);
  }

  @Override
  @SuppressWarnings("unchecked")
  protected Collection<AbstractDeclarationNode> getFirstNonEmptyDeclaringNode(PsiElement element) {
    if (element != null) {
      ModuleDeclarationNode firstDeclaringParent = (ModuleDeclarationNode) PsiTreeUtil.findFirstParent(element.getParent(), (el) -> el instanceof ModuleDeclarationNode);
      if (firstDeclaringParent != null && element != firstDeclaringParent) {
        Collection<AbstractDeclarationNode> functionDeclarationNodes = PsiTreeUtil.findChildrenOfAnyType(firstDeclaringParent, FunctionDeclarationNode.class);
        Collection<AbstractDeclarationNode> found = functionDeclarationNodes.stream().filter((node) -> key.equals(node.getYonaFQN().functionName)).collect(Collectors.toList());
        if (!found.isEmpty()) {
          return found;
        } else {
          return getFirstNonEmptyDeclaringNode(firstDeclaringParent);
        }
      }
    }

    return Collections.EMPTY_LIST;
  }

  @Override
  protected boolean compareReference(YonaFQN reference, YonaFQN nodeFQN) {
    return reference.functionName.equals(nodeFQN.functionName);
  }

  @Override
  protected Function<PsiElement, Collection<FunctionDeclarationNode>> variantsGetter() {
    return YonaPsiUtil.finderOfDeclarationsOfType(FunctionDeclarationNode.class);
  }
}
