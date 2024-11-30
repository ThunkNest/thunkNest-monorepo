package com.validate.monorepo.commonlibrary.model.vote;

import com.validate.monorepo.commonlibrary.exception.BadRequestException;

/**
 * Represents a request to perform a voting action (upvote, downvote, or remove) on a post or reply.
 * <p>
 * This class encapsulates the details required for processing a vote, including the target (post or reply),
 * the user performing the action, and the type of action being taken.
 * <p>
 * <b>Validation Rules:</b>
 * <ul>
 *     <li>Only one of {@code postId} or {@code replyId} can be provided in a single request. Providing both will result in a {@code BadRequestException}.</li>
 *     <li>At least one of {@code postId} or {@code replyId} must be provided.</li>
 * </ul>
 *
 * @author [tolanny-dev]
 */
public record VoteRequest(
		VoteType action,
		String postId,
		String replyId,
		String voterUserId
) {
	
	/**
	 * Validates the request to ensure that only one of {@code postId} or {@code replyId} is provided.
	 *
	 * @throws BadRequestException if both {@code postId} and {@code replyId} are provided,
	 *                                  or if neither is provided.
	 */
	public void validate() {
		if ((postId != null && replyId != null) || (postId == null && replyId == null)) {
			throw new BadRequestException("Invalid request: Exactly one of postId or replyId must be provided.");
		}
	}
	
}
