package org.wangpai.mathlab.advanced.numeric.extend.radix;

import org.junit.jupiter.api.Test;

class FixedRadixTest {

    @Test
    void increaseOne() {
        var result = new FixedRadix(new int[]{0, 0, 0, 1}, 10, false);
        for (int index = 1; index <= 9999; ++index) {
            result.increaseOne();
            System.out.println(result);
        }
    }
}