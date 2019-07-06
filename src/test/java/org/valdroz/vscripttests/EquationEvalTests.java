package org.valdroz.vscripttests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.valdroz.vscript.DefaultVariantContainer;
import org.valdroz.vscript.EquationEval;
import org.valdroz.vscript.Variant;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EquationEvalTests {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void textEquationEvaluator() {
        DefaultVariantContainer container = new DefaultVariantContainer();
        container.setVariant("movie.price", new Variant(5));

        Variant var = new EquationEval("3 + movie.price * sqrt(4)").eval(container);

        assertThat("Expected 13", var.getDouble(), is(13.0));
    }

    @Test
    public void textEquationEvaluatorBooleans() {
        Variant var = new EquationEval("true + true").eval();
        assertThat("Expected 2", var.getDouble(), is(2.0));
    }

    @Test
    public void textEquationEvaluatorBooleans2() {
        Variant var = new EquationEval("true && true").eval();
        assertThat("Expected 1", var.getDouble(), is(1.0));
    }


    @Test
    public void textEquationEvaluatorString() {
        Variant var = new EquationEval("\"2\" + 1").eval();
        assertThat("Expected 21", var.getString(), is("21"));
    }

    @Test
    public void textEquationEvaluatorNegativeAndPriority() {
        Variant var = new EquationEval("-1.0 * (2.5 + 3.5)").eval();
        assertThat("Expected -6", var.getDouble(), is(-6.0));
    }

    @Test
    public void textEquationEvaluatorAssignment() {
        DefaultVariantContainer container = new DefaultVariantContainer();
        new EquationEval("a = -1.0 * (2.5 + 3.5)").eval(container);
        assertThat("Expected -6", container.getVariant("a").getDouble(), is(-6.0));
    }

    @Test
    public void textEquationEvaluatorConstAssignment() {
        exception.expect(RuntimeException.class);
        new EquationEval("true = -1.0 * (2.5 + 3.5)").eval();
    }

}