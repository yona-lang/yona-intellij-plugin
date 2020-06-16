package org.yatta.intellij.plugin;

import com.intellij.lang.Language;

public class YattaLanguage extends Language {
  public static final YattaLanguage INSTANCE = new YattaLanguage();

  private YattaLanguage() {
    super("Yatta");
  }
}
