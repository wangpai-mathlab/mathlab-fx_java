package org.wangpai.mathlab.builtin.interval;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 本类描述的是一种离散点的区间集合：[start, end]
 *
 * @since 2023-2-19
 */
@Accessors(chain = true)
public class LongInterval implements Comparable<LongInterval> {
    @Getter
    private long start; // 区间起点

    @Getter
    private long end; // 区间终点

    /**
     * @since 2023-2-11
     */
    public LongInterval(long start, long end) {
        this.start = start;
        this.end = end;
    }

    /**
     * @since 2023-2-11
     */
    public static LongInterval getInstance(long start, long end) {
        return new LongInterval(start, end);
    }

    /**
     * 判断 value 值是否在本区间中
     *
     * @since 2022-11-21
     */
    public boolean contain(long value) {
        return value >= this.start && value <= this.end;
    }

    /**
     * 判断 value 值是否在这一系列区间中
     *
     * @since 2022-11-21
     */
    public static boolean contain(long value, LongInterval[] intervals) {
        for (var interval : intervals) {
            if (interval.contain(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 得到本区间的中心
     *
     * @since 2022-11-22
     */
    public long intervalCenter() {
        // 此处会有向下取整的误差，但这是整数除法中无法避免的问题
        return (this.start + this.end) / 2;
    }

    /**
     * 返回本区间中包含的点个数
     *
     * @since 2023-2-19
     */
    public long size() {
        return this.end - this.start + 1;
    }

    /**
     * @since 2022-12-24
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (other instanceof LongInterval interval) {
            return this.start == interval.start && this.end == interval.end;
        }

        return false;
    }

    /**
     * @since 2023-1-11
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.start, this.end);
    }

    /**
     * 统计区间的长度之和
     *
     * @since 2022-12-25
     */
    public static long accumulate(LongInterval[] intervals) {
        long sum = 0L;
        for (var interval : intervals) {
            sum += interval.size();
        }
        return sum;
    }

    /**
     * 将区间排序。排序是按照区间左端点来排序的
     *
     * @since 2022-12-24
     */
    public static LongInterval[] sort(LongInterval[] intervals, boolean increasing) {
        if (increasing) { // 升序排列
            Arrays.sort(intervals, (first, second) -> Long.compare(first.start, second.start));
        } else {
            Arrays.sort(intervals, (first, second) -> Long.compare(second.start, first.start));
        }
        return intervals;
    }

    /**
     * @since 2022-11-21
     * @return 包含或相交时，返回 true。不相交时，返回 false
     */
    public static boolean intersectOrContain(LongInterval first, LongInterval second) {
        return first.start <= second.end && second.start <= first.end;
    }

    /**
     * 这是按照区间左端点判断大小的
     *
     * @since 2023-2-11
     */
    @Override
    public int compareTo(LongInterval other) {
        return (int) (this.start - other.start);
    }

    /**
     * @since 2023-2-11
     */
    @Override
    public String toString() {
        return this.toStringByEndpoint();
    }

    /**
     * 以【起点 + 长度】来显示区间
     *
     * @since 2023-2-11
     */
    public String toStringBySize() {
        return "DoubleInterval{" +
                "start=" + this.start +
                ", size=" + this.size() +
                '}';
    }

    /**
     * 以【起点 + 终点】来显示区间
     *
     * @since 2023-2-11
     */
    public String toStringByEndpoint() {
        return "DoubleInterval{" +
                "start=" + this.start +
                ", end=" + (this.start + this.end) +
                '}';
    }
}
