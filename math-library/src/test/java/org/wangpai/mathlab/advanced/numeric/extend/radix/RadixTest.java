package org.wangpai.mathlab.advanced.numeric.extend.radix;

import org.junit.jupiter.api.Test;
import org.wangpai.exception.checked.failed.OverflowException;

class RadixTest {

    @Test
    void increaseOne() {
        var result = new Radix(new int[]{1, 9, 9, 0}, 10, true);
        for (int index = 1; index <= 2400; ++index) {
            result.increaseOne();
            System.out.println(result);
        }
    }

    @Test
    void decreaseOne() {
        var result = new Radix(new int[]{1, 9, 9, 0}, 10, false);
        for (int index = 1; index <= 2400; ++index) {
            result.decreaseOne();
            System.out.println(result);
        }
    }

    @Test
    void increase1Abs() {
        var result = new Radix(new int[]{0, 0, 0}, 10, false);
        for (int index = 1; index <= 99997; ++index) {
            if (index == 20) {
                System.out.println();
            }
            result.increase1Abs();
            System.out.println(result);
        }
    }

    @Test
    void decrease1Abs() {
        var result = new Radix(new int[]{1, 9, 9, 0}, 10, true);
        for (int index = 1; index <= 1991; ++index) {
            result.decrease1Abs();
            System.out.println(result);
        }
    }

    @Test
    void toLong() throws OverflowException {
        {
            var radix = new Radix(new int[]{1, 1, 0}, 10, false).toLong();
            System.out.println();
        }
        {
            var radix = new Radix(new int[]{8, 2, 2, 3, 3, 7, 2, 0, 3, 6, 8, 5, 4, 7, 7, 5, 8, 0, 7},
                    10, false).toLong();
            System.out.println();
        }
        {
            // 此数刚好是 long 的最大正整数值
            var radix = new Radix(new int[]{9, 2, 2, 3, 3, 7, 2, 0, 3, 6, 8, 5, 4, 7, 7, 5, 8, 0, 7},
                    10, false).toLong();
            System.out.println();
        }
    }
}