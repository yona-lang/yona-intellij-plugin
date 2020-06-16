package org.yatta.intellij.plugin.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.yatta.intellij.plugin.YattaLanguage;

public class YattaTokenType extends IElementType {
  public YattaTokenType(@NotNull @NonNls String debugName) {
    super(debugName, YattaLanguage.INSTANCE);
  }

  @Override
  public String toString() {
    return "YattaTokenType." + super.toString();
  }
}
