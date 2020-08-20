package org.yona.intellij.plugin;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class YonaFileType extends LanguageFileType {
  public static final YonaFileType INSTANCE = new YonaFileType();

  private YonaFileType() {
    super(YonaLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public String getName() {
    return "Yona File";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Yona language file";
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return "yona";
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return YonaIcons.FILE;
  }
}
