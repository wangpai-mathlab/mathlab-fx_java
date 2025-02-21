package org.wangpai.mathlab.advanced.encode;

import java.util.Arrays;
import org.wangpai.exception.unchecked.LogicalException;
import org.wangpai.exception.unchecked.dev.DevelopingException;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.advanced.numeric.basic.operation.FigureOperation;
import org.wangpai.exception.checked.failed.OverflowException;

/**
 * @since 2022-11-17
 */
public class CombinationEncoding {
    /**
     * 有 n 个号码球，它们的编号分别为 1 ~ n。从这 n 个号码球中组合选 m 个号码，然后对这组号码进行编号。
     * 不同的组合对应不同的编号，编号的范围为 [1, C(n,m)]。返回 ascending 对应的编号
     *
     * 为了提高效率，本方法不会对形参进行检查，所以要求形参：
     * - n >= ascending.length。
     * - ascending 数组需要按元素下标升序排列，且满足：
     * - * 1 <= ascending[i] <= n，
     * - * ascending[i] 的值不重复
     *
     * @since 2022-11-17
     */
    public static Figure encodeWithNoCheck(final int n, int[] ascending) {
        Figure offset = Figure.ZERO.clone();
        int lastNumber = 0;
        // 计算除最后一位之外的累计偏移量
        for (int index = 0; index < ascending.length - 1; ++index) {
            for (int num = lastNumber + 1; num < ascending[index]; ++num) {
                /**
                 * 单次偏移量计算公式：（i、j 从 1 开始）
                 * 对于第 i 位为号码 j 的组合号码数为：C(n-j, m-i)
                 */
                offset = FigureOperation.add(offset,
                        FigureOperation.combination(n - num, ascending.length - (index + 1)));
            }
            lastNumber = ascending[index];
        }
        int lastOffset = ascending[ascending.length - 1] - lastNumber;
        return FigureOperation.add(offset, new Figure(lastOffset));
    }

    /**
     * 有 n 个号码球，它们的编号分别为 1 ~ n。从这 n 个号码球中组合选 m 个号码，然后对这组号码进行编号。
     * 不同的组合对应不同的编号，编号的范围为 [1, C(n,m)]。返回 combination 对应的编号
     *
     * @since 2022-11-17
     */
    public static Figure encode(final int n, int[] combination) {
        if (n < combination.length) {
            throw new LogicalException("n 不能小于组合号码个数");
        }
        Arrays.sort(combination); // 将 combination 按元素下标升序排列
        if (combination[0] <= 0) {
            throw new LogicalException("号码元素值只能为正数");
        }
        if (combination[combination.length - 1] > n) {
            throw new LogicalException("号码元素值超出范围");
        }
        for (int index = 0; index < combination.length - 1; ++index) {
            if (combination[index] == combination[index + 1]) {
                throw new LogicalException("号码元素出现重复");
            }
        }

        return encodeWithNoCheck(n, combination);
    }

    /**
     * 有 n 个号码球，它们的编号分别为 1 ~ n。从这 n 个号码球中组合选 m 个号码，然后对这组号码进行编号。
     * 不同的组合对应不同的编号，编号的范围为 [1, C(n,m)]。
     * 现在已知编号 encode，求其对应的原号码组合
     *
     * 为了提高效率，本方法不会对形参进行检查，所以要求形参：
     * - 1 <= m <= n
     * - encodeForLong <= C(n, m)
     *
     * @since 2022-11-28
     */
    public static int[] decodeWithNoCheck(Figure encode, int n, int m) {
        try { // 如果可以转化为 long 类型，那么就使用 long 类型的函数来计算
            long encodeForLong = encode.toLong();
            return org.wangpai.mathlab.builtin.encode.CombinationEncoding.decodeWithNoCheck(encodeForLong, n, m);
        } catch (OverflowException exception) { // 如果不能转化就使用 Figure 类型的函数
            return decodeWithNoCheckForFigure(encode, n, m);
        }
    }

    /**
     * 有 n 个号码球，它们的编号分别为 1 ~ n。从这 n 个号码球中组合选 m 个号码，然后对这组号码进行编号。
     * 不同的组合对应不同的编号，编号的范围为 [1, C(n,m)]。
     * 现在已知编号 encode，求其对应的原号码组合
     *
     * 为了提高效率，本方法不会对形参进行检查，所以要求形参：
     * - 1 <= m <= n
     * - encodeForLong <= C(n, m)
     *
     * @since 2022-11-28
     */
    public static int[] decodeWithNoCheckForFigure(Figure encode, int n, int m) {
        throw new DevelopingException("错误：此方法尚未完成，禁止调用"); // FIXME：2022年11月28日
    }
}
