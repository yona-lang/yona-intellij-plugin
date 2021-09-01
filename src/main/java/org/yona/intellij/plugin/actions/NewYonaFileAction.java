package org.yona.intellij.plugin.actions;

import com.intellij.ide.actions.CreateFileAction;
import org.jetbrains.annotations.Nullable;
import org.yona.intellij.plugin.YonaIcons;

public class NewYonaFileAction extends CreateFileAction {
  public NewYonaFileAction() {
    super("Yona File", "Create an empty Yona file", YonaIcons.FILE);
  }

  @Override
  protected @Nullable String getDefaultExtension() {
    return "yona";
  }
}
