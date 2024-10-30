package com.validate.monorepo.commonlibrary.model.auth;

import com.validate.monorepo.commonlibrary.model.user.OauthProvider;

public record UserAuthRequest(
		String email,
		OauthProvider provider,
		String providerId
) {}
