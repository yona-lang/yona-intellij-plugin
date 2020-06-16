package org.yatta.intellij.plugin;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class YattaFile extends PsiFileBase {
  public YattaFile(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, YattaLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return YattaFileType.INSTANCE;
  }

  @Override
  public String toString() {
    return "Yatta File";
  }
}
