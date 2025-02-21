package org.wangpai.mathlab.advanced.numeric.basic.operand;

import java.math.BigInteger;
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
import org.wangpai.mathlab.advanced.android.BigIntegerAdapter;
import org.wangpai.mathlab.advanced.numeric.basic.operation.FigureOperation;

/**
 * 整数
 *
 * 因为除法对整数有余数，因此此类不支持除法运算
 *
 * @since 2021-7-22
 */
@AllArgsConstructor // 此构造器只用于 JSON 反序列化时自动调用，禁止手动使用
@Accessors(chain = true)
public class Figure implements Operand, Comparable<Figure> {
    private static final int MAX_SMALL = Integer.MAX_VALUE;
    private static final int MIN_SMALL = Integer.MIN_VALUE;

    private boolean isSmall = true; // 标记本对象是小整数还是大整数。为了提高效率，没有必要全都用大整数
    private int small;
    private BigInteger big;

    /**
     * 标记本对象是不是常量。是常量的对象，不能对其使用自增、自减函数。
     * 由于 Java 的语法限制，无法在定义自增函数的同时，确保 Figure 常量不被破坏，因此只能以此加以逻辑限制
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
     * 注意：不能直接使用 Figure 常量进行赋值！必须使用它的 clone 方法
     */
    public final static Figure ZERO = new Figure(0).setFinal(true).setName("ZERO");
    public final static Figure ONE = new Figure(1).setFinal(true).setName("ONE");
    public final static Figure TWO = new Figure(2).setFinal(true).setName("TWO");
    public final static Figure INTEGER_MAX_VALUE =
            new Figure(Integer.MAX_VALUE).setFinal(true).setName("INTEGER_MAX_VALUE");
    public final static Figure INTEGER_MIN_VALUE =
            new Figure(Integer.MIN_VALUE).setFinal(true).setName("INTEGER_MIN_VALUE");
    public final static Figure LONG_MAX_VALUE =
            new Figure(Long.MAX_VALUE).setFinal(true).setName("LONG_MAX_VALUE");
    public final static Figure LONG_MIN_VALUE =
            new Figure(Long.MIN_VALUE).setFinal(true).setName("LONG_MIN_VALUE");

    protected Figure() {
        super();
    }

    /**
     * 此方法使用的是深克隆
     *
     * @since 2021-8-3
     * @lastModified 2022-9-9
     */
    public Figure(Figure other) {
        super();
        this.isFinal = false; // 凡是手动创建的都不算做常量
        this.isSmall = other.isSmall;
        if (other.isSmall) {
            this.small = other.small;
        } else {
            this.big = Figure.cloneBigInteger(other.big);
        }
    }

    /**
     * 此方法使用的是深克隆
     *
     * @since 2021-8-3
     * @lastModified 2022-9-9
     */
    public Figure(BigInteger num) {
        super();
        this.isFinal = false; // 凡是手动创建的都不算做常量
        long longResult = 0;
        try {
//            longResult = num.longValueExact(); // 此方法在安卓 12 才开始支持
            longResult = BigIntegerAdapter.longValueExactBelowAndroid12(num); // 此方法此安卓 12 以下的版本支持
        } catch (ArithmeticException exception) {
            // 如果抛出异常说明是大整数
            this.big = Figure.cloneBigInteger(num);
            this.isSmall = false;
            return;
        }
        this.valueOf(longResult);
    }

    /**
     * @since 2022-11-18
     */
    public Figure(int num) {
        super();
        this.valueOf(num);
    }

    /**
     * @since 2022-9-9
     */
    public Figure(long num) {
        super();
        this.valueOf(num);
    }

    /**
     * 注意：此方法不会涉及对字段 isFinal 的初始化
     *
     * @since 2022-11-18
     */
    private void valueOf(int num) {
        this.small = num;
        this.isSmall = true;
    }

    /**
     * 注意：此方法不会涉及对字段 isFinal 的初始化
     *
     * @since 2022-9-9
     */
    private void valueOf(long num) {
        if (couldSmall(num) == 0) {
            this.small = (int) num;
            this.isSmall = true;
        } else {
            this.big = BigInteger.valueOf(num);
            this.isSmall = false;
        }
    }

    /**
     * 对自身的数据格式进行化简，以节约空间
     *
     * 建议在使用本类的 absorbBigInteger 方法之后，使用本方法来整理本类的数据空间
     *
     * @since 2024-8-30
     */
    public Figure simplify() {
        // 是常量或者已经是小整数了，就没必要化简
        if (this.isFinal || this.isSmall) {
            return this;
        }

        long longResult = 0;
        try {
//            longResult = num.longValueExact(); // 此方法在安卓 12 才开始支持
            longResult = BigIntegerAdapter.longValueExactBelowAndroid12(this.big); // 此方法此安卓 12 以下的版本支持
        } catch (ArithmeticException exception) {
            // 如果抛出异常说明是大整数，化简失败
            return this;
        }
        // 再进一步化简
        this.valueOf(longResult);
        return this;
    }

