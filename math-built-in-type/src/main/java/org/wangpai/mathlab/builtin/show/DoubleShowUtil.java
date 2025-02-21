package org.wangpai.mathlab.builtin.show;

import org.wangpai.exception.unchecked.dev.DevelopingException;
import org.wangpai.mathlab.common.DigitalStringUtil;

/**
 * @since 2023-7-3
 */
public class DoubleShowUtil {
    /**
     * 将 double 转化为 double 字符串。参数 format 用于控制显示的样式，这与 String.format(...) 中的规则相同
     *
     * @since 2023-7-3
     */
    public static String toDoubleString(double value, String format) {
        return String.format(format, value);
    }

    /**
     * 将有理数转化为 double 字符串，并向结果添加逗号分隔符。参数 format 用于控制显示的样式，这与 String.format(...) 中的规则相同
     *
     * @param commaInterval 逗号之间的间隔
     * @since 2022-9-24
     */
    public static String toDoubleString(double value, String format, int commaInterval) {
        String result = String.format(format, value);
        if (commaInterval == -1) {
            return result;
        } else {
            return DigitalStringUtil.addComma(result, commaInterval);
        }
    }

    /**
     * 将 double 转化为 double 字符串
     *
     * @param validNum double 显示的有效数字的位数
     * @since 2023-7-3
     */
    public static String toDoubleString(double value, int validNum) {
//        double number = 3.1415926;
//        DecimalFormat df = new DecimalFormat("#.##");
//        String result = df.format(number);
        // FIXME：2023年7月3日
        throw new DevelopingException("此方法未完成编写");
    }
}
