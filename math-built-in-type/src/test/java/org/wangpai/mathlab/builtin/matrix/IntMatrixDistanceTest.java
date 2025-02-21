package org.wangpai.mathlab.builtin.matrix;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class IntMatrixDistanceTest {

    @Test
    void distance() {
        int[] first = new int[]{1, 2, 3, 4, 5};
        int[] second = new int[]{1, 2, 3, 4, 5};
        var distance = IntMatrixDistance.distance(first, second);
        System.out.println(IntMatrixUtil.matrixToString(distance));
    }

    @Test
    void squareDistance() {
        int[] first = new int[]{1, 2, 3, 4, 5};
        int[] second = new int[]{1, 2, 3, 4, 5};
        var distance = IntMatrixDistance.squareDistance(first, second);
        System.out.println(IntMatrixUtil.matrixToString(distance));
    }

    @Test
    void correspondingDistanceWithNoCheck() {
        int[] first = new int[]{0, 1, 2, 3, 4};
        int[] second = new int[]{1, 2, 3, 4, 5};
        var distance = IntMatrixDistance.correspondingDistanceWnc(first, second);
        System.out.println(Arrays.toString(distance));
    }

    @Test
    void correspondingSquareDistanceWithNoCheck() {
        int[] first = new int[]{0, 11, 12, 13, 14};
        int[] second = new int[]{1, 2, 3, 4, 5};
        var distance = IntMatrixDistance.correspondingSquareDistanceWnc(first, second);
        System.out.println(Arrays.toString(distance));
    }

    @Test
    void minDistance_intArray_intArray() {
        int[] first = new int[]{0, 11, -12, 13, -14};
        int[] second = new int[]{1, -2, 3, -4, -5};
        var distance = IntMatrixDistance.minDistance(first, second);
        System.out.println(Arrays.toString(distance));
    }

    @Test
    void minDistance_intArray() {
        int[] first = new int[]{0, 11, -12, 13, -14};
        var distance = IntMatrixDistance.minDistance(first);
        System.out.println(Arrays.toString(distance));
    }

    @Test
    void maxDistance_int_intArray() {
        int[] array = new int[]{0, 11, -12, 13, -14};
        System.out.println(IntMatrixDistance.maxDistance(0, array));
    }

    @Test
    void maxDistance_intArray() {
        int[] first = new int[]{0, 11, -12, 13, -14};
        var distance = IntMatrixDistance.maxDistance(first);
        System.out.println(Arrays.toString(distance));
    }
}