package org.yona.intellij.plugin;

import com.intellij.lang.ASTFactory;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import yona.parser.YonaParser;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class YonaNodeFactory extends ASTFactory {
    private static final Map<IElementType, Class<? extends PsiElement>> ruleNodeCtors = new HashMap<>();

    static {
    }

    private static <T extends PsiElement> T newNodeInstance(Class<T> clz, ASTNode astNode) {
        if (clz != null) {
            try {
                return clz.getDeclaredConstructor(ASTNode.class).newInstance(astNode);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
            }
        }
        return null;
    }

    public static PsiElement createInternalParseTreeNode(ASTNode node) {
        IElementType tokenType = node.getElementType();

        PsiElement compositeNode = newNodeInstance(ruleNodeCtors.get(tokenType), node);
        if (compositeNode != null) {
            return compositeNode;
        }

        return new ANTLRPsiNode(node);
    }
}
