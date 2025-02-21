package org.wangpai.mathlab.advanced.numeric.basic.operand;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.wangpai.exception.checked.failed.OverflowException;
import org.wangpai.exception.unchecked.LogicalException;
import org.wangpai.exception.unchecked.UnexpectedException;
import org.wangpai.logfx.Logfx;
import org.wangpai.mathlab.advanced.numeric.basic.algorithm.DividedBetweenBigIntegers;
import org.wangpai.mathlab.advanced.numeric.basic.operation.FigureOperation;
import org.wangpai.mathlab.advanced.numeric.basic.operation.RationalOperation;
import org.wangpai.mathlab.builtin.show.DoubleShowUtil;

/**
 * Rational Number：有理数
 *
 * @since 2021-8-1
 */
@AllArgsConstructor // 此构造器只用于 JSON 反序列化时自动调用，禁止手动使用
@Accessors(chain = true)
public class Rational implements Operand, Comparable<Rational> {
    private Figure numerator; // 分子

    private Figure denominator; // 分母

    /**
     * 标记本对象是不是常量。是常量的对象，不能对其使用自增、自减函数。
     * 由于 Java 的语法限制，无法在定义自增函数的同时，确保 Rational 常量不被破坏，因此只能以此加以逻辑限制
     *
     * @since before 2022-8-24
     */
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PUBLIC)
    private boolean isFinal = false;

    /**
     * 名称，原本为常量设计。注意：即便是用反射，也无法获得变量自己的名称
     *
     * @since 2024-9-5
     */
    @Setter
    @Getter
    private String name = null;

    /**
     * 注意：不能直接使用 Rational 常量进行赋值！必须使用它的 clone 方法
     *
     * @since before 2022-8-24
     */
    public final static Rational ZERO = new Rational(0).setFinal(true).setName("ZERO");
    public final static Rational ONE = new Rational(1).setFinal(true).setName("ONE");

    /**
     * @since 2021-8-1
     */
    protected Rational() {
        super();
    }

    /**
     * 此构造函数会进行且必须约分操作。此构造函数使用的是深克隆
     *
     * @since 2021-8-1
     */
    public Rational(Rational other) {
        super();
        this.numerator = other.numerator.clone();
        this.denominator = other.denominator.clone();
        // 相信传入的 other 已经进行了约分，所以此处不需要约分
    }

    /**
     * 此构造函数会进行且必须约分操作。此构造函数使用的是深克隆
     *
     * @param numerator   分子
     * @param denominator 分母
     * @since 2021-8-1
     */
    public Rational(Figure numerator, Figure denominator) {
        super();
        if (denominator.isZero()) {
            throw new LogicalException("错误：0 不能作分母");
        } else if (numerator.isZero()) {
            // 分子为 0，分母不管是正是负，都设置为 1
            this.numerator = Figure.ZERO.clone();
            this.denominator = Figure.ONE.clone();
        } else {
            this.numerator = new Figure(numerator);
            this.denominator = new Figure(denominator);
            this.reduceFraction();
        }
    }

    /**
     * 此构造函数会进行且必须约分操作。此构造函数使用的是深克隆
     *
     * @since 2021-8-1
     */
    public Rational(long numerator, long denominator) {
        super();
        if (denominator == 0) {
            throw new LogicalException("错误：0 不能作分母");
        } else if (numerator == 0) {
            // 分子为 0，分母不管是正是负，都设置为 1
            this.numerator = Figure.ZERO.clone();
            this.denominator = Figure.ONE.clone();
        } else {
            this.numerator = new Figure(numerator);
            this.denominator = new Figure(denominator);
            this.reduceFraction();
        }
    }

    /**
     * 此构造函数使用的是深克隆
     *
     * @since 2021-8-1
     */
    public Rational(Figure numerator) {
        super();
        this.numerator = numerator.clone();
        this.denominator = Figure.ONE.clone();
        // 此处不需要约分
    }

    /**
     * @since 2021-8-1
     */
    public Rational(long numerator) {
        this.numerator = new Figure(numerator);
        this.denominator = Figure.ONE.clone();
        // 此处不需要约分
    }

    /**
     * @since 2021-8-1
     */
    @Override
    public boolean isZero() {
        return this.numerator.isZero();
    }

    /**
     * @since 2021-8-1
     */
    @Override
    public boolean isPositive() {
        return this.reduceFraction().numerator.isPositive();
    }

    /**
     * @since 2021-8-1
     */
    @Override
    public boolean isNegative() {
        return Operand.super.isNegative();
    }

    /**
     * 此方法只会克隆分子和分母的数据。常量的克隆会变成变量
     *
     * @since 2021-8-1
     */
    @Override
    public Rational clone() {
        var cloned = new Rational();
        cloned.numerator = this.numerator.clone();
        cloned.denominator = this.denominator.clone();
        return cloned;
    }

    /**
     * 因为这个方法是重写方法，所以这个方法不能抛出异常
     *
     * 注意：other 不可能为基本类型
     *
     * @since 2021-8-5
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other instanceof Operand) {
            if (other instanceof Rational) {
                return this.equals((Rational) other);
            }
            if (other instanceof Figure) {
                return this.equals(new Rational((Figure) other));
            }
        }

        return false;
    }

    /**
     * 为了保证效率，此方法假设 this 与 other 均已进行了约分
     *
     * @since 2022-12-11 v1
     *        2021-8-5 v0
     */
    public boolean equals(Rational other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        try {
            return this.numerator.equals(other.numerator) && this.denominator.equals(other.denominator);
        } catch (Exception exception) {
            return false;  // 只要此处抛出了异常，均视为相等判断失败
        }
    }

    /**
     * @since 2023-1-29
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.numerator, this.denominator);
    }

    /**
     * @since 2021-10-12
     */
    public String toString(boolean needShowBrackets) {
        if (needShowBrackets) {
            /**
             * 规定外加括号的样式
             */
            final var LEFT_BRACKET = "[";
            final var RIGHT_BRACKET = "]";

            // 如果此有理数为整数，不输出分母
            if (this.denominator.equals(new Figure(1))) {
                // 如果此整数为负数，外加括号
                if (this.numerator.isNegative()) {
                    return LEFT_BRACKET +
                            this.numerator +
                            RIGHT_BRACKET;
                }
                // 如果此整数为正数，直接转化，不外加括号
                return this.numerator.toString();
            } else {
                return LEFT_BRACKET +
                        this.numerator + "/" + this.denominator +
                        RIGHT_BRACKET;
            }
        } else {
            // 如果此有理数为整数，不输出分母
            if (this.denominator.equals(new Figure(1))) {
                return this.numerator.toString();
            } else {
                return this.numerator + "/" + this.denominator;
            }
        }
    }

    /**
     * @since 2021-8-1
     * @lastModified 2021-10-12
     */
    @Override
    public String toString() {
        return this.toString(true);
    }

    /**
     * @since 2021-8-1
     * @lastModified 2022-9-1
     */
    public double toDouble() {
        if (FigureOperation.greaterThan(this.numerator, Figure.INTEGER_MAX_VALUE)
                || FigureOperation.greaterThan(this.denominator, Figure.INTEGER_MAX_VALUE)) {
            return this.toDoubleForBig();
        } else {
            return this.toDoubleForSmall();
        }
    }

    /**
     * 此方法不能用于分子或分母超出 int 类型的分数，但此方法的转换结果可能更精确
     *
     * @since 2021-8-1
     */
    private double toDoubleForSmall() {
        // 将分子、分母中较大的那个数转换为类型 double 来运算
        int numerator = this.numerator.getOriginSmallUnsafely();
        int denominator = this.denominator.getOriginSmallUnsafely();
        return (double) numerator / denominator;
    }

    /**
     * 此方法可以用于分子或分母很大的情形，但此方法的转换结果可能会导致精确度下降
     *
     * @since 2022-8-25
     */
    public double toDoubleForBig() {
        boolean isNegative = false;
        if (this.isNegative()) {
            isNegative = true;
        }
        Figure absNumerator = FigureOperation.getAbsolute(this.numerator);
        Figure absDenominator = FigureOperation.getAbsolute(this.denominator);
        // 这里使用了笔者自研的“大整数相除防溢出递归算法”
        double absResult = DividedBetweenBigIntegers.dividedBetweenBigIntegers(absNumerator, 0, absDenominator, 0);
        return isNegative ? -absResult : absResult;
    }

    /**
     * 将有理数转化为 double 字符串。参数 format 用于控制显示的样式，这与 String.format(...) 中的规则相同
     *
     * @since 2022-8-24
     * @lastModified 2022-9-24
     */
    public String toDoubleString(String format) {
        return DoubleShowUtil.toDoubleString(this.toDouble(), format);
    }

    /**
     * 将有理数转化为 double 字符串，并向结果添加逗号分隔符。参数 format 用于控制显示的样式，这与 String.format(...) 中的规则相同
     *
     * @param commaInterval 逗号之间的间隔
     * @since 2022-9-24
     */
    public String toDoubleString(String format, int commaInterval) {
        return DoubleShowUtil.toDoubleString(this.toDouble(), format, commaInterval);
    }

    /**
     * 将有理数转化为百分率格式的字符串，转化时会自动四舍五入。如 1.25%
     *
     * @param afterPointNum 在转化为百分数后，小数点之后的位数
     * @since 2024-8-30
     */
    public String toPercentageString(int afterPointNum) {
        if (afterPointNum < 0) {
            throw new LogicalException("错误：afterPointNum 不能为负数。提供的 afterPointNum=" + afterPointNum);
        }
        String format = "%." + afterPointNum + "f";
        return DoubleShowUtil.toDoubleString(RationalOperation.multiply(this, 100).toDouble(), format) + "%";
    }

    /**
     * 将本类对象序列化时，转化为 String 存储。只有在需要序列化时，才能调用本方法。在进行这种转化时，一律将本对象视为变量，而不是常量。
     * 转化格式为：分子/分母
     *
     * @since 2024-8-30
     */
    public String toSerializableString() {
        return this.numerator.toSerializableString() + "/" + this.denominator.toSerializableString();
    }

    /**
     * 将 String 存储的本对象数据转化为原数据。只有在需要反序列化时，才能调用本方法。在进行这种转化时，一律将本对象视为变量，而不是常量
     *
     * @since 2024-8-30
     */
    public static Rational serializableString2Rational(String serializableFigure) {
        String[] result = serializableFigure.split("/");
        String numeratorString = result[0];
        String denominatorString = result[1];
        Figure numerator = Figure.serializableString2Figure(numeratorString);
        Figure denominator = Figure.serializableString2Figure(denominatorString);
        // 相信在存储前已经进行了约分，所以此处不需要再进行约分了
        return Rational.absorbFigure(numerator, denominator);
    }

    /**
     * 是否是真分数。注意：真分数判断是根据绝对值来判断的，不是说小于 1 就一定为真分数
     *
     * 算法：如果分子的绝对值比分母的小，说明是真分数，反之不是。规定：0 是真分数，1 不是真分数
     *
     * @since 2021-8-5
     */
    public boolean isProperFraction() {
        this.reduceFraction(); // 约分后，分母不可能为负
        if (this.isZero()) {
            return true;
        } else if (this.denominator.equals(new Figure(1))) {
            return false;
        }

        if (FigureOperation.subtract(
                        FigureOperation.getAbsolute(this.numerator), this.denominator)
                .isNegative()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 约分
     *
     * 约分后，分母恒为正数
     *
     * 算法：
     * 1. 求分子、分母的最大公约数
     * 2. 将分子、分母分别除以最大公约数
     * 3. 如果结果分母为负数，将分子、分母同时取反
     *
     * @since before 2021-8-5
     */
    public Rational reduceFraction() {
        Figure commonDivisor = FigureOperation.findGcd(this.numerator, this.denominator);
        this.numerator = FigureOperation.modsQuotient(this.numerator, commonDivisor);
        this.denominator = FigureOperation.modsQuotient(this.denominator, commonDivisor);

        if (this.denominator.isNegative()) {
            this.denominator = FigureOperation.getOpposite(this.denominator);
            this.numerator = FigureOperation.getOpposite(this.numerator);
        }

        return this;
    }

    /**
     * @since 2023-1-30
     */
    @Override
    public int compareTo(Rational other) {
        var diff = RationalOperation.subtract(this, other);
        try {
            return diff.numerator.toInt(); // 为了提高效率，这里直接使用分子作为返回值，而不使用将 diff 取整的办法
        } catch (OverflowException e) {
            if (diff.isPositive()) {
                return Integer.MAX_VALUE;
            } else if (diff.isNegative()) {
                return Integer.MIN_VALUE;
            } else { // 此分支代表 diff 为 0，这应该是不会发生的
                var exceptionMsg = "diff 超出 int 类型的范围但其实际值等于 0";
                Logfx.error(String.format("错误：发生了意料之外的异常：%s", exceptionMsg));
                throw new UnexpectedException(exceptionMsg);
            }
        }
    }

    /********************* 此区域的方法只供本项目的其它包使用，使用本项目的其它类禁止使用 **********************************/

    /**
     * 此方法会在不加判断的时返回原始数据，请慎重使用。
     * 只有对效率要求很高时才能调用本方法！此方法只供本项目的其它包使用，使用本项目的其它类禁止使用！
     *
     * @since 2024-8-30
     */
    public Figure getNumeratorUnsafely() {
        return this.numerator;
    }

    /**
     * 此方法会在不加判断的时返回原始数据，请慎重使用。
     * 只有对效率要求很高时才能调用本方法！此方法只供本项目的其它包使用，使用本项目的其它类禁止使用！
     *
     * @since 2024-8-30
     */
    public Figure getDenominatorUnsafely() {
        return this.denominator;
    }

    /**
     * 直接用形参的数据来创建 Rational 对象，请确保以后不会将形参用于其它用途！
     *
     * 此方法会在不加判断的时返回原始数据，请慎重使用。
     * 只有对效率要求很高时才能调用本方法！此方法只供本项目的其它包使用，使用本项目的其它类禁止使用！
     *
     * 本方法不会进行约分，请使用时依照具体情况进行选择
     *
     * @since 2024-8-30
     */
    public static Rational absorbFigure(Figure numerator, Figure denominator) {
        var result = new Rational();
        result.isFinal = false; // 凡是手动创建的都不算做常量
        result.numerator = numerator;
        result.denominator = denominator;
        return result;
    }

    /*-------------------- 以上区域的方法只供本项目的其它包使用，使用本项目的其它类禁止使用 ---------------------------------*/
}
