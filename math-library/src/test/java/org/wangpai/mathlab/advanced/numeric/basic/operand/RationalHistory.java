package org.wangpai.mathlab.advanced.numeric.basic.operand;

import org.wangpai.mathlab.advanced.numeric.basic.operation.RationalOperation;

/**
 * 本类记录了 Rational 的历史方法以供对比测试，
 * 只有未来可能需要进行对比测试时才需要添加到本类中。
 *
 * 本类只短暂记录存在 bug 的代码
 *
 * 本类禁止在非测试类中调用
 *
 * @since 2022-12-11
 */
public class RationalHistory {
    /**
     * 算法：相减结果为 0 即为相等
     *
     * @param innerThis 这是隐参数 this，在真实的方法中不需要亮出
     * @since 2021-8-5 v0
     */
    public boolean equals_v0(Rational innerThis, Rational other) {
        if (innerThis == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        try {
            return RationalOperation.subtract(innerThis, other).isZero();
        } catch (Exception exception) {
            return false;  // 只要此处抛出了异常，均视为相等判断失败
        }
    }
}
