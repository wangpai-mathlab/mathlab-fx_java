package org.wangpai.mathlab.builtin.factorial;

/**
 * @since 2022-11-20
 */
public class Combination {
    /**
     * @since 2022-8-29
     */
    public static long combination(final int n, final int m) {
        if (n > 0 && m == 0) {
            return 1; // 数学规定：C(n,0) = 1
        }
        return Arrangement.arrangement(n, m) / Factorial.factorial(m); // 此处必定能整除，无需担心除法舍入误差
    }
}
