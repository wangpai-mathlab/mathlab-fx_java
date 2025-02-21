package org.wangpai.mathlab.builtin.factorial;

import org.wangpai.exception.unchecked.LogicalException;

/**
 * @since 2022-11-20
 */
public class Arrangement {
    /**
     * @since 2022-8-29
     */
    public static long arrangement(final int n, final int m) {
        if (n <= 0 || m < 0) {
            throw new LogicalException("错误：计算排列数时发现非正数。其中：n = " + n + "，m = " + m);
        }
        if (n < m) {
            throw new LogicalException("错误：计算排列数时发现大小关系（n < m）有误。其中：n = " + n + "，m = " + m);
        }

        long result = n;
        for (long i = n - (m - 1); i < n; ++i) {
            result *= i;
        }
        return result;
    }
}
