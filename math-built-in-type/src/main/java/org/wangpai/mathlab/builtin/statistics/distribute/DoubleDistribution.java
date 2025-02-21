package org.wangpai.mathlab.builtin.statistics.distribute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wangpai.commonutil.jdkenhance.structure.memorylinkedlist.MapTraverseProcess;
import org.wangpai.exception.unchecked.LogicalException;
import org.wangpai.mathlab.builtin.interval.DoubleInterval;
import org.wangpai.mathlab.builtin.statistics.primitive.LongStatistics;

/**
 * 对 double 类型的值的分布。此对象相当于数学上的概率密度函数，只不过是对离散值而言的
 *
 * @since 2023-2-11
 */
@ToString
@Accessors(chain = true)
public class DoubleDistribution {
    private ArrayList<Double> originData = new ArrayList<>();

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
    private long[] distribution;

    private boolean isFixed = false; // 标记本对象使用的是长度预设模式，还是自适应模式

    @Getter
    @Setter
    private double rightOffset = 0.0001; // 右端点的向右偏移量

    @Getter
    @Setter
    private int intervalNum; // 数据分割的区间数

    @Getter
    private double theoreticStart; // 非长度预设模式下，此值无效

    @Getter
    private double theoreticEnd; // 非长度预设模式下，此值无效

    @Getter
    private double actualStart; // 非自适应模式下，此值无效

    @Getter
    private double actualEnd; // 非自适应模式下，此值无效

    @Getter
    public double perIntervalLength; // 每个区间的长度

    /**
     * 此方法禁止外部调用
     *
     * @since 2023-2-11
     */
    private DoubleDistribution() {
        super();
    }

    /**
     * 此方法禁止外部调用
     *
     * @since 2023-2-11
     */
    private DoubleDistribution(int intervalNum) {
        super();
        this.intervalNum = intervalNum;
        this.isFixed = false;
    }

    /**
     * 此方法禁止外部调用
     *
     * @since 2023-2-11
     */
    private DoubleDistribution(int start, int end, int intervalNum) {
        super();
        this.theoreticStart = start;
        this.theoreticEnd = end;
        this.intervalNum = intervalNum;
        this.isFixed = true;
    }

    /**
     * @since 2023-2-11
     */
    public static DoubleDistribution getInstance(int intervalNum) {
        return new DoubleDistribution(intervalNum);
    }

    /**
     * @since 2023-2-11
     */
    public static DoubleDistribution getInstance(int start, int end, int intervalNum) {
        return new DoubleDistribution(start, end, intervalNum);
    }

    /**
     * @since 2023-2-11
     */
    public DoubleDistribution setFixedRange(double start, double end) {
        this.theoreticStart = start;
        this.theoreticEnd = end;
        this.isFixed = true;
        return this;
    }

    /**
     * @since 2023-2-11
     */
    public DoubleDistribution setFixedRange(double start, double end, int intervalNum) {
        this.setFixedRange(start, end);
        this.intervalNum = intervalNum;
        return this;
    }

    /**
     * @since 2023-2-11
     */
    public DoubleDistribution setSelfAdaptiveMode() {
        this.isFixed = false;
        return this;
    }

    /**
     * @since 2023-2-12
     */
    public double getStart() {
        if (this.isFixed) {
            return this.theoreticStart;
        } else {
            return this.actualStart;
        }
    }

    /**
     * @since 2023-2-12
     */
    public double getEnd() {
        if (this.isFixed) {
            return this.theoreticEnd;
        } else {
            return this.actualEnd;
        }
    }

    /**
     * @since 2023-2-11
     */
    public DoubleDistribution put(double data) {
        this.originData.add(data);
        return this;
    }

    /**
     * @since 2023-2-11
     */
    public DoubleDistribution put(int data) {
        this.put((double) data);
        return this;
    }

    /**
     * @since 2023-2-11
     */
    public DoubleDistribution put(double[] data) {
        for (var element : data) {
            this.put(element);
        }
        return this;
    }

    /**
     * @since 2023-2-11
     */
    public DoubleDistribution put(int[] data) {
        for (var element : data) {
            this.put(element);
        }
        return this;
    }

    /**
     * @since 2023-2-11
     */
    public DoubleDistribution put(List<Double> data) {
        for (var element : data) {
            this.put(element);
        }
        return this;
    }

