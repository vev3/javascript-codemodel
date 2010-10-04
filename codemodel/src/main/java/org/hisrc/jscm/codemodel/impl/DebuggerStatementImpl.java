package org.hisrc.jscm.codemodel.impl;

import org.hisrc.jscm.codemodel.JSCodeModel;
import org.hisrc.jscm.codemodel.JSDebuggerStatement;
import org.hisrc.jscm.codemodel.JSStatementVisitor;

public class DebuggerStatementImpl extends StatementImpl implements
		JSDebuggerStatement {

	public DebuggerStatementImpl(JSCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	public <V, E extends Exception> V acceptStatementVisitor(
			JSStatementVisitor<V, E> visitor) throws E {
		return visitor.visitDebugger(this);
	}

}
