package com.validate.monorepo.commonlibrary.model.reply;

public record CreateReplyRequest (
		String replyText,
		String authorUsername,
		String parentPostId
){}
