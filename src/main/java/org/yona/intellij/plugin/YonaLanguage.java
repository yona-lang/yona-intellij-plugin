package org.yona.intellij.plugin;

import com.intellij.lang.Language;

public class YonaLanguage extends Language {
  public static final YonaLanguage INSTANCE = new YonaLanguage();

  private YonaLanguage() {
    super("Yona");
  }
}
