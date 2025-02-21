package org.wangpai.mathlab.advanced.numeric.extend.discretefun;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.wangpai.commonutil.jdkenhance.util.MapUtil;
import org.wangpai.exception.unchecked.ForbiddenCallingException;
import org.wangpai.exception.unchecked.LogicalException;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Rational;
import org.wangpai.mathlab.advanced.numeric.basic.operation.FigureOperation;
import org.wangpai.mathlab.advanced.numeric.basic.operation.RationalOperation;
import org.wangpai.mathlab.advanced.numeric.extend.complex.ComplexDiscrete;
import org.wangpai.mathlab.advanced.numeric.extend.mathset.FigureDomain;
import org.wangpai.mathlab.advanced.numeric.extend.mathset.RationalDomain;
import org.wangpai.mathlab.advanced.statistics.RationalStatistics;

/**
 * 离散函数
 *
 * @since 2023-1-26
 */
@Accessors(chain = true)
public class Discrete implements Cloneable {
    /**
     * 数列。
     *
     * Map<Figure, Figure> 代表 <x, y>。x 为从 1 开始的连续正整数。但 x 可以不从 1 开始，可在任意连续正整数区间内取值
     *
     * 返回值按 x 升序排列
     *
     * @since 2023-1-26
     */
    @Getter
    private TreeMap<Figure, Rational> sequence = new TreeMap<>();

    /**
     * @since 2023-1-26
     */
    public Discrete() {
        super();
    }

    /**
     * @since 2023-1-26
     */
    public static Discrete getInstance() {
        return new Discrete();
    }

    /**
     * @since 2023-1-26
     */
    public static Discrete getInstance(int[] array) {
        return new Discrete().init(array);
    }

    /**
     * @since 2023-1-26
     */
    public static Discrete getInstance(long[] array) {
        return new Discrete().init(array);
    }

    /**
     * 将传入的 array 视为“x = i + 1，y = array[i]，i 为数组下标”的数据
     *
     * @since 2023-1-26
     */
    public Discrete init(int[] array) {
        this.clear();
        for (int index = 0; index < array.length; index++) {
            this.sequence.put(new Figure(index + 1), new Rational(array[index]));
        }
        return this;
    }

    /**
     * 此方法会使用形参的数据副本，不会引用形参的数据
     *
     * 将传入的 array 视为“x = i + 1，y = array[i]，i 为数组下标”的数据
     *
     * @since 2023-1-26
     */
    public Discrete init(long[] array) {
        this.clear();
        for (int index = 0; index < array.length; index++) {
            this.sequence.put(new Figure(index + 1), new Rational(array[index]));
        }
        return this;
    }

    /**
     * 此方法会有数据安全歧义，禁止调用
     *
     * @since 2023-1-26
     * @deprecated 2023-1-29
     */
    @Deprecated
    private Discrete init(Map<Figure, Figure> data) {
        throw new ForbiddenCallingException("此方法会有数据安全歧义，禁止调用");
    }

    /**
     * 将形参中的 Map 数据加入到本对象中
     *
     * 此方法将直接引用形参的数据
     *
     * @since 2023-1-26
     */
    public Discrete takeInFrugally(Map<Figure, Rational> data) {
        var entries = data.entrySet();
        for (var pair : entries) {
            this.putFrugally(pair.getKey(), pair.getValue());
        }
        return this;
    }

    /**
     * @since 2023-1-26
     */
    public Discrete clear() {
        this.sequence.clear();
        return this;
    }

    /**
     * 逆置。逆置指的是保持 Map 的 Key 值顺序和值不变，将所有的 Value 值倒置
     *
     * 此方法不会改变本对象，会返回一个新对象。此方法使用的是浅克隆
     *
     * @since 2023-2-10
     */
    public Discrete reverse() {
        var data = MapUtil.reverseMap(this.sequence);
        var result = Discrete.getInstance();
        result.sequence = data;
        return result;
    }

    /**
     * 此方法是深克隆
     *
     * @since 2023-1-29
     */
    @Override
    public Discrete clone() {
        // 此处不能使用 TreeMap 的 clone 方法，因为它是浅克隆的
        var cloned = new Discrete();
        var pairs = this.sequence.entrySet();
        for (var pair : pairs) {
            cloned.putSafely(pair.getKey(), pair.getValue());
        }
        return cloned;
    }

    /**
     * 因为这个方法是重写方法，所以这个方法不能抛出异常
     *
     * 注意：other 不可能为基本类型
     *
     * @since 2023-1-29
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other instanceof Discrete) {
            return this.equals((Discrete) other);
        }

        return false;
    }

    /**
     * @since 2023-1-29
     */
    public boolean equals(Discrete other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        return this.sequence.equals(other.sequence);
    }

    /**
     * @since 2023-1-29
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(this.sequence);
    }

    /**
     * 判断 x 是否在定义域内
     *
     * @since 2023-2-10
     */
    public boolean contain(Figure x) {
        return this.sequence.containsKey(x);
    }

