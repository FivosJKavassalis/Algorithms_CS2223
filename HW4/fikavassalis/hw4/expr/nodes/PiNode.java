package fikavassalis.hw4.expr.nodes;

public class PiNode extends NoParameterOperatorNode {

	public PiNode(String op) {
		super(op);
	}
	
	/** No Parameter operator returns its value. */
	public double value() {
		return Math.PI;
	}

}
