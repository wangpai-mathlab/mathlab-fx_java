package org.wangpai.mathlab.builtin.compression;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * @since 2022-12-3
 */
class IntCompressionTest {

    /**
     * @since 2022-12-3
     */
    @Test
    void halfPartition() {
        var data = new int[]{1, 2, 3, 4, 5, 6, 7};
        {
            var compression = IntCompression.halfPartition(data, 4, HalfPartitionMode.DEFAULT);
            System.out.println(Arrays.toString(compression));
        }

        {
            var compression = IntCompression.halfPartition(data, 4, HalfPartitionMode.UP);
            System.out.println(Arrays.toString(compression));
        }

        {
            var compression = IntCompression.halfPartition(data, 4, HalfPartitionMode.DOWN);
            System.out.println(Arrays.toString(compression));
        }
    }

    @Test
    void partition() {
        {
            var data = new int[]{1, 2, 3, 5, 6, 7};
            var compression = IntCompression.partition(data, 4);
            System.out.println();
        }
    }

    @Test
    void uniformCompressWnc_intArray() {
        int num = 10;
        var figures = new int[num];

        int times = 100;
        for (int index = 1; index <= num; ++index) {
            figures[index - 1] = index * times;
        }

        var result = IntCompression.uniformlyWnc(
                figures,
                new int[]{0, num * times},
                new double[]{1, num + 1});

        System.out.println(Arrays.toString(figures));
        System.out.println(Arrays.toString(result));
    }

    @Test
    void combine() {
        {
            var data = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
            var result = IntCompression.combine(data, 4);
            System.out.println();
        }
        {
            var data = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
            var result = IntCompression.combine(data, 4);
            System.out.println();
        }
    }
}