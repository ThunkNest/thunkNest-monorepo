package com.validate.monorepo.commonlibrary.model.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostDto {
	private String id;
	private String title;
	private String description;
	private long likeCount;
	private List<Comment> comments;
	private String createdBy;
	private long createdAt;
}
