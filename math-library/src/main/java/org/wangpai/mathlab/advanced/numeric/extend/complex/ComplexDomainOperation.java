package org.wangpai.mathlab.advanced.numeric.extend.complex;

import java.util.HashSet;

/**
 * 集合之间的运算
 *
 * @since 2023-1-30
 */
public class ComplexDomainOperation {
    /*---------------集合之间的运算---------------*/

    /**
     * 求并集。形参可交换顺序
     *
     * @since 2023-1-28
     */
    public static ComplexDomain union(ComplexDomain a, ComplexDomain b) {
        var aSet = a.toSetSafely();
        var bSet = b.toSetSafely();
        aSet.addAll(bSet);
        return ComplexDomain.getInstanceFrugally((HashSet<Complex>) aSet);
    }

    /**
     * 求交集。形参可交换顺序
     *
     * @since 2023-1-28
     */
    public static ComplexDomain intersect(ComplexDomain a, ComplexDomain b) {
        var aSizes = a.size();
        var bSizes = b.size();
        // min、max 不会相等
        ComplexDomain min = aSizes < bSizes ? a : b;
        ComplexDomain max = aSizes < bSizes ? b : a;

        // 将较长的集合转换为 Set
        var maxSet = max.toSetSafely();

        var result = new ComplexDomain();
        // 遍历较短的集合
        var minSet = min.toSetSafely();
        for (var ele : minSet) {
            if (maxSet.contains(ele)) {
                result.addFrugally(ele); // 此处不需要使用安全方法，因为前面的安全方法已经保证了安全
            }
        }
        return result;
    }

    /**
     * 求 a - b。返回在 a 存在，但在 b 中不存在的元素，因此 subtract(a, b) 与 subtract(b, a) 是不同的
     *
     * @since 2023-1-28
     */
    public static ComplexDomain subtract(ComplexDomain a, ComplexDomain b) {
        var aSet = a.toSetSafely();
        var bSet = b.toSetFrugally(); // 此处不需要使用安全方法
        for (var ele : bSet) {
            aSet.remove(ele);
        }
        return ComplexDomain.getInstanceFrugally((HashSet<Complex>) aSet);
    }

    /**
     * 求 a、b 的差集。返回只在 a 存在或在 b 存在的元素。形参可交换顺序
     *
     * 两个集合的差集等于两个集合的并集减去它们的交集，也等于 (a - b) 与 (b - a) 的并集
     *
     * @since 2023-1-28
     */
    public static ComplexDomain diff(ComplexDomain a, ComplexDomain b) {
        var aSizes = a.size();
        var bSizes = b.size();
        // min、max 不会相等
        ComplexDomain min = aSizes < bSizes ? a : b;
        ComplexDomain max = aSizes < bSizes ? b : a;

        // 将较长的集合转换为 Set
        var maxSet = max.toSetSafely();

        // 遍历较短的集合
        var minSet = min.toSetSafely();
        for (var ele : minSet) {
            if (maxSet.contains(ele)) {
                maxSet.remove(ele);
            } else {
                maxSet.add(ele);
            }
        }
        return ComplexDomain.getInstanceFrugally((HashSet<Complex>) maxSet);
    }

    /**
     * 判断 a 是否包含 b。不考虑 a、b 中的元素顺序
     *
     * @since 2023-1-28
     */
    public static boolean contain(ComplexDomain a, ComplexDomain b) {
        if (b.size() > a.size()) {
            return false;
        }
        var aSet = a.toSetFrugally();
        var bSet = b.toSetFrugally();
        for (var ele : bSet) {
            if (!aSet.contains(ele)) {
                return false;
            }
        }
        return true;
    }

    /****************集合之间的运算****************/
}
