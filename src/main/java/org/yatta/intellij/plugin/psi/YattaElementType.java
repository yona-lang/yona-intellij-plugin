package org.yatta.intellij.plugin.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.yatta.intellij.plugin.YattaLanguage;

public class YattaElementType extends IElementType {
  public YattaElementType(@NotNull @NonNls String debugName) {
    super(debugName, YattaLanguage.INSTANCE);
  }
}
