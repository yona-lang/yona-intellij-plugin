package org.yona.intellij.plugin.references;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.yona.intellij.plugin.psi.FunctionCallNode;
import org.yona.intellij.plugin.psi.ModuleCallNode;

public class YonaReferenceContributor extends PsiReferenceContributor {
  @Override
  public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
    registrar.registerReferenceProvider(
        PlatformPatterns.psiElement(ModuleCallNode.class),
        new PsiReferenceProvider() {
          @Override
          public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
            return new PsiReference[]{new ModuleCallReference(element, new TextRange(0, element.getText().length()))};
          }
        });
    registrar.registerReferenceProvider(
        PlatformPatterns.psiElement(FunctionCallNode.class),
        new PsiReferenceProvider() {
          @Override
          public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
            return new PsiReference[]{new FunctionCallReference(element, new TextRange(0, element.getText().length()))};
          }
        });
  }
}
