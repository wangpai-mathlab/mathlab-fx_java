package org.wangpai.mathlab.exp.exception.checked;

import org.wangpai.mathlab.exception.checked.MathlabCheckedException;

/**
 * 本类特指无法直接预料（无法直接算出，但理论可能发生）的 0 除问题。
 * 这种 0 除不是开发者主动导致的，而是程序运行时意外产生。
 * 如果是开发者主动导致的，如主动将 0 作为除数，应归到 LogicalException 中。
 * 因为此异常本质上描述一种意料之中的情况，所以本异常属于检查型异常
 *
 * @since 2021-7-9
 */
public class ZeroDivisorExpException extends MathlabCheckedException {
    public ZeroDivisorExpException() {
        super("异常：发生了 0 除");
    }

    public ZeroDivisorExpException(String msg) {
        super(msg);
    }

    public ZeroDivisorExpException(String msg, Object obj) {
        super(msg, obj);
    }
}
