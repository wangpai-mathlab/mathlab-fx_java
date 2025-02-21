package org.wangpai.mathlab.advanced.numeric.basic.operation;

import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.exception.checked.MathlabCheckedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @since 2021-7-30
 */
public class FigureOperationTest {
    private long firstInt = 234234;
    private long secondInt = 2341;

    private long firstLong = this.firstInt;
    private long secondLong = this.secondInt;

    private final long maxInt = Integer.MAX_VALUE;

    @Test
    public void add() {
        var result1 = FigureOperation.add(
                new Figure(this.firstLong), new Figure(this.secondLong));
        assertEquals(new Figure(this.firstLong + this.secondLong), result1);

        var result2 = FigureOperation.add(
                new Figure(this.maxInt), new Figure(this.maxInt));
        assertEquals(new Figure(this.maxInt * 2), result2);
    }

    @Test
    public void subtract() {
        var result1 = FigureOperation.subtract(
                new Figure(this.firstLong), new Figure(this.secondLong));
        assertEquals(new Figure(this.firstLong - this.secondLong), result1);

        var result2 = FigureOperation.subtract(
                new Figure(this.maxInt), new Figure(this.maxInt * (-1)));
        assertEquals(new Figure(this.maxInt * 2), result2);
    }

    @Test
    public void multiply() {
        var result1 = FigureOperation.multiply(
                new Figure(this.firstLong), new Figure(this.secondLong));
        assertEquals(new Figure(this.firstLong * this.secondLong), result1);

        var result2 = FigureOperation.multiply(
                new Figure(this.maxInt), new Figure(this.maxInt));
        assertEquals(new Figure(this.maxInt * this.maxInt), result2);
    }

    @Test
    public void divideAndRemainder_Fraction() {
        var result = FigureOperation.divideAndRemainder(
                new Figure(this.firstLong), new Figure(this.secondLong));
        // 测试商是否正确
        assertEquals(new Figure(this.firstLong / this.secondLong), result[0]);
        // 测试余数是否正确
        assertEquals(new Figure(this.firstLong % this.secondLong), result[1]);
    }

    @Test
    public void divideAndRemainder_long() {
        var result = FigureOperation.divideAndRemainder(
                new Figure(this.firstLong), this.secondLong);
        // 测试商是否正确
        assertEquals(new Figure(this.firstLong / this.secondLong), result[0]);
        // 测试余数是否正确
        assertEquals(new Figure(this.firstLong % this.secondLong), result[1]);
    }

    @Test
    public void mod_Fraction() {
        assertEquals(new Figure(this.firstLong % this.secondLong),
                FigureOperation.mod(
                        new Figure(this.firstLong), new Figure(this.secondLong)));
    }

    @Test
    public void mod_long() {
        assertEquals(new Figure(this.firstLong % this.secondLong),
                FigureOperation.mod(
                        new Figure(this.firstLong), this.secondLong));
    }

    @Test
    public void modsQuotient_Fraction() {
        assertEquals(new Figure(this.firstLong / this.secondLong),
                FigureOperation.modsQuotient(
                        new Figure(this.firstLong), new Figure(this.secondLong)));
    }

    @Test
    public void modsQuotient_long() {
        assertEquals(new Figure(this.firstLong / this.secondLong),
                FigureOperation.modsQuotient(
                        new Figure(this.firstLong), this.secondLong));
    }

    @Test
    public void getOpposite_Fraction() {
        assertEquals(new Figure(-this.firstLong),
                FigureOperation.getOpposite(new Figure(this.firstLong)));
    }

    @Test
    public void getAbsolute() {
        assertEquals(new Figure(this.firstLong),
                FigureOperation.getAbsolute(new Figure(-this.firstLong)));
    }

    @Test
    public void getOpposite_long() {
        assertEquals(new Figure(-this.firstLong),
                FigureOperation.getOpposite(this.firstLong));
    }

