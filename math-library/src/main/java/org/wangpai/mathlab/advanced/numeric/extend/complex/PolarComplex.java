package org.wangpai.mathlab.advanced.numeric.extend.complex;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.wangpai.exception.unchecked.UnexpectedException;
import org.wangpai.mathlab.common.DoubleUtil;
import org.wangpai.exception.unchecked.ForbiddenCallingException;

/**
 * 使用极坐标表示的复数
 *
 * @since 2023-1-31
 */
@Accessors(chain = true)
public class PolarComplex implements Cloneable, Comparable<PolarComplex> {
    @Getter
    private double modulus; // 复数的模

    /**
     * 复数的辐角。
     *
     * 数学上规定，这个主值辐角只能为 (-π, π]。
     * 这里额外规定，当 modulus 为 0 时，argument 也为 0
     */
    @Getter
    private double argument;

    /**
     * 标记本对象是不是常量。是常量的对象，不能对其使用自增、自减函数。
     * 由于 Java 的语法限制，无法在定义自增函数的同时，确保 PolarComplex 常量不被破坏，因此只能以此加以逻辑限制
     */
    @Setter(AccessLevel.PRIVATE)
    private boolean isFinal = false;

    /**
     * 注意：不能直接使用 PolarComplex 常量进行赋值！必须使用它的 clone 方法
     */
    public final static PolarComplex ZERO = PolarComplex.getInstanceByRect(0, 0).setFinal(true);
    public final static PolarComplex ONE = PolarComplex.getInstanceByRect(1, 0).setFinal(true);

    /**
     * 此方法禁止外部调用
     *
     * @since 2023-1-31
     */
    private PolarComplex() {
        super();
    }

    /**
     * 使用极坐标初始化复数
     *
     * @since 2023-1-31
     */
    public PolarComplex(double abs, double arg) {
        this.modulus = abs;
        this.argument = arg;
    }

    /**
     * 使用直角坐标初始化复数
     *
     * @since 2023-1-31
     */
    public static PolarComplex getInstanceByRect(double re, double im) {
        var result = new PolarComplex();
        result.modulus = Math.sqrt(re * re + im * im);

        var sinArg = im / result.modulus;
        var cosArg = re / result.modulus;
        if (DoubleUtil.isEqual(result.modulus, 0)) { // 这里额外规定，当 modulus 为 0 时，argument 也为 0
            result.argument = 0;
        } else if (sinArg >= 0 && cosArg >= 0) { // 第一象限
            result.argument = Math.asin(sinArg);
        } else if (sinArg >= 0 && cosArg <= 0) { // 第二象限
            result.argument = Math.PI - Math.asin(sinArg);
        } else if (sinArg <= 0 && cosArg <= 0) { // 第三象限
            result.argument = -Math.PI - Math.asin(sinArg);
        } else if (sinArg <= 0 && cosArg >= 0) { // 第四象限
            result.argument = Math.asin(sinArg);
        } else { // 此分支应该不会发生
            throw new UnexpectedException("错误，辐角无法计算");
        }

        return result;
    }

    /**
     * 使用极坐标初始化复数
     *
     * @since 2023-1-31
     */
    public static PolarComplex getInstanceByPolar(double abs, double arg) {
        return new PolarComplex(abs, arg);
    }

    /**
     * 此方法是深克隆
     *
     * @since 2023-1-30
     */
    @Override
    public PolarComplex clone() {
        try {
            return (PolarComplex) super.clone();
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

        if (other instanceof PolarComplex) {
            return this.equals((PolarComplex) other);
        }

        return false;
    }

    /**
     * @since 2023-1-30
     */
    public boolean equals(PolarComplex other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        // 注意：double 类型不能直接用 == 来比较是否相等
        return DoubleUtil.isEqual(this.modulus, other.modulus)
                && DoubleUtil.isEqual(this.argument, other.argument);
    }

    /**
     * @since 2023-1-30
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.modulus, this.modulus);
    }

    /**
     * @since 2023-1-30
     */
    @Override
    public String toString() {
        return "(" + this.modulus + ", " + (this.argument / Math.PI) + "π" + ")";
    }

    /**
     * @since 2023-1-30
     */
    public boolean isZero() {
        return DoubleUtil.isEqual(this.modulus, 0.0)
                && DoubleUtil.isEqual(this.argument, 0.0);
    }

    /**
     * 此方法禁止调用
     *
     * @since 2023-1-30
     * @deprecated 2023-1-30 复数无法大小比较
     */
    @Override
    @Deprecated
    public int compareTo(PolarComplex other) {
        throw new ForbiddenCallingException("错误：复数无法大小比较");
    }

    /**
     * 转化为直角坐标表示
     *
     * @since 2023-1-31
     */
    public Complex toRect() {
        return Complex.getInstanceByPolar(this.modulus, this.argument);
    }
}
