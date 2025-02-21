package org.wangpai.mathlab.builtin.statistics.primitive;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DoubleStatisticsTest {
    @Test
    void absoluteSum() {
        var numbers = new double[]{1, -2, 3, -4, 5};
        assertEquals(15, DoubleStatistics.absoluteSum(numbers));
    }

    @Test
    void quadraticSum() {
        var numbers = new double[]{1, -2, 3, -4, 5};
        assertEquals(55, DoubleStatistics.quadraticSum(numbers));
    }

    @Test
    void average() {
        var numbers = new double[]{1, 2, 3, 4, 5};
        assertEquals(3, DoubleStatistics.average(numbers));
    }

    @Test
    void variance() {
        var numbers = new double[]{1, 2, 3, 4, 5};
        assertEquals(2.0, DoubleStatistics.variance(numbers, DeviationMode.MODE_2));
    }
}