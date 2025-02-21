package org.wangpai.mathlab.advanced.numeric.custom.arrangement;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.wangpai.commonutil.random.basic.forint.IntRandomUtil;

class RepeatableArrTest {

    @Test
    void entropy() throws InterruptedException {
//        {
//            var data = new int[]{2, 1, 1, 1, 2, 1, 3, 4, 5, 6};
//            System.out.println(RepeatableArr.getInstance(data, 1, 7).entropy());
//        }
        {
            final long taskStartTime = System.currentTimeMillis();
            var dataNum = 10;
            int testTime = 1000;

            int total = 0;
            int max = 0;
            for (var index = 0; index < testTime; index++) {
                var rnd = IntRandomUtil.getRandomNumbers(1, dataNum, dataNum);
                int chaos = RepeatableArr.getInstance(rnd).entropy();
                total += chaos;
                max = Math.max(max, chaos);
                System.out.print(Arrays.toString(rnd) + "：");
                System.out.println(chaos);
            }
            System.out.println("最大交换次数：" + max);
            System.out.println(dataNum + " 个元素平均交换次数：" + ((double) total) / testTime);
            System.out.println("单位元素平均交换次数：" + ((double) total) / testTime / dataNum);

            System.out.printf("******** 程序执行结束，用时：%fs ********%n",
                    (System.currentTimeMillis() - taskStartTime) / 1000.0);

            Thread.sleep(Integer.MAX_VALUE);
        }
    }

    @Test
    void forwardExTime() {
        {
            var data = new int[]{2, 1, 1, 1, 2, 1, 3, 4, 5, 6};
            System.out.println(RepeatableArr.getInstance(data, 1, 7).forwardExTime());
        }
    }

    @Test
    void backwardExTime() {
    }
}