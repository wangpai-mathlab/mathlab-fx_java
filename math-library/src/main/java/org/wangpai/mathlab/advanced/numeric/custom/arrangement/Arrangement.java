package org.wangpai.mathlab.advanced.numeric.custom.arrangement;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wangpai.commonutil.tc.collection.BasicArrayTc;

/**
 * @since 2023-2-12
 */
@ToString
@Accessors(chain = true)
public class Arrangement {
    /**
     * data 中的数据不能重复
     *
     * 为了后续排序的方便，此处使用包装类型
     */
    private Integer[] arrangement;

    /**
     * 此方法禁止外部调用
     *
     * @since 2023-2-12
     */
    private Arrangement() {
        super();
    }

    /**
     * 此方法禁止外部调用
     *
     * @since 2023-2-12
     */
    private Arrangement(int[] data) {
        super();
        this.arrangement = BasicArrayTc.intArray2IntegerArray(data);
    }

    /**
     * @since 2023-2-13
     */
    public static Arrangement getInstance() {
        return new Arrangement();
    }

    /**
     * data 中的数据不能重复
     *
     * @since 2023-2-13
     */
    public static Arrangement getInstance(int[] data) {
        return new Arrangement(data);
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
        return this.exchangeTime(Comparator.comparingInt(o -> o));
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
    public int exchangeTime(Comparator<Integer> comparator) {
        var cloned = this.arrangement.clone();
        Arrays.sort(cloned, comparator);
        var positiveMap = new HashMap<Integer, Integer>(cloned.length);
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
