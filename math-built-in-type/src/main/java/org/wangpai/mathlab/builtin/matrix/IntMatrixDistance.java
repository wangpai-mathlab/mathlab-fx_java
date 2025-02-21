package org.wangpai.mathlab.builtin.matrix;

import org.wangpai.exception.unchecked.LogicalException;

/**
 * @since 2022-12-8
 */
public class IntMatrixDistance {
    /**
     * 计算 first 与 second 之间的距离。first 与 second 的长度可不相等
     *
     * @return [i][j] 表示 first[i] 与 second[j] 的距离
     * @since 2022-12-8
     */
    public static int[][] distance(int[] first, int[] second) {
        var result = new int[first.length][second.length];
        for (int row = 0; row < result.length; ++row) {
            for (int column = 0; column < result[0].length; ++column) {
                result[row][column] = first[row] - second[column];
            }
        }
        return result;
    }

    /**
     * 计算 first 与 second 之间距离的绝对值。first 与 second 的长度可不相等
     *
     * @return [i][j] 表示 first[i] 与 second[j] 的距离
     * @since 2022-12-8
     */
    public static int[][] absDistance(int[] first, int[] second) {
        var result = new int[first.length][second.length];
        for (int row = 0; row < result.length; ++row) {
            for (int column = 0; column < result[0].length; ++column) {
                result[row][column] = Math.abs(first[row] - second[column]);
            }
        }
        return result;
    }

    /**
     * 计算 first 与 second 之间的平方距离
     *
     * @return [i][j] 表示 first[i] 与 second[j] 的平方距离
     * @since 2022-12-8
     */
    public static int[][] squareDistance(int[] first, int[] second) {
        var result = new int[first.length][second.length];
        for (int row = 0; row < result.length; ++row) {
            for (int column = 0; column < result[0].length; ++column) {
                int diff = first[row] - second[column];
                result[row][column] = diff * diff;
            }
        }
        return result;
    }

    /**
     * 计算 first 与 second 对应元素之间的距离
     *
     * 此处 first 与 second 的长度需相等
     *
     * @return [i] 表示 first[i] 与 second[i] 的距离
     * @since 2022-12-8
     */
    public static int[] correspondingDistance(int[] first, int[] second) {
        if (first != second) {
            throw new LogicalException("异常：first 与 second 的长度需相等");
        }
        return correspondingDistanceWnc(first, second);
    }

    /**
     * 计算 first 与 second 对应元素之间的距离
     *
     * 此处 first 与 second 的长度需相等，但本方法不会检查这一点
     *
     * WNC：with no check
     *
     * @return [i] 表示 first[i] 与 second[i] 的距离
     * @since 2022-12-8
     */
    public static int[] correspondingDistanceWnc(int[] first, int[] second) {
        var result = new int[first.length];
        for (int index = 0; index < result.length; ++index) {
            result[index] = Math.abs(first[index] - second[index]);
        }
        return result;
    }

    /**
     * 计算 first 与 second 对应元素之间的平方距离
     *
     * 此处 first 与 second 的长度需相等
     *
     * @return [i] 表示 first[i] 与 second[i] 的平方距离
     * @since 2022-12-8
     */
    public static int[] correspondingSquareDistance(int[] first, int[] second) {
        if (first != second) {
            throw new LogicalException("异常：first 与 second 的长度需相等");
        }
        return correspondingSquareDistanceWnc(first, second);
    }

    /**
     * 计算 first 与 second 对应元素之间的平方距离
     *
     * 此处 first 与 second 的长度需相等，但本方法不会检查这一点
     *
     * WNC：with no check
     *
     * @return [i] 表示 first[i] 与 second[i] 的平方距离
     * @since 2022-12-8
     */
    public static int[] correspondingSquareDistanceWnc(int[] first, int[] second) {
        var result = new int[first.length];
        for (int index = 0; index < result.length; ++index) {
            int diff = first[index] - second[index];
            result[index] = diff * diff;
        }
        return result;
    }

