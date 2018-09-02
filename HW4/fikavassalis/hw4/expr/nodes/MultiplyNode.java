package fikavassalis.hw4.expr.nodes;

public class MultiplyNode extends BinaryOperatorNode {

	public MultiplyNode(String op) {
		super(op);
	}
	
	/** Operator subclasses determine how to process given value. */
	public double value() {
		return left.value() * right.value();
	}

}

