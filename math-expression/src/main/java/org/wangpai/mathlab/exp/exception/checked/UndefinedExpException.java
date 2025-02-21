package org.wangpai.mathlab.exp.exception.checked;

import org.wangpai.mathlab.exception.checked.MathlabCheckedException;

/**
 * 当进行的操作、使用的符号未定义或不支持时，使用该异常
 *
 * @since 2021-7-29
 */
public class UndefinedExpException extends MathlabCheckedException {
    public UndefinedExpException() {
        super("错误：发生了未定义异常");
    }

    public UndefinedExpException(String msg) {
        super(msg);
    }

    public UndefinedExpException(String msg, Object obj) {
        super(msg, obj);
    }
}
