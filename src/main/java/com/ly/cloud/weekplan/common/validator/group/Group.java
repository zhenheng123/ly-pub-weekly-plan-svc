
package com.ly.cloud.weekplan.common.validator.group;

import javax.validation.GroupSequence;

/**
 * 
 * Class Name: Group  
 * Description: 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 * @author: Liyewang
 * @mail: Liyewang@ly-sky.com 
 * @date: 2018年6月13日
 * @version: 1.0
 *
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
