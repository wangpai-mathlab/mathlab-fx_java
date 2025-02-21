package org.wangpai.mathlab.advanced.numeric.custom.arrangement;

import org.junit.jupiter.api.Test;
import org.wangpai.commonutil.random.basic.forint.IntRandomUtil;

class ArrangementTest {

    @Test
    void entropy() throws InterruptedException {
        {
            final long taskStartTime = System.currentTimeMillis();
            var dataNum = 1000;
            int testTime = 1000000;

            var data = new int[dataNum];
            for (int index = 0; index < dataNum; ++index) {
                data[index] = index;
            }
            int total = 0;
            int max = 0;
            for (var index = 0; index < testTime; index++) {
                var rnd = IntRandomUtil.getNoDuplicateRandomData(data.clone(), data.length);
                int chaos = Arrangement.getInstance(rnd).entropy();
                total += chaos;
                max = Math.max(max, chaos);
//                System.out.print(Arrays.toString(rnd) + "：");
//                System.out.println(chaos);
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
    void forwardExTime() throws InterruptedException {
        {
//            var data = new int[]{2, 1, 3, 4, 5, 6};
//            System.out.println(Arrangement.getInstance(data).forwardExTime());
        }

        {
            final long taskStartTime = System.currentTimeMillis();
            var dataNum = 1000;
            int testTime = 1000000;

            var data = new int[dataNum];
            for (int index = 0; index < dataNum; ++index) {
                data[index] = index;
            }
            int total = 0;
            int max = 0;
            for (var index = 0; index < testTime; index++) {
                var rnd = IntRandomUtil.getNoDuplicateRandomData(data.clone(), data.length);
                int chaos = Arrangement.getInstance(rnd).forwardExTime();
                total += chaos;
                max = Math.max(max, chaos);
//                System.out.print(Arrays.toString(rnd) + "：");
//                System.out.println(chaos);
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
    void backwardExTime() throws InterruptedException {
        {
//            var data = new int[]{2, 1, 3, 4, 5, 6};
//            System.out.println(Arrangement.getInstance(data).backwardExTime());
        }

        {
            final long taskStartTime = System.currentTimeMillis();
            var dataNum = 1000;
            int testTime = 100000;

            var data = new int[dataNum];
            for (int index = 0; index < dataNum; ++index) {
                data[index] = index;
            }
            int total = 0;
            int max = 0;
            for (var index = 0; index < testTime; index++) {
                var rnd = IntRandomUtil.getNoDuplicateRandomData(data.clone(), data.length);
                int chaos = Arrangement.getInstance(rnd).backwardExTime();
                total += chaos;
                max = Math.max(max, chaos);
//                System.out.print(Arrays.toString(rnd) + "：");
//                System.out.println(chaos);
            }
            System.out.println("最大交换次数：" + max);
            System.out.println(dataNum + " 个元素平均交换次数：" + ((double) total) / testTime);
            System.out.println("单位元素平均交换次数：" + ((double) total) / testTime / dataNum);

            System.out.printf("******** 程序执行结束，用时：%fs ********%n",
                    (System.currentTimeMillis() - taskStartTime) / 1000.0);

            Thread.sleep(Integer.MAX_VALUE);
        }
    }
}