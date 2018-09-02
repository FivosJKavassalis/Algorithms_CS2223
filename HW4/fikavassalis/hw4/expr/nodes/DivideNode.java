package fikavassalis.hw4.expr.nodes;

public class DivideNode extends BinaryOperatorNode {

	public DivideNode(String op) {
		super(op);
	}
	
	/** Operator subclasses determine how to process given value. */
	public double value() {
		return left.value() / right.value();
	}

}

