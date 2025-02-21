package org.wangpai.mathlab.advanced.lottery;

import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Rational;
import org.wangpai.mathlab.advanced.numeric.basic.operation.FigureOperation;
import org.wangpai.exception.unchecked.LogicalException;

/**
 * @since 2022-12-7
 */
public class LotteryTool {
    /**
     * 一共有 total 个球，valid 个有效，选 choice 个，中 winNum 个的概率
     *
     * total >= 1
     * total >= valid >= 0
     * total >= choice >= winNum >= 0
     * (total - valid) >= (choice - winNum)
     * 但 valid 与 choice 之间的大小关系不限
     *
     * 公式：C(valid, winNum) * C(total - valid, choice - winNum) / C(total, choice)
     *
     * 概率一词的英文是 probability，而不是 possibility
     *
     * @param needException 当此值为 false 时，如果入参中有逻辑错误，则不抛出异常，而将其视为概率为 0 的事件来处理
     * @since 2022-9-24
     */
    public static Rational probability(int total, int valid, int choice, int winNum, boolean needException) {
        boolean shouldBe = ((total >= valid) && (total >= choice) && (choice >= winNum)
                && ((total - valid) >= (choice - winNum))
                && (total >= 1) && (valid >= 0) && (winNum >= 0));
        if (!shouldBe) {
            if (needException) {
                throw new LogicalException("total、valid、choice、winNum 关系错误");
            } else {
                return Rational.ZERO.clone(); // 在不需要异常的情况下，将逻辑错误的情况视为概率为 0 的事件
            }
        }

        return new Rational(
                winningEventNum(total, valid, choice, winNum),
                FigureOperation.combination(new Figure(total), new Figure(choice)));
    }

    /**
     * 一共有 total 个球，valid 个有效，选 choice 个，中 winNum 个的概率
     *
     * total >= 1
     * total >= valid >= 0
     * total >= choice >= winNum >= 0
     * (total - valid) >= (choice - winNum)
     * 但 valid 与 choice 之间的大小关系不限
     *
     * @since 2022-12-7
     */
    public static Rational probability(int total, int valid, int choice, int winNum) {
        return probability(total, valid, choice, winNum, true);
    }

    /**
     * 一共有 total 个球，valid 个有效，选 choice 个的中奖情况的分布
     *
     * total >= valid >= 0
     * total >= choice >= 0
     * 但 valid 与 choice 之间的大小关系不限
     *
     * 规定：
     * 1. 如果 total = valid = choice = winNum = 0，返回值的各字段都是 0
     *
     * @since 2022-12-11
     */
    public static InvestResult invest(int total, int valid, int choice) {
        boolean shouldBe = ((total >= valid) && (total >= choice) && (total >= 0) && (valid >= 0));
        if (!shouldBe) {
            throw new LogicalException("total、valid、choice 关系错误");
        }
        if (total == 0) {
            return new InvestResult(Figure.ZERO.clone(), new Figure[]{Figure.ZERO.clone()});
        }

        var winningDistribution = new Figure[choice + 1];
        int maxWinning = Math.min(valid, choice);
        for (int winNum = 0; winNum <= maxWinning; ++winNum) {
            if ((total - valid) >= (choice - winNum)) {
                winningDistribution[winNum] = winningEventNum(total, valid, choice, winNum);
            } else {
                winningDistribution[winNum] = Figure.ZERO;
            }
        }
        for (int index = maxWinning + 1; index < winningDistribution.length; ++index) {
            winningDistribution[index] = Figure.ZERO;
        }
        return new InvestResult(FigureOperation.combination(new Figure(total), new Figure(choice)),
                winningDistribution);
    }

    /**
     * 为了提高效率，本方法不对形参进行范围检查，假定它们都符合逻辑
     *
     * 一共有 total 个球，valid 个有效，选 choice 个，中 winNum 个的基本事件数
     *
     * total >= 1
     * total >= valid >= winNum >= 0
     * total >= choice >= winNum >= 0
     * (total - valid) >= (choice - winNum)
     * 但 valid 与 choice 之间的大小关系不限
     *
     * 公式：C(valid, winNum) * C(total - valid, choice - winNum)
     *
     * 规定：
     * 1. 如果 total = valid = choice = winNum = 0，返回 0
     * 2. 如果不符合 【1】,但符合 valid = winNum = 0，返回  C(total, choice)
     *
     * @since 2022-12-11
     */
    private static Figure winningEventNum(int total, int valid, int choice, int winNum) {
        if (total == 0) {
            return Figure.ZERO.clone();
        }
        if (valid == 0) {
            return FigureOperation.combination(new Figure(total), new Figure(choice));
        }

        if (total == valid) { // 当 total = valid 时，应该有 choice = winNum
            return FigureOperation.combination(new Figure(total), new Figure(choice));
        } else {
            return FigureOperation.multiply(
                    FigureOperation.combination(new Figure(valid), new Figure(winNum)),
                    FigureOperation.combination(
                            new Figure(total - valid),
                            new Figure(choice - winNum)));
        }
    }
}
