package com.ly.cloud.weekplan.common.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.ly.cloud.exception.CloudException;

/**
 * 
 * Class Name: ValidatorUtils Description: hibernate-validator校验工具类
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 * 
 * @author: Liyewang
 * @mail: Liyewang@ly-sky.com
 * @date: 2018年4月18日
 * @version: 1.0
 *
 */
public class ValidatorUtils {
	private static Validator validator;

	static {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	/**
	 * 校验对象
	 * 
	 * @param object
	 *            待校验对象
	 * @param groups
	 *            待校验的组
	 * @throws RRException
	 *             校验不通过，则报OAException异常
	 */
	public static void validateEntity(Object object, Class<?>... groups) throws Exception {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
		if (!constraintViolations.isEmpty()) {
			ConstraintViolation<Object> constraint = (ConstraintViolation<Object>) constraintViolations.iterator().next();
			throw new CloudException(constraint.getMessage());
		}
	}
}
