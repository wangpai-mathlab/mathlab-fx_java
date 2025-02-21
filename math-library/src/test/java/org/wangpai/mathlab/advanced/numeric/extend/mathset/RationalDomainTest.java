package org.wangpai.mathlab.advanced.numeric.extend.mathset;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class RationalDomainTest {

    @Test
    void getMinValue() {
        var a = new int[]{1, 2, 3, 4, 5};
        var result = new RationalDomain(a).getMinValue();

        assertEquals(8.0, 8.0);
        System.out.println();
    }

    @Test
    void getMaxValue() {
        var a = new int[]{1, 2, 3, 4, 5};
        var result = new RationalDomain(a).getMaxValue();
        System.out.println();
    }

    @Test
    void clone_test() {
        var a = new RationalDomain(new int[]{1, 2, 3, 4, 5});
        var cloned = a.clone();
        System.out.println();
    }

    @Test
    void equals_Domain() {
        var a = new int[]{1, 2, 3, 4, 5};
        var b = new int[]{1, 2, 3, 4, 5, 6};
        assertEquals(new RationalDomain(a), new RationalDomain(a));
        assertNotEquals(new RationalDomain(a), new RationalDomain(b));
    }
}