    /**
     * 判断 long 类型是否可以转换为 int。如果可以，返回 0；如果正数溢出，返回 1；如果负数溢出，返回 -1
     *
     * @since 2022-9-9
     */
    private static int couldSmall(long num) {
        if (num > MAX_SMALL) {
            return 1;
        } else if (num < MIN_SMALL) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 此方法只会返回数据副本
     *
     * @since 2022-9-9
     */
    public BigInteger toBigInteger() {
        if (this.isSmall) {
            return BigInteger.valueOf(this.small);
        } else {
            return cloneBigInteger(this.big);
        }
    }

    /**
     * @since 2022-9-9
     */
    private static BigInteger cloneBigInteger(BigInteger bigInteger) {
        return new BigInteger(bigInteger.toString());
    }

    /**
     * @since 2022-9-9
     */
    @Override
    public Figure clone() {
        if (this.isSmall) {
            return new Figure(this.small);
        } else {
            return new Figure(this.big);
        }
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
            if (other instanceof Figure) {
                return this.equals((Figure) other);
            }
        }

        return false;
    }

    /**
     * @since 2022-12-11 v1
     *        2021-8-5 v0
     */
    public boolean equals(Figure other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        try {
            if (this.isSmall && other.isSmall) {
                return this.small == other.small;
            } else if ((!this.isSmall) && (!other.isSmall)) {
                return this.big.equals(other.big);
            }

            return false;
        } catch (Exception exception) {
            return false;  // 只要此处抛出了异常，均视为相等判断失败
        }
    }

    /**
     * @since 2023-1-29
     */
    @Override
    public int hashCode() {
        if (this.isSmall) {
            return Objects.hashCode(this.small);
        } else {
            return Objects.hashCode(this.big);
        }
    }

    /**
     * @since 2021-8-1
     * @lastModified 2022-9-9
     */
    @Override
    public String toString() {
        if (this.isSmall) {
            return String.valueOf(this.small);
        } else {
            return this.big.toString();
        }
    }

    /**
     * 将本类对象序列化时，转化为 String 存储。只有在需要序列化时，才能调用本方法。在进行这种转化时，一律将本对象视为变量，而不是常量
     *
     * @since 2024-8-30
     */
    public String toSerializableString() {
        if (this.isSmall) {
            return String.valueOf(this.small);
        } else {
            return this.big.toString(10); // 这会转化为 10 进制下的数字，且会显示出负号（如果有的话）
        }
    }

    /**
     * 将 String 存储的本对象数据转化为原数据。只有在需要反序列化时，才能调用本方法。在进行这种转化时，一律将本对象视为变量，而不是常量
     *
     * @since 2024-8-30
     */
    public static Figure serializableString2Figure(String serializableFigure) {
        return Figure.absorbBigInteger(new BigInteger(serializableFigure, 10)).simplify();
    }

    /**
     * 返回 Figure 用十进制数表示时，该十进制数的位数。此方法不计正负号，使用绝对值作判断
     *
     * @since 2023-1-24
     */
    public int calculateDigitNum() {
        return FigureOperation.getAbsolute(this).toString().length();
    }

    /**
     * 判断是否可以转换为 long 类型
     *
     * @since 2022-9-9
     */
    public boolean tryToLong() {
        if (this.isSmall) {
            return true;
        }

        try {
            this.big.longValueExact();
        } catch (ArithmeticException exception) {
            return false;
        }
        return true; // 如果不抛出异常就认为可以转换为 long 类型
    }

    /**
     * 将 Figure 转化成 long
     *
     * @since 2022-8-29
     * @lastModified 2022-9-9
     */
    public long toLong() throws OverflowException {
        if (this.isSmall) {
            return this.small;
        }

        long result;
        try {
            result = this.big.longValueExact();
        } catch (ArithmeticException exception) {
            throw new OverflowException("本 Figure 对象超出 long 类型的范围，转换失败");
        }
        return result;
    }

    /**
     * 将 Figure 转化成 int
     *
     * @since 2022-9-9
     */
    public int toInt() throws OverflowException {
        if (!this.isSmall) {
            throw new OverflowException("本 Figure 对象超出 int 类型的范围，转换失败");
        }

        return this.small;
    }

    @Override
    public boolean isZero() {
        if (this.isSmall) {
            return this.small == 0;
        } else {
            return this.big.equals(BigInteger.ZERO);
        }
    }

    @Override
    public boolean isPositive() {
        if (this.isSmall) {
            return this.small > 0;
        } else {
            /**
             * 对于 BigInteger 的函数 signum 的返回值：
             *   > 1：代表正数
             *   > 0：代表 0
             *   > -1：代表 负数
             */
            return this.big.signum() == 1;
        }
    }

    @Override
    public boolean isNegative() {
        return Operand.super.isNegative();
    }