    /**
     * 如果元素不存在，将返回 null
     *
     * @since 2023-1-26
     */
    public Rational get(Figure x) {
        return this.sequence.get(x);
    }

    /**
     * @since 2023-1-26
     */
    public Rational get(int x) {
        return this.get(new Figure(x));
    }

    /**
     * 此方法将直接引用形参的数据
     *
     * @since 2023-1-26
     */
    public Discrete putFrugally(Figure x, Rational y) {
        this.sequence.put(x, y);
        return this;
    }

    /**
     * 此方法会使用形参的数据副本，不会引用形参的数据
     *
     * @since 2023-1-26
     */
    public Discrete putSafely(Figure x, Rational y) {
        this.sequence.put(x.clone(), y.clone());
        return this;
    }

    /**
     * 获得所有的 x 值。此方法返回的原始数据副本，不会返回原始数据
     *
     * @since 2023-1-28
     */
    public FigureDomain getXDomain() {
        return FigureDomain.getInstanceSafely(this.sequence.keySet());
    }

    /**
     * 获得 y 值集合。此方法返回的原始数据副本，不会返回原始数据
     *
     * 注意：这不会重复包含 y 中重复的值
     *
     * @since 2023-1-28
     */
    public RationalDomain getYDomain() {
        return RationalDomain.getInstanceSafely(this.sequence.values());
    }

    /**
     * @since 2023-1-30
     */
    public int size() {
        return this.sequence.size();
    }

    /**
     * 获得所有的 y 值。这包含 y 中所有重复的值
     *
     * @since 2023-1-28
     * @deprecated 2023-1-29 此方法会返回原始数据，因此不安全
     */
    @Deprecated
    public Collection<Rational> getOriginYDomain() {
        return this.sequence.values();
    }

