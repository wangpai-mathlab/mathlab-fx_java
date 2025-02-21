package org.wangpai.mathlab.builtin.encode;

import java.util.Arrays;
import org.wangpai.mathlab.builtin.factorial.Combination;
import org.wangpai.exception.unchecked.LogicalException;

/**
 * @since 2022-11-20
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
    public static long encodeWithNoCheck(final int n, int[] ascending) {
        long offset = 0; // 正向计算时，此处应从 0 开始
        int lastNumber = 0;
        // 计算除最后一位之外的累计偏移量
        int maxIndex = ascending.length - 1;
        for (int index = 0; index < maxIndex; ++index) {
            for (int num = lastNumber + 1; num < ascending[index]; ++num) {
                /**
                 * 单次偏移量计算公式：（i、j 从 1 开始）
                 * 对于第 i 位为号码 j 的组合号码数为：C(n-j, m-i)
                 * offset 代表以第 i 位为号码 j 开头的所有的组合号码数
                 */
                offset += Combination.combination(n - num, ascending.length - (index + 1));
            }
            lastNumber = ascending[index];
        }
        // 因为末尾的计算直接使用减法运算即可完成，没有必要组合函数来计算，所以将其从上面的循环中分离出来
        int lastOffset = ascending[maxIndex] - lastNumber;
        return offset + lastOffset;
    }

    /**
     * 有 n 个号码球，它们的编号分别为 1 ~ n。从这 n 个号码球中组合选 m 个号码，然后对这组号码进行编号。
     * 不同的组合对应不同的编号，编号的范围为 [1, C(n,m)]。返回 combination 对应的编号
     *
     * @since 2022-11-17
     */
    public static long encode(final int n, int[] combination) {
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
     * - encode <= C(n, m)
     *
     * @since 2022-11-17
     */
    public static int[] decodeWithNoCheck(long encode, int n, int m) {
        var result = new int[m];
        long offset = 1; // 逆向计算时，此处应从 1 开始
        int lastNumber = 0;
        // 计算除最后一位之外的累计偏移量
        int maxIndex = m - 1;
        for (int index = 0; index < maxIndex; ++index) {
            // 因为后面的计算结果与前面有关，所以此处不能使用折半查找
            int end = n - m + index + 1;
            for (int num = lastNumber + 1; num <= end; ++num) {
                /**
                 * 单次偏移量计算公式：（i、j 从 1 开始）
                 * 对于第 i 位为号码 j 的组合号码数为：C(n-j, m-i)
                 * offset 代表以第 i 位为号码 j 开头的所有的组合号码数
                 */
                long digitOffset = Combination.combination(n - num, m - (index + 1));
                if (offset + digitOffset > encode) {
                    result[index] = num;
                    lastNumber = num;
                    break;
                }
                offset += digitOffset;
            }
        }
        result[maxIndex] = (int) (encode - offset + lastNumber + 1);
        return result;
    }
}
