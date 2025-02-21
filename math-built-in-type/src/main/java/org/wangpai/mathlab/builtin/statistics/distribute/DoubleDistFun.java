package org.wangpai.mathlab.builtin.statistics.distribute;

import java.util.Map;
import java.util.TreeMap;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wangpai.mathlab.builtin.interval.DoubleInterval;

/**
 * 对 double 类型的值的分布函数
 *
 * @since 2023-2-12
 */
@ToString
@Accessors(chain = true)
public class DoubleDistFun {
    /**
     * 设变量原来的取值范围为 [a, b]。将这个范围先向右进行拓展，变成 [a, b + offset) =  [a, c)，
     * 然后对原区间进行分割，每个区间的大小为 c - a / intervalNum，
     * （为了方便表示，设 intervalNum 为 n，c - a / n 为 d）
     * 一共有 n 个区间：[a, a + d)，[a + d, a + 2d)，[a + 2d, a + 3d)，...，[a + (n-1)d, a + nd)
     *
     * 判断值在哪个区间的算法：
     * 先将值减去左端点值 a。这样也相当于将这 n 个区间变成了
     * [0, d)，[d, 2d)，[2d, 3d)，...，[(n-1)d, nd)。
     * 然后，将减去之后的值除以 d，然后向下取整。
     * 如果得到的数为 x，则原数在第 x + 1 个区间中
     */
    private long[] sumDist; // 此数组应该是单调不减的，且最大值为数据的总个数

    @Getter
    private double start;

    @Getter
    private double end;

    @Getter
    private double rightOffset; // 右端点的向右偏移量

    @Getter
    private int intervalNum; // 数据分割的区间数

    @Getter
    public double perIntervalLength; // 每个区间的长度

    /**
     * 此方法禁止外部调用
     *
     * @since 2023-2-12
     */
    private DoubleDistFun() {
        super();
    }

    /**
     * 此方法禁止外部调用
     *
     * @since 2023-2-12
     */
    private DoubleDistFun(DoubleDistribution pDensity) {
        super();
        this.start = pDensity.getStart();
        this.end = pDensity.getEnd();
        this.rightOffset = pDensity.getRightOffset();
        this.intervalNum = pDensity.getIntervalNum();
        this.perIntervalLength = pDensity.getPerIntervalLength();
        var p = pDensity.getUnderlyingArray();
        this.sumDist = new long[p.length];
        this.sumDist[0] = p[0];
        for (int index = 1; index < p.length; index++) {
            this.sumDist[index] = this.sumDist[index - 1] + p[index];
        }
    }

    /**
     * @since 2023-2-12
     */
    public static DoubleDistFun getInstance() {
        return new DoubleDistFun();
    }

    /**
     * @since 2023-2-12
     */
    public static DoubleDistFun getInstance(DoubleDistribution pDensity) {
        return new DoubleDistFun(pDensity);
    }

    /**
     * 将数据转化为 Map 对象
     *
     * @since 2023-2-12
     */
    public Map<DoubleInterval, Long> toMap() {
        var result = new TreeMap<DoubleInterval, Long>();
        for (int index = 0; index < this.sumDist.length; ++index) {
            var interval = DoubleInterval.getInstance(
                    this.start + index * this.perIntervalLength, this.perIntervalLength);
            result.put(interval, this.sumDist[index]);
        }
        return result;
    }

    /**
     * 转化为数学上概率分布函数的数据
     *
     * @since 2023-2-12
     */
    public Map<Double, Double> toDistFunMap() {
        var result = new TreeMap<Double, Double>();
        var total = this.sumDist[this.sumDist.length - 1];
        for (int index = 0; index < this.sumDist.length; ++index) {
            double x = this.start + index * this.perIntervalLength + this.perIntervalLength / 2;
            double y = ((double) this.sumDist[index]) / (total);
            result.put(x, y);
        }
        return result;
    }


    /**
     * 得到底层的 long[] 数据
     *
     * @since 2023-2-12
     */
    public long[] getUnderlyingArray() {
        return this.sumDist;
    }
}
