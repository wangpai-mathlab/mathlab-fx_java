package org.wangpai.mathlab.builtin.statistics.distribute;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wangpai.commonutil.jdkenhance.structure.memorylinkedlist.MapTraverseProcess;

/**
 * 对 int 类型的值的分布。此对象相当于数学上的概率密度函数，只不过是对离散值而言的
 *
 * @since 2023-2-5
 */
@ToString
@Accessors(chain = true)
public class IntDistribution {
    private long[] distribution;

    @Getter
    private int theoreticStart;

    @Getter
    private int theoreticEnd;

    @Getter
    private int actualStart; // 初始状态下，此值无效

    @Getter
    private int actualEnd; // 初始状态下，此值无效

    /**
     * 此方法禁止外部调用
     *
     * @since 2023-2-5
     */
    private IntDistribution() {
        super();
    }

    /**
     * 此方法禁止外部调用
     *
     * @since 2023-2-5
     */
    private IntDistribution(int start, int end) {
        super();
        this.theoreticStart = start;
        this.theoreticEnd = end;
        // 初始状态下，此两个值是颠倒的。这个为了方便后面的统计
        this.actualStart = this.theoreticEnd;
        this.actualEnd = this.theoreticStart;
        this.distribution = new long[end - start + 1];
    }

    /**
     * 得到理论上为 [start, end] 之间的数据统计区间
     *
     * @since 2023-2-5
     */
    public static IntDistribution getInstance(int start, int end) {
        return new IntDistribution(start, end);
    }

    /**
     * @since 2023-2-5
     */
    public IntDistribution put(int data) {
        ++this.distribution[data - this.theoreticStart];
        if (data < this.actualStart) {
            this.actualStart = data;
        }
        if (data > this.actualEnd) {
            this.actualEnd = data;
        }
        return this;
    }

    /**
     * @since 2023-2-5
     */
    public IntDistribution put(int[] data) {
        for (var element : data) {
            this.put(element);
        }
        return this;
    }

    /**
     * @since 2023-2-5
     */
    public IntDistribution put(List<Integer> data) {
        for (var element : data) {
            this.put(element);
        }
        return this;
    }

    /**
     * 将非零数据转化为 Map 对象。返回结果中不含数据值为零的数据
     *
     * @param needIgnoreZero 决定是否需要在返回结果中包含值为 0 的数据
     * @since 2023-2-5
     */
    public Map<Integer, Long> toMap(boolean needIgnoreZero) {
        var result = new TreeMap<Integer, Long>();
        if (needIgnoreZero) { // 此条件判断语句必须放到下面的循环之外
            for (int index = 0; index < this.distribution.length; ++index) {
                if (this.distribution[index] != 0) {
                    result.put(index + this.theoreticStart, this.distribution[index]);
                }
            }
        } else {
            for (int index = 0; index < this.distribution.length; ++index) {
                result.put(index + this.theoreticStart, this.distribution[index]);
            }
        }
        return result;
    }

    /**
     * 遍历。遍历时不会提供原数据，提供的是数据副本
     *
     * MapTraverseProcess：此方法的入参代表遍历的每个元素<元素值，此元素值出现的次数>，返回值代表是否继续循环（true 代表继续循环）
     *
     * @since 2023-2-5
     */
    public IntDistribution traverse(MapTraverseProcess<Integer, Long> process) {
        for (int index = 0; index < this.distribution.length; ++index) {
            if (!process.foreach(index + this.theoreticStart, this.distribution[index])) {
                break;
            }
        }
        return this;
    }
}