    /**
     * 进行一次自适应计算。
     * 此方法会对 originData 进行一次排序
     *
     * @since 2023-2-11
     */
    public DoubleDistribution selfAdapting() {
        this.isFixed = false;
        Collections.sort(this.originData);
        this.distribution = new long[this.intervalNum];
        this.actualStart = this.originData.get(0);
        this.actualEnd = this.originData.get(this.originData.size() - 1);
        this.perIntervalLength = (this.actualEnd + this.rightOffset - this.actualStart) / this.intervalNum;

        for (var ele : this.originData) {
            int num = (int) ((ele - this.actualStart) / this.perIntervalLength);
            ++this.distribution[num];
        }

        return this;
    }

    /**
     * 进行计算
     *
     * @since 2023-2-12
     */
    public DoubleDistribution calculate() {
        if (this.isFixed) {
            this.fixedAnalyse();
        } else {
            this.selfAdapting();
        }
        return this;
    }

    /**
     * 进行一次预设长度的计算。
     * 此方法会对 originData 进行一次排序
     *
     * @since 2023-2-11
     */
    public DoubleDistribution fixedAnalyse() {
        if (!this.isFixed) {
            throw new LogicalException("只有在 fixed 模式下才能进行预设长度的计算");
        }

        Collections.sort(this.originData);
        this.distribution = new long[this.intervalNum];
        this.perIntervalLength = (this.theoreticEnd + this.rightOffset - this.theoreticStart) / this.intervalNum;

        for (var ele : this.originData) {
            int num = (int) ((ele - this.theoreticStart) / this.perIntervalLength);
            ++this.distribution[num];
        }

        return this;
    }

    /**
     * 将数据转化为 Map 对象
     *
     * @param needIgnoreZero 决定是否需要在返回结果中包含值为 0 的数据
     * @since 2023-2-11
     */
    public Map<DoubleInterval, Long> toMap(boolean needIgnoreZero) {
        double start = 0;
        if (this.isFixed) {
            this.fixedAnalyse();
            start = this.theoreticStart;
        } else {
            this.selfAdapting();
            start = this.actualStart;
        }
        var result = new TreeMap<DoubleInterval, Long>();
        if (needIgnoreZero) { // 此条件判断语句必须放到下面的循环之外
            for (int index = 0; index < this.distribution.length; ++index) {
                if (this.distribution[index] != 0) {
                    var interval = DoubleInterval.getInstance(
                            start + index * this.perIntervalLength, this.perIntervalLength);
                    result.put(interval, this.distribution[index]);
                }
            }
        } else {
            for (int index = 0; index < this.distribution.length; ++index) {
                var interval = DoubleInterval.getInstance(
                        start + index * this.perIntervalLength, this.perIntervalLength);
                result.put(interval, this.distribution[index]);
            }
        }
        return result;
    }

    /**
     * 转化为数学上概率密度函数的数据
     *
     * @since 2023-2-11
     */
    public Map<Double, Double> toDensityMap() {
        double start = 0;
        if (this.isFixed) {
            this.fixedAnalyse();
            start = this.theoreticStart;
        } else {
            this.selfAdapting();
            start = this.actualStart;
        }
        var total = LongStatistics.sum(this.distribution);
        var result = new TreeMap<Double, Double>();
        for (int index = 0; index < this.distribution.length; ++index) {
            double x = start + index * this.perIntervalLength + this.perIntervalLength / 2;
            double y = this.distribution[index] / (total * this.perIntervalLength);
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
        return this.distribution;
    }

    /**
     * 数据总数
     *
     * @since 2023-2-12
     */
    public long dataTotal() {
        this.calculate();
        return LongStatistics.sum(this.distribution);
    }

    /**
     * 对本对象求分布函数
     *
     * @since 2023-2-12
     */
    public DoubleDistFun integral() {
        this.calculate();
        return DoubleDistFun.getInstance(this);
    }

    /**
     * 遍历。遍历时不会提供原数据，提供的是数据副本
     *
     * MapTraverseProcess：此方法的入参代表遍历的每个元素<元素值，此元素值出现的次数>，返回值代表是否继续循环（true 代表继续循环）
     *
     * @since 2023-2-11
     */
    public DoubleDistribution traverse(MapTraverseProcess<DoubleInterval, Long> process) {
        for (int index = 0; index < this.distribution.length; ++index) {
            var interval = DoubleInterval.getInstance(
                    this.theoreticStart + index * this.perIntervalLength, this.perIntervalLength);
            if (!process.foreach(interval, this.distribution[index])) {
                break;
            }
        }
        return this;
    }
}
