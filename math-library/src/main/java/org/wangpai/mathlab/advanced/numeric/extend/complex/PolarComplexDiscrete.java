package org.wangpai.mathlab.advanced.numeric.extend.complex;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wangpai.exception.unchecked.ForbiddenCallingException;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.advanced.numeric.extend.mathset.FigureDomain;

/**
 * @since 2023-1-30
 */
@ToString
@Accessors(chain = true)
public class PolarComplexDiscrete implements Cloneable {
    /**
     * Map<Figure, PolarComplex> 代表 <x, y>。x 为从 1 开始的连续正整数。但 x 可以不从 1 开始，可在任意连续正整数区间内取值
     *
     * 返回值按 x 升序排列
     *
     * 此处是自变量为整数，因变量为复数的离散函数
     *
     * @since 2023-1-26
     */
    @Getter
    private TreeMap<Figure, PolarComplex> sequence = new TreeMap<>();

    /**
     * @since 2023-1-26
     */
    public PolarComplexDiscrete() {
        super();
    }

    public static PolarComplexDiscrete getInstance() {
        return new PolarComplexDiscrete();
    }

    /**
     * 此方法会有数据安全歧义，禁止调用
     *
     * @since 2023-1-26
     * @deprecated 2023-1-29
     */
    @Deprecated
    private PolarComplexDiscrete init(Map<Complex, PolarComplex> data) {
        throw new ForbiddenCallingException("此方法会有数据安全歧义，禁止调用");
    }

    /**
     * @since 2023-1-26
     */
    public PolarComplexDiscrete clear() {
        this.sequence.clear();
        return this;
    }

    /**
     * 此方法是深克隆
     *
     * @since 2023-1-29
     */
    @Override
    public PolarComplexDiscrete clone() {
        // 此处不能使用 TreeMap 的 clone 方法，因为它是浅克隆的
        var cloned = new PolarComplexDiscrete();
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

        if (other instanceof PolarComplexDiscrete) {
            return this.equals((PolarComplexDiscrete) other);
        }

        return false;
    }

    /**
     * @since 2023-1-29
     */
    public boolean equals(PolarComplexDiscrete other) {
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
    public PolarComplex get(Figure x) {
        return this.sequence.get(x);
    }

    /**
     * 此方法将直接引用形参的数据
     *
     * @since 2023-1-26
     */
    public PolarComplexDiscrete putFrugally(Figure x, PolarComplex y) {
        this.sequence.put(x, y);
        return this;
    }

    /**
     * 此方法会使用形参的数据副本，不会引用形参的数据
     *
     * @since 2023-1-26
     */
    public PolarComplexDiscrete putSafely(Figure x, PolarComplex y) {
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
    public PolarComplexDomain getYDomain() {
        return PolarComplexDomain.getInstanceSafely(this.sequence.values());
    }

    /**
     * 获得所有的 y 值。这包含 y 中所有重复的值
     *
     * @deprecated 2023-1-29 此方法会返回原始数据，因此不安全
     * @since 2023-1-28
     */
    @Deprecated
    public Collection<PolarComplex> getOriginYDomain() {
        return this.sequence.values();
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
        return "PolarComplexDiscrete{D[x]=y | " +
                result.toString() +
                '}';
    }
}