    /**
     * 计算 target 对 reference 的绝对值最小距离。距离可以是负的，但比较时是按照绝对值进行比较的
     *
     * 规定：一个数对参考数组的最小距离指的是这个数与参考数组中的每个数的距离的最小值
     *
     * 注意：交换 target 与 reference 的结果是不一样的
     *
     * @return [i] 表示 target[i] 与 reference 的最小距离
     * @since 2022-12-24
     */
    public static int[] minDistance(int[] target, int[] reference) {
        var result = new int[target.length];
        for (int index = 0; index < result.length; ++index) {
            result[index] = minDistance(target[index], reference);
        }
        return result;
    }

    /**
     * 计算 target 对 reference 的绝对值最小距离。距离可以是负的，但比较时是按照绝对值进行比较的
     *
     * 规定：一个数对参考数组的最小距离指的是这个数与参考数组中的每个数的距离的最小值
     *
     * @since 2022-12-25
     */
    public static int minDistance(int target, int[] reference) {
        int min = Integer.MAX_VALUE;
        int absMin = Integer.MAX_VALUE;
        for (var ref : reference) {
            int distance = target - ref;
            int absDistance = Math.abs(distance);
            if (absDistance < absMin) {
                min = distance;
                absMin = absDistance;
            }
        }
        return min;
    }

    /**
     * 计算 target 对 reference 的绝对值最大距离。距离可以是负的，但比较时是按照绝对值进行比较的
     *
     * 规定：一个数对参考数组的最大距离指的是这个数与参考数组中的每个数的距离的最大值
     *
     * 注意：交换 target 与 reference 的结果是不一样的
     *
     * @return [i] 表示 target[i] 与 reference 的最大距离
     * @since 2023-1-9
     */
    public static int[] maxDistance(int[] target, int[] reference) {
        var result = new int[target.length];
        for (int index = 0; index < result.length; ++index) {
            result[index] = maxDistance(target[index], reference);
        }
        return result;
    }

    /**
     * 计算 target 对 reference 的绝对值最大距离。距离可以是负的，但比较时是按照绝对值进行比较的
     *
     * 规定：一个数对参考数组的最大距离指的是这个数与参考数组中的每个数的距离的最大值
     *
     * @since 2022-12-25
     */
    public static int maxDistance(int target, int[] reference) {
        int max = Integer.MIN_VALUE;
        int absMax = 0;
        for (var ref : reference) {
            int distance = target - ref;
            int absDistance = Math.abs(distance);
            if (absDistance > absMax) {
                max = distance;
                absMax = absDistance;
            }
        }
        return max;
    }

    /**
     * 计算 target 对自身的绝对值最小距离。距离可以是负的，但比较时是按照绝对值进行比较的
     *
     * 规定：一个数对自身所在数组的最小距离指的是这个数与该数组中除它以外的每个数的距离的最小值
     *
     * 此值求和的绝对值反映了数据两两成对的程度（求和绝对值越接近 0，这种程度越强）
     *
     * @return [i] 表示 target[i] 与 target 的最小距离
     * @since 2023-1-3
     */
    public static int[] minDistance(int[] target) {
        var result = new int[target.length];
        for (int index = 0; index < result.length; ++index) {
            result[index] = minDistance(target[index], IntMatrixUtil.removeElement(target, index));
        }
        return result;
    }

    /**
     * 计算 target 对自身的绝对值最大距离。距离可以是负的，但比较时是按照绝对值进行比较的
     *
     * 规定：一个数对自身所在数组的最大距离指的是这个数与该数组中除它以外的每个数的距离的最大值
     *
     * 此值求和的绝对值反映了数据关于自身轴对称的程度（求和绝对值越接近 0，这种程度越强）
     *
     * @return [i] 表示 target[i] 与 target 的最大距离
     * @since 2023-1-3
     */
    public static int[] maxDistance(int[] target) {
        var result = new int[target.length];
        for (int index = 0; index < result.length; ++index) {
            result[index] = maxDistance(target[index], IntMatrixUtil.removeElement(target, index));
        }
        return result;
    }
}
