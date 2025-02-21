package org.wangpai.mathlab.builtin.combination;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.wangpai.mathlab.builtin.encode.CombinationEncoding;

class CombinationEncodingTest {

    /**
     * @since 2022-11-17
     */
    @Test
    void encodeWithNoCheck() {
        /**
         * 实现“排列组合”中，组合选择操作
         *
         * 这个类必须放在方法的前部
         *
         * @since 2022-10-22
         */
        class Combination {
            /**
             * @param combinationSource 输入序列。假设 combinationSource 中的所有元素都是可选元素，元素下标从 0 开始
             * @param singleCombination 其中的一条输出序列。singleCombination.length 表示需要选取的元素个数
             * @param combinations 最终的输出序列。如果不需要方法返回值输出，此形参可去掉
             * @param nextOrder 表示等下即将生成的是组合数第 nextOrder + 1 个元素
             *              nextOrder 范围：[0, combinationSource.length)。nextOrder 初始值应为 0
             * @param sourceStart 表示等下即将生成的值源自 combinationSource[sourceStart]。
             *              也就是说，combinationSource[sourceStart] 将是本次递归的起点，
             *              也即 combinationSource[sourceStart] 之前的元素属于已选元素
             *              nextOrder 范围：[0, combinationSource.length)。nextOrder 初始值应为 0
             * @since 2022-10-21
             */
            public static void combination0(int[] combinationSource, int[] singleCombination, LinkedList<int[]> combinations,
                                            int nextOrder, int sourceStart) {
                if (nextOrder == singleCombination.length) {
//            combinations.add(Arrays.copyOf(singleCombination, singleCombination.length));
                    System.out.println(Arrays.toString(singleCombination) + "："
                            + CombinationEncoding.encodeWithNoCheck(combinationSource.length, singleCombination));
                    return; // 完成一次组合的选取
                }

                /**
                 * 此代码是为了防止选到后面时，发生元素不够选的情况。
                 * 如：对于 10 选 4 来说，如果第 1 个元素选择的序号为 7，这将导致第 4 个元素选择的序号为 10，这就导致第 4 个元素的选择越界。
                 * 但实际上，end 可由 combinationSource.length 来代替。因为如果后续元素不够选，则后续元素的 sourceStart 序号将越界，
                 * 而这里有越界判断，所以结果不会有问题。但区别在于，使用本代码可以在第 1 个元素选择之前就判断出是否越界，
                 * 而使用 combinationSource.length 则是在后面那个越界的元素到来时才知道是否越界，因此效率低一些
                 */
                int end = combinationSource.length - singleCombination.length + nextOrder;
                // index 不能使用 int 类型，因为 num 只能是正数，范围比 combinationSource[...] 大
                for (int index = sourceStart; index <= end; index++) {
                    singleCombination[nextOrder] = combinationSource[index]; // TODO：此处可替换成具体的业务
                    combination0(combinationSource, singleCombination, combinations, nextOrder + 1, index + 1);
                }
            }

            /**
             * @param combinationSource 输入序列。假设 combinationSource 中的所有元素都是可选元素，元素下标从 0 开始
             * @param choiceNum 要在 combinationSource 中选择的元素个数，choiceNum 范围：[0, combinationSource.length]。
             *            choiceNum 不能使用 int 类型，因为 choiceNum 只能是正数，范围比 combinationSource[...] 大
             * @since 2022-10-22
             */
            public static List<int[]> combination(int[] combinationSource, int choiceNum) {
                int[] singleCombination = new int[choiceNum];
                LinkedList<int[]> combinations = new LinkedList<>();
                combination0(combinationSource, singleCombination, combinations, 0, 0);
                return combinations;
            }
        }

        final long START_TIME = System.currentTimeMillis();

        int n = 10;
        int[] combinationSource = new int[n];
        for (int index = 0; index < n; ++index) {
            combinationSource[index] = index + 1;
        }
        int m = 4; // 要选取的元素个数

        Combination.combination(combinationSource, m);

        System.out.printf("******** 应用后台程序退出：%fs ********%n",
                (System.currentTimeMillis() - START_TIME) / 1000.0);
    }

