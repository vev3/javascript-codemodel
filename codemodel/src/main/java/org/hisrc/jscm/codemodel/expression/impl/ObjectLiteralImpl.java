package org.hisrc.jscm.codemodel.expression.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.hisrc.jscm.codemodel.JSCodeModel;
import org.hisrc.jscm.codemodel.JSPropertyName;
import org.hisrc.jscm.codemodel.expression.JSAssignmentExpression;
import org.hisrc.jscm.codemodel.expression.JSExpressionVisitor;
import org.hisrc.jscm.codemodel.expression.JSObjectLiteral;
import org.hisrc.jscm.codemodel.impl.IdentifierNameImpl;

public class ObjectLiteralImpl extends PrimaryExpressionImpl implements
		JSObjectLiteral {

	private final List<JSPropertyAssignment> propertyAssignments = new ArrayList<JSPropertyAssignment>();
	private final List<JSPropertyAssignment> unmodifiablePropertyAssignments = Collections
			.unmodifiableList(propertyAssignments);

	public ObjectLiteralImpl(JSCodeModel codeModel) {
		super(codeModel);
	}

	@Override
	public List<JSPropertyAssignment> getPropertyAssignments() {
		return unmodifiablePropertyAssignments;
	}

	@Override
	public JSObjectLiteral append(String name, JSAssignmentExpression expression) {
		return append(new IdentifierNameImpl(name), expression);
	}

	@Override
	public JSObjectLiteral append(JSPropertyName name,
			JSAssignmentExpression value) {
		this.propertyAssignments.add(new PropertyAssignmentImpl(name, value));
		return this;
	}

	@Override
	public <V, E extends Exception> V acceptExpressionVisitor(
			JSExpressionVisitor<V, E> visitor) throws E {
		return visitor.visitObjectLiteral(this);
	}

	public static class PropertyAssignmentImpl implements JSPropertyAssignment {
		private final JSPropertyName name;
		private final JSAssignmentExpression value;

		public PropertyAssignmentImpl(JSPropertyName name,
				JSAssignmentExpression value) {
			Validate.notNull(name);
			Validate.notNull(value);
			this.name = name;
			this.value = value;
		}

		@Override
		public JSPropertyName getKey() {
			return name;
		}

		@Override
		public JSAssignmentExpression getValue() {
			return value;
		}

	}

}