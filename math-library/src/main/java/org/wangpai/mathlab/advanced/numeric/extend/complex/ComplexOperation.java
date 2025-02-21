package org.wangpai.mathlab.advanced.numeric.extend.complex;

import org.wangpai.exception.unchecked.LogicalException;

/**
 * @since 2023-1-30
 */
public class ComplexOperation {
    /**
     * 加法
     *
     * @since 2023-1-30
     */
    public static Complex add(Complex first, Complex second) {
        return Complex.getInstanceByRect(first.getReal() + second.getReal(),
                first.getImaginary() + second.getImaginary());
    }

    /**
     * 减法
     *
     * @since 2023-1-30
     */
    public static Complex subtract(Complex first, Complex second) {
        return Complex.getInstanceByRect(first.getReal() - second.getReal(),
                first.getImaginary() - second.getImaginary());
    }

    /**
     * 乘法
     *
     * @since 2023-1-30
     */
    public static Complex multiply(Complex first, double second) {
        return Complex.getInstanceByRect(first.getReal() * second,
                first.getImaginary() * second);
    }

    /**
     * 乘法
     *
     * 公式：z1 * z2 = (x1 * x2 - y1 * y2) + (x1 * y2 + x2 * y1) * i
     *
     * @since 2023-1-30
     */
    public static Complex multiply(Complex first, Complex second) {
        return Complex.getInstanceByRect(
                first.getReal() * second.getReal() - first.getImaginary() * second.getImaginary(),
                first.getReal() * second.getImaginary() + second.getReal() * first.getImaginary());
    }

    /**
     * 除法
     *
     * 公式：z1 / z2 = (x1 * x2 + y1 * y2) / (x2^2 + y2^2) + (x2 * y1 - x1 * y2) / (x2^2 + y2^2) * i
     *
     * @since 2023-1-30
     */
    public static Complex divide(Complex first, Complex second) {
        if (second.isZero()) {
            throw new LogicalException("错误：0 不能作除数");
        }

        double denominator = second.getReal() * second.getReal() + second.getImaginary() * second.getImaginary();
        return Complex.getInstanceByRect(
                (first.getReal() * second.getReal() + first.getImaginary() * second.getImaginary())
                        / denominator,
                (second.getReal() * first.getImaginary() - first.getReal() * second.getImaginary())
                        / denominator);
    }
}
