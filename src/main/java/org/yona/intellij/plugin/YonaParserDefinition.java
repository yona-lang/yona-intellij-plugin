package org.yona.intellij.plugin;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.intellij.adaptor.lexer.RuleIElementType;
import org.antlr.intellij.adaptor.lexer.TokenIElementType;
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor;
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jetbrains.annotations.NotNull;
import org.yona.intellij.plugin.psi.AliasSubtree;
import org.yona.intellij.plugin.psi.BlockSubtree;
import org.yona.intellij.plugin.psi.CallSubtree;
import org.yona.intellij.plugin.psi.FunctionSubtree;
import yona.parser.YonaLexer;
import yona.parser.YonaParser;

import java.util.List;

public class YonaParserDefinition implements ParserDefinition {
  public static final IFileElementType FILE = new IFileElementType(YonaLanguage.INSTANCE);

  public static TokenIElementType ID;

  static {
    PSIElementTypeFactory.defineLanguageIElementTypes(YonaLanguage.INSTANCE, YonaParser.tokenNames, YonaParser.ruleNames);
    List<TokenIElementType> tokenIElementTypes = PSIElementTypeFactory.getTokenIElementTypes(YonaLanguage.INSTANCE);
    ID = tokenIElementTypes.get(YonaLexer.LOWERCASE_NAME);
  }

  public static final TokenSet COMMENTS = PSIElementTypeFactory.createTokenSet(YonaLanguage.INSTANCE, YonaLexer.COMMENT);
  public static final TokenSet WHITESPACE = PSIElementTypeFactory.createTokenSet(YonaLanguage.INSTANCE, YonaLexer.WS);
  public static final TokenSet STRING = PSIElementTypeFactory.createTokenSet(YonaLanguage.INSTANCE, YonaLexer.CHARACTER_LITERAL);

  @NotNull
  @Override
  public Lexer createLexer(Project project) {
    YonaLexer lexer = new YonaLexer(null);
    return new ANTLRLexerAdaptor(YonaLanguage.INSTANCE, lexer);
  }

  @NotNull
  public PsiParser createParser(final Project project) {
    final YonaParser parser = new YonaParser(null);
    return new ANTLRParserAdaptor(YonaLanguage.INSTANCE, parser) {
      @Override
      protected ParseTree parse(Parser parser, IElementType root) {
        YonaParser yonaParser = (YonaParser) parser;
        if (root instanceof IFileElementType) {
          return yonaParser.input();
        }
        return yonaParser.expression();
      }
    };
  }

  @NotNull
  @Override
  public TokenSet getWhitespaceTokens() {
    return WHITESPACE;
  }

  @NotNull
  @Override
  public TokenSet getCommentTokens() {
    return COMMENTS;
  }

  @NotNull
  @Override
  public TokenSet getStringLiteralElements() {
    return STRING;
  }

  /**
   * What is the IFileElementType of the root parse tree node? It
   * is called from {@link #createFile(FileViewProvider)} at least.
   */
  @Override
  public IFileElementType getFileNodeType() {
    return FILE;
  }

  /**
   * Create the root of your PSI tree (a PsiFile).
   * <p>
   * From IntelliJ IDEA Architectural Overview:
   * "A PSI (Program Structure Interface) file is the root of a structure
   * representing the contents of a file as a hierarchy of elements
   * in a particular programming language."
   * <p>
   * PsiFile is to be distinguished from a FileASTNode, which is a parse
   * tree node that eventually becomes a PsiFile. From PsiFile, we can get
   * it back via: {@link PsiFile#getNode}.
   */
  @Override
  public PsiFile createFile(FileViewProvider viewProvider) {
    return new YonaFile(viewProvider);
  }

  /**
   * Convert from *NON-LEAF* parse node (AST they call it)
   * to PSI node. Leaves are created in the AST factory.
   * Rename re-factoring can cause this to be
   * called on a TokenIElementType since we want to rename ID nodes.
   * In that case, this method is called to create the root node
   * but with ID type. Kind of strange, but we can simply create a
   * ASTWrapperPsiElement to make everything work correctly.
   * <p>
   * RuleIElementType.  Ah! It's that ID is the root
   * IElementType requested to parse, which means that the root
   * node returned from parsetree->PSI conversion.  But, it
   * must be a CompositeElement! The adaptor calls
   * rootMarker.done(root) to finish off the PSI conversion.
   * See {@link ANTLRParserAdaptor#parse(IElementType root,
   * PsiBuilder)}
   * <p>
   * If you don't care to distinguish PSI nodes by type, it is
   * sufficient to create a {@link ANTLRPsiNode} around
   * the parse tree node
   */
  @NotNull
  public PsiElement createElement(ASTNode node) {
    IElementType elType = node.getElementType();
    if (elType instanceof TokenIElementType) {
      return new ANTLRPsiNode(node);
    }
    if (!(elType instanceof RuleIElementType)) {
      return new ANTLRPsiNode(node);
    }
    RuleIElementType ruleElType = (RuleIElementType) elType;
    switch (ruleElType.getRuleIndex()) {
      case YonaParser.RULE_function:
        return new FunctionSubtree(node, elType);
      case YonaParser.RULE_alias:
        return new AliasSubtree(node, elType);
      case YonaParser.RULE_doExpr:
      case YonaParser.RULE_let:
        return new BlockSubtree(node);
      case YonaParser.RULE_apply:
        return new CallSubtree(node);
      default:
        return new ANTLRPsiNode(node);
    }
  }
}
