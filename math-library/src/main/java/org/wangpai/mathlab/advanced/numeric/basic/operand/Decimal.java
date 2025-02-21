package org.wangpai.mathlab.advanced.numeric.basic.operand;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;
import lombok.experimental.Accessors;
import org.wangpai.commonutil.tc.builtin.BuiltInTc;
import org.wangpai.exception.unchecked.LogicalException;
import org.wangpai.exception.unchecked.UndefinedException;
import org.wangpai.mathlab.advanced.numeric.basic.enumeration.Symbol;
import org.wangpai.mathlab.advanced.numeric.basic.operation.FigureOperation;
import org.wangpai.mathlab.advanced.numeric.basic.operation.RationalOperation;
import org.wangpai.mathlab.exception.unchecked.SyntaxUncheckedException;

/**
 * 小数。可以允许负数
 *
 * @since 2021-8-2
 */
@Accessors(chain = true)
public final class Decimal implements Operand {
    /**
     * 由于此类最终要用于转换为自定义类 Rational，
     * 为了便于实现这一点，这里不使用库类 BigDecimal 作为内部实现。
     *
     * integerPart、decimalPart 均不准为 null，如果需要不存在，使用长度为 0 的数组代替
     */
    private Symbol[] integerPart; // 高位的序号小（高位在前。且不能在高位上包含多余的 0）
    private Symbol[] decimalPart; // 高位的序号小（高位在前。允许在低位上包含多余的 0）
    private boolean sign; // 是否有负号。对于 0，此值应为 false

    /**
     * 此方法禁止调用。因为此方法会导致 integerPart、decimalPart 为 null
     *
     * @since 2021-8-2
     */
    private Decimal() {
        super();
    }

    /**
     * @since 2021-8-2
     */
    public Decimal(Symbol[] decimal) {
        super();
        this.init(decimal);
    }

    /**
     * @since 2021-8-2
     */
    public Decimal(String decimal) {
        super();
        this.init(decimal);
    }

    /**
     * @since 2023-1-31
     */
    public Decimal(double decimal) {
        super();
        this.init(decimal);
    }

    /**
     * 初始化
     *
     * @param decimal 可以含小数点、负号。高位在前
     * @since 2021-8-2
     */
    public Decimal init(Symbol[] decimal) {
        preInitCheck(decimal);

        this.sign = (decimal[0] == Symbol.NEGATIVE);
        int pointLocation = Decimal.locatePoint(decimal);
        int digitStart = BuiltInTc.boolean2int(this.sign);
        int integerLength;
        int decimalLength;

        if (pointLocation == -1) {
            integerLength = decimal.length - digitStart;
            decimalLength = 0;
        } else {
            integerLength = pointLocation - digitStart;
            decimalLength = decimal.length - (pointLocation + 1);
        }

        this.integerPart = new Symbol[integerLength];
        this.decimalPart = new Symbol[decimalLength];

        System.arraycopy(decimal, digitStart,
                this.integerPart, 0, integerLength);
        System.arraycopy(decimal, pointLocation + 1,
                this.decimalPart, 0, decimalLength);

        return this;
    }

    /**
     * 初始化
     *
     * @param decimal 可以含小数点、负号。高位在前
     * @since 2021-8-2
     */
    public Decimal init(char[] decimal) {
        int symbolLength = decimal.length;
        var symbols = new Symbol[symbolLength];
        for (int order = 0; order < symbolLength; ++order) {
            var symbol = Symbol.getEnum(String.valueOf(decimal[order]));
            // 因为减号与负号的 String 符号是同一个，所以此处要改成负号
            if (symbol == Symbol.SUBTRACT) {
                symbol = Symbol.NEGATIVE;
            }
            symbols[order] = symbol;
            if (symbols[order] == null) {
                throw new UndefinedException("异常：使用了未定义符号");
            }
        }

        return this.init(symbols);
    }

    /**
     * 初始化
     *
     * @param decimal 可以含小数点、负号。高位在前
     * @since 2022-8-24
     */
    public Decimal init(String decimal) {
        return this.init(decimal.toCharArray());
    }

    /**
     * 初始化
     *
     * @since 2023-1-31
     */
    public Decimal init(double decimal) {
        var str = new BigDecimal(String.valueOf(decimal)) // 先将 double 转化为字符串传入来减小精度损失
                .stripTrailingZeros() // 去除 BigDecimal 末尾没用的 0
                .toPlainString(); // 阻止 BigDecimal 转化为字符串时使用科学计数法
        return this.init(str);
    }

    /**
     * @since 2021-8-3
     */
    public Rational toRational() {
        Rational result = new Rational(0);
        int integerLength = this.integerPart.length;
        int decimalLength = this.decimalPart.length;

        Figure digit; // 此变量在循环外定义是为了提高效率，避免变量的反复创建
        /**
         * 整数部分的计算要从最低位开始，而在 this 中整数部分是高位在前，因此要从数组尾端开始遍历
         */
        for (int order = integerLength - 1; order >= 0; --order) {
            digit = new Figure(Integer.parseInt(this.integerPart[order].toString()));
            // 下面表达式指的是：result = result + digit * pow(10, digit 对应的整数的位数 -1)
            result = RationalOperation.add(result,
                    RationalOperation.multiply(digit,
                            FigureOperation.power(
                                    10,
                                    integerLength - order - 1)));
        }

        /**
         * 小数部分的计算要从最高位开始，而在 this 中小数部分是高位在前，因此要从数组首端开始遍历
         */
        for (int order = 0; order < decimalLength; ++order) {
            digit = new Figure(Integer.parseInt(this.decimalPart[order].toString()));
            // 下面表达式指的是：result = result + digit / pow(10, digit 对应的小数的位数)
            result = RationalOperation.add(result,
                    RationalOperation.divide(digit,
                            FigureOperation.power(10,
                                    order + 1)));
        }

        if (this.sign) {
            result = RationalOperation.getOpposite(result);
        }

        return result;
    }

