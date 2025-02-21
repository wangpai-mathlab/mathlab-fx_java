package org.wangpai.mathlab.exception.unchecked;

import org.wangpai.exception.unchecked.LogicalException;

/**
 * 涉及如需要字符串中遵循某种语法时，使用本类。
 *
 * 本类是一种非检查型异常，继承自 LogicalException
 *
 * @since 2023-1-25
 */
public class SyntaxUncheckedException extends LogicalException {
    public SyntaxUncheckedException() {
        super("异常：不符语法");
    }

    public SyntaxUncheckedException(String msg) {
        super(msg);
    }

    public SyntaxUncheckedException(String msg, Object obj) {
        super(msg, obj);
    }
}
