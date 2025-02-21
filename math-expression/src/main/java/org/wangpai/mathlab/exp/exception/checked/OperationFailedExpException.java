package org.wangpai.mathlab.exp.exception.checked;

import org.wangpai.mathlab.exception.checked.MathlabCheckedException;

/**
 * 这指的是一种尝试进行某种操作，但结果失败时抛出的异常。通常在某种方法的返回值有其它用途时需要此异常
 *
 * @since 2022-9-9
 */
public class OperationFailedExpException extends MathlabCheckedException {
    public OperationFailedExpException() {
        super("异常：操作失败");
    }

    public OperationFailedExpException(String msg) {
        super(msg);
    }

    public OperationFailedExpException(String msg, Object obj) {
        super(msg, obj);
    }
}