    @Test
    void lessThan() {
        long pv = 2344235543L; // pv：positive Value
        assertTrue(FigureOperation.lessThan(new Figure(2), new Figure(3)));
        assertFalse(FigureOperation.lessThan(new Figure(1), new Figure(-1)));
        assertTrue(FigureOperation.lessThan(new Figure(-1), new Figure(1)));
        assertFalse(FigureOperation.lessThan(new Figure(1), new Figure(1)));
        assertFalse(FigureOperation.lessThan(new Figure(-1), new Figure(-1)));

        assertFalse(FigureOperation.lessThan(new Figure(pv), new Figure(-pv)));
        assertTrue(FigureOperation.lessThan(new Figure(-pv), new Figure(pv)));
        assertFalse(FigureOperation.lessThan(new Figure(pv), new Figure(pv)));
        assertFalse(FigureOperation.lessThan(new Figure(-pv), new Figure(-pv)));

        assertTrue(FigureOperation.lessThan(new Figure(1), new Figure(pv)));
        assertFalse(FigureOperation.lessThan(new Figure(pv), new Figure(1)));

        assertFalse(FigureOperation.lessThan(new Figure(1), new Figure(-pv)));
        assertTrue(FigureOperation.lessThan(new Figure(-pv), new Figure(1)));
    }

    @Test
    public void lessOrEqual() {
        long pv = 2344235543L; // pv：positive Value
        assertTrue(FigureOperation.lessOrEqual(new Figure(2), new Figure(3)));
        assertFalse(FigureOperation.lessOrEqual(new Figure(1), new Figure(-1)));
        assertTrue(FigureOperation.lessOrEqual(new Figure(-1), new Figure(1)));
        assertTrue(FigureOperation.lessOrEqual(new Figure(1), new Figure(1)));
        assertTrue(FigureOperation.lessOrEqual(new Figure(-1), new Figure(-1)));

        assertFalse(FigureOperation.lessOrEqual(new Figure(pv), new Figure(-pv)));
        assertTrue(FigureOperation.lessOrEqual(new Figure(-pv), new Figure(pv)));
        assertTrue(FigureOperation.lessOrEqual(new Figure(pv), new Figure(pv)));
        assertTrue(FigureOperation.lessOrEqual(new Figure(-pv), new Figure(-pv)));

        assertTrue(FigureOperation.lessOrEqual(new Figure(1), new Figure(pv)));
        assertFalse(FigureOperation.lessOrEqual(new Figure(pv), new Figure(1)));

        assertFalse(FigureOperation.lessOrEqual(new Figure(1), new Figure(-pv)));
        assertTrue(FigureOperation.lessOrEqual(new Figure(-pv), new Figure(1)));
    }

    @Test
    void greaterThan() {
        long pv = 2344235543L; // pv：positive Value
        assertTrue(FigureOperation.greaterThan(new Figure(3), new Figure(2)));
        assertTrue(FigureOperation.greaterThan(new Figure(1), new Figure(-1)));
        assertFalse(FigureOperation.greaterThan(new Figure(-1), new Figure(1)));
        assertFalse(FigureOperation.greaterThan(new Figure(1), new Figure(1)));
        assertFalse(FigureOperation.greaterThan(new Figure(-1), new Figure(-1)));

        assertTrue(FigureOperation.greaterThan(new Figure(pv), new Figure(-pv)));
        assertFalse(FigureOperation.greaterThan(new Figure(-pv), new Figure(pv)));
        assertFalse(FigureOperation.greaterThan(new Figure(pv), new Figure(pv)));
        assertFalse(FigureOperation.greaterThan(new Figure(-pv), new Figure(-pv)));

        assertFalse(FigureOperation.greaterThan(new Figure(1), new Figure(pv)));
        assertTrue(FigureOperation.greaterThan(new Figure(pv), new Figure(1)));

        assertTrue(FigureOperation.greaterThan(new Figure(1), new Figure(-pv)));
        assertFalse(FigureOperation.greaterThan(new Figure(-pv), new Figure(1)));
    }

    @Test
    public void greaterOrEqual() {
        long pv = 2344235543L; // pv：positive Value
        assertTrue(FigureOperation.greaterOrEqual(new Figure(3), new Figure(2)));
        assertTrue(FigureOperation.greaterOrEqual(new Figure(1), new Figure(-1)));
        assertFalse(FigureOperation.greaterOrEqual(new Figure(-1), new Figure(1)));
        assertTrue(FigureOperation.greaterOrEqual(new Figure(1), new Figure(1)));
        assertTrue(FigureOperation.greaterOrEqual(new Figure(-1), new Figure(-1)));

        assertTrue(FigureOperation.greaterOrEqual(new Figure(pv), new Figure(-pv)));
        assertFalse(FigureOperation.greaterOrEqual(new Figure(-pv), new Figure(pv)));
        assertTrue(FigureOperation.greaterOrEqual(new Figure(pv), new Figure(pv)));
        assertTrue(FigureOperation.greaterOrEqual(new Figure(-pv), new Figure(-pv)));

        assertFalse(FigureOperation.greaterOrEqual(new Figure(1), new Figure(pv)));
        assertTrue(FigureOperation.greaterOrEqual(new Figure(pv), new Figure(1)));

        assertTrue(FigureOperation.greaterOrEqual(new Figure(1), new Figure(-pv)));
        assertFalse(FigureOperation.greaterOrEqual(new Figure(-pv), new Figure(1)));
    }

