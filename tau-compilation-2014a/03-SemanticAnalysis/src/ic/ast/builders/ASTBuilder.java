/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

package ic.ast.builders;

import fun.parser.Tree;
import ic.ast.Node;
import ic.ast.builders.decl.ClassTypeBuilder;
import ic.ast.builders.decl.DeclClassBuilder;
import ic.ast.builders.decl.DeclFieldBuilder;
import ic.ast.builders.decl.DeclLibraryMethodBuilder;
import ic.ast.builders.decl.DeclMethodBuilder;
import ic.ast.builders.decl.DeclStaticMethodBuilder;
import ic.ast.builders.decl.DeclVirtualMethodBuilder;
import ic.ast.builders.decl.ParameterBuilder;
import ic.ast.builders.decl.PrimitiveTypeBuilder;
import ic.ast.builders.decl.ProgramBuilder;
import ic.ast.builders.decl.TypeBuilder;
import ic.ast.builders.expr.BinaryOpBuilder;
import ic.ast.builders.expr.CallBuilder;
import ic.ast.builders.expr.ExpressionBlockBuilder;
import ic.ast.builders.expr.ExpressionBuilder;
import ic.ast.builders.expr.ExpressionCloseBuilder;
import ic.ast.builders.expr.LengthBuilder;
import ic.ast.builders.expr.LiteralBuilder;
import ic.ast.builders.expr.NewArrayBuilder;
import ic.ast.builders.expr.NewBuilder;
import ic.ast.builders.expr.NewInstanceBuilder;
import ic.ast.builders.expr.RefArrayElementBuilder;
import ic.ast.builders.expr.RefBuilder;
import ic.ast.builders.expr.RefFieldBuilder;
import ic.ast.builders.expr.RefVariableBuilder;
import ic.ast.builders.expr.StaticCallBuilder;
import ic.ast.builders.expr.ThisBuilder;
import ic.ast.builders.expr.UnaryOpBuilder;
import ic.ast.builders.expr.VirtualCallBuilder;
import ic.ast.builders.stmt.LocalVariableBuilder;
import ic.ast.builders.stmt.StatementBuilder;
import ic.ast.builders.stmt.StmtAssignmentBuilder;
import ic.ast.builders.stmt.StmtBlockBuilder;
import ic.ast.builders.stmt.StmtBreakBuilder;
import ic.ast.builders.stmt.StmtCallBuilder;
import ic.ast.builders.stmt.StmtContinueBuilder;
import ic.ast.builders.stmt.StmtIfBuilder;
import ic.ast.builders.stmt.StmtReturnBuilder;
import ic.ast.builders.stmt.StmtWhileBuilder;
import ic.syntax.SyntaxException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ASTBuilder {

	private static ASTBuilder singleBuilder = null;
	
	private Map<String, ASTNodeBuilder> builders = null;
	
	private ASTBuilder() {
		
		builders = new HashMap<String, ASTNodeBuilder>();
		
		// Load all builders
		this.registerBuilder(new ClassTypeBuilder());
		this.registerBuilder(new DeclClassBuilder());
		this.registerBuilder(new DeclFieldBuilder());
		this.registerBuilder(new DeclMethodBuilder());
		this.registerBuilder(new DeclStaticMethodBuilder());
		this.registerBuilder(new DeclVirtualMethodBuilder());
		this.registerBuilder(new DeclLibraryMethodBuilder());
		this.registerBuilder(new ParameterBuilder());
		this.registerBuilder(new PrimitiveTypeBuilder());
		this.registerBuilder(new ProgramBuilder());
		this.registerBuilder(new TypeBuilder());
		
		this.registerBuilder(new BinaryOpBuilder());
		this.registerBuilder(new CallBuilder());
		this.registerBuilder(new ExpressionBlockBuilder());
		this.registerBuilder(new ExpressionBuilder());
		this.registerBuilder(new ExpressionCloseBuilder());
		this.registerBuilder(new LengthBuilder());
		this.registerBuilder(new LiteralBuilder());
		this.registerBuilder(new NewArrayBuilder());
		this.registerBuilder(new NewBuilder());
		this.registerBuilder(new NewInstanceBuilder());
		this.registerBuilder(new RefArrayElementBuilder());
		this.registerBuilder(new RefBuilder());
		this.registerBuilder(new RefFieldBuilder());
		this.registerBuilder(new RefVariableBuilder());
		this.registerBuilder(new StaticCallBuilder());
		this.registerBuilder(new ThisBuilder());
		this.registerBuilder(new UnaryOpBuilder());
		this.registerBuilder(new VirtualCallBuilder());
		
		this.registerBuilder(new LocalVariableBuilder());
		this.registerBuilder(new StatementBuilder());
		this.registerBuilder(new StmtAssignmentBuilder());
		this.registerBuilder(new StmtBlockBuilder());
		this.registerBuilder(new StmtBreakBuilder());
		this.registerBuilder(new StmtCallBuilder());
		this.registerBuilder(new StmtContinueBuilder());
		this.registerBuilder(new StmtIfBuilder());
		this.registerBuilder(new StmtReturnBuilder());
		this.registerBuilder(new StmtWhileBuilder());
	}
	
	private void registerBuilder(ASTNodeBuilder builder) {
		this.builders.put(builder.getParseTreeTag(), builder);
	}
	
	public static ASTBuilder getBuilder() {
		if (singleBuilder == null) {
			singleBuilder = new ASTBuilder();
		}
		
		return (singleBuilder);
	}
	
	public Node build(Tree parsingTree) throws SyntaxException {
		if (parsingTree == null)
			return (null);
		
		if (!this.builders.containsKey(parsingTree.root.tag))
			throw new Error("internal error; AST builder not found for: " + parsingTree.root.tag);
		
		return (this.builders.get(parsingTree.root.tag).Build(parsingTree, this));
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Node> T build(Tree parsingTree, Class<T> type) throws SyntaxException {
		Node node = this.build(parsingTree);
		
		return (node != null ? (T)node : null);
	}
	
	public <T extends Node> List<T> build(Iterable<Tree> parsingTrees, Class<T> type) throws SyntaxException {
		if (parsingTrees == null)
			return (null);
		
		List<T> results = new ArrayList<T>();
		
		for (Tree currTree : parsingTrees) {
			results.add(this.build(currTree, type));
		}
		
		return (results);
	}
	
	public <T extends Node> List<T> depthBuild(Tree startTree, 
			String[] expands, String[] buildTags, Class<T> type) throws SyntaxException {
		
		List<String> expandsList = Arrays.asList(expands);
		List<String> buildTagsList = Arrays.asList(buildTags);
		
		List<T> results = new ArrayList<T>();
		Queue<Tree> treesQueue = new LinkedList<Tree>();
		treesQueue.add(startTree);
		
		while (!treesQueue.isEmpty()) {
			
			Tree currTree = treesQueue.poll();
			
			if (expandsList.contains(currTree.root.tag)) {
				treesQueue.addAll(currTree.subtrees);
			}
			else if (buildTagsList.contains(currTree.root.tag)) {
				results.add(this.build(currTree, type));
			}
		}
		
		return (results);
	}

}