    /**
     * @since 2022-11-26
     */
    @Test
    void decodeWithNoCheck() {
        /**
         * 实现“排列组合”中，组合选择操作
         *
         * 这个类必须放在方法的前部
         *
         * @since 2022-10-22
         */
        class Combination {
            /**
             * @param combinationSource 输入序列。假设 combinationSource 中的所有元素都是可选元素，元素下标从 0 开始
             * @param singleCombination 其中的一条输出序列。singleCombination.length 表示需要选取的元素个数
             * @param combinations 最终的输出序列。如果不需要方法返回值输出，此形参可去掉
             * @param nextOrder 表示等下即将生成的是组合数第 nextOrder + 1 个元素
             *              nextOrder 范围：[0, combinationSource.length)。nextOrder 初始值应为 0
             * @param sourceStart 表示等下即将生成的值源自 combinationSource[sourceStart]。
             *              也就是说，combinationSource[sourceStart] 将是本次递归的起点，
             *              也即 combinationSource[sourceStart] 之前的元素属于已选元素
             *              nextOrder 范围：[0, combinationSource.length)。nextOrder 初始值应为 0
             * @since 2022-10-21
             */
            public static void combination0(int[] combinationSource, int[] singleCombination, LinkedList<int[]> combinations,
                                            int nextOrder, int sourceStart) {
                if (nextOrder == singleCombination.length) {
//            combinations.add(Arrays.copyOf(singleCombination, singleCombination.length));
                    long encodeValue = CombinationEncoding.encodeWithNoCheck(combinationSource.length, singleCombination);
                    var decodeArray = CombinationEncoding.decodeWithNoCheck(
                            encodeValue, combinationSource.length, singleCombination.length);
                    System.out.println(Arrays.toString(singleCombination) + "："
                            + encodeValue + "："
                            + Arrays.toString(decodeArray) + "："
                            + Arrays.equals(singleCombination, decodeArray));
                    return; // 完成一次组合的选取
                }

                /**
                 * 此代码是为了防止选到后面时，发生元素不够选的情况。
                 * 如：对于 10 选 4 来说，如果第 1 个元素选择的序号为 7，这将导致第 4 个元素选择的序号为 10，这就导致第 4 个元素的选择越界。
                 * 但实际上，end 可由 combinationSource.length 来代替。因为如果后续元素不够选，则后续元素的 sourceStart 序号将越界，
                 * 而这里有越界判断，所以结果不会有问题。但区别在于，使用本代码可以在第 1 个元素选择之前就判断出是否越界，
                 * 而使用 combinationSource.length 则是在后面那个越界的元素到来时才知道是否越界，因此效率低一些
                 */
                int end = combinationSource.length - singleCombination.length + nextOrder;
                // index 不能使用 int 类型，因为 num 只能是正数，范围比 combinationSource[...] 大
                for (int index = sourceStart; index <= end; index++) {
                    singleCombination[nextOrder] = combinationSource[index]; // TODO：此处可替换成具体的业务
                    combination0(combinationSource, singleCombination, combinations, nextOrder + 1, index + 1);
                }
            }

            /**
             * @param combinationSource 输入序列。假设 combinationSource 中的所有元素都是可选元素，元素下标从 0 开始
             * @param choiceNum 要在 combinationSource 中选择的元素个数，choiceNum 范围：[0, combinationSource.length]。
             *            choiceNum 不能使用 int 类型，因为 choiceNum 只能是正数，范围比 combinationSource[...] 大
             * @since 2022-10-22
             */
            public static List<int[]> combination(int[] combinationSource, int choiceNum) {
                int[] singleCombination = new int[choiceNum];
                LinkedList<int[]> combinations = new LinkedList<>();
                combination0(combinationSource, singleCombination, combinations, 0, 0);
                return combinations;
            }
        }

        final long START_TIME = System.currentTimeMillis();

        int n = 4;
        int[] combinationSource = new int[n];
        for (int index = 0; index < n; ++index) {
            combinationSource[index] = index + 1;
        }
        int m = 2; // 要选取的元素个数

        Combination.combination(combinationSource, m);

        System.out.printf("******** 应用后台程序退出：%fs ********%n",
                (System.currentTimeMillis() - START_TIME) / 1000.0);
    }

