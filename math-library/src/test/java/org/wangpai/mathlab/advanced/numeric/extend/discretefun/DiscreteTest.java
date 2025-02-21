package org.wangpai.mathlab.advanced.numeric.extend.discretefun;

import org.junit.jupiter.api.Test;

class DiscreteTest {
    @Test
    void reverse() {
        var discrete = Discrete.getInstance(new int[]{1, 1, 9, 16, 25, 36});
        var reverse = discrete.reverse();
        System.out.println();
    }

    @Test
    void clone_test() {
        var discrete = Discrete.getInstance(new int[]{1, 1, 9, 16, 25, 36});
        var cloned = discrete.clone();
        System.out.println();
    }

    @Test
    void getYDomain() {
        var discrete = Discrete.getInstance(new int[]{1, 1, 9, 16, 25, 36});
        var result = discrete.getYDomain();
        System.out.println();
    }

    @Test
    void average() {
        var discrete = Discrete.getInstance(new int[]{1, 1, 9, 16, 25, 36});
        var result = discrete.average();
        System.out.println();
    }

    @Test
    void difference() {
        var discrete = Discrete.getInstance(new int[]{1, 4, 9, 16, 25, 36});
        var result = discrete.difference();
        System.out.println();
    }

    @Test
    void dDifference_int() {
        var discrete = Discrete.getInstance(new int[]{1, 4, 9, 16, 25, 36});
        var result = discrete.dDifference(2);
        System.out.println();
    }

    @Test
    void dDifference_int_int() {
        var discrete = Discrete.getInstance(new int[]{1, 4, 9, 16, 25, 36});
        var result = discrete.dDifference(2, 2);
        System.out.println();
    }

    @Test
    void sum() {
        var discrete = Discrete.getInstance(new int[]{1, 1, 9, 16, 25, 36});
        var result = discrete.sum();
        System.out.println();
    }

    @Test
    void nSum_int() {
        var discrete = Discrete.getInstance(new int[]{1, 1, 9, 16, 25, 36});
        var result = discrete.sum(2);
        System.out.println();
    }

    @Test
    void groupSum() {
        {
            var discrete = Discrete.getInstance(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
            var result = discrete.groupSum(2);
            System.out.println();
        }
        {
            var discrete = Discrete.getInstance(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
            var result = discrete.groupSum(4);
            System.out.println();
        }
    }

    @Test
    void dft() {
        {
            var discrete = Discrete.getInstance(new int[]{1, 3, 4, 3, 1, 2});
            var result = discrete.dft();
            System.out.println();
        }
        {
            var discrete = Discrete.getInstance(new int[]{-1, 3, 2, 8, 3});
            var result = discrete.dft();
            System.out.println();
        }
    }
}