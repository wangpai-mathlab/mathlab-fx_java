package org.wangpai.mathlab.advanced.numeric.basic.operation;

import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Rational;
import org.wangpai.exception.unchecked.LogicalException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @since 2021-7-22
 */
public class RationalOperationTest {
    private final int firstInt = 234234;
    private final int secondInt = 2341;

    /**
     * @since 2022-12-11
     */
    @Test
    public void multiply() {
        // 1/2 = (4/3) * (3/8)
        assertEquals(new Rational(1, 2),
                RationalOperation.multiply(
                        new Rational(4, 3),
                        new Rational(3, 8)
                ));
    }

    /**
     * @since 2022-12-11
     */
    @Test
    public void divide() {
        // 1/14 = (4/35) / (8/5)
        assertEquals(new Rational(1, 14),
                RationalOperation.divide(
                        new Rational(4, 35),
                        new Rational(8, 5)
                ));
    }

    @Test
    public void getOpposite() {
        assertEquals(new Rational(-this.firstInt, this.secondInt),
                RationalOperation.getOpposite
                        (new Rational(this.firstInt, this.secondInt)));
    }

    @Test
    public void getAbsolute() {
        assertEquals(new Rational(this.firstInt, this.secondInt),
                RationalOperation.getAbsolute
                        (new Rational(this.firstInt, -this.secondInt)));
    }

    @Test
    public void getReciprocal() {
        assertEquals(new Rational(this.secondInt, this.firstInt),
                RationalOperation.getReciprocal
                        (new Rational(this.firstInt, this.secondInt)));
    }

    @Test
    public void power() {
        // 8 = 2 ^ 3
        assertEquals(new Rational(8),
                RationalOperation.power(new Rational(2), new Figure(3)));
        // 1/8 = 2 ^ (-3)
        assertEquals(new Rational(1, 8),
                RationalOperation.power(new Rational(2), new Figure(-3)));
        // -1/8 = (-2) ^ (-3)
        assertEquals(new Rational(-1, 8),
                RationalOperation.power(new Rational(-2), new Figure(-3)));
        // ? = (?) ^ (1)
        assertEquals(new Rational(this.firstInt, this.secondInt),
                RationalOperation.power(new Rational(this.firstInt, this.secondInt), Figure.ONE));
        // 1 = (?) ^ (0)
        assertEquals(new Rational(1),
                RationalOperation.power(new Rational(this.firstInt, this.secondInt), Figure.ZERO));

        // 0 的负数次方引发异常
        assertThrows(LogicalException.class,
                () -> RationalOperation.power(new Rational(0), new Figure(-3)));
    }

    @Test
    public void roundUp() {
        var test1 = new Rational(3, 2);
        assertEquals(new Rational(2),
                RationalOperation.roundUp(test1));

        var test2 = new Rational(3, 1);
        assertEquals(new Rational(3), RationalOperation.roundUp(test2));

        var test3 = new Rational(20, 13);
        assertEquals(new Rational(2), RationalOperation.roundUp(test3));
    }

    @Test
    public void roundDown() {
        var test1 = new Rational(3, 2);
        assertEquals(new Rational(1),
                RationalOperation.roundDown(test1));

        var test2 = new Rational(3, 1);
        assertEquals(test2, RationalOperation.roundDown(test2));
    }

    @Test
    void random0To1_void() {
        final long startTime = System.currentTimeMillis();
        int maxTimes = 1000000;
        Rational sum = Rational.ZERO.clone();
        for (int time = 0; time < maxTimes; ++time) {
            Rational random0To1 = RationalOperation.random0To1();
            sum = RationalOperation.add(sum, random0To1);
//            System.out.println(random0To1.toDouble());
        }
//        System.out.println("-----------");
        System.out.println(RationalOperation.divide(sum, new Rational(maxTimes)).toDouble());
        System.out.printf("******** 应用后台程序退出：%fs ********%n",
                (System.currentTimeMillis() - startTime) / 1000.0);
    }

    @Test
    void random0To1_int() {
        final long startTime = System.currentTimeMillis();
        int maxTimes = 1000000;
        Rational sum = Rational.ZERO.clone();
        for (int time = 0; time < maxTimes; ++time) {
            Rational random0To1 = RationalOperation.random(100);
            sum = RationalOperation.add(sum, random0To1);
//            System.out.println(random0To1.toDouble());
        }
//        System.out.println("-----------");
        System.out.println(RationalOperation.divide(sum, new Rational(maxTimes)).toDouble());
        System.out.printf("******** 应用后台程序退出：%fs ********%n",
                (System.currentTimeMillis() - startTime) / 1000.0);
    }

    @Test
    void min() {
        {
            var first = new Rational(1);
            var second = new Rational(2);
            var min = RationalOperation.min(first, second);
            assertSame(first, min); // 此断言进行的是地址比较
        }
        {
            var first = new Rational(-1);
            var second = new Rational(2);
            var min = RationalOperation.min(first, second);
            assertSame(first, min); // 此断言进行的是地址比较
        }
        {
            var first = new Rational(1);
            var second = new Rational(-1);
            var min = RationalOperation.min(first, second);
            assertSame(second, min); // 此断言进行的是地址比较
        }
        {
            var first = new Rational(123456789012345L);
            var second = new Rational(1234567890123456789L);
            var min = RationalOperation.min(first, second);
            assertSame(first, min); // 此断言进行的是地址比较
        }
        {
            var first = new Rational(123456789012345L);
            var second = new Rational(1);
            var min = RationalOperation.min(first, second);
            assertSame(second, min); // 此断言进行的是地址比较
        }
    }

    @Test
    void max() {
        {
            var first = new Rational(1, 2);
            var second = new Rational(2);
            var max = RationalOperation.max(first, second);
            assertSame(second, max); // 此断言进行的是地址比较
        }
        {
            var first = new Rational(-1, 2);
            var second = new Rational(2);
            var max = RationalOperation.max(first, second);
            assertSame(second, max); // 此断言进行的是地址比较
        }
        {
            var first = new Rational(1, 2);
            var second = new Rational(-1);
            var max = RationalOperation.max(first, second);
            assertSame(first, max); // 此断言进行的是地址比较
        }
        {
            var first = new Rational(123456789012345L, 13);
            var second = new Rational(1234567890123456789L);
            var max = RationalOperation.max(first, second);
            assertSame(second, max); // 此断言进行的是地址比较
        }
        {
            var first = new Rational(123456789012345L, 13);
            var second = new Rational(1);
            var max = RationalOperation.max(first, second);
            assertSame(first, max); // 此断言进行的是地址比较
        }
    }
}
