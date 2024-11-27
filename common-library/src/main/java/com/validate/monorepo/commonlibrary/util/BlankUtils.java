package com.validate.monorepo.commonlibrary.util;

import com.validate.monorepo.commonlibrary.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;

public class BlankUtils {
	
	public static void validateBlank(String s) {
		if (StringUtils.isBlank(s)) throw new BadRequestException("Value cannot be null or empty");
	}
	
}
