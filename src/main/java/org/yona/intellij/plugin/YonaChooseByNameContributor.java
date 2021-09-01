package org.yona.intellij.plugin;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.yona.intellij.plugin.psi.AbstractDeclarationNode;

import java.util.Collection;

public class YonaChooseByNameContributor implements ChooseByNameContributor {
  @Override
  public String @NotNull [] getNames(Project project, boolean includeNonProjectItems) {
    Collection<AbstractDeclarationNode> declarations = YonaUtil.findProjectTopLevelDeclarations(project);
    return declarations.stream().map(AbstractDeclarationNode::getName).toArray(String[]::new);
  }

  @Override
  public NavigationItem @NotNull [] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
    Collection<AbstractDeclarationNode> declarations = YonaUtil.findProjectTopLevelDeclarations(project);
    return declarations.stream().filter(d -> d.getName().equals(name)).toArray(NavigationItem[]::new);
  }
}
