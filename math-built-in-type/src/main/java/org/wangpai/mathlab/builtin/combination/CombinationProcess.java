package org.wangpai.mathlab.builtin.combination;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 此类是一个示例类，提供的是供复制、手动修改的代码，一般不能直接引用本类的 API，所以本类对外不可见
 *
 * 实现“排列组合”中，组合选择操作
 *
 * @since 2022-10-22
 */
class CombinationProcess {
    /**
     * @param combinationSource 输入序列。假设 combinationSource 中的所有元素都是可选元素，元素下标从 0 开始
     * @param sourceStart 表示等下即将生成的值源自 combinationSource[sourceStart]。
     *              也就是说，combinationSource[sourceStart] 将是本次递归的起点，
     *              也即 combinationSource[sourceStart] 之前的元素属于已选元素
     *              nextOrder 范围：[0, combinationSource.length)。nextOrder 初始值应为 0
     * @param singleCombination 其中的一条输出序列。singleCombination.length 表示需要选取的元素个数
     * @param nextOrder 表示等下即将生成的是组合数第 nextOrder + 1 个元素
     *              nextOrder 范围：[0, combinationSource.length)。nextOrder 初始值应为 0
     * @param combinations 最终的输出序列。如果不需要方法返回值输出，此形参可去掉
     * @since 2022-10-21
     */
    public static void combination0(byte[] combinationSource, int sourceStart,
                                    byte[] singleCombination, int nextOrder,
                                    LinkedList<byte[]> combinations) {
        if (nextOrder == singleCombination.length) {
            combinations.add(Arrays.copyOf(singleCombination, singleCombination.length));
            System.out.println(Arrays.toString(singleCombination)); // TODO：此处可替换成具体的业务
            return; // 完成一次组合的选取
        }

        /**
         * 此代码是为了防止选到后面时，发生元素不够选的情况。
         * 如：对于 10 选 4 来说，如果第 1 个元素选择的序号为 7，这将导致第 4 个元素选择的序号为 10，这就导致第 4 个元素的选择越界。
         * 但实际上，此处也可直接使用 end = combinationSource.length。因为如果后续元素不够选，则后续元素的 sourceStart 序号将越界，
         * 而这里有越界判断，所以结果不会有问题。但区别在于，使用本代码可以在第 1 个元素选择之前就判断出是否越界，
         * 而使用 combinationSource.length 则是在后面那个越界的元素到来时才知道是否越界，因此效率低一些
         */
        int end = combinationSource.length - singleCombination.length + nextOrder;
        // index 只能使用 int 类型，因为 choiceNum 会成为数组下标
        for (int index = sourceStart; index <= end; ++index) {
            singleCombination[nextOrder] = combinationSource[index]; // TODO：此处可替换成具体的业务
            combination0(combinationSource, index + 1, singleCombination, nextOrder + 1, combinations);
        }
    }

    /**
     * @param combinationSource 输入序列。假设 combinationSource 中的所有元素都是可选元素，元素下标从 0 开始
     * @param choiceNum 要在 combinationSource 中选择的元素个数，choiceNum 范围：[0, combinationSource.length]。
     *            choiceNum 只能使用 int 类型，因为 choiceNum 会成为数组下标
     * @since 2022-10-22
     */
    public static List<byte[]> combination(byte[] combinationSource, int choiceNum) {
        byte[] singleCombination = new byte[choiceNum];
        LinkedList<byte[]> combinations = new LinkedList<>();
        combination0(combinationSource, 0, singleCombination, 0, combinations);
        return combinations;
    }

    public static void main(String[] args) {
        /**
         * combinationSource[x] 指的是每个元素的编号
         *
         * 为了防止溢出（当基数很大的时候，溢出是很常见的事情。如果 combinationSource.length 超出 100，
         * 运行时产生的数据很容量达到 GB 级别），所以必须先对元素进行编码，从而使得元素编号 combinationSource[x] 的数据类型尽量更小，
         * 直至等于 log2(combinationSource.length) 比特。
         * 这里，combinationSource[x] 为 byte 的类型，byte 大小为 1 字节，
         * 所以能允许 x 最大为 255。因为这里只需要 9 个元素，所以只需要使用 byte 数组。
         * 设 combinationSource 数组的每个元素所占的比特数为 x，combinationSource 数组的元素个数为 y。它们的关系为：2^x=y，x=log2(y)
         *
         * 如果不考虑运行空间效率，则 combinationSource[x] 可以是任意类型，也可以是任意值（不需要连续整数，甚至可以为任意对象）
         */
        byte[] combinationSource = {10, 20, 30, 40, 50, 60, 70, 80, 90};
        int num = 5; // 要选取的元素个数
        System.out.println("------------ 输出办法 1：方法内部输出--------------");
        List<byte[]> combinations = combination(combinationSource, num);

        System.out.println(System.lineSeparator() + "------------ 输出办法 2：方法返回值输出--------------");
        for (byte[] singleCombination : combinations) {
            System.out.println(Arrays.toString(singleCombination));
        }
    }
}