    /**
     * 求平均数。此方法只会使用因变量的值
     *
     * 本方法不会改变自身
     *
     * @since 2023-2-16
     */
    public Rational average() {
        var sum = Rational.ZERO;
        var values = this.sequence.values();
        for (var ele : values) {
            sum = RationalOperation.add(sum, ele);
        }
        return RationalOperation.divide(sum, values.size());
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
    public Discrete difference() {
        var result = new Discrete();
        for (var pair : this.sequence.entrySet()) {
            var x = pair.getKey();
            var y = pair.getValue();
            var xP1 = FigureOperation.add(x, Figure.ONE);
            if (this.sequence.containsKey(xP1)) {
                var dy = RationalOperation.subtract(this.sequence.get(xP1), y);
                result.putFrugally(x, dy);
            }
        }
        return result;
    }

    /**
     * 绝对值差分
     *
     * 本方法不会改变自身，会返回一个新对象
     *
     * 数学上规定，f(x) 的差分为 f(x+1) - f(x)
     *
     * 因为最后一个元素无法求差分，所以求差分之后，元素个数会减一
     *
     * @since 2023-1-26
     */
    public Discrete absDiff() {
        var result = new Discrete();
        for (var pair : this.sequence.entrySet()) {
            var x = pair.getKey();
            var y = pair.getValue();
            var xP1 = FigureOperation.add(x, Figure.ONE);
            if (this.sequence.containsKey(xP1)) {
                var dy = RationalOperation.subtract(this.sequence.get(xP1), y);
                var absDy = RationalOperation.getAbsolute(dy);
                result.putFrugally(x, absDy);
            }
        }
        return result;
    }

    /**
     * n 阶绝对值差分。n 需要小于当前函数的元素个数。
     * 因为对 n 个元素求 n 阶差分，元素个数会变成 0，所以求差分次数需要小于当前函数的元素个数
     *
     * 规定：0 阶差分为其自身
     *
     * 本方法不会改变自身，会返回一个新对象
     *
     * @since 2023-1-26
     */
    public Discrete difference(int n) {
        if (n == 0) {
            return this.clone();
        }
        if (n < 0) {
            throw new LogicalException("差分次数不能为负数");
        }
        if (n >= this.sequence.size()) {
            throw new LogicalException("差分次数需要小于当前函数的元素个数");
        }

        Discrete result = this;
        for (int i = 0; i < n; i++) {
            result = result.difference();
        }
        return result;
    }

    /**
     * n 阶绝对值差分。n 需要小于当前函数的元素个数。
     * 因为对 n 个元素求 n 阶差分，元素个数会变成 0，所以求差分次数需要小于当前函数的元素个数
     *
     * 规定：0 阶绝对值差分为其自身取绝对值
     *
     * 本方法不会改变自身，会返回一个新对象
     *
     * @since 2023-1-26
     */
    public Discrete absDiff(int n) {
        if (n == 0) {
            var result = new Discrete();
            for (var pair : this.sequence.entrySet()) {
                result.putFrugally(pair.getKey(), RationalOperation.getOpposite(pair.getValue()));
            }
        }
        if (n < 1) {
            throw new LogicalException("差分次数不能小于 1");
        }
        if (n >= this.sequence.size()) {
            throw new LogicalException("差分次数需要小于当前函数的元素个数");
        }

        var result = this;
        for (int i = 0; i < n; i++) {
            result = result.absDiff();
        }
        return result;
    }

    /**
     * d 距差分
     *
     * 本方法不会改变自身，会返回一个新对象
     *
     * f(x) 的 d 距差分为 f(x+d) - f(x)。所以求差分之后，元素个数会减 d
     *
     * @since 2023-1-26
     */
    public Discrete dDifference(int d) {
        if (d < 1) {
            throw new LogicalException("求 d 距差分差分距离不能小于 1");
        }
        int size = this.sequence.size();
        if (d >= size) {
            throw new LogicalException("求 d 距差分，差分距离需要小于当前函数的元素个数");
        }

        var result = new Discrete();
        for (var pair : this.sequence.entrySet()) {
            var x = pair.getKey();
            var y = pair.getValue();
            var xPd = FigureOperation.add(x, new Figure(d));
            if (this.sequence.containsKey(xPd)) {
                var dy = RationalOperation.subtract(this.sequence.get(xPd), y);
                result.putFrugally(x, dy);
            }
        }
        return result;
    }

    /**
     * n 阶 d 距差分。n * d 需要小于当前函数的元素个数
     *
     * 规定：0 阶差分为其自身
     *
     * 本方法不会改变自身，会返回一个新对象
     *
     * @since 2023-1-26
     */
    public Discrete dDifference(int n, int d) {
        if (n == 0) {
            return this.clone();
        }
        if (n < 1) {
            throw new LogicalException("差分次数不能小于 1");
        }
        if (d < 1) {
            throw new LogicalException("求 d 距差分差分距离不能小于 1");
        }
        if (n * d >= this.sequence.size()) {
            throw new LogicalException("差分次数与差分距离之积需要小于当前函数的元素个数");
        }

        var result = this;
        for (int i = 0; i < n; i++) {
            result = result.dDifference(d);
        }
        return result;
    }

    /**
     * 求前 n 项和
     *
     * 本方法不会改变自身，会返回一个新对象
     *
     * @since 2023-2-16
     */
    public Discrete sum() {
        var result = new Discrete();
        var sum = Rational.ZERO;
        for (var pair : this.sequence.entrySet()) {
            sum = RationalOperation.add(sum, pair.getValue());
            result.putFrugally(pair.getKey().clone(), sum);
        }
        return result;
    }

    /**
     * 求 m 次前 n 项和。规定：当 m = 0 时，返回自身的副本
     *
     * 本方法不会改变自身，会返回一个新对象
     *
     * @since 2023-2-16
     */
    public Discrete sum(int m) {
        if (m == 0) {
            return this.clone();
        }
        if (m < 0) {
            throw new LogicalException("求前 n 项和次数不能为负数");
        }

        Discrete result = this;
        for (int i = 0; i < m; i++) {
            result = result.sum();
        }
        return result;
    }

    /**
     * 求组和。组和指的是将数据以每 groupSize 个进行合并，尾部剩下的元素丢弃。
     * 因此，如果有 n 个元素，则返回的数据中 n / num 个元素。
     *
     * 合并只针对因变量，因此合并后自变量的数据会消失
     *
     * 本方法不会改变自身，会返回一个新对象
     *
     * @since 2023-1-26
     */
    public Discrete groupSum(int groupSize) {
        var result = new Discrete();
        var resultIndex = Figure.ONE.clone();
        int groupIndex = 0;
        var group = new Rational[groupSize];
        for (var pair : this.sequence.entrySet()) {
            if (groupIndex == groupSize) {
                result.putSafely(resultIndex, RationalStatistics.sum(group));
                resultIndex.increaseOne();
                groupIndex = 0;
            }
            group[groupIndex] = pair.getValue();
            ++groupIndex;
        }
        // 防止原数据个数刚好是 groupSize 的倍数时，尾部数据被放弃
        if (groupIndex == groupSize) {
            result.putSafely(resultIndex, RationalStatistics.sum(group));
        }
        return result;
    }

    /**
     * 自卷积
     *
     * 本方法不会改变自身，会返回一个新对象
     *
     * @since 2023-1-28
     */
    public Discrete selfConvolution() {
        return DiscreteOperation.convolution(this, this);
    }

    /**
     * 离散傅里叶变换
     *
     * @since 2023-1-30
     */
    public ComplexDiscrete dft() {
        return DiscreteOperation.dft(this);
    }

    /**
     * @since 2024-12-16
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (var entry : this.sequence.entrySet()) {
            // 如果不是第一个坐标，加上逗号
            if (!result.isEmpty()) {
                result.append(", ");
            }
            result.append("[")
                    .append(entry.getKey())
                    .append("]=")
                    .append(entry.getValue());
        }
        return "Discrete{D[x]=y | " +
                result.toString() +
                '}';
    }
}
