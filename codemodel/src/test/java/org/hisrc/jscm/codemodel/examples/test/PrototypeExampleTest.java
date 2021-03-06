package org.hisrc.jscm.codemodel.examples.test;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.io.IOUtils;
import org.hisrc.jscm.codemodel.JSCodeModel;
import org.hisrc.jscm.codemodel.JSFunctionBody;
import org.hisrc.jscm.codemodel.JSProgram;
import org.hisrc.jscm.codemodel.expression.JSFunctionExpression.Function;
import org.hisrc.jscm.codemodel.expression.JSGlobalVariable;
import org.hisrc.jscm.codemodel.expression.JSObjectLiteral;
import org.hisrc.jscm.codemodel.expression.JSVariable;
import org.hisrc.jscm.codemodel.impl.CodeModelImpl;
import org.hisrc.jscm.codemodel.writer.CodeWriter;
import org.junit.Assert;
import org.junit.Test;

public class PrototypeExampleTest {

	private final JSCodeModel codeModel = new CodeModelImpl();

	@Test
	public void programsPrototype() throws IOException {
		JSProgram program = codeModel.program();

		JSGlobalVariable $Object = codeModel.globalVariable("Object");
		JSGlobalVariable $navigator = codeModel.globalVariable("navigator");
		JSGlobalVariable $window = codeModel.globalVariable("window");
		JSGlobalVariable $document = codeModel.globalVariable("document");

		JSObjectLiteral _Prototype = codeModel.object();
		program.var("Prototype", _Prototype);

		{
			_Prototype.append("Version", codeModel.string("1.6.1"));
		}
		{
			final Function function = codeModel.function();
			JSFunctionBody body = function.getBody();
			JSVariable $ua = body.var("ua",
					$navigator.p("userAgent")).getVariable();
			JSVariable $isOpera = body.var(
					"isOpera",
					$Object.p("prototype").p("toString")
							.i("call").args($window.p("opera"))
							.eq(codeModel.string("[object Opera]")))
					.getVariable();
			body._return(codeModel
					.object()
					.append("IE",
							$window.p("attachEvent").not().not()
									.and($isOpera.not()))
					.append("Opera", $isOpera)
					.append("WebKit",
							$ua.i("indexOf")
									.args(codeModel.string("AppleWebKit/"))
									.gt(codeModel.integer(-1)))
					.append("Gecko",
							$ua.i("indexOf")
									.args(codeModel.string("Gecko"))
									.gt(codeModel.integer(-1))
									.and($ua.i("indexOf")
											.args(codeModel.string("KHTML"))
											.eeq(codeModel.integer(-1))))
			// Regexps are not supported at the moment
			// append("MobileSafari",
			// codeModel.regexp("/Apple.*Mobile.*Safari/").call("test").args($ua))
			);

			_Prototype.append("Browser", function.brackets().i());
		}

		{
			final JSObjectLiteral _BrowserFeatures = codeModel.object();
			_Prototype.append("BrowserFeatures", _BrowserFeatures);

			_BrowserFeatures.append("XPath", $document.p("evaluate")
					.not().not());
			_BrowserFeatures.append("SelectorsAPI",
					$document.p("querySelector").not().not());
			{
				final Function _ElementExtensions = codeModel.function();

				JSVariable $constructor = _ElementExtensions
						.getBody()
						.var(
								"constructor",
								$window.p("Element").or(
										$window.p("HTMLElement")))
						.getVariable();
				_ElementExtensions.getBody()._return(
						$constructor.and($constructor.p("prototype"))
								.brackets().not().not());
				_BrowserFeatures.append("ElementExtensions", _ElementExtensions
						.brackets().i());
			}

			{
				final Function f = codeModel.function();
				_BrowserFeatures.append("SpecificElementExtensions", f
						.brackets().i());
				JSFunctionBody b = f.getBody();
				b._if($window.p("HTMLDivElement").typeof()
						.nee(codeModel.string("undefined")))._then()
						._return(codeModel._boolean(true));

				JSVariable $div = b.var(
						"div",
						$document.i("createElement").args(
								codeModel.string("div"))).getVariable();
				JSVariable $form = b.var(
						"form",
						$document.i("createElement").args(
								codeModel.string("form"))).getVariable();

				JSVariable $isSupported = b.var("isSupported",
						codeModel._boolean(false)).getVariable();

				b._if($div.e(codeModel.string("__proto__")).and(
						$div.e(codeModel.string("__proto__"))
								.nee($form.e(codeModel
										.string("__proto__"))).brackets()))
						._then()
						.block()
						.expression(
								$isSupported.assign(codeModel._boolean(true)));

				b.expression($div.assign($form.assign(codeModel._null())));
				b._return($isSupported);
			}

		}
		{
			_Prototype.append("ScriptFragment",
					codeModel.string("<script[^>]*>([\\S\\s]*?)</script>"));
			// <script[^>]*>([\\S\\s]*?)<\/script>
		}
		{
			// o.append("JSONFilter",
			// codeModel.regexp("/^\\/\\*-secure-([\\s\\S]*)\\*\\/\\s*$/"));
		}
		{
			_Prototype.append("emptyFunction", codeModel.function());
		}
		{
			final Function _K = codeModel.function();
			JSVariable $x = _K.parameter("x");
			_K.getBody()._return($x);
			_Prototype.append("K", _K);
		}

		final CodeWriter systemOutCodeWriter = new CodeWriter(System.out);
		systemOutCodeWriter.program(program);

		final Writer stringWriter = new StringWriter();
		final CodeWriter stringCodeWriter = new CodeWriter(stringWriter);
		stringCodeWriter.program(program);
		stringWriter.close();

		String test = IOUtils.toString(getClass().getResourceAsStream(
				"Prototype[0].test.js"));
		String sample = stringWriter.toString();

		// Assert.assertEquals(test.length(), sample.length());
		Assert.assertEquals(test, sample);

	}
}
