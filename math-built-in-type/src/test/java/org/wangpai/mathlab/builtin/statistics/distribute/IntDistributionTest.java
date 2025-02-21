package org.wangpai.mathlab.builtin.statistics.distribute;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.wangpai.commonutil.random.basic.forint.IntRandomUtil;

class IntDistributionTest {
    @Test
    void put() {
        int start = 101;
        int end = 200;
        int dataNum = 1000000;
        var data = new ArrayList<Integer>();
        for (int index = 0; index < dataNum; index++) {
            data.add(IntRandomUtil.getRandomNumber(start, end));
        }

        var result = IntDistribution.getInstance(start, end);
        result.put(data);

        data.sort(Integer::compareTo);

        System.out.println("");
    }

    @Test
    void traverse() {
        int start = 101;
        int end = 120;
        int dataNum = 200000;
        var data = new ArrayList<Integer>();
        for (int index = 0; index < dataNum; index++) {
            data.add(IntRandomUtil.getRandomNumber(start, end));
        }

        var result = IntDistribution.getInstance(start, end);
        result.put(data);

        data.sort(Integer::compareTo);

        result.traverse((key, value) -> {
            System.out.println(key + ": " + value);
            return true;
        });

        System.out.println("");
    }

    @Test
    void toMap() {
        int start = 101;
        int end = 120;
        int dataNum = 200000;
        var data = new ArrayList<Integer>();
        for (int index = 0; index < dataNum; index++) {
            data.add(IntRandomUtil.getRandomNumber(start, end));
        }

        var distribution = IntDistribution.getInstance(start, end);
        distribution.put(data);

        data.sort(Integer::compareTo);

        var result = distribution.toMap(false);

        System.out.println("");
    }
}