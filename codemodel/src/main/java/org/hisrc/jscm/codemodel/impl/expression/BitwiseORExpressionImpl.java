package org.hisrc.jscm.codemodel.impl.expression;

import org.apache.commons.lang.Validate;
import org.hisrc.jscm.codemodel.JSCodeModel;
import org.hisrc.jscm.codemodel.JSOperator;
import org.hisrc.jscm.codemodel.expression.JSBitwiseORExpression;
import org.hisrc.jscm.codemodel.expression.JSBitwiseXORExpression;
import org.hisrc.jscm.codemodel.expression.JSExpressionVisitor;

public abstract class BitwiseORExpressionImpl extends LogicalANDExpressionImpl
		implements JSBitwiseORExpression {

	public BitwiseORExpressionImpl(JSCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	public Bor bor(JSBitwiseXORExpression value) {
		return new BorImpl(getCodeModel(), this, value);
	}

	public static class BorImpl extends BitwiseORExpressionImpl implements Bor {
		private final JSBitwiseORExpression left;
		private final JSOperator operator = new OperatorImpl("|");

		private final JSBitwiseXORExpression right;

		public BorImpl(JSCodeModel codeModel, JSBitwiseORExpression left,
				JSBitwiseXORExpression right) {
			super(codeModel);
			Validate.notNull(left);
			Validate.notNull(right);
			this.left = left;
			this.right = right;

		}

		public JSBitwiseORExpression getLeft() {
			return left;
		}
		
		public JSOperator getOperator() {
			return operator;
		}


		public JSBitwiseXORExpression getRight() {
			return right;
		}

		@Override
		public <V, E extends Exception> V acceptExpressionVisitor(
				JSExpressionVisitor<V, E> visitor) throws E {
			return visitor.visitBor(this);
		}

	}
}