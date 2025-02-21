package org.wangpai.mathlab.advanced.numeric.extend.discretefun;

import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Decimal;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Rational;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscreteOperationTest {
    @Test
    void add() {
        var aFun = new Discrete();
        var bFun = new Discrete();
        for (int index = 0; index <= 5; ++index) {
            aFun.putFrugally(new Figure(index), new Rational(index));
        }
        for (int index = 0; index <= 2; ++index) {
            bFun.putFrugally(new Figure(index), new Rational(index));
        }
        var result = DiscreteOperation.add(aFun, bFun);

        System.out.println("");
    }

    @Test
    void subtract() {
        var aFun = new Discrete();
        var bFun = new Discrete();
        for (int index = 0; index <= 5; ++index) {
            aFun.putFrugally(new Figure(index), new Rational(index * index));
        }
        for (int index = 0; index <= 4; ++index) {
            bFun.putFrugally(new Figure(index), new Rational(index));
        }
        var result = DiscreteOperation.subtract(aFun, bFun);

        System.out.println("");
    }

    @Test
    void multiply() {
        var aFun = new Discrete();
        var bFun = new Discrete();
        for (int index = 0; index <= 5; ++index) {
            aFun.putFrugally(new Figure(index), new Rational(index * index));
        }
        for (int index = 0; index <= 4; ++index) {
            bFun.putFrugally(new Figure(index), new Rational(index));
        }
        var result = DiscreteOperation.multiply(aFun, bFun);

        System.out.println("");
    }

    @Test
    void convolution() {
        var aFun = new Discrete();
        var bFun = new Discrete();
        for (int index = 0; index <= 5; ++index) {
            aFun.putFrugally(new Figure(index), new Rational(1));
        }
        for (int index = 0; index <= 2; ++index) {
            bFun.putFrugally(new Figure(index), new Rational(1));
        }
        var result = DiscreteOperation.convolution(aFun, bFun);

        var expected = new Discrete();
        expected.putFrugally(new Figure(0), new Rational(1));
        expected.putFrugally(new Figure(1), new Rational(2));
        expected.putFrugally(new Figure(2), new Rational(3));
        expected.putFrugally(new Figure(3), new Rational(3));
        expected.putFrugally(new Figure(4), new Rational(3));
        expected.putFrugally(new Figure(5), new Rational(3));
        expected.putFrugally(new Figure(6), new Rational(2));
        expected.putFrugally(new Figure(7), new Rational(1));

        assertEquals(expected, result);
    }

    @Test
    void dft() {
        var discrete = new Discrete();
        discrete.putFrugally(new Figure(1), new Decimal(-8.3907152907645).toRational());
        discrete.putFrugally(new Figure(2), new Decimal(4.0808206181339).toRational());
        discrete.putFrugally(new Figure(3), new Decimal(1.5425144988758).toRational());
        discrete.putFrugally(new Figure(4), new Decimal(-6.6693806165226).toRational());
        discrete.putFrugally(new Figure(5), new Decimal(9.6496602849211).toRational());
        discrete.putFrugally(new Figure(6), new Decimal(-9.5241298041516).toRational());
        discrete.putFrugally(new Figure(7), new Decimal(6.333192030863).toRational());
        discrete.putFrugally(new Figure(8), new Decimal(-1.1038724383905).toRational());
        discrete.putFrugally(new Figure(9), new Decimal(-4.4807361612917).toRational());
        discrete.putFrugally(new Figure(10), new Decimal(8.6231887228768).toRational());
        discrete.putFrugally(new Figure(11), new Decimal(-9.9902081331465).toRational());
        discrete.putFrugally(new Figure(12), new Decimal(8.1418097052656).toRational());
        discrete.putFrugally(new Figure(13), new Decimal(-3.672913304547).toRational());
        discrete.putFrugally(new Figure(14), new Decimal(-1.9781357400427).toRational());
        discrete.putFrugally(new Figure(15), new Decimal(6.9925080647838).toRational());
        discrete.putFrugally(new Figure(16), new Decimal(-9.7562931279524).toRational());
        discrete.putFrugally(new Figure(17), new Decimal(9.3799475211944).toRational());
        discrete.putFrugally(new Figure(18), new Decimal(-5.9846006905786).toRational());
        discrete.putFrugally(new Figure(19), new Decimal(0.6630685835171).toRational());
        discrete.putFrugally(new Figure(20), new Decimal(4.8718767500701).toRational());
        var result = DiscreteOperation.dft(discrete);
        var result2 = result.toPolarComplexDiscrete();

        System.out.println(result);

    }
}