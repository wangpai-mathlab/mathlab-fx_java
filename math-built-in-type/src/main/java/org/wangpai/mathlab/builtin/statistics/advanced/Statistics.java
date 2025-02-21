package org.wangpai.mathlab.builtin.statistics.advanced;

import org.wangpai.mathlab.builtin.statistics.primitive.DeviationMode;

public class Statistics {
    /**
     * 求和
     *
     * @since 2022-10-21
     */
    public static Calculable sum(Calculable... numbers) {
        Calculable result = numbers[0].zero();
        for (var num : numbers) {
            result = result.add(num);
        }
        return result;
    }

    /**
     * 平均数
     *
     * @since 2022-10-21
     */
    public static Calculable average(Calculable... numbers) {
        return sum(numbers).divide(numbers.length);
    }

    /**
     * 方差
     *
     * @since 2022-10-21
     */
    public static Calculable variance(DeviationMode mode, Calculable... numbers) {
        Calculable result = numbers[0].zero();
        for (Calculable num : numbers) {
            result = result.add(num.multiply(num));
        }
        var temp = sum(numbers);
        result = result.subtract(temp.multiply(temp).divide(numbers.length));
        return switch (mode) {
            case MODE_1 -> result.divide(numbers.length - 1);
            case MODE_2 -> result.divide(numbers.length);
        };
    }
}
