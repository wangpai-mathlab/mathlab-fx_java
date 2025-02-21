package org.wangpai.mathlab.builtin.arrangement;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 此类是一个示例类，提供的是供复制、手动修改的代码，一般不能直接引用本类的 API，所以本类对外不可见
 *
 * 实现“排列组合”中，排列选择操作
 *
 * @since 2023-1-10
 */
class ArrangementProcess {
    /**
     * @param arrangementSource 输入序列。假设 arrangementSource 中的所有元素都是可选元素，元素下标从 0 开始
     * @param singleArrangement 其中的一条输出序列。singleArrangement.length 表示需要选取的元素个数
     * @param arrangements 最终的输出序列。如果不需要方法返回值输出，此形参可去掉
     * @param nextOrder 表示等下即将生成的是组合数第 nextOrder + 1 个元素
     *              nextOrder 范围：[0, arrangementSource.length)。nextOrder 初始值应为 0
     * @since 2023-1-10
     */
    public static void arrangement0(byte[] arrangementSource, int nextOrder,
                                    byte[] singleArrangement, LinkedList<byte[]> arrangements) {
        if (nextOrder == singleArrangement.length) {
            arrangements.add(Arrays.copyOf(singleArrangement, singleArrangement.length));
            System.out.println(Arrays.toString(singleArrangement)); // TODO：此处可替换成具体的业务
            return; // 完成一次排列的选取
        }

        for (int sourceIndex = 0; sourceIndex < arrangementSource.length; ++sourceIndex) {
            boolean repeated = false;
            // 判断下一个将要选择的元素在之前有没有被选择过。如果有，则这个元素直接跳过
            for (int resultIndex = 0; resultIndex < nextOrder; ++resultIndex) {
                if (arrangementSource[sourceIndex] == singleArrangement[resultIndex]) {
                    repeated = true;
                    break;
                }
            }
            if (!repeated) {
                singleArrangement[nextOrder] = arrangementSource[sourceIndex]; // TODO：此处可替换成具体的业务
                arrangement0(arrangementSource, nextOrder + 1, singleArrangement, arrangements);
            }
        }
    }

    /**
     * @param arrangementSource 输入序列。假设 arrangementSource 中的所有元素都是可选元素，元素下标从 0 开始
     * @param choiceNum 要在 arrangementSource 中选择的元素个数，choiceNum 范围：[0, arrangementSource.length]。
     *            choiceNum 只能使用 int 类型，因为 choiceNum 会成为数组下标
     * @since 2023-1-10
     */
    public static List<byte[]> arrangement(byte[] arrangementSource, int choiceNum) {
        byte[] singleArrangement = new byte[choiceNum];
        LinkedList<byte[]> arrangements = new LinkedList<>();
        arrangement0(arrangementSource, 0, singleArrangement, arrangements);
        return arrangements;
    }

    public static void main(String[] args) {
        /**
         * arrangementSource[x] 指的是每个元素的编号
         *
         * 为了防止溢出（当基数很大的时候，溢出是很常见的事情。如果 arrangementSource.length 超出 80，
         * 运行时产生的数据很容量达到 GB 级别），所以必须先对元素进行编码，从而使得元素编号 arrangementSource[x] 的数据类型尽量更小，
         * 直至等于 log2(arrangementSource.length) 比特。
         * 这里，arrangementSource[x] 为 byte 的类型，byte 大小为 1 字节，
         * 所以能允许 x 最大为 255。因为这里只需要 9 个元素，所以只需要使用 byte 数组。
         * 设 arrangementSource 数组的每个元素所占的比特数为 x，arrangementSource 数组的元素个数为 y。它们的关系为：2^x=y，x=log2(y)
         *
         * 如果不考虑运行空间效率，则 arrangementSource[x] 可以是任意类型，也可以是任意值（不需要连续整数，甚至可以为任意对象）
         */
        byte[] arrangementSource = {10, 20, 30, 40, 50, 60, 70, 80, 90};
        int num = 5; // 要选取的元素个数
        System.out.println("------------ 输出办法 1：方法内部输出--------------");
        List<byte[]> arrangements = arrangement(arrangementSource, num);

        System.out.println(System.lineSeparator() + "------------ 输出办法 2：方法返回值输出--------------");
        for (byte[] singleArrangement : arrangements) {
            System.out.println(Arrays.toString(singleArrangement));
        }
    }
}
