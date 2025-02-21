package org.wangpai.mathlab.advanced.numeric.extend.complex;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wangpai.exception.unchecked.ForbiddenCallingException;
import org.wangpai.exception.unchecked.LogicalException;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.advanced.numeric.basic.operation.FigureOperation;
import org.wangpai.mathlab.advanced.numeric.extend.mathset.FigureDomain;

/**
 * @since 2023-1-30
 */
@ToString
@Accessors(chain = true)
public class ComplexDiscrete implements Cloneable {
    /**
     * Map<Figure, Complex> 代表 <x, y>。x 为从 1 开始的连续正整数。但 x 可以不从 1 开始，可在任意连续正整数区间内取值
     *
     * 返回值按 x 升序排列
     *
     * 此处是自变量为整数，因变量为复数的离散函数
     *
     * @since 2023-1-26
     */
    @Getter
    private TreeMap<Figure, Complex> sequence = new TreeMap<>();

    /**
     * @since 2023-1-26
     */
    public ComplexDiscrete() {
        super();
    }

    public static ComplexDiscrete getInstance() {
        return new ComplexDiscrete();
    }

    /**
     * 此方法会有数据安全歧义，禁止调用
     *
     * @since 2023-1-26
     * @deprecated 2023-1-29
     */
    @Deprecated
    private ComplexDiscrete init(Map<Complex, Complex> data) {
        throw new ForbiddenCallingException("此方法会有数据安全歧义，禁止调用");
    }

    /**
     * @since 2023-1-26
     */
    public ComplexDiscrete clear() {
        this.sequence.clear();
        return this;
    }

    /**
     * 此方法是深克隆
     *
     * @since 2023-1-29
     */
    @Override
    public ComplexDiscrete clone() {
        // 此处不能使用 TreeMap 的 clone 方法，因为它是浅克隆的
        var cloned = new ComplexDiscrete();
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

        if (other instanceof ComplexDiscrete) {
            return this.equals((ComplexDiscrete) other);
        }

        return false;
    }

    /**
     * @since 2023-1-29
     */
    public boolean equals(ComplexDiscrete other) {
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
     * 如果元素不存在，将返回 null
     *
     * @since 2023-1-26
     */
    public Complex get(Figure x) {
        return this.sequence.get(x);
    }

    /**
     * 此方法将直接引用形参的数据
     *
     * @since 2023-1-26
     */
    public ComplexDiscrete putFrugally(Figure x, Complex y) {
        this.sequence.put(x, y);
        return this;
    }

    /**
     * 此方法会使用形参的数据副本，不会引用形参的数据
     *
     * @since 2023-1-26
     */
    public ComplexDiscrete putSafely(Figure x, Complex y) {
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
    public ComplexDomain getYDomain() {
        return ComplexDomain.getInstanceSafely(this.sequence.values());
    }

    /**
     * 获得所有的 y 值。这包含 y 中所有重复的值
     *
     * @deprecated 2023-1-29 此方法会返回原始数据，因此不安全
     * @since 2023-1-28
     */
    @Deprecated
    public Collection<Complex> getOriginYDomain() {
        return this.sequence.values();
    }

    /**
     * @since 2024-12-16
     */
    public PolarComplexDiscrete toPolarComplexDiscrete() {
        var result = new PolarComplexDiscrete();
        for (var pair : this.sequence.entrySet()) {
            var x = pair.getKey();
            var y = pair.getValue();
            result.putFrugally(x, y.toPolar());
        }
        return result;
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
    public ComplexDiscrete difference() {
        var result = new ComplexDiscrete();
        for (var pair : this.sequence.entrySet()) {
            var x = pair.getKey();
            var y = pair.getValue();
            var xP1 = FigureOperation.add(x, Figure.ONE);
            if (this.sequence.containsKey(xP1)) {
                var dy = ComplexOperation.subtract(this.sequence.get(xP1), y);
                result.putFrugally(x, dy);
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
    public ComplexDiscrete difference(int n) {
        if (n < 1) {
            throw new LogicalException("差分次数不能小于 1");
        }
        if (n >= this.sequence.size()) {
            throw new LogicalException("差分次数需要小于当前函数的元素个数");
        }

        ComplexDiscrete result = this;
        for (int i = 0; i < n; i++) {
            result = result.difference();
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
    public ComplexDiscrete dDifference(int d) {
        if (d < 1) {
            throw new LogicalException("求 d 距差分差分距离不能小于 1");
        }
        int size = this.sequence.size();
        if (d >= size) {
            throw new LogicalException("求 d 距差分，差分距离需要小于当前函数的元素个数");
        }

        var result = new ComplexDiscrete();
        for (var pair : this.sequence.entrySet()) {
            var x = pair.getKey();
            var y = pair.getValue();
            var xPd = FigureOperation.add(x, new Figure(d));
            if (this.sequence.containsKey(xPd)) {
                var dy = ComplexOperation.subtract(this.sequence.get(xPd), y);
                result.putFrugally(x, dy);
            }
        }
        return result;
    }

    /**
     * n 阶 d 距差分。n * d 需要小于当前函数的元素个数
     *
     * 本方法不会改变自身，会返回一个新对象
     *
     * @since 2023-1-26
     */
    public ComplexDiscrete dDifference(int n, int d) {
        if (n < 1) {
            throw new LogicalException("差分次数不能小于 1");
        }
        if (d < 1) {
            throw new LogicalException("求 d 距差分差分距离不能小于 1");
        }
        if (n * d >= this.sequence.size()) {
            throw new LogicalException("差分次数与差分距离之积需要小于当前函数的元素个数");
        }

        ComplexDiscrete result = this;
        for (int i = 0; i < n; i++) {
            result = result.dDifference(d);
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
    public ComplexDiscrete selfConvolution() {
        return ComplexDiscreteOperation.convolution(this, this);
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
        return "ComplexDiscrete{D[x]=y | " +
                result.toString() +
                '}';
    }
}
