package org.yona.intellij.plugin.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.PsiElementBase;
import com.intellij.psi.util.PsiTreeUtil;
import org.yona.intellij.plugin.YonaFQN;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public class YonaPsiUtil {
  public static Collection<AbstractDeclarationNode> findTopLevelDeclarations(PsiElement element) {
    Collection<AbstractDeclarationNode> topLevelDecls = PsiTreeUtil.findChildrenOfAnyType(element, ModuleDeclarationNode.class, FunctionDeclarationNode.class, RecordDeclarationNode.class);
    ArrayList<AbstractDeclarationNode> result = new ArrayList<>();

    YonaFQN lastFQN = null;

    // dedupe pattern match branches of functions
    for (AbstractDeclarationNode decl : topLevelDecls) {
      if (decl instanceof ModuleDeclarationNode) {
        result.add(decl);
      } else if (lastFQN == null || !lastFQN.equals(decl.getYonaFQN())) {
        result.add(decl);
        lastFQN = decl.getYonaFQN();
      }
    }

    return result;
  }

  public static <T extends PsiElementBase> Function<PsiElement, Collection<T>> finderOfDeclarationsOfType(Class<T> clz) {
    return (element) -> (Collection<T>) PsiTreeUtil.findChildrenOfAnyType(element, clz);
  }
}