    @Test
    void min() {
        {
            var first = new Figure(1);
            var second = new Figure(2);
            var min = FigureOperation.min(first, second);
            assertSame(first, min); // 此断言进行的是地址比较
        }
        {
            var first = new Figure(-1);
            var second = new Figure(2);
            var min = FigureOperation.min(first, second);
            assertSame(first, min); // 此断言进行的是地址比较
        }
        {
            var first = new Figure(1);
            var second = new Figure(-1);
            var min = FigureOperation.min(first, second);
            assertSame(second, min); // 此断言进行的是地址比较
        }
        {
            var first = new Figure(123456789012345L);
            var second = new Figure(1234567890123456789L);
            var min = FigureOperation.min(first, second);
            assertSame(first, min); // 此断言进行的是地址比较
        }
        {
            var first = new Figure(123456789012345L);
            var second = new Figure(1);
            var min = FigureOperation.min(first, second);
            assertSame(second, min); // 此断言进行的是地址比较
        }
    }

    @Test
    void max() {
        {
            var first = new Figure(1);
            var second = new Figure(2);
            var max = FigureOperation.max(first, second);
            assertSame(second, max); // 此断言进行的是地址比较
        }
        {
            var first = new Figure(-1);
            var second = new Figure(2);
            var max = FigureOperation.max(first, second);
            assertSame(second, max); // 此断言进行的是地址比较
        }
        {
            var first = new Figure(1);
            var second = new Figure(-1);
            var max = FigureOperation.max(first, second);
            assertSame(first, max); // 此断言进行的是地址比较
        }
        {
            var first = new Figure(123456789012345L);
            var second = new Figure(1234567890123456789L);
            var max = FigureOperation.max(first, second);
            assertSame(second, max); // 此断言进行的是地址比较
        }
        {
            var first = new Figure(123456789012345L);
            var second = new Figure(1);
            var max = FigureOperation.max(first, second);
            assertSame(first, max); // 此断言进行的是地址比较
        }
    }

    @Test
    public void findGcd() {
        assertEquals(FigureOperationHistory.findGcd_v_0(
                        new Figure(this.firstLong), new Figure(this.secondLong)),
                FigureOperation.findGcd(
                        new Figure(this.firstLong), new Figure(this.secondLong)));
    }

    @Test
    public void findLcm() {
        assertEquals(FigureOperationHistory.findLcm_v_0(
                        new Figure(this.firstLong), new Figure(this.secondLong)),
                FigureOperation.findLcm(new Figure(this.firstLong), new Figure(this.secondLong)));
    }

    @Test
    public void power() throws MathlabCheckedException {
        // 8 = 2 ^ 3
        assertEquals(new Figure(8),
                FigureOperation.power(2, 3));
        // ? = (?) ^ (1)
        assertEquals(new Figure(this.firstLong),
                FigureOperation.power(new Figure(this.firstLong), Figure.ONE));
        // 1 = (?) ^ (0)
        assertEquals(Figure.ONE,
                FigureOperation.power(new Figure(this.firstLong), Figure.ZERO));
    }

    @Test
    public void arrangementForBigResult() {
        Figure n = new Figure(4);
        Figure m = new Figure(2);
        assertEquals(new Figure(12), FigureOperation.arrangementForBigResult(n, m));
        assertEquals(n, FigureOperation.arrangementForBigResult(n, Figure.ONE));
    }

    @Test
    void combinationForBigResult() {
        Figure n = new Figure(4);
        Figure m = new Figure(2);
        assertEquals(new Figure(6), FigureOperation.combinationForBigResult(n, m));
        assertEquals(n, FigureOperation.combinationForBigResult(n, Figure.ONE));
    }

    @Test
    public void factorialForSmallResult() {
        assertEquals(new Figure(24), FigureOperation.factorialForSmallResult(4));
    }

    @Test
    public void factorialForBigResult() {
        Figure num = new Figure(4);
        assertEquals(new Figure(24), FigureOperation.factorialForBigResult(num));
    }
}
