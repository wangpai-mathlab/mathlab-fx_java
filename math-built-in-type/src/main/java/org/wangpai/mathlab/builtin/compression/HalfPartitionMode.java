package org.wangpai.mathlab.builtin.compression;

/**
 * @since 2022-12-3
 */
public enum HalfPartitionMode {
    DEFAULT, // 默认方式
    UP, // 将 reference 归到 1 中
    DOWN, // 将 reference 归到 -1 中
}
