package com.validate.monorepo.commonlibrary.util;

import com.sanctionco.jmail.JMail;
import org.apache.commons.lang3.StringUtils;

public class EmailUtils {
	
	public static boolean isValidEmail(String email) {
		if (StringUtils.isBlank(email)) return false;
		String normalizedEmail = normalizeEmail(email);
		return JMail.isValid(normalizedEmail);
	}
	
	public static String normalizeEmail(String email) {
		StringBuilder normalizedEmail = new StringBuilder();
		
		for (int i = 0; i < email.length(); i++) {
			char c = email.charAt(i);
			
			// Check if the character is in the full-width range
			if (c >= '\uFF01' && c <= '\uFF5E') {
				// Convert to half-width equivalent
				c = (char) (c - 0xFEE0);
			}
			
			normalizedEmail.append(c);
		}
		
		return normalizedEmail.toString();
	}

}
