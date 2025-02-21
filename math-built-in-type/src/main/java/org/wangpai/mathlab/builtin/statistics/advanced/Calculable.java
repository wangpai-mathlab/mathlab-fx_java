package org.wangpai.mathlab.builtin.statistics.advanced;

import org.wangpai.exception.unchecked.LogicalException;

/**
 * @since 2022-10-21
 */
public interface Calculable {
    /**
     * 这个方法不能改变本对象
     *
     * @since 2022-10-21
     */
    default Calculable add(Calculable other) {
        throw new LogicalException("异常：此方法未实现"); // 允许实现类按需实现
    }

    /**
     * 这个方法不能改变本对象
     *
     * @since 2022-10-21
     */
    default Calculable subtract(Calculable other) {
        throw new LogicalException("异常：此方法未实现"); // 允许实现类按需实现
    }

    /**
     * 这个方法不能改变本对象
     *
     * @since 2022-10-21
     */
    default Calculable multiply(Calculable other) {
        throw new LogicalException("异常：此方法未实现"); // 允许实现类按需实现
    }

    /**
     * 这个方法不能改变本对象
     *
     * @since 2022-10-21
     */
    default Calculable divide(int num) {
        throw new LogicalException("异常：此方法未实现"); // 允许实现类按需实现
    }

    /**
     * 这个方法不能改变本对象
     *
     * @since 2022-10-21
     */
    default Calculable zero() {
        throw new LogicalException("异常：此方法未实现"); // 允许实现类按需实现
    }
}
