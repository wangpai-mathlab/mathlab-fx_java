package org.wangpai.mathlab.advanced.numeric.extend.discretefun;

import org.wangpai.exception.unchecked.ForbiddenCallingException;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Rational;
import org.wangpai.mathlab.advanced.numeric.basic.operation.FigureOperation;
import org.wangpai.mathlab.advanced.numeric.basic.operation.RationalOperation;
import org.wangpai.mathlab.advanced.numeric.extend.complex.Complex;
import org.wangpai.mathlab.advanced.numeric.extend.complex.ComplexDiscrete;
import org.wangpai.mathlab.advanced.numeric.extend.complex.ComplexOperation;

/**
 * @since 2023-1-28
 */
public class DiscreteOperation {
    /**
     * 加法。加法的定义为，只对双方的公共定义域作加法，其它区域无定义
     *
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * @since 2023-2-10
     */
    public static Discrete add(Discrete first, Discrete second) {
        var result = Discrete.getInstance();
        var firstData = first.getSequence();
        for (var pair : firstData.entrySet()) {
            var x = pair.getKey();
            if (second.contain(x)) {
                var y = RationalOperation.add(pair.getValue(), second.get(x));
                result.putFrugally(x, y);
            }
        }
        return result;
    }

    /**
     * 减法。减法的定义为，只对双方的公共定义域作减法，其它区域无定义
     *
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * @since 2023-2-10
     */
    public static Discrete subtract(Discrete first, Discrete second) {
        var result = Discrete.getInstance();
        var firstData = first.getSequence();
        for (var pair : firstData.entrySet()) {
            var x = pair.getKey();
            if (second.contain(x)) {
                var y = RationalOperation.subtract(pair.getValue(), second.get(x));
                result.putFrugally(x.clone(), y);
            }
        }
        return result;
    }

    /**
     * 乘法。乘法的定义为，只对双方的公共定义域作乘法，其它区域无定义
     *
     * 此方法会返回一个新对象，不会修改形参，也不会引用形参的数据
     *
     * @since 2023-2-10
     */
    public static Discrete multiply(Discrete first, Discrete second) {
        var result = Discrete.getInstance();
        var firstData = first.getSequence();
        for (var pair : firstData.entrySet()) {
            var x = pair.getKey();
            if (second.contain(x)) {
                var y = RationalOperation.multiply(pair.getValue(), second.get(x));
                result.putFrugally(x.clone(), y);
            }
        }
        return result;
    }

    /**
     * 除法。此方法禁止使用
     *
     * 占位空方法
     *
     * @since 2023-2-10
     * @deprecated 2023-2-10 整数不支持除法运算
     */
    @Deprecated
    public static Discrete divide(Discrete first, Discrete second) {
        throw new ForbiddenCallingException("错误：整数不支持除法运算");
    }

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
    public static Discrete convolution(Discrete first, Discrete second) {
        var result = new Discrete();
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
            Rational sum = Rational.ZERO.clone();
            for (Figure i = firstFront.clone(); FigureOperation.lessOrEqual(i, firstEnd); i.increaseOne()) {
                var temp1 = first.get(i);
                var temp2 = second.get(FigureOperation.subtract(n, i));
                if (temp1 != null && temp2 != null) {
                    var temp3 = RationalOperation.multiply(temp1, temp2);
                    sum = RationalOperation.add(new Rational(sum), temp3);
                }
            }
            result.putSafely(n, new Rational(sum));
        }
        return result;
    }

    /**
     * 离散傅里叶变换
     *
     * @since 2023-1-30
     */
    public static ComplexDiscrete dft(Discrete discrete) {
        var result = new ComplexDiscrete();
        final int N = discrete.size();
        double v = -2 * Math.PI / N;
        for (int k = 0; k < N; ++k) {
            var sum = Complex.ZERO.clone();
            double w = v * k;
            int n = 0;
            var xns = discrete.getSequence().values();
            for (var xn : xns) {
                sum = ComplexOperation.add(sum,
                        Complex.getInstanceByPolar(xn.toDouble(), w * n));
                ++n;
            }
            result.putSafely(new Figure(k), sum);
        }

        return result;
    }
}
