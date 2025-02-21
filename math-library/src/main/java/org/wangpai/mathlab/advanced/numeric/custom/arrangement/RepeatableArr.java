package org.wangpai.mathlab.advanced.numeric.custom.arrangement;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wangpai.mathlab.builtin.statistics.primitive.IntStatistics;

/**
 * 可重复排列
 *
 * @since 2023-2-13
 */
@ToString
@Accessors(chain = true)
public class RepeatableArr {
    /**
     * 为了后续排序的方便，此处使用包装类型
     */
    private int[] originData;

    private int originMin;

    private int originMax;

    /**
     * data 中的数据不能重复
     *
     * 为了后续排序的方便，此处使用包装类型
     */
    private Long[] arrangement;

    /**
     * 此方法禁止外部调用
     *
     * @since 2023-2-12
     */
    private RepeatableArr() {
        super();
    }

    /**
     * 此方法禁止外部调用
     *
     * 此方法会自动判断 data 的取值范围
     *
     * 当 data 中出现重复数字时，将该数字将重复的次数加上 originMax 的相应重复次数的倍数以区分。
     * 比如，如果该数字是第 3 次重复，则将此数字加上 2 * originMax
     *
     * @since 2023-2-13
     */
    private RepeatableArr(int[] data) {
        super();
        this.originData = data;
        this.originMin = IntStatistics.min(data);
        this.originMax = IntStatistics.max(data);
        this.arrangement = new Long[this.originData.length];
        int[] countMap = new int[this.originMax - this.originMin + 1]; // 统计数字的重复情况
        for (int index = 0; index < this.originData.length; index++) {
            int countIndex = this.originData[index] - this.originMin;
            this.arrangement[index] = this.originData[index]
                    + (long) this.originMax * (countMap[countIndex]);
            ++countMap[countIndex];
        }
    }

    /**
     * 此方法禁止外部调用
     *
     * 当 data 中出现重复数字时，将该数字将重复的次数加上 originMax 的相应重复次数的倍数以区分。
     * 比如，如果该数字是第 3 次重复，则将此数字加上 2 * originMax
     *
     * @since 2023-2-13
     */
    private RepeatableArr(int[] data, int min, int max) {
        super();
        this.originData = data;
        this.originMin = min;
        this.originMax = max;
        this.arrangement = new Long[this.originData.length];
        int[] countMap = new int[this.originMax - this.originMin + 1]; // 统计数字的重复情况
        for (int index = 0; index < this.originData.length; index++) {
            int countIndex = this.originData[index] - this.originMin;
            this.arrangement[index] = this.originData[index]
                    + (long) this.originMax * (countMap[countIndex]);
            ++countMap[countIndex];
        }
    }

    /**
     * @since 2023-2-13
     */
    public static RepeatableArr getInstance() {
        return new RepeatableArr();
    }

    /**
     * @since 2023-2-13
     */
    public static RepeatableArr getInstance(int[] data) {
        return new RepeatableArr(data);
    }

    /**
     * data 中的数据可以重复
     *
     * @since 2023-2-13
     */
    public static RepeatableArr getInstance(int[] data, int start, int end) {
        return new RepeatableArr(data, start, end);
    }

    /**
     * 求交换熵值
     *
     * @since 2023-2-13
     */
    public int entropy() {
        return Math.min(this.forwardExTime(), this.backwardExTime());
    }

    /**
     * 正向交换次数
     *
     * @since 2023-2-13
     */
    public int forwardExTime() {
        return this.exchangeTime(Comparator.comparingLong(o -> o));
    }

    /**
     * 逆向交换次数
     *
     * @since 2023-2-13
     */
    public int backwardExTime() {
        return this.exchangeTime(Collections.reverseOrder());
    }

    /**
     * 求对由 comparator 决定的标准序列，data 的交换次数。
     * 此方法不会改变 data
     *
     * @since 2023-2-12
     */
    public int exchangeTime(Comparator<Long> comparator) {
        var cloned = this.arrangement.clone();
        Arrays.sort(cloned, comparator);
        var positiveMap = new HashMap<Long, Integer>(cloned.length);
        for (var index = 0; index < cloned.length; index++) {
            positiveMap.put(cloned[index], index);
        }
        int loop = 0;
        int length = this.arrangement.length;
        boolean[] visited = new boolean[length];
        for (int index = 0; index < length; index++) {
            if (!visited[index]) {
                int loopIndex = index;
                while (!visited[loopIndex]) {
                    visited[loopIndex] = true;
                    loopIndex = positiveMap.get(this.arrangement[loopIndex]);
                }
                loop++;
            }
        }
        return length - loop;
    }
}
