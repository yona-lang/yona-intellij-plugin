package org.yona.intellij.plugin;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class YonaFQN {
  public final String[] packageParts;
  public final String moduleName;
  public final String functionName;

  private final static String REGEX = "^(?<packageParts>(?:[a-z_][a-zA-Z0-9_]*\\\\)*)(?<moduleName>[A-Z_][a-zA-Z0-9_]+)?(?<functionName>(([a-z_][a-zA-Z0-9_]*))|(::([a-z_][a-zA-Z0-9_]*)))?$";
  private final static Pattern PATTERN = Pattern.compile(REGEX);

  public YonaFQN(String[] packageParts, String moduleName, String functionName) {
    this.packageParts = packageParts;
    this.moduleName = moduleName;
    this.functionName = functionName;
  }

  public static YonaFQN parse(String fqn) {
    final Matcher matcher = PATTERN.matcher(fqn);
    if (!matcher.matches()) {
      System.err.println(fqn);
      return null;
    } else {
      return new YonaFQN(Arrays.stream(
          Objects.requireNonNullElse(matcher.group("packageParts"), "").split("\\\\")).filter(p -> !p.isEmpty()).toArray(String[]::new),
          matcher.group("moduleName"),
          Objects.requireNonNullElse(matcher.group("functionName"), "").replace("::", "")
      );
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    YonaFQN yonaFQN = (YonaFQN) o;
    return Arrays.equals(packageParts, yonaFQN.packageParts) && Objects.equals(moduleName, yonaFQN.moduleName) && Objects.equals(functionName, yonaFQN.functionName);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(moduleName, functionName);
    result = 31 * result + Arrays.hashCode(packageParts);
    return result;
  }

  public YonaFQN withoutFunctionName() {
    return new YonaFQN(packageParts, moduleName, null);
  }
}
