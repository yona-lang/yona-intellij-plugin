package org.yona.intellij.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.PsiElementBase;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.yona.intellij.plugin.psi.AbstractDeclarationNode;
import org.yona.intellij.plugin.psi.YonaPsiUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class YonaUtil {
  public static Collection<AbstractDeclarationNode> findProjectTopLevelDeclarations(Project project) {
    List<AbstractDeclarationNode> result = new ArrayList<>();
    Collection<VirtualFile> virtualFiles =
        FileTypeIndex.getFiles(YonaFileType.INSTANCE, GlobalSearchScope.projectScope(project));
    for (VirtualFile virtualFile : virtualFiles) {
      YonaFile nstFile = (YonaFile) PsiManager.getInstance(project).findFile(virtualFile);
      if (nstFile != null) {
        result.addAll(YonaPsiUtil.findTopLevelDeclarations(nstFile));
      }
    }
    return result;
  }

  public static <T extends PsiElementBase> Collection<T> findProjectTopLevelDeclarations(Project project, Function<PsiElement, Collection<T>> getter) {
    List<T> result = new ArrayList<>();
    Collection<VirtualFile> virtualFiles =
        FileTypeIndex.getFiles(YonaFileType.INSTANCE, GlobalSearchScope.projectScope(project));
    for (VirtualFile virtualFile : virtualFiles) {
      YonaFile nstFile = (YonaFile) PsiManager.getInstance(project).findFile(virtualFile);
      if (nstFile != null) {
        result.addAll(getter.apply(nstFile));
      }
    }
    return result;
  }
}
