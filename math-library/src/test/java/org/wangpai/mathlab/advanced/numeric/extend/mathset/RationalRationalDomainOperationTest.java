package org.wangpai.mathlab.advanced.numeric.extend.mathset;

import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Rational;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RationalRationalDomainOperationTest {

    @Test
    void union() {
        var a = new int[]{1, 2, 3, 4, 5};
        var b = new int[]{4, 6, 7};
        var result = RationalDomainOperation.union(new RationalDomain(a), new RationalDomain(b));
        System.out.println();
    }

    @Test
    void intersect() {
        var a = new int[]{1, 2, 3, 4, 5};
        var b = new int[]{4, 6, 7};
        var result = RationalDomainOperation.intersect(new RationalDomain(a), new RationalDomain(b));
        System.out.println();
    }

    @Test
    void subtract() {
        var a = new int[]{1, 2, 3, 4, 5};
        var b = new int[]{4, 6, 7};
        var result = RationalDomainOperation.subtract(new RationalDomain(a), new RationalDomain(b));
        System.out.println();
    }

    @Test
    void diff() {
        var a = new int[]{1, 2, 3, 4, 5};
        var b = new int[]{4, 6, 7};
        var result = RationalDomainOperation.diff(new RationalDomain(a), new RationalDomain(b));
        System.out.println();
    }

    @Test
    void contain() {
        assertTrue(RationalDomainOperation.contain(new RationalDomain(new int[]{
                1,
                2,
                3,
                4,
                5
        }), new RationalDomain(new int[]{1, 2})));
    }

    @Test
    void multiply() {
        var a = new int[]{1, 2, 3, 4, 5};
        var result = RationalDomainOperation.multiply(new RationalDomain(a), new Rational(2));
        System.out.println();
    }
}