package com.github.rabitarochan.deeta;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;


public class DeetaTest {

    @Test
    public void test() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("${name.name}");
        assertThat(res, is("rabitarochan"));
    }

    @Test
    public void test2() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("${name.single}");
        assertThat(res, is("single value"));
    }

    @Test
    public void test3() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("${name.multi}");
        System.out.println(res);
        assertThat(res, anyOf(is("one"), is("two")));

        System.out.println(deeta.resolve("${name.variable}"));
    }

    @Test
    public void numericTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("${name.numeric}");
        System.out.println(res);
    }

    @Test
    public void variableTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("${name.variable}");
        System.out.println(res);
    }

    @Test
    public void test4() {
        Deeta deeta = new Deeta();

        System.out.println(deeta.resolve("${name.test}"));
    }

    @Test
    public void test5() {
        Deeta deeta = new Deeta();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String res = deeta.resolve("${seq} ${name.lastName}${name.firstName}", i + 1);
            if (i < 10) {
                System.out.println(res);
            }
        }
        System.out.println(String.format("[time: %s ms]", System.currentTimeMillis() - start));
    }

}
