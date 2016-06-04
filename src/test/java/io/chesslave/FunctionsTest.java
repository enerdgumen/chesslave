package io.chesslave;

import javaslang.Function2;
import javaslang.Function3;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class FunctionsTest {

    @Test
    public void ofFunction2Test() {
        Function2<Object, Object, Boolean> function2 = Object::equals;
        assertSame(function2, Functions.of(function2));
    }

    @Test
    public void ofFunction3Test() {
        Function3<Object, Object, Object, Boolean> function3 = (t1, t2, t3) -> t1.equals(t3) || t2.equals(t3);
        assertSame(function3, Functions.of(function3));
    }
}
