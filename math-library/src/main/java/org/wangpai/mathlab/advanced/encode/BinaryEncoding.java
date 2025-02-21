package org.wangpai.mathlab.advanced.encode;

import org.wangpai.mathlab.advanced.numeric.extend.radix.Binary;

/**
 * @since 2022-12-3
 */
public class BinaryEncoding {
    /**
     * 有 n 个号码球，它们的编号分别为 (1 + offset) ~ (n + offset)。从这 n 个号码球中组合选任意个号码，然后对这组号码进行编号。
     * 将这 n 个号码球看成是 n 位的二进制数。（如果编号是 1 ~ n，则 offset 为 0）
     * 编号为 i （1 + offset <= i <= n + offset）的号码球被选中代表此二进制数的第 i - offset 位（从右往左）为 1，否则为 0。
     *
     * 不同的组合对应不同的编号，编号的范围为 [0, 2^n - 1]。返回 ascendCom 对应的编号。
     * ascendCom 可以为空，此时对应的编号为 0
     *
     * 为了提高效率，本方法不会对形参进行检查，所以要求形参：
     * - n >= ascendCom.length。
     * - ascendCom 数组需要按元素下标升序排列，且满足：
     * - * 1 + offset <= ascendCom[i] <= n + offset，
     * - * ascendCom[i] 的值不重复
     *
     * @since 2022-12-3
     */
    public static Binary encodeWithNoCheckForCom(final int n, int[] ascendCom, int offset) {
        var digits = new boolean[n];
        for (var num : ascendCom) {
            digits[digits.length - (num - offset)] = true; // 因为类 Binary 是高位在前，低位在后，所以这里需要反过来
        }
        return new Binary(digits);
    }
}
