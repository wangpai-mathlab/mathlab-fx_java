package org.wangpai.mathlab.builtin.statistics.distribute;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.wangpai.commonutil.random.basic.fordouble.DoubleRandomUtil;

class DoubleDistributionTest {

    @Test
    void toMap() {
        {
            int start = 101;
            int end = 120;
            int dataNum = 200000;
            int intervalNum = 100;
            var data = new ArrayList<Double>();
            for (int index = 0; index < dataNum; index++) {
                data.add(DoubleRandomUtil.getRandomNumber(start, end));
            }

            var distribution = DoubleDistribution.getInstance(intervalNum);
            distribution.put(data);

            data.sort(Double::compareTo);

            var result = distribution.toMap(false);

            System.out.println("");
        }

        {
            int start = 101;
            int end = 120;
            int dataNum = 200000;
            int intervalNum = 100;
            var data = new ArrayList<Double>();
            for (int index = 0; index < dataNum; index++) {
                data.add(DoubleRandomUtil.getRandomNumber(start, end));
            }

            var distribution = DoubleDistribution.getInstance(100, 200, intervalNum);
            distribution.put(data);

            data.sort(Double::compareTo);

            var result = distribution.toMap(true);

            System.out.println("");
        }
    }
}