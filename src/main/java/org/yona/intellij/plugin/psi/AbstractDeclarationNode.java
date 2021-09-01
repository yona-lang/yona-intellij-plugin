package org.yona.intellij.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.impl.PsiFileFactoryImpl;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.yona.intellij.plugin.YonaFQN;
import org.yona.intellij.plugin.YonaLanguage;

public abstract class AbstractDeclarationNode extends NamedDeclaration implements PsiNameIdentifierOwner {
  protected String name = null; // an override to input text ID

  public AbstractDeclarationNode(@NotNull final ASTNode node) {
    super(node);
  }

  @NotNull
  @Override
  public String getName() {
    if (name != null) return name;
    AbstractRefNode id = getNameIdentifier();
    if (id != null) {
      return id.getText();
    } else {
      return "unknown";
    }
  }

  public abstract AbstractRefNode getNameIdentifier();

  @Override
  public PsiElement setName(@NonNls @NotNull String name) throws IncorrectOperationException {
		/*
		From doc: "Creating a fully correct AST node from scratch is
		          quite difficult. Thus, surprisingly, the easiest way to
		          get the replacement node is to create a dummy file in the
		          custom language so that it would contain the necessary
		          node in its parse tree, build the parse tree and
		          extract the necessary node from it.
		 */
    AbstractRefNode id = getNameIdentifier();
    PsiFileFactoryImpl factory = (PsiFileFactoryImpl) PsiFileFactory.getInstance(getProject());
    PsiElement el = factory.createElementFromText(name, YonaLanguage.INSTANCE, getRuleRefType(), getContext());
    id.replace(PsiTreeUtil.getDeepestFirst(el));
    this.name = name;
    return this;
  }

  public abstract IElementType getRuleRefType();

  @Override
  public void subtreeChanged() {
    super.subtreeChanged();
    name = null;
  }

  @Override
  public int getTextOffset() {
    return getNameIdentifier().getTextOffset();
  }

  public abstract YonaFQN getYonaFQN();
}
