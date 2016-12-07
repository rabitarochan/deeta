package com.github.rabitarochan.deeta;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ResolverTest {

    @Test
    public void simpleValueTest() {
        Deeta deeta = new Deeta();

        String s = deeta.resolve("test value");
        assertThat(s, is("test value"));
    }

    @Test
    public void simpleValueFromYamlTest() {
        Deeta deeta = new Deeta();

        String s = deeta.resolve("${test.value.const}");
        assertThat(s, is("test value"));
    }

    @Test
    public void getListValuesFromYamlTest() {
        Deeta deeta = new Deeta();

        String s = deeta.resolve("${test.value.multipleConst}");
        assertThat(s, anyOf(is("first value"), is("second value")));
    }

    @Test
    public void resolveSimpleVariableTest() {
        Deeta deeta = new Deeta();

        String s = deeta.resolve("${test.variable.simple}");
        assertThat(s, is("test value"));
    }

    @Test
    public void resolveComplexVariableTest1() {
        Deeta deeta = new Deeta();

        String s = deeta.resolve("${test.variable.complex1}");
        assertThat(s, is("test value test value"));
    }

    @Test
    public void resolveComplexVariableTest2() {
        Deeta deeta = new Deeta();

        String s = deeta.resolve("${test.variable.complex2}");
        assertThat(s, anyOf(is("test value first value"), is("test value second value")));
    }

}
