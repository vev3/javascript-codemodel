package org.hisrc.jscm.codemodel.expression.impl;

import org.hisrc.jscm.codemodel.JSCodeModel;
import org.hisrc.jscm.codemodel.expression.JSEqualityExpression;
import org.hisrc.jscm.codemodel.expression.JSExpressionVisitor;
import org.hisrc.jscm.codemodel.expression.JSRelationalExpression;
import org.hisrc.jscm.codemodel.lang.Validate;
import org.hisrc.jscm.codemodel.operator.impl.EqualityOperator;

public abstract class EqualityExpressionImpl extends BitwiseANDExpressionImpl
		implements JSEqualityExpression {

	public EqualityExpressionImpl(JSCodeModel codeModel) {
		super(codeModel);
	}

	public Equality eq(JSRelationalExpression value) {
		return new EqualityImpl(getCodeModel(), this, value,
				EqualityOperator.EQ);
	}

	public Equality ne(JSRelationalExpression value) {
		return new EqualityImpl(getCodeModel(), this, value,
				EqualityOperator.NE);
	}

	public Equality eeq(JSRelationalExpression value) {
		return new EqualityImpl(getCodeModel(), this, value,
				EqualityOperator.EEQ);
	}

	public Equality nee(JSRelationalExpression value) {
		return new EqualityImpl(getCodeModel(), this, value,
				EqualityOperator.NEE);
	}

	public static class EqualityImpl extends EqualityExpressionImpl implements
			Equality {

		private final JSEqualityExpression left;
		private final JSRelationalExpression right;
		private final EqualityOperator operator;

		public EqualityImpl(JSCodeModel codeModel, JSEqualityExpression left,
				JSRelationalExpression right,
				EqualityOperator operator) {
			super(codeModel);
			Validate.notNull(left);
			Validate.notNull(right);
			Validate.notNull(operator);
			this.left = left;
			this.right = right;
			this.operator = operator;
		}

		public JSEqualityExpression getLeft() {
			return left;
		}

		public JSRelationalExpression getRight() {
			return right;
		}

		public EqualityOperator getOperator() {
			return operator;
		}

		@Override
		public <V, E extends Exception> V acceptExpressionVisitor(
				JSExpressionVisitor<V, E> visitor) throws E {
			return visitor.visitEquality(this);
		}
	}

}
