package org.wangpai.mathlab.advanced.numeric.basic.operand;

import java.io.Serializable;
import org.wangpai.exception.unchecked.LogicalException;

/**
 * @since 2021-8-1
 */
public interface Operand extends Cloneable, Serializable {
    /**
     * 此方法不能依赖于方法 equals，否则会造成循环依赖
     */
    /**
     * @since 2021-8-1
     * @lastModified 2022-8-29
     */
    boolean isZero() throws LogicalException; // 此处的异常签名是提供给不想实现本方法的子类

    /**
     * 此方法不能依赖于方法 isNegative，否则会造成循环依赖
     *
     * @since 2022-8-24
     * @lastModified 2022-8-29
     */
    boolean isPositive() throws LogicalException; // 此处的异常签名是提供给不想实现本方法的子类

    /**
     * @since 2024-9-4
     */
    default boolean isPositiveOrZero() throws LogicalException {
        return this.isPositive() || this.isZero();
    }

    /**
     * @since 2021-8-5
     * @lastModified 2022-8-24
     */
    default boolean isNegative() throws LogicalException {
        // 默认方法的实现只能依赖于抽象方法，否则很容易导致死循环调用
        return !(this.isPositive() || this.isZero());
    }

    /**
     * @since 2024-9-4
     */
    default boolean isNegativeOrZero() throws LogicalException {
        // 默认方法的实现只能依赖于抽象方法，否则很容易导致死循环调用
        return !this.isPositive();
    }

    /**
     * 操作数的符号：
     *   > 1：代表正数
     *   > 0：代表 0
     *   > -1：代表 负数
     *
     * @since 2022-12-23
     */
    default int sign() {
        if (this.isPositive()) {
            return 1;
        } else if (this.isNegative()) {
            return -1;
        } else {
            return 0;
        }
    }
}
