package com.validate.monorepo.commonlibrary.model.reply;

public record ReplyRequest(
		String replyText,
		String authorUsername,
		String parentPostId
){}
