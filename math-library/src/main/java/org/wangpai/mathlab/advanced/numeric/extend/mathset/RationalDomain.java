package org.wangpai.mathlab.advanced.numeric.extend.mathset;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wangpai.exception.unchecked.ForbiddenCallingException;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Rational;

/**
 * 集合。可指定义域或值域
 *
 * 此集合的元素个数不能超过 int 类型的最大值
 *
 * @since 2024-12-16
 */
@ToString
@Accessors(chain = true)
public class RationalDomain implements Cloneable {
    private TreeSet<Rational> set = new TreeSet<>(); // 注意：TreeSet 的方法（包括 clone 方法）都是浅复制方法

    /**
     * @since 2023-1-28
     */
    public RationalDomain() {
        super();
    }

    /**
     * 此方法会有数据安全歧义，禁止调用
     *
     * @since 2023-1-28
     * @deprecated 2023-1-29
     */
    @Deprecated
    private RationalDomain(Set<Figure> data) {
        throw new ForbiddenCallingException("此方法会有数据安全歧义，禁止调用");
    }

    /**
     * 此方法会有数据安全歧义，禁止调用
     *
     * @since 2023-1-28
     * @deprecated 2023-1-29
     */
    @Deprecated
    private RationalDomain(Collection<Figure> data) {
        throw new ForbiddenCallingException("此方法会有数据安全歧义，禁止调用");
    }

    /**
     * @since 2023-1-28
     */
    public RationalDomain(int[] data) {
        for (var ele : data) {
            this.set.add(new Rational(ele));
        }
    }

    /**
     * 此方法会使用形参的数据副本，不会引用形参的数据
     *
     * @since 2023-1-28
     */
    public static RationalDomain getInstanceSafelyWithFigure(Set<Figure> data) {
        var result = new RationalDomain();
        for (var ele : data) {
            result.addSafely(new Rational(ele));
        }
        return result;
    }

    /**
     * 此方法会使用形参的数据副本，不会引用形参的数据
     *
     * @since 2023-1-28
     */
    public static RationalDomain getInstanceSafely(Set<Rational> data) {
        var result = new RationalDomain();
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
    public static RationalDomain getInstanceSafely(Collection<Rational> data) {
        var result = new RationalDomain();
        for (var ele : data) {
            result.addSafely(ele);
        }
        return result;
    }

    /**
     * 此方法生成对象将直接引用形参的 Figure 数据。在对效率要求高且安全得到保障时才可使用本方法
     *
     * @since 2023-1-28
     */
    public static RationalDomain getInstanceFrugally(Set<Rational> data) {
        var result = new RationalDomain();
        result.set.addAll(data);
        return result;
    }

    /**
     * 此方法生成对象将直接引用形参的 Figure 数据。在对效率要求高且安全得到保障时才可使用本方法
     *
     * @since 2023-1-28
     */
    public static RationalDomain getInstanceFrugally(Collection<Rational> data) {
        var result = new RationalDomain();
        result.set.addAll(data);
        return result;
    }

    /**
     * 此方法生成对象将直接引用形参的 Figure 数据。在对效率要求高且安全得到保障时才可使用本方法
     *
     * @since 2023-1-28
     */
    public static RationalDomain getInstanceFrugally(TreeSet<Rational> data) {
        var result = new RationalDomain();
        result.set = data;
        return result;
    }


    /**
     * 此方法是深克隆
     *
     * @since 2023-1-29
     */
    @Override
    public RationalDomain clone() {
        // 此处不能使用 TreeSet 的 clone 方法，因为它是浅克隆的
        return getInstanceSafely(this.set);
    }

    /**
     * 此方法会返回原始底层数据。在对效率要求高且安全得到保障时才可使用本方法
     *
     * @since 2023-1-28
     */
    public Set<Rational> toSetFrugally() {
        return this.set;
    }

    /**
     * 此方法返回的原始数据副本，不会返回原始数据
     *
     * @since 2023-1-28
     */
    public Set<Rational> toSetSafely() {
        return this.clone().set;
    }

    /**
     * 此方法返回的原始数据副本，不会返回原始数据
     *
     * @since 2023-1-28
     */
    public Rational getMinValue() {
        return this.set.first().clone();
    }

    /**
     * 此方法返回的原始数据副本，不会返回原始数据
     *
     * @since 2023-1-28
     */
    public Rational getMaxValue() {
        return this.set.last().clone();
    }

    /**
     * 此方法将直接引用形参的数据
     *
     * @since 2023-1-28
     */
    public RationalDomain addFrugally(Rational figure) {
        this.set.add(figure);
        return this;
    }

    /**
     * 此方法会使用形参的数据副本，不会引用形参的数据
     *
     * @since 2023-1-28
     */
    public RationalDomain addSafely(Rational figure) {
        this.set.add(figure.clone());
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
    public RationalDomain clear() {
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

        if (other instanceof RationalDomain) {
            return this.equals((RationalDomain) other);
        }

        return false;
    }

    /**
     * @since 2023-1-29
     */
    public boolean equals(RationalDomain other) {
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
