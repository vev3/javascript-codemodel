package org.hisrc.jscm.codemodel.statement;

import org.hisrc.jscm.codemodel.statement.JSLabelledStatement.JSLabel;

public interface JSContinueStatement extends JSStatement {

	public JSLabel getLabel();
}