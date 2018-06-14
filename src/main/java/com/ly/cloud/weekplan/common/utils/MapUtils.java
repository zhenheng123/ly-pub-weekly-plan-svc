

package com.ly.cloud.weekplan.common.utils;

import java.util.HashMap;


/**
 * 
 * Class Name: MapUtils  
 * Description: Map工具类
 * @author: Liyewang
 * @mail: Liyewang@ly-sky.com 
 * @date: 2018年6月13日
 * @version: 1.0
 *
 */
public class MapUtils extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	@Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
