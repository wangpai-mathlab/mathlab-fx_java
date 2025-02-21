package org.wangpai.mathlab.builtin.binary;

import org.wangpai.exception.unchecked.LogicalException;

/**
 * @since 2024-12-19
 */
public class BinaryUtil {
    /**
     * 将一个分数转化为二进制下的小数，以字符串的形式将结果输出
     *
     * 注意：
     * 1. 整数与小数之间以小数点分隔，且会去掉首尾不必要的 0
     * 2. 小数部分如果除不尽，会直接截断（只舍不入）
     *
     * @param numerator 分子。只能为自然数
     * @param denominator 分母。只能为正整数
     * @param decimalMaxBits 小数部分最大位数
     * @since 2024-12-19
     */
    public static String fractionToBinary(int numerator, int denominator, int decimalMaxBits) {
        if (numerator < 0) {
            throw new LogicalException("错误：分子只能为自然数");
        }
        if (denominator <= 0) {
            throw new LogicalException("错误：分母只能为正整数");
        }
        int integerPart = numerator / denominator;
        StringBuilder result = new StringBuilder();
        String integerBinary = Integer.toBinaryString(integerPart);
        result.append(integerBinary);
        result.append(".");
        int remainingNumerator = numerator % denominator;
        for (int i = 0; i < decimalMaxBits; ++i) {
            remainingNumerator *= 2;
            int fractionalBit = remainingNumerator / denominator;
            result.append(fractionalBit);
            remainingNumerator %= denominator;
            if (remainingNumerator == 0) {
                break;
            }
        }
        return result.toString();
    }

    /**
     * 将一个 double 值转化为二进制下的小数，以字符串的形式将结果输出
     *
     * 注意：
     * 1. 整数与小数之间以小数点分隔，且会去掉首尾不必要的 0
     * 2. 小数部分如果除不尽，会直接截断（只舍不入）
     *
     * @param decimal 必须为正数
     * @param decimalMaxBits 小数部分最大位数
     * @since 2024-12-19
     */
    public static String decimalToBinary(double decimal, int decimalMaxBits) {
        if (decimal < 0) {
            throw new LogicalException("错误：decimal 只能为正数");
        }
        // 获取整数部分
        long integerPart = (long) decimal;
        StringBuilder binaryString = new StringBuilder(Long.toBinaryString(integerPart));
        // 获取小数部分
        double fractionalPart = decimal - integerPart;
        if (fractionalPart > 0) {
            binaryString.append(".");
            int bitLength = 0;
            while (fractionalPart > 0 && bitLength < decimalMaxBits) {
                fractionalPart *= 2;
                int bit = (int) fractionalPart;
                binaryString.append(bit);
                fractionalPart -= bit;
                bitLength++;
            }
        }
        return binaryString.toString();
    }
}