    /**
     * @since 2023-1-24
     */
    @Override
    public String toString() {
        var result = new StringBuilder();
        if (this.sign) {
            result.append(Symbol.NEGATIVE);
        }
        for (var digit : this.integerPart) {
            result.append(digit);
        }
        if (this.decimalPart != null && this.decimalPart.length >= 1) {
            result.append(Symbol.DOT);
            for (var digit : this.decimalPart) {
                result.append(digit);
            }
        }
        return result.toString();
    }

    /**
     * 判断形参是否是合法的小数
     *
     * @param decimal 可以含小数点、负号。高位在前
     * @since 2021-8-2
     */
    public static void preInitCheck(Symbol[] decimal) throws SyntaxUncheckedException {
        if (decimal == null) {
            throw new SyntaxUncheckedException("传入的 Symbol[] 为空");
        }
        var begin = decimal[0];
        var length = decimal.length;

        if (begin == Symbol.ZERO && length > 1) { // 如果 decimal 首位是 0，但 decimal 本身不是 0
            if (length == 2) {
                throw new SyntaxUncheckedException("传入的 Symbol[] 有误：首位是 0，但长度只有 2");
            }
            if (decimal[1] != Symbol.DOT) {
                throw new SyntaxUncheckedException("传入的 Symbol[] 有误：不是 0，首位是 0，但紧接着没有小数点");
            }
        }
        if (!(begin.isDigit() || begin == Symbol.NEGATIVE)) { // 如果第一个不是数字也不是负号，返回 false
            throw new SyntaxUncheckedException("传入的 Symbol[] 有误：第一个不是数字也不是负号");
        }
        if (decimal[decimal.length - 1] == Symbol.DOT) {
            throw new SyntaxUncheckedException("传入的 Symbol[] 有误：最后一个字符不能是小数点");
        }

        int pointNum = 0;
        Symbol bit; // 此变量在循环外定义是为了提高效率，避免变量的反复创建
        for (int order = 1; order < length; ++order) {
            bit = decimal[order];
            if (bit.isDigit()) {
                continue;
            }
            if (bit == Symbol.DOT) {
                ++pointNum;
                if (pointNum > 1) { // 如果发现小数点超过一个，返回 false
                    throw new SyntaxUncheckedException("传入的 Symbol[] 有误：小数点超过一个");
                } else {
                    continue;
                }
            }
            throw new SyntaxUncheckedException(
                    String.format("传入的 Symbol[] 有误：Symbol[%d] 即不是数字，也不是小数点", order));
        }
    }

    /**
     * 调用该方法之前，要保证形参是符合语法的小数
     *
     * 定位小数点的位置。如果没有发现小数点，将返回 -1
     *
     * @since 2021-8-2
     */
    public static int locatePoint(Symbol[] decimal) {
        for (int order = 0; order < decimal.length; ++order) {
            if (decimal[order] == Symbol.DOT) {
                return order;
            }
        }
        return -1;
    }

    /**
     * @since 2023-1-25
     */
    @Override
    public boolean isZero() throws LogicalException {
        return this.integerPart.length == 1 && this.integerPart[0] == Symbol.ZERO
                && this.decimalPart.length == 0;
    }

    /**
     * @since 2023-1-25
     */
    @Override
    public boolean isPositive() throws LogicalException {
        return !this.sign && !this.isZero(); // 如果非负且不是 0，那就是正数
    }

    /**
     * @since 2023-1-25
     */
    @Override
    public boolean isNegative() throws LogicalException {
        return this.sign;
    }

    /**
     * 此克隆是深克隆
     *
     * @since 2023-1-29
     */
    @Override
    public Decimal clone() {
        Decimal cloned = null;
        try {
            cloned = (Decimal) super.clone(); // 此克隆是浅克隆
            cloned.integerPart = this.integerPart.clone();
            cloned.decimalPart = this.decimalPart.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
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

        if (other instanceof Operand) {
            if (other instanceof Decimal) {
                return this.equals((Decimal) other);
            }
        }

        return false;
    }

    /**
     * 这里相等的判断标准是，Decimal 的符号和位数相同，且每一位都相等才相等
     *
     * @since 2023-1-29
     */
    public boolean equals(Decimal other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        return this.sign == other.sign
                && Arrays.equals(this.integerPart, other.integerPart)
                && Arrays.equals(this.decimalPart, other.decimalPart);
    }

    /**
     * @since 2023-1-29
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                Arrays.hashCode(this.integerPart),
                Arrays.hashCode(this.decimalPart),
                this.sign);
    }
}
