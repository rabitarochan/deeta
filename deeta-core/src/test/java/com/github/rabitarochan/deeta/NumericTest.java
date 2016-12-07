package com.github.rabitarochan.deeta;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class NumericTest {

    @Test
    public void simpleNumericTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("###");
        assertThat(res.matches("\\d\\d\\d"), is(true));
    }

    @Test
    public void multipleNumericTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("###-###");
        assertThat(res.matches("\\d\\d\\d-\\d\\d\\d"), is(true));
    }

    @Test
    public void simpleNumericFromYamlTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("${test.numeric.simple}");
        assertThat(res.matches("\\d\\d\\d"), is(true));
    }

    @Test
    public void multipleNumericFromYamlTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("${test.numeric.multiple}");
        assertThat(res.matches("\\d\\d\\d-\\d\\d\\d"), is(true));
    }

    @Test
    public void escapedSimpleNumericTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("\\#\\#\\#");
        assertThat(res, is("###"));
    }

    @Test
    public void escapedMultipleNumericTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("\\#\\#\\#-\\#\\#\\#");
        assertThat(res, is("###-###"));
    }

    @Test
    public void escapedSimpleNumericFromYamlTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("${test.numeric.escapedSimple}");
        assertThat(res, is("###"));
    }

    @Test
    public void escapedMultipleNumericFromYamlTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("${test.numeric.escapedMultiple}");
        assertThat(res, is("###-###"));
    }

}