    /**
     * @since 2022-11-17
     */
    @Test
    void encode() {
        /**
         * 实现“排列组合”中，组合选择操作
         *
         * 这个类必须放在方法的前部
         *
         * @since 2022-10-22
         */
        class Combination {
            /**
             * @param combinationSource 输入序列。假设 combinationSource 中的所有元素都是可选元素，元素下标从 0 开始
             * @param singleCombination 其中的一条输出序列。singleCombination.length 表示需要选取的元素个数
             * @param combinations 最终的输出序列。如果不需要方法返回值输出，此形参可去掉
             * @param nextOrder 表示等下即将生成的是组合数第 nextOrder + 1 个元素
             *              nextOrder 范围：[0, combinationSource.length)。nextOrder 初始值应为 0
             * @param sourceStart 表示等下即将生成的值源自 combinationSource[sourceStart]。
             *              也就是说，combinationSource[sourceStart] 将是本次递归的起点，
             *              也即 combinationSource[sourceStart] 之前的元素属于已选元素
             *              nextOrder 范围：[0, combinationSource.length)。nextOrder 初始值应为 0
             * @since 2022-10-21
             */
            public static void combination0(int[] combinationSource, int[] singleCombination, LinkedList<int[]> combinations,
                                            int nextOrder, int sourceStart) {
                if (nextOrder == singleCombination.length) {
//            combinations.add(Arrays.copyOf(singleCombination, singleCombination.length));
                    System.out.println(Arrays.toString(singleCombination) + "："
                            + CombinationEncoding.encode(combinationSource.length, singleCombination));
                    return; // 完成一次组合的选取
                }

                /**
                 * 此代码是为了防止选到后面时，发生元素不够选的情况。
                 * 如：对于 10 选 4 来说，如果第 1 个元素选择的序号为 7，这将导致第 4 个元素选择的序号为 10，这就导致第 4 个元素的选择越界。
                 * 但实际上，end 可由 combinationSource.length 来代替。因为如果后续元素不够选，则后续元素的 sourceStart 序号将越界，
                 * 而这里有越界判断，所以结果不会有问题。但区别在于，使用本代码可以在第 1 个元素选择之前就判断出是否越界，
                 * 而使用 combinationSource.length 则是在后面那个越界的元素到来时才知道是否越界，因此效率低一些
                 */
                int end = combinationSource.length - singleCombination.length + nextOrder;
                // index 不能使用 int 类型，因为 num 只能是正数，范围比 combinationSource[...] 大
                for (int index = sourceStart; index <= end; index++) {
                    singleCombination[nextOrder] = combinationSource[index]; // TODO：此处可替换成具体的业务
                    combination0(combinationSource, singleCombination, combinations, nextOrder + 1, index + 1);
                }
            }

            /**
             * @param combinationSource 输入序列。假设 combinationSource 中的所有元素都是可选元素，元素下标从 0 开始
             * @param choiceNum 要在 combinationSource 中选择的元素个数，choiceNum 范围：[0, combinationSource.length]。
             *            choiceNum 不能使用 int 类型，因为 choiceNum 只能是正数，范围比 combinationSource[...] 大
             * @since 2022-10-22
             */
            public static List<int[]> combination(int[] combinationSource, int choiceNum) {
                int[] singleCombination = new int[choiceNum];
                LinkedList<int[]> combinations = new LinkedList<>();
                combination0(combinationSource, singleCombination, combinations, 0, 0);
                return combinations;
            }
        }

        final long START_TIME = System.currentTimeMillis();

        int n = 10;
        int[] combinationSource = new int[n];
        for (int index = 0; index < n; ++index) {
            combinationSource[index] = index + 1;
        }
        int m = 4; // 要选取的元素个数
        Combination.combination(combinationSource, m);

        System.out.printf("******** 应用后台程序退出：%fs ********%n",
                (System.currentTimeMillis() - START_TIME) / 1000.0);
    }
}