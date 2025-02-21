package org.wangpai.mathlab.advanced.compression;

import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Rational;
import org.wangpai.mathlab.advanced.numeric.basic.operation.FigureOperation;
import org.wangpai.mathlab.advanced.numeric.basic.operation.RationalOperation;

/**
 * @since 2022-11-18
 */
public class FigureCompression {
    /**
     * 将 data 进行均匀压缩
     *
     * 为了提高效率，本方法不会对形参进行检查，所以要求形参：
     * - data 每个元素的都在 [formerRange[0], formerRange[1]] 之中
     * - formerRange、compressedRange 各为长度为 2 的数组，且各自的第一个元素不大于第二个元素
     *
     * @param data 储存需要压缩的原数据
     * @param formerRange 原数据的取值范围：[formerRange[0], formerRange[1]]
     * @param compressedRange 数据压缩之后的目标取值范围：[compressedRange[0], compressedRange[1]]
     * @since 2022-11-18
     */
    public static double[][] uniformlyWnc(Figure[][] data, Figure[] formerRange, int[] compressedRange) {
        /**
         * 设数据为 x，范围为 [a, b]。转换之后的数据为 fx，范围为 [c, d]。
         * dx = b - a，dfx = d - c，k = dfx / dx。
         * 转换公式为：fx = (x-a) / dx * dfx + c。
         * 变形之后为：fx = x * k - a * k + c
         */
        Figure a = formerRange[0];
        Figure b = formerRange[1];
        Figure dx = FigureOperation.subtract(b, a);
        int c = compressedRange[0];
        int d = compressedRange[1];
        int dfx = d - c;
        Rational k = new Rational(new Figure(dfx), dx);
        Rational ak = RationalOperation.multiply(k, a);
        Rational m = RationalOperation.subtract(new Rational(c), ak);

        var result = new double[data.length][data[0].length];
        for (int row = 0; row < data.length; ++row) {
            for (int column = 0; column < data[0].length; ++column) {
                result[row][column] = RationalOperation.add(
                                RationalOperation.multiply(k, data[row][column]),
                                m)
                        .toDouble();
            }
        }

        return result;
    }

    /**
     * 将 data 进行均匀压缩
     *
     * 为了提高效率，本方法不会对形参进行检查，所以要求形参：
     * - data 每个元素的都在 [formerRange[0], formerRange[1]] 之中
     * - formerRange、compressedRange 各为长度为 2 的数组，且各自的第一个元素不大于第二个元素
     *
     * @param data 储存需要压缩的原数据
     * @param formerRange 原数据的取值范围：[formerRange[0], formerRange[1]]
     * @param compressedRange 数据压缩之后的目标取值范围：[compressedRange[0], compressedRange[1]]
     * @since 2022-11-18
     */
    public static double[] uniformlyWnc(Figure[] data, Figure[] formerRange, int[] compressedRange) {
        /**
         * 设数据为 x，范围为 [a, b]。转换之后的数据为 fx，范围为 [c, d]。
         * dx = b - a，dfx = d - c，k = dfx / dx。
         * 转换公式为：fx = (x-a) / dx * dfx + c。
         * 变形之后为：fx = x * k - a * k + c
         */
        Figure a = formerRange[0];
        Figure b = formerRange[1];
        Figure dx = FigureOperation.subtract(b, a);
        int c = compressedRange[0];
        int d = compressedRange[1];
        int dfx = d - c;
        Rational k = new Rational(new Figure(dfx), dx);
        Rational ak = RationalOperation.multiply(k, a);
        Rational m = RationalOperation.subtract(new Rational(c), ak);

        var result = new double[data.length];
        for (int index = 0; index < data.length; ++index) {
            result[index] = RationalOperation.add(
                            RationalOperation.multiply(k, data[index]),
                            m)
                    .toDouble();
        }

        return result;
    }
}
