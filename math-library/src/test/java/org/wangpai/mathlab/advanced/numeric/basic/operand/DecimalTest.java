package org.wangpai.mathlab.advanced.numeric.basic.operand;

import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.advanced.numeric.basic.enumeration.Symbol;
import org.wangpai.mathlab.exception.checked.MathlabCheckedException;
import org.wangpai.mathlab.exception.unchecked.SyntaxUncheckedException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.wangpai.mathlab.advanced.numeric.basic.enumeration.Symbol.ADD;
import static org.wangpai.mathlab.advanced.numeric.basic.enumeration.Symbol.DOT;
import static org.wangpai.mathlab.advanced.numeric.basic.enumeration.Symbol.FIVE;
import static org.wangpai.mathlab.advanced.numeric.basic.enumeration.Symbol.NEGATIVE;
import static org.wangpai.mathlab.advanced.numeric.basic.enumeration.Symbol.ONE;
import static org.wangpai.mathlab.advanced.numeric.basic.enumeration.Symbol.THREE;
import static org.wangpai.mathlab.advanced.numeric.basic.enumeration.Symbol.TWO;
import static org.wangpai.mathlab.advanced.numeric.basic.enumeration.Symbol.ZERO;

public class DecimalTest {
    @Test
    void init_double() {
        assertEquals(new Decimal(0.00000001234), new Decimal("0.00000001234"));
        assertEquals(new Decimal(1.234), new Decimal("1.234"));
        assertEquals(new Decimal(123400000), new Decimal("123400000"));
        assertEquals(new Decimal(123400000.5678), new Decimal("123400000.5678"));
    }

    /**
     * @since 2021-8-2
     */
    @Test
    public void preInitCheck() {
        Symbol[] zero = {ZERO}; // 0
        Symbol[] positive = {ONE, TWO, THREE}; // 正数
        Symbol[] negative = {NEGATIVE, ONE, TWO, THREE}; // 负数
        Symbol[] properFraction = {ZERO, DOT, ONE, TWO, THREE}; // 真分数
        Symbol[] decimal = {NEGATIVE, ONE, DOT, TWO, THREE}; // 小数

        assertDoesNotThrow(() -> Decimal.preInitCheck(zero));
        assertDoesNotThrow(() -> Decimal.preInitCheck(positive));
        assertDoesNotThrow(() -> Decimal.preInitCheck(negative));
        assertDoesNotThrow(() -> Decimal.preInitCheck(properFraction));
        assertDoesNotThrow(() -> Decimal.preInitCheck(decimal));

        Symbol[] wrongFirst = {ADD, ONE, TWO, THREE}; // 第一个为加号，错误
        Symbol[] doubleSubtract = {NEGATIVE, ONE, NEGATIVE, TWO, THREE}; // 有两个减号，错误
        Symbol[] innerSubtract = {ONE, NEGATIVE, TWO, THREE}; // 中间有减号，错误
        Symbol[] doublePoint = {DOT, ONE, DOT, TWO, THREE}; // 有两个减号，错误
        Symbol[] beginDot = {DOT, ONE, TWO, DOT, THREE}; // 小数点位置错误

        assertThrows(SyntaxUncheckedException.class, () -> Decimal.preInitCheck(wrongFirst));
        assertThrows(SyntaxUncheckedException.class, () -> Decimal.preInitCheck(doubleSubtract));
        assertThrows(SyntaxUncheckedException.class, () -> Decimal.preInitCheck(innerSubtract));
        assertThrows(SyntaxUncheckedException.class, () -> Decimal.preInitCheck(doublePoint));
        assertThrows(SyntaxUncheckedException.class, () -> Decimal.preInitCheck(beginDot));
        assertThrows(SyntaxUncheckedException.class, () -> Decimal.preInitCheck(null));
    }

    @Test
    public void toRational() throws MathlabCheckedException {
        Symbol[] zero = {ZERO}; // 0
        Symbol[] lessThanZero = {ZERO, DOT, FIVE}; // 0.5
        Symbol[] largerThanZero = {ONE, DOT, FIVE}; // 1.5
        Symbol[] lessThanZeroNegative = {NEGATIVE, ZERO, DOT, FIVE, ZERO}; // -0.50
        Symbol[] largerThanZeroNegative = {NEGATIVE, ONE, DOT, FIVE}; // -1.5

        assertEquals(new Rational(0),
                new Decimal(zero).toRational());
        assertEquals(new Rational(1, 2),
                new Decimal(lessThanZero).toRational());
        assertEquals(new Rational(3, 2),
                new Decimal(largerThanZero).toRational());
        assertEquals(new Rational(-1, 2),
                new Decimal(lessThanZeroNegative).toRational());
        assertEquals(new Rational(-3, 2),
                new Decimal(largerThanZeroNegative).toRational());
    }

    @Test
    void toString_test() {
        assertEquals("0", new Decimal("0").toString());
        assertEquals("-1", new Decimal("-1").toString());
        assertEquals("123456", new Decimal("123456").toString());
        assertEquals("123456.23432", new Decimal("123456.23432").toString());
        assertEquals("-123456.23432", new Decimal("-123456.23432").toString());
    }

    @Test
    void isZero() {
        assertTrue(new Decimal("0").isZero());
        assertFalse(new Decimal("-1").isZero());
        assertFalse(new Decimal("1").isZero());
        assertFalse(new Decimal("123456.234320").isZero());
        assertFalse(new Decimal("-123456.23432").isZero());
    }

    @Test
    void isPositive() {
        assertTrue(new Decimal("1").isPositive());
        assertTrue(new Decimal("123456.23432").isPositive());
        assertFalse(new Decimal("-123456.23432").isPositive());
        assertFalse(new Decimal("0").isPositive());
        assertFalse(new Decimal("-1").isPositive());
    }

    @Test
    void isNegative() {
        assertTrue(new Decimal("-1").isNegative());
        assertTrue(new Decimal("-123456.23432").isNegative());
        assertFalse(new Decimal("0").isNegative());
        assertFalse(new Decimal("1").isNegative());
        assertFalse(new Decimal("123456.23432").isNegative());
    }

    @Test
    void clone_test() {
        var decimal = new Decimal("1");
        var cloned = decimal.clone();
        System.out.println("");
    }

    @Test
    void equals_Decimal() {
        assertEquals(new Decimal("0").toString(), new Decimal("0").toString());
        assertEquals(new Decimal("100").toString(), new Decimal("100").toString());
        assertEquals(new Decimal("-100").toString(), new Decimal("-100").toString());
        assertNotEquals(new Decimal("-1").toString(), new Decimal("0").toString());
        assertNotEquals(new Decimal("1").toString(), new Decimal("-1").toString());
    }
}
