package org.wangpai.mathlab.advanced.numeric.extend.radix;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.wangpai.commonutil.tc.collection.BasicArrayTc;
import org.wangpai.exception.checked.failed.OverflowException;
import org.wangpai.mathlab.builtin.matrix.BooleanMatrixUtil;

/**
 * 表示一个二进制整数
 *
 * @since 2022-12-3
 */
@Accessors(chain = true)
public class Binary {
    /**
     * 高位在前，低位在后。且不能在高位上包含多余的 0
     *
     * 本数据使用的原码而不是补码格式。对于负数，此处储存的也是其绝对值
     *
     * @since 2022-12-3
     */
    private boolean[] digits; // 高位在前，低位在后。且不能在高位上包含多余的 0

    @Getter(AccessLevel.PUBLIC)
    private boolean isNegative = false;

    /**
     * 标记本对象是不是常量。是常量的对象，不能对其使用自增、自减函数。
     * 由于 Java 的语法限制，无法在定义自增函数的同时，确保 Binary 常量不被破坏，因此只能以此加以逻辑限制
     */
    @Setter(AccessLevel.PRIVATE)
    private boolean isFinal = false;

    /**
     * 此方法不能外部调用
     *
     * @since 2022-12-3
     */
    private Binary() {
        super();
    }

    /**
     * 高位在前，低位在后
     *
     * @since 2022-12-24
     */
    public Binary(boolean[] digits, boolean isNegative) {
        this.digits = removePrefix0(digits);
        this.isNegative = isNegative;
    }

    /**
     * 高位在前，低位在后
     *
     * @since 2022-12-3
     */
    public Binary(boolean[] digits) {
        this.digits = removePrefix0(digits);
    }

    /**
     * 高位在前，低位在后
     *
     * @since 2022-12-24
     */
    public Binary(int[] digits, boolean isNegative) {
        this.digits = removePrefix0(BasicArrayTc.intArray2booleanArray(digits));
        this.isNegative = isNegative;
    }

    /**
     * 高位在前，低位在后
     *
     * @since 2022-12-3
     */
    public Binary(int[] digits) {
        this.digits = removePrefix0(BasicArrayTc.intArray2booleanArray(digits));
    }

    /**
     * @since 2022-12-3
     */
    public long toLong() throws OverflowException {
        if (this.digits.length > 63) {
            throw new OverflowException("本 Binary 对象超出 long 类型的范围，转换失败");
        }
        long result = 0L;
        for (int index = 0; index < this.digits.length; ++index) {
            long digit = this.digits[this.digits.length - 1 - index] ? 1L : 0L;
            result += digit << index; // long 类型的右移
        }
        return this.isNegative ? -result : result;
    }

    /**
     * @since 2022-12-3
     */
    public boolean isZero() {
        for (var digit : this.digits) {
            if (digit) {
                return false;
            }
        }
        return true;
    }

    /**
     * @since 2022-12-3
     */
    @Override
    public String toString() {
        var digitString = BooleanMatrixUtil.matrixToString(this.digits);
        return "Binary(digits=" + digitString + ", "
                + "isNegative=" + this.isNegative() + ", "
                + "isFinal=" + this.isFinal + ")";
    }

    /**
     * 去掉 digits 中前面没用的 0
     *
     * @since 2022-12-24
     */
    private static boolean[] removePrefix0(boolean[] digits) {
        int pointer = 0; // 指向一个不为 0 的位，也即 digits 中前缀 0 的个数
        while (pointer < digits.length && !digits[pointer]) {
            ++pointer;
        }
        if (pointer == 0) { // 说明从高往低，第一位都不是 0
            return digits;
        }
        if (pointer == digits.length) { // 说明 digits 中全是 0
            return new boolean[]{false};
        }

        var result = new boolean[digits.length - pointer];
        System.arraycopy(digits, pointer, result, 0, result.length);
        return result;
    }
}
