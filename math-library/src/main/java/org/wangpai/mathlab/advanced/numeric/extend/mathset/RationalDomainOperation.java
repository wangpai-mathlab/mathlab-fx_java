package org.wangpai.mathlab.advanced.numeric.extend.mathset;

import java.util.TreeSet;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Rational;
import org.wangpai.mathlab.advanced.numeric.basic.operation.RationalOperation;

/**
 * 集合之间的运算
 *
 * @since 2024-12-16
 */
public class RationalDomainOperation {
    /*---------------集合之间的运算---------------*/

    /**
     * 求并集。形参可交换顺序
     *
     * @since 2023-1-28
     */
    public static RationalDomain union(RationalDomain a, RationalDomain b) {
        var aSet = a.toSetSafely();
        var bSet = b.toSetSafely();
        aSet.addAll(bSet);
        return RationalDomain.getInstanceFrugally((TreeSet<Rational>) aSet);
    }

    /**
     * 求交集。形参可交换顺序
     *
     * @since 2023-1-28
     */
    public static RationalDomain intersect(RationalDomain a, RationalDomain b) {
        var aSizes = a.size();
        var bSizes = b.size();
        // min、max 不会相等
        RationalDomain min = aSizes < bSizes ? a : b;
        RationalDomain max = aSizes < bSizes ? b : a;

        // 将较长的集合转换为 Set
        var maxSet = max.toSetSafely();

        var result = new RationalDomain();
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
    public static RationalDomain subtract(RationalDomain a, RationalDomain b) {
        var aSet = a.toSetSafely();
        var bSet = b.toSetFrugally(); // 此处不需要使用安全方法
        for (var ele : bSet) {
            aSet.remove(ele);
        }
        return RationalDomain.getInstanceFrugally((TreeSet<Rational>) aSet);
    }

    /**
     * 求 a、b 的差集。返回只在 a 存在或在 b 存在的元素。形参可交换顺序
     *
     * 两个集合的差集等于两个集合的并集减去它们的交集，也等于 (a - b) 与 (b - a) 的并集
     *
     * @since 2023-1-28
     */
    public static RationalDomain diff(RationalDomain a, RationalDomain b) {
        var aSizes = a.size();
        var bSizes = b.size();
        // min、max 不会相等
        RationalDomain min = aSizes < bSizes ? a : b;
        RationalDomain max = aSizes < bSizes ? b : a;

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
        return RationalDomain.getInstanceFrugally((TreeSet<Rational>) maxSet);
    }

    /**
     * 判断 a 是否包含 b。不考虑 a、b 中的元素顺序
     *
     * @since 2023-1-28
     */
    public static boolean contain(RationalDomain a, RationalDomain b) {
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

    /**
     * 将集合 rationalDomain 中的每个元素都加 rational
     *
     * @since 2023-1-28
     */
    public static RationalDomain add(RationalDomain rationalDomain, Rational rational) {
        var set = rationalDomain.toSetFrugally();
        var result = new RationalDomain();
        for (var ele : set) {
            result.addFrugally(RationalOperation.add(ele, rational));
        }
        return result;
    }

    /**
     * 将集合 rationalDomain 中的每个元素都减去 rational
     *
     * @since 2023-1-28
     */
    public static RationalDomain subtract(RationalDomain rationalDomain, Rational rational) {
        var set = rationalDomain.toSetFrugally();
        var result = new RationalDomain();
        for (var ele : set) {
            result.addFrugally(RationalOperation.subtract(ele, rational));
        }
        return result;
    }

    /**
     * 将集合 rationalDomain 中的每个元素都乘以 rational
     *
     * @since 2023-1-28
     */
    public static RationalDomain multiply(RationalDomain rationalDomain, Rational rational) {
        var set = rationalDomain.toSetFrugally();
        var result = new RationalDomain();
        for (var ele : set) {
            result.addFrugally(RationalOperation.multiply(ele, rational));
        }
        return result;
    }

    /**
     * 将集合 rationalDomain 中的每个元素都除以 rational
     *
     * @since 2025-2-19
     */
    public static RationalDomain divide(RationalDomain rationalDomain, Rational rational) {
        var set = rationalDomain.toSetFrugally();
        var result = new RationalDomain();
        for (var ele : set) {
            result.addFrugally(RationalOperation.divide(ele, rational));
        }
        return result;
    }
}
