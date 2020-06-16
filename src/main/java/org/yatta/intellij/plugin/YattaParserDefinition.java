package org.yatta.intellij.plugin;

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
import org.yatta.intellij.plugin.parser.YattaLexer;
import org.yatta.intellij.plugin.parser.YattaParser;

public class YattaParserDefinition implements ParserDefinition {
  public static final IFileElementType FILE = new IFileElementType(YattaLanguage.INSTANCE);

  static {
    PSIElementTypeFactory.defineLanguageIElementTypes(YattaLanguage.INSTANCE, YattaParser.tokenNames, YattaParser.ruleNames);
  }

  public static final TokenSet COMMENTS = PSIElementTypeFactory.createTokenSet(YattaLanguage.INSTANCE, YattaLexer.COMMENT);
  public static final TokenSet WHITESPACE = PSIElementTypeFactory.createTokenSet(YattaLanguage.INSTANCE, YattaLexer.WS, YattaLexer.NEWLINE, YattaLexer.HIDDEN);
  public static final TokenSet STRING = PSIElementTypeFactory.createTokenSet(YattaLanguage.INSTANCE, YattaLexer.CHARACTER_LITERAL);

  @NotNull
  @Override
  public Lexer createLexer(Project project) {
    YattaLexer lexer = new YattaLexer(null);
    return new ANTLRLexerAdaptor(YattaLanguage.INSTANCE, lexer);
  }

  @NotNull
  public PsiParser createParser(final Project project) {
    final YattaParser parser = new YattaParser(null);
    return new ANTLRParserAdaptor(YattaLanguage.INSTANCE, parser) {
      @Override
      protected ParseTree parse(Parser parser, IElementType root) {
        YattaParser yattaParser = (YattaParser) parser;
        if (root instanceof IFileElementType) {
          return yattaParser.input();
        }
        return yattaParser.expression();
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
    return new YattaFile(viewProvider);
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
      default:
        return new ANTLRPsiNode(node);
    }
  }
}
