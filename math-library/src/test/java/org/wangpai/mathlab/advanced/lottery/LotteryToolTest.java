package org.wangpai.mathlab.advanced.lottery;

import org.junit.jupiter.api.Test;

class LotteryToolTest {

    @Test
    void invest() throws InterruptedException {
        var result = LotteryTool.invest(11, 6, 5);
        var result2 = result.toReciprocalProbability();
        Thread.sleep(Integer.MAX_VALUE);
    }
}