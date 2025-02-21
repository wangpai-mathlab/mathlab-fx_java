package org.wangpai.mathlab.advanced.statistics;

import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Rational;
import org.wangpai.mathlab.builtin.statistics.primitive.DeviationMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FigureStatisticsTest {

    @Test
    void average() {
        var numbers = new Figure[]{
                Figure.ONE.clone(),
                Figure.TWO.clone(),
                new Figure(3),
                new Figure(4),
                new Figure(5)};
        assertEquals(new Rational(3), FigureStatistics.average(numbers));
    }

    @Test
    void variance() {
        var numbers = new Figure[]{
                Figure.ONE.clone(),
                Figure.TWO.clone(),
                new Figure(3),
                new Figure(4),
                new Figure(5)};
        assertEquals(new Rational(2), FigureStatistics.variance(numbers, DeviationMode.MODE_2));
    }
}