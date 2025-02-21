package org.wangpai.mathlab.builtin.discretefun;

import java.util.Map;
import java.util.TreeMap;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wangpai.exception.unchecked.LogicalException;

/**
 * 离散函数
 *
 * @since 2023-1-26
 * @deprecated 2023-1-29 考虑 long 类型的范围有限和个人精力，此类暂时不继续更新，未来有可能会删除
 */
@ToString
@Accessors(chain = true)
@Deprecated
public class LongDiscrete {
    /**
     * 数列。
     *
     * Map<Long, Long> 代表 <x, y>。x 为从 1 开始的连续正整数。但 x 可以不从 1 开始，可在任意连续正整数区间内取值
     * y 必须使用 Long 类型，因为连续求差分之后，结果很容易变得很大
     *
     * 返回值按 x 升序排列
     *
     * @since 2023-1-26
     */
    @Getter
    private final Map<Long, Long> sequence = new TreeMap<>();

    /**
     * 此方法禁止外部调用
     *
     * @since 2023-1-26
     */
    private LongDiscrete() {
        super();
    }

    public static LongDiscrete getInstance() {
        return new LongDiscrete();
    }

    public static LongDiscrete getInstance(int[] array) {
        return new LongDiscrete().init(array);
    }

    public static LongDiscrete getInstance(long[] array) {
        return new LongDiscrete().init(array);
    }

    /**
     * 将传入的 array 视为“x = i + 1，y = array[i]，i 为数组下标”的数据
     *
     * @since 2023-1-26
     */
    public LongDiscrete init(int[] array) {
        this.clear();
        for (int index = 0; index < array.length; index++) {
            this.sequence.put((long) (index + 1), (long) array[index]);
        }
        return this;
    }

    /**
     * 将传入的 array 视为“x = i + 1，y = array[i]，i 为数组下标”的数据
     *
     * @since 2023-1-26
     */
    public LongDiscrete init(long[] array) {
        this.clear();
        for (int index = 0; index < array.length; index++) {
            this.sequence.put((long) (index + 1), (long) array[index]);
        }
        return this;
    }

    /**
     * @since 2023-1-26
     */
    public LongDiscrete init(Map<Long, Long> data) {
        this.clear();
        this.sequence.putAll(data);
        return this;
    }

    /**
     * @since 2023-1-26
     */
    public LongDiscrete clear() {
        this.sequence.clear();
        return this;
    }


    /**
     * @since 2023-1-26
     */
    public long get(int x) {
        return this.sequence.get(x);
    }

    /**
     * @since 2023-1-26
     */
    public LongDiscrete put(long x, long y) {
        this.sequence.put(x, y);
        return this;
    }

    /**
     * 本方法不会改变自身，会返回一个新对象
     *
     * 数学上规定，f(x) 的差分为 f(x+1) - f(x)
     *
     * 因为最后一个元素无法求差分，所以求差分之后，元素个数会减一
     *
     * @since 2023-1-26
     */
    public LongDiscrete difference() {
        var result = new LongDiscrete();
        for (var pair : this.sequence.entrySet()) {
            var x = pair.getKey();
            var y = pair.getValue();
            if (this.sequence.containsKey(x + 1)) {
                var dy = this.sequence.get(x + 1) - y;
                result.put(x, dy);
            }
        }
        return result;
    }

    /**
     * n 阶差分。n 需要小于当前函数的元素个数。
     * 因为对 n 个元素求 n 阶差分，元素个数会变成 0，所以求差分次数需要小于当前函数的元素个数
     *
     * 本方法不会改变自身，会返回一个新对象
     *
     * @since 2023-1-26
     */
    public LongDiscrete difference(int n) {
        if (n < 1) {
            throw new LogicalException("求差分次数不能小于 1");
        }
        int size = this.sequence.size();
        if (n >= size) {
            throw new LogicalException("求差分次数需要小于当前函数的元素个数");
        }

        LongDiscrete result = this;
        for (int i = 0; i < n; i++) {
            result = result.difference();
        }
        return result;
    }
}
