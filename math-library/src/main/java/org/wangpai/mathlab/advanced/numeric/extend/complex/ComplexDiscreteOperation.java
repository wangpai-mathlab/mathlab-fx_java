package org.wangpai.mathlab.advanced.numeric.extend.complex;

import lombok.ToString;
import lombok.experimental.Accessors;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.advanced.numeric.basic.operation.FigureOperation;

/**
 * @since 2023-1-30
 */
@ToString
@Accessors(chain = true)
public class ComplexDiscreteOperation {
    /*
     * 卷积。若 first 的定义域为 [a, b]，second 的定义域为 [c, d]。则返回结果的定义域为 [a+c, b+d]
     *
     * 数学定义：y(n) = 【以 i 求和【x(i) * h(n-i)】，其中 i 取正无穷到负无穷】
     *
     * 算法：
     * 分别取 first 的定义域 [a, b]，返回值的定义域 [a+c, b+d]。
     * 以返回值的定义域为外层循环，first 的定义域为内层循环，依次求返回值的值域集合
     *
     * @since 2023-1-26
     */
    public static ComplexDiscrete convolution(ComplexDiscrete first, ComplexDiscrete second) {
        var result = new ComplexDiscrete();
        var firstFront = first.getXDomain().getMinValue();
        var firstEnd = first.getXDomain().getMaxValue();
        var secondFront = second.getXDomain().getMinValue();
        var secondEnd = second.getXDomain().getMaxValue();
        var resultFront = FigureOperation.add(firstFront, secondFront);
        var resultEnd = FigureOperation.add(firstEnd, secondEnd);

        /**
         * 此处两层循环的初始变量必须使用克隆初始化，原因有很多。
         * 比如，初始化使用的是循环外变量，如果不使用克隆，则内层循环在二次循环时，初始变量不会重置
         */
        for (Figure n = resultFront.clone(); FigureOperation.lessOrEqual(n, resultEnd); n.increaseOne()) {
            Complex sum = Complex.ZERO.clone();
            for (Figure i = firstFront.clone(); FigureOperation.lessOrEqual(i, firstEnd); i.increaseOne()) {
                var temp1 = first.get(i);
                var temp2 = second.get(FigureOperation.subtract(n, i));
                if (temp1 != null && temp2 != null) {
                    var temp3 = ComplexOperation.multiply(temp1, temp2);
                    sum = ComplexOperation.add(sum, temp3);
                }
            }
            result.putSafely(n, sum);
        }
        return result;
    }
}
