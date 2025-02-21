package org.wangpai.mathlab.advanced.numeric.extend.complex;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.wangpai.mathlab.common.DoubleUtil;
import org.wangpai.exception.unchecked.ForbiddenCallingException;

/**
 * 复数
 *
 * @since 2023-1-30
 */
@Accessors(chain = true)
public class Complex implements Cloneable, Comparable<Complex> {
    @Getter
    private double real; // 实部

    @Getter
    private double imaginary; // 虚部

    /**
     * 标记本对象是不是常量。是常量的对象，不能对其使用自增、自减函数。
     * 由于 Java 的语法限制，无法在定义自增函数的同时，确保 Complex 常量不被破坏，因此只能以此加以逻辑限制
     */
    @Setter(AccessLevel.PRIVATE)
    private boolean isFinal = false;

    /**
     * 注意：不能直接使用 Complex 常量进行赋值！必须使用它的 clone 方法
     */
    public final static Complex ZERO = Complex.getInstanceByRect(0, 0).setFinal(true);
    public final static Complex ONE = Complex.getInstanceByRect(1, 0).setFinal(true);

    /**
     * 此方法禁止外部调用
     *
     * @since 2023-1-30
     */
    private Complex() {
        super();
    }

    /**
     * 使用直角坐标初始化复数
     *
     * @since 2023-1-31
     */
    public Complex(double re, double im) {
        this.real = re;
        this.imaginary = im;
    }

    /**
     * 使用直角坐标初始化复数
     *
     * @since 2023-1-30
     */
    public static Complex getInstanceByRect(double re, double im) {
        return new Complex(re, im);
    }

    /**
     * 使用极坐标初始化复数
     *
     * @since 2023-1-30
     */
    public static Complex getInstanceByPolar(double abs, double arg) {
        return new Complex(abs * Math.cos(arg), abs * Math.sin(arg));
    }

    /**
     * 此方法是深克隆
     *
     * @since 2023-1-30
     */
    @Override
    public Complex clone() {
        try {
            return (Complex) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 因为这个方法是重写方法，所以这个方法不能抛出异常
     *
     * 注意：other 不可能为基本类型
     *
     * @since 2023-1-30
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other instanceof Complex) {
            return this.equals((Complex) other);
        }

        return false;
    }

    /**
     * @since 2023-1-30
     */
    public boolean equals(Complex other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        // 注意：double 类型不能直接用 == 来比较是否相等
        return DoubleUtil.isEqual(this.real, other.real)
                && DoubleUtil.isEqual(this.imaginary, other.imaginary);
    }

    /**
     * @since 2023-1-30
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.real, this.real);
    }

    /**
     * @since 2023-1-30
     */
    @Override
    public String toString() {
        return "(" + this.real + ", " + this.imaginary + ")";
    }

    /**
     * @since 2023-1-30
     */
    public boolean isZero() {
        return DoubleUtil.isEqual(this.real, 0.0)
                && DoubleUtil.isEqual(this.imaginary, 0.0);
    }

    /**
     * 此方法禁止调用
     *
     * @since 2023-1-30
     * @deprecated 2023-1-30 复数无法大小比较
     */
    @Override
    @Deprecated
    public int compareTo(Complex other) {
        throw new ForbiddenCallingException("错误：复数无法大小比较");
    }

    /**
     * 转化为极坐标表示
     *
     * @since 2023-1-31
     */
    public PolarComplex toPolar() {
        return PolarComplex.getInstanceByRect(this.real, this.imaginary);
    }
}
