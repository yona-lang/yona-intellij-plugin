package org.yatta.intellij.plugin;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class YattaFileType extends LanguageFileType {
  public static final YattaFileType INSTANCE = new YattaFileType();

  private YattaFileType() {
    super(YattaLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public String getName() {
    return "Yatta File";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Yatta language file";
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return "yatta";
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return YattaIcons.FILE;
  }
}
