package fikavassalis.hw4.expr.nodes;

public class TriangleNode extends UnaryOperatorNode {


	public TriangleNode(String op) {
		super(op);
	}
	
	/** Operator subclasses determine how to process given value. */
	public double value() {
		return left.value() * 3.5  ; // or 7/2 ...
	}

}
