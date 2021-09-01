package org.yona.intellij.plugin.psi;

import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import org.yona.intellij.plugin.YonaFQN;

public class FQNNode extends AbstractRefNode {
  private YonaFQN yonaFQN;

  public FQNNode(@NotNull ASTNode node) {
    super(node);
  }

  public YonaFQN getYonaFQN() {
    if (yonaFQN == null) {
      yonaFQN = YonaFQN.parse(getText());
    }

    return yonaFQN;
  }
}
