package io.gitee.welkinfast.im.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @Author yuanjg
 * @CreateTime 2021/03/21 11:49
 * @Version 1.0.0
 */
public class FormatUtil {

    /**
     * 设置数字格式，保留有效小数位数为fractions
     *
     * @param fractions 保留有效小数位数
     * @return 数字格式
     */
    public static DecimalFormat decimalFormat(int fractions) {

        DecimalFormat df = new DecimalFormat("#0.0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        df.setMinimumFractionDigits(fractions);
        df.setMaximumFractionDigits(fractions);
        return df;
    }
}
