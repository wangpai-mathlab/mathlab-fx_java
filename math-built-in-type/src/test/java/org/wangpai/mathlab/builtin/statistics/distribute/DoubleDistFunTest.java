package org.wangpai.mathlab.builtin.statistics.distribute;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.wangpai.commonutil.random.basic.fordouble.DoubleRandomUtil;

class DoubleDistFunTest {

    @Test
    void toMap() {
        {
            int start = 1;
            int end = 10;
            int intervalNum = 10;
            var data = new ArrayList<Double>();
            for (int index = start; index <= end; index++) {
                data.add((double) index);
            }

            var distribution = DoubleDistribution.getInstance(intervalNum);
            distribution.put(data);

            data.sort(Double::compareTo);

            var result = distribution.toMap(false);
            var result2 = DoubleDistFun.getInstance(distribution).toMap();

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

            var distribution = DoubleDistribution.getInstance(intervalNum);
            distribution.put(data);

            data.sort(Double::compareTo);

            var result = distribution.toMap(false);
            var result2 = DoubleDistFun.getInstance(distribution).toMap();

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
            var result2 = DoubleDistFun.getInstance(distribution).toMap();

            System.out.println("");
        }
    }
}