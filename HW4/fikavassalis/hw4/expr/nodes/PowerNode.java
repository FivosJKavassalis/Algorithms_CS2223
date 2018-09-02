package fikavassalis.hw4.expr.nodes;

public class PowerNode extends BinaryOperatorNode {

	public PowerNode(String op) {
		super(op);
	}
	
	/** Operator subclasses determine how to process given value. */
	public double value() {
		return Math.pow(left.value(),right.value());
	}

}


