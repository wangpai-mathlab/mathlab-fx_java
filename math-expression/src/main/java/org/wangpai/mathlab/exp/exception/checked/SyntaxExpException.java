package org.wangpai.mathlab.exp.exception.checked;

import org.wangpai.mathlab.exception.checked.MathlabCheckedException;

/**
 * 此异常是用于描述明显可能发生的，但不希望发生的情况。比如：用户输入的数学表达式字符串可能有误等等。
 * 如果是本来情形中就通常不应该发生的，但是实际在编程中可能发生，则用 LogicalException。
 * 比如：计算对数，但传入的形参是负数。因为 Java 语法没办法直接防止使用者不传入负数，所以这种情况理论上有可能发生。
 * 但传入负数这种行为在数学上非常无聊，因此对形参是负数这种情况就不应该使用 SyntaxExpException
 *
 * @since 2021-7-9
 */
public class SyntaxExpException extends MathlabCheckedException {
    public SyntaxExpException() {
        super("异常：不符语法");
    }

    public SyntaxExpException(String msg) {
        super(msg);
    }

    public SyntaxExpException(String msg, Object obj) {
        super(msg, obj);
    }
}
