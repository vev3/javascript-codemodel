package org.hisrc.jscm.codemodel.statement.impl;

import org.hisrc.jscm.codemodel.JSCodeModel;
import org.hisrc.jscm.codemodel.expression.JSExpression;
import org.hisrc.jscm.codemodel.expression.JSLeftHandSideExpression;
import org.hisrc.jscm.codemodel.lang.Validate;
import org.hisrc.jscm.codemodel.statement.JSForInStatement;
import org.hisrc.jscm.codemodel.statement.JSStatement;
import org.hisrc.jscm.codemodel.statement.JSStatementVisitor;

public class ForInStatementImpl extends IterationStatementImpl implements
		JSForInStatement {

	private final JSLeftHandSideExpression expression;
	private final JSExpression _in;

	public ForInStatementImpl(JSCodeModel codeModel,
			JSLeftHandSideExpression expression, JSExpression _in) {
		this(codeModel, expression, _in, new EmptyStatementImpl(codeModel));
	}

	public ForInStatementImpl(JSCodeModel codeModel,
			JSLeftHandSideExpression expression, JSExpression _in,
			JSStatement statement) {
		super(codeModel, statement);
		Validate.notNull(expression);
		Validate.notNull(_in);
		this.expression = expression;
		this._in = _in;
	}

	@Override
	public JSLeftHandSideExpression getExpression() {
		return expression;
	}

	public JSExpression getIn() {
		return _in;
	}

	@Override
	public <V, E extends Exception> V acceptStatementVisitor(
			JSStatementVisitor<V, E> visitor) throws E {
		return visitor.visitForIn(this);
	}

}
