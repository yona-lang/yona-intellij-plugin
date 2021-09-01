package org.yona.intellij.plugin.structview;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.yona.intellij.plugin.YonaFile;
import org.yona.intellij.plugin.psi.AbstractDeclarationNode;
import org.yona.intellij.plugin.psi.ModuleDeclarationNode;
import org.yona.intellij.plugin.psi.YonaPsiUtil;

import java.util.Arrays;
import java.util.Collection;

public class YonaStructureViewElement implements StructureViewTreeElement, SortableTreeElement {
  protected final NavigatablePsiElement element;

  public YonaStructureViewElement(NavigatablePsiElement element) {
    this.element = element;
  }

  @Override
  public Object getValue() {
    return element;
  }

  @Override
  public void navigate(boolean requestFocus) {
    element.navigate(requestFocus);
  }

  @Override
  public boolean canNavigate() {
    return element.canNavigate();
  }

  @Override
  public boolean canNavigateToSource() {
    return element.canNavigateToSource();
  }

  @NotNull
  @Override
  public String getAlphaSortKey() {
    String name = element.getName();
    return name != null ? name : "";
  }

  @NotNull
  @Override
  public ItemPresentation getPresentation() {
    ItemPresentation presentation = element.getPresentation();
    return presentation != null ? presentation : new PresentationData();
  }

  @Override
  public TreeElement @NotNull [] getChildren() {
    TreeElement[] result = TreeElement.EMPTY_ARRAY;
    if (element instanceof YonaFile) {
      Collection<AbstractDeclarationNode> subtrees = PsiTreeUtil.findChildrenOfAnyType(element, ModuleDeclarationNode.class);
      result = subtrees.stream().map(YonaStructureViewElement::new).flatMap(el -> Arrays.stream(el.getChildren())).toArray(TreeElement[]::new);
    } else if (element instanceof ModuleDeclarationNode) {
      Collection<AbstractDeclarationNode> subtrees = YonaPsiUtil.findTopLevelDeclarations(element);
      result = subtrees.stream().map(YonaStructureViewElement::new).toArray(TreeElement[]::new);
    }

    return result;
  }
}
