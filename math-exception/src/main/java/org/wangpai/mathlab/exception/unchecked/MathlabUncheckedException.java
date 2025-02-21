package org.wangpai.mathlab.exception.unchecked;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.wangpai.exception.base.WangpaiUncheckedException;

/**
 * 本项目的非检查型异常基类。这指的是一种理论上可能发生的异常，但通常认为这种异常的发生属于一种非常无聊的情况。开发人员应避免这种异常的发生。
 * 比如：计算对数，但传入的形参是负数。因为 Java 语法没办法直接防止使用者不传入负数，所以这种情况理论上有可能发生。
 * 但传入负数这种行为在数学上非常无聊，因此这种情况应该抛出 MathlabUncheckedException 类型的异常
 *
 * @since 2022-8-29
 */
@Accessors(chain = true)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public abstract class MathlabUncheckedException extends WangpaiUncheckedException {
    private String exceptionMsg;
    private Object data;

    /**
     * 因为本类是抽象类，所以此构造器可以声明为 protected，
     * 但它的非抽象子类的构造器只能声明为 public
     */
    protected MathlabUncheckedException() {
        super();
    }

    protected MathlabUncheckedException(String msg) {
        this(msg, null);
    }

    protected MathlabUncheckedException(String msg, Object obj) {
        super(msg);
        this.setExceptionMsg(msg);
        this.setData(obj);
    }
}
