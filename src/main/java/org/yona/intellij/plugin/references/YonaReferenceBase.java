package org.yona.intellij.plugin.references;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yona.intellij.plugin.YonaFQN;
import org.yona.intellij.plugin.YonaUtil;
import org.yona.intellij.plugin.psi.AbstractDeclarationNode;
import org.yona.intellij.plugin.psi.NamedDeclaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public abstract class YonaReferenceBase extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
  protected final String key;

  public YonaReferenceBase(@NotNull PsiElement element, TextRange rangeInElement) {
    super(element, rangeInElement);
    key = element.getText();
  }

  protected abstract Collection<AbstractDeclarationNode> getFirstNonEmptyDeclaringNode(PsiElement element);

  @Override
  public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
    Collection<AbstractDeclarationNode> declarationNodes = getFirstNonEmptyDeclaringNode(myElement);
    if (!declarationNodes.isEmpty()) {
      YonaFQN yonaFQN = YonaFQN.parse(key);
      return declarationNodes.stream().filter((node) -> compareReference(yonaFQN, node.getYonaFQN())).map(PsiElementResolveResult::new).toArray(PsiElementResolveResult[]::new);
    }
    return ResolveResult.EMPTY_ARRAY;
  }

  @Override
  public @Nullable PsiElement resolve() {
    ResolveResult[] resolveResults = multiResolve(false);
    return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
  }

  protected abstract <T extends NamedDeclaration> Function<PsiElement, Collection<T>> variantsGetter();

  protected abstract boolean compareReference(YonaFQN reference, YonaFQN nodeFQN);

  @Override
  public Object @NotNull [] getVariants() {
    Project project = myElement.getProject();
    Collection<NamedDeclaration> declarationNodes = YonaUtil.findProjectTopLevelDeclarations(project, variantsGetter());
    List<LookupElement> variants = new ArrayList<>();
    for (final NamedDeclaration node : declarationNodes) {
      variants.add(LookupElementBuilder
          .create(node).withIcon(node.getPresentation().getIcon(false))
          .withTypeText(node.getContainingFile().getName())
      );
    }
    return variants.toArray();
  }
}
