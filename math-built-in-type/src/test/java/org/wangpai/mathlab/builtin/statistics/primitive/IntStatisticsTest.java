package org.wangpai.mathlab.builtin.statistics.primitive;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntStatisticsTest {
    @Test
    void absoluteSum() {
        var numbers = new int[]{1, -2, 3, -4, 5};
        assertEquals(15, IntStatistics.absoluteSum(numbers));
    }

    @Test
    void quadraticSum() {
        var numbers = new int[]{1, -2, 3, -4, 5};
        assertEquals(55, IntStatistics.quadraticSum(numbers));
    }

    @Test
    void average() {
        var numbers = new int[]{1, 2, 3, 4, 5};
        assertEquals(3, IntStatistics.average(numbers));
    }

    @Test
    void variance() {
        {
            var numbers = new int[]{1, 2, 3, 4, 5};
            assertEquals(2.0, IntStatistics.variance(numbers, DeviationMode.MODE_2));
        }
        {
            var numbers = new int[]{1, 2, 3, 4, 5, 1, 2, 3, 4, 5};
            assertEquals(2.0, IntStatistics.variance(numbers, DeviationMode.MODE_2));
        }
    }
}