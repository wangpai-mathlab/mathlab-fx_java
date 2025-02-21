package org.wangpai.mathlab.builtin.matrix;

import java.util.ArrayList;
import org.wangpai.commonutil.tc.collection.BasicArrayTc;
import org.wangpai.commonutil.tc.collection.CollectionTc;

/**
 * @since 2023-1-1
 */
public class IntMathSetUtil {
    /**
     * 求并集。形参可交换顺序
     *
     * @since 2023-1-6
     */
    public static int[] union(int[] a, int[] b) {
        var aSet = CollectionTc.intArray2Set(a);
        var bSet = CollectionTc.intArray2Set(b);
        aSet.addAll(bSet);
        return BasicArrayTc.integerArray2IntArray(aSet.toArray(Integer[]::new));
    }

    /**
     * 求交集。形参可交换顺序
     *
     * @since 2023-1-6
     */
    public static int[] intersect(int[] a, int[] b) {
        // minArray、maxArray 不会相等
        int[] minArray = a.length < b.length ? a : b;
        int[] maxArray = a.length < b.length ? b : a;

        // 将较长的数组转换为 Set
        var maxSet = CollectionTc.intArray2Set(maxArray);

        var result = new ArrayList<Integer>(minArray.length);
        // 遍历较短的数组
        for (var ele : minArray) {
            if (maxSet.contains(ele)) {
                result.add(ele);
            }
        }
        return BasicArrayTc.integerArray2IntArray(result.toArray(Integer[]::new));
    }

    /**
     * 求 a - b。返回在 a 存在，但在 b 中不存在的元素，因此 subtract(a, b) 与 subtract(b, a) 是不同的
     *
     * @since 2023-1-1
     */
    public static int[] subtract(int[] a, int[] b) {
        var set = CollectionTc.intArray2Set(a);
        for (var ele : b) {
            set.remove(ele);
        }
        return BasicArrayTc.integerArray2IntArray(set.toArray(Integer[]::new));
    }

    /**
     * 求 a、b 的差集。返回只在 a 存在或在 b 存在的元素。形参可交换顺序
     *
     * 两个集合的差集等于两个集合的并集减去它们的交集，也等于 (a - b) 与 (b - a) 的并集
     *
     * @since 2023-1-6
     */
    public static int[] diff(int[] a, int[] b) {
        // minArray、maxArray 不会相等
        int[] minArray = a.length < b.length ? a : b;
        int[] maxArray = a.length < b.length ? b : a;

        // 将较长的数组转换为 Set
        var maxSet = CollectionTc.intArray2Set(maxArray);

        // 遍历较短的数组
        for (var ele : minArray) {
            if (maxSet.contains(ele)) {
                maxSet.remove(ele);
            } else {
                maxSet.add(ele);
            }
        }
        return BasicArrayTc.integerArray2IntArray(maxSet.toArray(Integer[]::new));
    }

    /**
     * 判断 a 是否包含 b。不考虑 a、b 中的元素顺序
     *
     * @since 2023-1-7
     */
    public static boolean contain(int[] a, int[] b) {
        if (b.length > a.length) {
            return false;
        }
        var aSet = CollectionTc.intArray2Set(a);
        for (var ele : b) {
            if (!aSet.contains(ele)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断 a 是否包含 b
     *
     * @since 2023-3-9
     */
    public static boolean contain(int[] a, int b) {
        var aSet = CollectionTc.intArray2Set(a);
        return aSet.contains(b);
    }
}
