package org.wangpai.mathlab.advanced.numeric.extend.radix;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wangpai.exception.checked.failed.OverflowException;
import org.wangpai.exception.unchecked.LogicalException;
import org.wangpai.exception.unchecked.dev.DevelopingException;

/**
 * 表示一个按位存储的任意进制的整数，位数可无限长
 *
 * @since 2022-11-17
 */
@ToString
@Accessors(chain = true)
public class Radix implements Cloneable {
    /**
     * 高位在前，低位在后。且不能在高位上包含多余的 0
     *
     * 本数据使用的原码而不是补码格式。对于负数，此处储存的也是其绝对值
     *
     * @since 2022-11-17
     */
    @Getter(AccessLevel.PUBLIC)
    private int[] digits;

    @Getter(AccessLevel.PUBLIC)
    private int radix;

    @Getter(AccessLevel.PUBLIC)
    private boolean isNegative = false;

    /**
     * 标记本对象是不是常量。是常量的对象，不能对其使用自增、自减函数。
     * 由于 Java 的语法限制，无法在定义自增函数的同时，确保 Radix 常量不被破坏，因此只能以此加以逻辑限制
     */
    @Setter(AccessLevel.PRIVATE)
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
     * 注意：不能直接使用 Radix 常量进行赋值！必须使用它的 clone 方法
     */
    public final static Radix ZERO_10 =
            new Radix(new int[]{0}, 10, false).setFinal(true).setName("ZERO_10");
    public final static Radix ONE_10 =
            new Radix(new int[]{1}, 10, false).setFinal(true).setName("ONE_10");
    public final static Radix TWO_10 =
            new Radix(new int[]{2}, 10, false).setFinal(true).setName("TWO_10");

    /**
     * 此方法不能外部调用
     *
     * @since 2022-11-17
     */
    private Radix() {
        super();
    }

    /**
     * @since 2022-11-17
     */
    public Radix(int[] digits, int radix, boolean isNegative) {
        if (radix <= 1) {
            throw new LogicalException("进制数需大于 1");
        }
        for (var digit : digits) {
            if (digit < 0) {
                throw new LogicalException("digits 的每一位都必须为非负数");
            }
            if (digit >= radix) {
                throw new LogicalException("digits 的每一位都必须小于 radix");
            }
        }

        this.digits = removePrefix0(digits);
        this.radix = radix;
        this.isNegative = isNegative;
    }

    /**
     * 本方法不会对形参进行检查，所以只有自已为了效率能确保形参没有问题时才能使用
     *
     * WNC：with no check
     *
     * @since 2022-11-17
     */
    public static Radix generateRadixWnc(int[] digits, int radix, boolean isNegative) {
        var result = new Radix();
        result.digits = digits;
        result.radix = radix;
        result.isNegative = isNegative;
        return result;
    }

    /**
     * 得到 0 对象
     *
     * @since 2022-12-25
     */
    public static Radix generateZero(int radix) {
        var result = new Radix();
        result.digits = new int[]{0};
        result.radix = radix;
        return result;
    }

