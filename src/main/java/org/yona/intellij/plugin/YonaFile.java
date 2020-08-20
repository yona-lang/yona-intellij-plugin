package org.yona.intellij.plugin;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class YonaFile extends PsiFileBase {
  public YonaFile(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, YonaLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return YonaFileType.INSTANCE;
  }

  @Override
  public String toString() {
    return "Yona File";
  }
}