    /**
     * @since 2022-12-23
     */
    @Override
    public int sign() {
        if (this.isSmall) {
            return Integer.compare(this.small, 0);
        } else {
            /**
             * 对于 BigInteger 的函数 signum 的返回值：
             *   > 1：代表正数
             *   > 0：代表 0
             *   > -1：代表 负数
             */
            return this.big.signum();
        }
    }

    /**
     * 自增 1
     *
     * 因为这个方法需要改变自身，所以不将其独立到工厂方法中
     *
     * @since 2022-8-24
     */
    public Figure increaseOne() throws LogicalException {
        if (this.isFinal) {
            throw new LogicalException("错误：不能对常量【" + this.name + "】进行自增");
        }

        if (this.isSmall) { // 如果现在是小整数
            if (this.small == MAX_SMALL) {
                this.isSmall = false;
                this.big = BigInteger.valueOf(MAX_SMALL).add(BigInteger.ONE);
            } else {
                ++this.small;
            }
        } else { // 如果现在是大整数
            long longResult;
            try {
                longResult = this.toLong();
            } catch (OverflowException exception) {
                /**
                 * 抛出异常说明现在已经超出 long 的范围，
                 * 此时加 1 不管怎样都是不可能缩小到 int 的范围的
                 */
                this.big = this.big.add(BigInteger.ONE);
                return this;
            }
            if (longResult == MIN_SMALL - (long) 1) { // 如果此时不把 1 强制转换为 long，会发生无声溢出
                this.small = MIN_SMALL;
                this.isSmall = true;
            } else {
                this.big = this.big.add(BigInteger.ONE);
            }
        }
        return this;
    }

    /**
     * 自减 1
     *
     * 因为这个方法需要改变自身，所以不将其独立到工厂方法中
     *
     * @since 2022-8-24
     */
    public Figure decreaseOne() throws LogicalException {
        if (this.isFinal) {
            throw new LogicalException("错误：不能对常量【" + this.name + "】进行自减");
        }

        if (this.isSmall) { // 如果现在是小整数
            if (this.small == MIN_SMALL) {
                this.isSmall = false;
                this.big = BigInteger.valueOf(MIN_SMALL).subtract(BigInteger.ONE);
            } else {
                --this.small;
            }
        } else { // 如果现在是大整数
            long longResult;
            try {
                longResult = this.toLong();
            } catch (OverflowException exception) {
                /**
                 * 抛出异常说明现在已经超出 long 的范围，
                 * 此时加 1 不管怎样都是不可能缩小到 int 的范围的
                 */
                this.big = this.big.subtract(BigInteger.ONE);
                return this;
            }
            if (longResult == MAX_SMALL + (long) 1) { // 如果此时不把 1 强制转换为 long，会发生无声溢出
                this.small = MAX_SMALL;
                this.isSmall = true;
            } else {
                this.big = this.big.subtract(BigInteger.ONE);
            }
        }
        return this;
    }

    /**
     * @since 2023-1-27
     */
    @Override
    public int compareTo(Figure other) {
        var diff = FigureOperation.subtract(this, other);
        try {
            return diff.toInt();
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
     * 判断底层数据结构是否为 int。如果是，返回 true
     *
     * 此方法会在不加判断的时返回原始数据，请慎重使用。
     * 只有对效率要求很高时才能调用本方法！此方法只供本项目的其它包使用，使用本项目的其它类禁止使用！
     *
     * @since 2024-8-28
     */
    public boolean underlyingIsInt() {
        return this.isSmall;
    }

    /**
     * 此方法会在不加判断的时返回原始数据，请慎重使用。
     * 只有对效率要求很高时才能调用本方法！此方法只供本项目的其它包使用，使用本项目的其它类禁止使用！
     *
     * @since 2024-8-28
     */
    public int getOriginSmallUnsafely() {
        return this.small;
    }

    /**
     * 此方法会在不加判断的时返回原始数据，请慎重使用。
     * 只有对效率要求很高时才能调用本方法！此方法只供本项目的其它包使用，使用本项目的其它类禁止使用！
     *
     * @since 2024-8-28
     */
    public BigInteger getOriginBigUnsafely() {
        return this.big;
    }

    /**
     * 直接用形参的数据来创建 Figure 对象，请确保以后不会将形参用于其它用途！
     *
     * 此方法会在不加判断的时返回原始数据，请慎重使用。
     * 只有对效率要求很高时才能调用本方法！此方法只供本项目的其它包使用，使用本项目的其它类禁止使用！
     *
     * 建议在使用本类之后，再调用本类的 simplify() 方法来整理本类的数据空间
     *
     * @since 2024-8-28
     */
    public static Figure absorbBigInteger(BigInteger bigInteger) {
        var result = new Figure();
        result.isFinal = false; // 凡是手动创建的都不算做常量
        result.big = bigInteger;
        result.isSmall = false;
        return result;
    }

    /*-------------------- 以上区域的方法只供本项目的其它包使用，使用本项目的其它类禁止使用 ---------------------------------*/
}
