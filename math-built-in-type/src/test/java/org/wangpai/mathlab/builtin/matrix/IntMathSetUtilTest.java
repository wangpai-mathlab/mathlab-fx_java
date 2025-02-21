package org.wangpai.mathlab.builtin.matrix;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntMathSetUtilTest {

    @Test
    void subtract() {
        var a = new int[]{1, 2, 3, 4, 5};
        var b = new int[]{4, 6, 7};
        var result = IntMathSetUtil.subtract(a, b);
        Arrays.sort(result);
        assertArrayEquals(new int[]{1, 2, 3, 5}, result);
    }

    @Test
    void union() {
        var a = new int[]{1, 2, 3, 4, 5};
        var b = new int[]{4, 6, 7};
        var result = IntMathSetUtil.union(a, b);
        Arrays.sort(result);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7}, result);
    }

    @Test
    void intersect() {
        {
            var a = new int[]{1, 2, 3, 4, 5};
            var b = new int[]{4, 6, 7};
            var result = IntMathSetUtil.intersect(a, b);
            Arrays.sort(result);
            assertArrayEquals(new int[]{4}, result);
        }
        {
            var a = new int[]{1, 2, 3, 4, 5};
            var b = new int[]{1, 2, 3, 4, 6};
            var result = IntMathSetUtil.intersect(a, b);
            Arrays.sort(result);
            assertArrayEquals(new int[]{1, 2, 3, 4}, result);
        }
    }

    @Test
    void diff() {
        {
            var a = new int[]{1, 2, 3, 4, 5};
            var b = new int[]{4, 6, 7};
            var result = IntMathSetUtil.diff(a, b);
            Arrays.sort(result);
            assertArrayEquals(new int[]{1, 2, 3, 5, 6, 7}, result);

            // 两个集合的差集等于两个集合的并集减去它们的交集
            assertArrayEquals(
                    result,
                    IntMathSetUtil.subtract(IntMathSetUtil.union(a, b), IntMathSetUtil.intersect(a, b)));
            // 也等于 (a - b) 与 (b - a) 的并集
            assertArrayEquals(
                    result,
                    IntMathSetUtil.union(IntMathSetUtil.subtract(a, b), IntMathSetUtil.subtract(b, a)));
        }
    }

    @Test
    void contain_intArray_intArray() {
        assertTrue(IntMathSetUtil.contain(new int[]{1, 2, 3, 4, 5}, new int[]{1}));
        assertTrue(IntMathSetUtil.contain(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 3, 4, 5}));
        assertTrue(IntMathSetUtil.contain(new int[]{1, 2, 3, 4, 5}, new int[]{2, 3, 5}));

        assertFalse(IntMathSetUtil.contain(new int[]{1, 2, 3, 4, 5}, new int[]{2, 3, 6}));
        assertFalse(IntMathSetUtil.contain(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 3, 4, 5, 6}));
    }

    @Test
    void contain_intArray_int() {
        assertTrue(IntMathSetUtil.contain(new int[]{1, 2, 3, 4, 5}, 1));
        assertFalse(IntMathSetUtil.contain(new int[]{1, 2, 3, 4, 5}, 116));
    }
}