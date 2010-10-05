package org.hisrc.jscm.codemodel.statement;

import org.hisrc.jscm.codemodel.expression.JSExpression;

public interface JSForStatement extends JSIterationStatement {

	public JSExpression getExpression();

	public JSExpression getTest();

	public JSExpression getUpdate();

	public JSForStatement test(JSExpression expression);

	public JSForStatement update(JSExpression expression);
}
