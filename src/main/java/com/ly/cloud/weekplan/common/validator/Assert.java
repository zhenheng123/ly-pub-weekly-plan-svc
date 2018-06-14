
package com.ly.cloud.weekplan.common.validator;

import org.apache.commons.lang.StringUtils;

import com.ly.cloud.exception.CloudException;

/**
 * 
 * Class Name: Assert  
 * Description: 数据校验
 * @author: Liyewang
 * @mail: Liyewang@ly-sky.com 
 * @date: 2018年6月13日
 * @version: 1.0
 *
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new CloudException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new CloudException(message);
        }
    }
}
