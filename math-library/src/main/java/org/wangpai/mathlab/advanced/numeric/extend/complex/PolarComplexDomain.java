package org.wangpai.mathlab.advanced.numeric.extend.complex;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wangpai.exception.unchecked.ForbiddenCallingException;

/**
 * 集合。可指定义域或值域
 *
 * 此集合的元素个数不能超过 int 类型的最大值
 *
 * @since 2024-12-16
 */
@ToString
@Accessors(chain = true)
public class PolarComplexDomain implements Cloneable {
    private HashSet<PolarComplex> set = new HashSet<>(); // 注意：因为 PolarComplex 不支持大小比较，所以此处不能使用 TreeSet

    /**
     * @since 2023-1-28
     */
    public PolarComplexDomain() {
        super();
    }

    /**
     * 此方法会有数据安全歧义，禁止调用
     *
     * @since 2023-1-28
     * @deprecated 2023-1-29
     */
    @Deprecated
    private PolarComplexDomain(Set<PolarComplex> data) {
        throw new ForbiddenCallingException("此方法会有数据安全歧义，禁止调用");
    }

    /**
     * 此方法会有数据安全歧义，禁止调用
     *
     * @since 2023-1-28
     * @deprecated 2023-1-29
     */
    @Deprecated
    private PolarComplexDomain(Collection<PolarComplex> data) {
        throw new ForbiddenCallingException("此方法会有数据安全歧义，禁止调用");
    }

    /**
     * 此方法会使用形参的数据副本，不会引用形参的数据
     *
     * @since 2023-1-28
     */
    public static PolarComplexDomain getInstanceSafely(Set<PolarComplex> data) {
        var result = new PolarComplexDomain();
        for (var ele : data) {
            result.addSafely(ele);
        }
        return result;
    }

    /**
     * 此方法会使用形参的数据副本，不会引用形参的数据
     *
     * @since 2023-1-28
     */
    public static PolarComplexDomain getInstanceSafely(Collection<PolarComplex> data) {
        var result = new PolarComplexDomain();
        for (var ele : data) {
            result.addSafely(ele);
        }
        return result;
    }

    /**
     * 此方法生成对象将直接引用形参的  PolarComplex 数据。在对效率要求高且安全得到保障时才可使用本方法
     *
     * @since 2023-1-28
     */
    public static PolarComplexDomain getInstanceFrugally(Set<PolarComplex> data) {
        var result = new PolarComplexDomain();
        result.set.addAll(data);
        return result;
    }

    /**
     * 此方法生成对象将直接引用形参的  PolarComplex 数据。在对效率要求高且安全得到保障时才可使用本方法
     *
     * @since 2023-1-28
     */
    public static PolarComplexDomain getInstanceFrugally(Collection<PolarComplex> data) {
        var result = new PolarComplexDomain();
        result.set.addAll(data);
        return result;
    }

    /**
     * 此方法生成对象将直接引用形参的  Complex 数据。在对效率要求高且安全得到保障时才可使用本方法
     *
     * @since 2023-1-28
     */
    public static PolarComplexDomain getInstanceFrugally(HashSet<PolarComplex> data) {
        var result = new PolarComplexDomain();
        result.set = data;
        return result;
    }

    /**
     * 此方法是深克隆
     *
     * @since 2023-1-29
     */
    @Override
    public PolarComplexDomain clone() {
        // 此处不能使用 TreeSet 的 clone 方法，因为它是浅克隆的
        return getInstanceSafely(this.set);
    }

    /**
     * 此方法会返回原始底层数据。在对效率要求高且安全得到保障时才可使用本方法
     *
     * @since 2023-1-28
     */
    public Set<PolarComplex> toSetFrugally() {
        return this.set;
    }

    /**
     * 此方法返回的原始数据副本，不会返回原始数据
     *
     * @since 2023-1-28
     */
    public Set<PolarComplex> toSetSafely() {
        return this.clone().set;
    }

    /**
     * 此方法将直接引用形参的数据
     *
     * @since 2023-1-28
     */
    public PolarComplexDomain addFrugally(PolarComplex ele) {
        this.set.add(ele);
        return this;
    }

    /**
     * 此方法会使用形参的数据副本，不会引用形参的数据
     *
     * @since 2023-1-28
     */
    public PolarComplexDomain addSafely(PolarComplex ele) {
        this.set.add(ele);
        return this;
    }

    /**
     * @since 2023-1-28
     */
    public int size() {
        return this.set.size();
    }

    /**
     * @since 2023-1-28
     */
    public PolarComplexDomain clear() {
        this.set.clear();
        return this;
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

        if (other instanceof PolarComplexDomain) {
            return this.equals((PolarComplexDomain) other);
        }

        return false;
    }

    /**
     * @since 2023-1-29
     */
    public boolean equals(PolarComplexDomain other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        return this.set.equals(other.set);
    }

    /**
     * @since 2023-1-29
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(this.set);
    }
}
