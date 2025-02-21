package org.wangpai.mathlab.advanced.lottery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Rational;

/**
 * @since 2022-12-11
 */
@Getter
@AllArgsConstructor
@ToString
@Accessors(chain = true)
public class InvestResult {
    private Figure total; // 基本事件总数
    private Figure[] winningDistribution; // 下标表示 winning 值，元素值表示 winning 值出现的次数

    /**
     * @since 2022-12-11
     */
    public Rational[] toProbability() {
        var result = new Rational[this.winningDistribution.length];
        for (int index = 0; index < this.winningDistribution.length; ++index) {
            result[index] = new Rational(this.winningDistribution[index], this.total);
        }
        return result;
    }

    /**
     * 特别规定：0 的倒数也为 0
     *
     * @since 2022-12-11
     */
    public double[] toReciprocalProbability() {
        var result = new double[this.winningDistribution.length];
        for (int index = 0; index < this.winningDistribution.length; ++index) {
            Figure winNum = this.winningDistribution[index];
            if (winNum.isZero()) {
                result[index] = 0;
            } else {
                result[index] = new Rational(this.total, winNum).toDouble();
            }
        }
        return result;
    }
}
