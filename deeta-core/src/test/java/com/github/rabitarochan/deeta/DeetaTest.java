package com.github.rabitarochan.deeta;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;


public class DeetaTest {

    @Test
    public void test() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("${test.case1.singleValue}");
        assertThat(res, is("test value"));
    }

}
