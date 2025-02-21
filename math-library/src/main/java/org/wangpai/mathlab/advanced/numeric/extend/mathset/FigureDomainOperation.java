package org.wangpai.mathlab.advanced.numeric.extend.mathset;

import java.util.TreeSet;
import org.wangpai.exception.unchecked.ForbiddenCallingException;
import org.wangpai.mathlab.advanced.numeric.basic.operand.Figure;
import org.wangpai.mathlab.advanced.numeric.basic.operation.FigureOperation;

/**
 * 集合之间的运算
 *
 * @since 2023-1-28
 */
public class FigureDomainOperation {
    /*---------------集合之间的运算---------------*/

    /**
     * 求并集。形参可交换顺序
     *
     * @since 2023-1-28
     */
    public static FigureDomain union(FigureDomain a, FigureDomain b) {
        var aSet = a.toSetSafely();
        var bSet = b.toSetSafely();
        aSet.addAll(bSet);
        return FigureDomain.getInstanceFrugally((TreeSet<Figure>) aSet);
    }

    /**
     * 求交集。形参可交换顺序
     *
     * @since 2023-1-28
     */
    public static FigureDomain intersect(FigureDomain a, FigureDomain b) {
        var aSizes = a.size();
        var bSizes = b.size();
        // min、max 不会相等
        FigureDomain min = aSizes < bSizes ? a : b;
        FigureDomain max = aSizes < bSizes ? b : a;

        // 将较长的集合转换为 Set
        var maxSet = max.toSetSafely();

        var result = new FigureDomain();
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
    public static FigureDomain subtract(FigureDomain a, FigureDomain b) {
        var aSet = a.toSetSafely();
        var bSet = b.toSetFrugally(); // 此处不需要使用安全方法
        for (var ele : bSet) {
            aSet.remove(ele);
        }
        return FigureDomain.getInstanceFrugally((TreeSet<Figure>) aSet);
    }

    /**
     * 求 a、b 的差集。返回只在 a 存在或在 b 存在的元素。形参可交换顺序
     *
     * 两个集合的差集等于两个集合的并集减去它们的交集，也等于 (a - b) 与 (b - a) 的并集
     *
     * @since 2023-1-28
     */
    public static FigureDomain diff(FigureDomain a, FigureDomain b) {
        var aSizes = a.size();
        var bSizes = b.size();
        // min、max 不会相等
        FigureDomain min = aSizes < bSizes ? a : b;
        FigureDomain max = aSizes < bSizes ? b : a;

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
        return FigureDomain.getInstanceFrugally((TreeSet<Figure>) maxSet);
    }

    /**
     * 判断 a 是否包含 b。不考虑 a、b 中的元素顺序
     *
     * @since 2023-1-28
     */
    public static boolean contain(FigureDomain a, FigureDomain b) {
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
     * 将集合 rationalDomain 中的每个元素都加 figure
     *
     * @since 2023-1-28
     */
    public static FigureDomain add(FigureDomain rationalDomain, Figure figure) {
        var set = rationalDomain.toSetFrugally();
        var result = new FigureDomain();
        for (var ele : set) {
            result.addFrugally(FigureOperation.add(ele, figure));
        }
        return result;
    }

    /**
     * 将集合 rationalDomain 中的每个元素都减去 figure
     *
     * @since 2023-1-28
     */
    public static FigureDomain subtract(FigureDomain rationalDomain, Figure figure) {
        var set = rationalDomain.toSetFrugally();
        var result = new FigureDomain();
        for (var ele : set) {
            result.addFrugally(FigureOperation.subtract(ele, figure));
        }
        return result;
    }

    /**
     * 将集合 rationalDomain 中的每个元素都乘以 figure
     *
     * @since 2023-1-28
     */
    public static FigureDomain multiply(FigureDomain rationalDomain, Figure figure) {
        var set = rationalDomain.toSetFrugally();
        var result = new FigureDomain();
        for (var ele : set) {
            result.addFrugally(FigureOperation.multiply(ele, figure));
        }
        return result;
    }

    /**
     * 除法。此方法禁止使用
     *
     * 占位空方法
     *
     * @since 2023-1-28
     * @deprecated 2023-1-28
     */
    @Deprecated
    public final static FigureDomain divide(FigureDomain rationalDomain, Figure figure) {
        throw new ForbiddenCallingException("错误：整数 FigureDomain 不支持除法运算");
    }
}
