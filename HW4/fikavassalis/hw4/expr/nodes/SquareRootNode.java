package fikavassalis.hw4.expr.nodes;

public class SquareRootNode extends UnaryOperatorNode {

	public SquareRootNode(String op) {
		super(op);
	}
	
	/** Operator subclasses determine how to process given value. */
	public double value() {
		return Math.sqrt(left.value());
	}

}

 