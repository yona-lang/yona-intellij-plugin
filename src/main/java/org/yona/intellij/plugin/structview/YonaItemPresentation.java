package org.yona.intellij.plugin.structview;

import com.intellij.navigation.ItemPresentation;
import org.jetbrains.annotations.Nullable;
import org.yona.intellij.plugin.psi.AbstractDeclarationNode;

import javax.swing.*;

public class YonaItemPresentation implements ItemPresentation {
  protected final AbstractDeclarationNode element;
  private final Icon icon;

  public YonaItemPresentation(AbstractDeclarationNode element, Icon icon) {
    this.element = element;
    this.icon = icon;
  }

  @Nullable
  @Override
  public Icon getIcon(boolean unused) {
    return this.icon;
  }

  @Nullable
  @Override
  public String getPresentableText() {
    return element.getName();
  }

  @Override
  @Nullable
  public String getLocationString() {
    return null;
  }
}
