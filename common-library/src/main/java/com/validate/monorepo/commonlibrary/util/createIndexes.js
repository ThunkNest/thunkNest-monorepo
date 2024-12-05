// Posts Collection Indexes
db.posts.createIndex({ "author._id": 1 });
print("posts: Indexes created successfully!");

// Users Collection Indexes
db.users.createIndex({ "username": 1 });
print("users: Indexes created successfully!");

// Replies Collection Indexes
db.replies.createIndex({ "taggedUsers._id": 1 });
db.replies.createIndex({ "_id": 1 });
db.replies.createIndex({ "parentPostId": 1 });
db.replies.createIndex({ "author._id": 1, "parentPostId": 1 });
print("replies: Indexes created successfully!");

// Votes Collection Indexes
db.votes.createIndex({ "userId": 1 });
db.votes.createIndex({ "postId": 1 });
db.votes.createIndex({ "replyId": 1 });
db.votes.createIndex({ "voteType": 1 });
db.votes.createIndex({ "postId": 1, "voteType": 1 });
db.votes.createIndex({ "replyId": 1, "voteType": 1 });
// Create a unique index for userId and postId when postId exists
db.votes.createIndex(
  { "userId": 1, "postId": 1 },
  { unique: true, partialFilterExpression: { postId: { $exists: true } } }
);

// Create a unique index for userId and replyId when replyId exists
db.votes.createIndex(
  { "userId": 1, "replyId": 1 },
  { unique: true, partialFilterExpression: { replyId: { $exists: true } } }
);
print("votes: Indexes created successfully!");

// ReputationChange Collection Indexes
db.reputationChange.createIndex({ "timestamp": 1 });
db.reputationChange.createIndex({ "user.id": 1, "postId": 1 });
db.reputationChange.createIndex({ "user.id": 1, "replyId": 1 });
print("reputationChange: Indexes created successfully!");

print("All indexes created successfully!");