    /**
     * @since 2022-11-17
     */
    public boolean isZero() {
        for (var digit : this.digits) {
            if (digit != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * @since 2022-12-25
     */
    @Override
    public Radix clone() {
        try {
            Radix cloned = (Radix) super.clone();
            cloned.digits = this.digits.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从数学上的数值的角度来判断相等。如：认为 10 进制下的 15 与 2 进制下的 1111 相等
     *
     * @since 2022-12-24
     */
    public boolean equalsByMathValue(Radix other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (this.isNegative != other.isNegative) {
            return false;
        }
        if (this.radix == other.radix) {
            return this.equalsByData(other);
        } else {
            // TODO：转化为同一进制进行比较
            throw new DevelopingException("此方法还未完成编写，无法使用"); // FIXME：2022年12月24日：此方法待完成
        }

//        return false;
    }

    /**
     * 从数据结构的数据的角度来判断相等。本方法仅仅忽略 isFinal 字段，其它数据只要有一个不相等，即认为不等
     *
     * @since 2022-12-24
     */
    public boolean equalsByData(Radix other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        return this.radix == other.radix
                && this.isNegative == other.isNegative
                && Arrays.equals(this.digits, other.digits);
    }

    /**
     * 此方法会导致歧义，因此禁止调用。因为相等是从数据结构的角度来判断呢，还是从数值的角度来判断呢？
     *
     * @deprecated 2022-12-24
     * @since 2022-12-24
     */
    @Override
    @Deprecated
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other instanceof Radix otherRadix) {
            if (this.equalsByData(otherRadix)) {
                return true; // 数据一致一定相等
            } else if (this.equalsByMathValue(otherRadix)) {
                throw new LogicalException("错误：无法比较"); // 仅在数学上相等有歧义
            } else {
                return false; // 从数据和数学上都不相等，结果一定不相等
            }
        }

        return false;
    }

    /**
     * 如果 Radix 对象与 long 的最大、小值过于接近，可能会导致转换失败
     *
     * @since 2022-12-24
     */
    public long toLong() throws OverflowException {
        /**
         * 如果能转化成功，需要满足近似公式 63 * log(r, 2) - log(r, (a + 1)) > n - 1。
         * 其中，r 为 radix，a 为最高位的值，n 为此数的位数
         */
        var comp = (63 * Math.log(2) - Math.log(this.digits[0] + 1)) / Math.log(this.radix);
        if (comp < this.digits.length - 1) {
            throw new OverflowException("本 Radix 对象可能会超出 long 类型的范围，转换中止");
        }

        long result = 0L;
        for (int digit : this.digits) {
            result = result * this.radix + digit;
        }
        return this.isNegative ? -result : result;
    }

    /**
     * 自增 1
     *
     * 因为这个方法需要改变自身，所以不将其独立到工厂方法中
     *
     * @since 2022-12-24
     */
    public Radix increaseOne() throws LogicalException {
        if (this.isFinal) {
            throw new LogicalException("错误：不能对常量【" + this.name + "】进行自增");
        }

        if (this.isNegative) {
            this.decrease1Abs(); // 如果是负数，自增相当于绝对值减小
        } else {
            this.increase1Abs();
        }

        return this;
    }

    /**
     * 自减 1
     *
     * 因为这个方法需要改变自身，所以不将其独立到工厂方法中
     *
     * @since 2022-12-24
     */
    public Radix decreaseOne() throws LogicalException {
        if (this.isFinal) {
            throw new LogicalException("错误：不能对常量【" + this.name + "】进行自增");
        }

        if (this.isNegative) {
            this.increase1Abs(); // 如果是负数，自增相当于绝对值增加
        } else if (this.isZero()) {
            this.increase1Abs(); // 如果是 0，自增相当于绝对值增加，且变号
            this.isNegative = true;
        } else {
            this.decrease1Abs();
        }

        return this;
    }

    /**
     * 将 radix 的绝对值加 1
     *
     * 因为这个方法需要改变自身，所以不将其独立到工厂方法中
     *
     * @since 2022-12-24
     */
    public void increase1Abs() {
        if (this.isFinal) {
            throw new LogicalException("错误：不能对常量【" + this.name + "】进行自增");
        }

        int index = this.digits.length - 1;
        while (index >= 0) {
            if (this.digits[index] + 1 == this.radix) {
                --index;
            } else {
                ++this.digits[index];
                // 如果是高位发生了递增，则所有低位都需要清 0
                for (int digit = index + 1; digit < this.digits.length; ++digit) {
                    this.digits[digit] = 0;
                }
                break;
            }
        }
        if (index < 0) { // 说明递增之前的数刚好是同一位数下的最大数，所以需要增加 1 位来进位
            var result = new int[this.digits.length + 1];
            result[0] = 1;
            this.digits = result;
        }
    }

    /**
     * 将 radix 的绝对值减 1
     *
     * 因为这个方法需要改变自身，所以不将其独立到工厂方法中
     *
     * @since 2022-12-24
     */
    public void decrease1Abs() {
        if (this.isFinal) {
            throw new LogicalException("错误：不能对常量【" + this.name + "】进行自减");
        }
        if (this.isZero()) {
            throw new LogicalException("错误：绝对值已经是 0，无法再自减");
        }

        int index = this.digits.length - 1;
        while (index >= 0) {
            if (this.digits[index] == 0) {
                --index;
            } else {
                --this.digits[index];
                var maxDigit = this.radix - 1;
                // 如果是高位发生了递减，则所有低位都需要变成最大数
                for (int digit = index + 1; digit < this.digits.length; ++digit) {
                    this.digits[digit] = maxDigit;
                }
                break;
            }
        }
        if (this.digits[0] == 0) { // 说明最高位出现了 0，这有可能是多余的 0，有可能不是
            if (this.digits.length == 1) {
                this.isNegative = false; // 这说明 Radix 对象已经变成了 0，所以一定是非负数
            } else { // 如果 Radix 不是 0，而只是高位变成了 0，那就删去这个高位的 0
                var result = new int[this.digits.length - 1];
                System.arraycopy(this.digits, 1, result, 0, result.length);
                this.digits = result;
            }
        }
    }

    /**
     * 去掉 digits 中前面没用的 0
     *
     * @since 2022-12-24
     */
    private static int[] removePrefix0(int[] digits) {
        int pointer = 0; // 指向一个不为 0 的位，也即 digits 中前缀 0 的个数
        while (pointer < digits.length && digits[pointer] == 0) {
            ++pointer;
        }
        if (pointer == 0) { // 说明从高往低，第一位都不是 0
            return digits;
        }
        if (pointer == digits.length) { // 说明 digits 中全是 0
            return new int[]{0};
        }

        var result = new int[digits.length - pointer];
        System.arraycopy(digits, pointer, result, 0, result.length);
        return result;
    }
}
