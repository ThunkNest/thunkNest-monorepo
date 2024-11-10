package com.validate.monorepo.commonlibrary.model.auth;

public record UserAuthRequest(
		String email,
		OauthProvider provider,
		String providerId
) {}
