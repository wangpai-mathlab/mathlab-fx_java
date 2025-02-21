package org.wangpai.mathlab.builtin.statistics.advanced;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.builtin.statistics.primitive.DeviationMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @since 2022-10-21
 */
class StatisticsTest {
    /**
     * @since 2022-10-21
     */
    @Test
    void average() {
        Value[] numbers = new Value[]{
                new Value(1),
                new Value(2),
                new Value(3),
                new Value(4),
                new Value(5),
        };
        assertEquals(new Value(3), Statistics.average(numbers));
    }

    /**
     * @since 2022-10-21
     */
    @Test
    void variance() {
        Value[] numbers = new Value[]{
                new Value(1),
                new Value(2),
                new Value(3),
                new Value(4),
                new Value(5),
        };
        assertEquals(new Value(2), Statistics.variance(DeviationMode.MODE_2, numbers));
    }

    /**
     * @since 2022-10-21
     */
    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @Accessors(chain = true)
    static class Value implements Calculable {
        double value;

        /**
         * @since 2022-10-21
         */
        @Override
        public Calculable add(Calculable other) {
            return new Value(this.value + ((Value) other).value);
        }

        /**
         * @since 2022-10-21
         */
        @Override
        public Calculable subtract(Calculable other) {
            return new Value(this.value - ((Value) other).value);
        }

        /**
         * @since 2022-10-21
         */
        @Override
        public Calculable multiply(Calculable other) {
            return new Value(this.value * ((Value) other).value);
        }

        /**
         * @since 2022-10-21
         */
        @Override
        public Calculable divide(int num) {
            return new Value(this.value / num);
        }

        /**
         * @since 2022-10-21
         */
        @Override
        public Calculable zero() {
            return new Value(0);
        }

        /**
         * @since 2022-10-21
         */
        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null) {
                return false;
            }
            if (other instanceof Value) {
                return Double.compare(this.value, ((Value) other).value) == 0;
            }
            return false;
        }
    }
}

