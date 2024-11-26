db = db.getSiblingDB("thunkNestDB");

// Posts Collection Indexes
db.posts.createIndex({ "author._id": 1 });
db.posts.createIndex({ "upvotedBy._id": 1 });
db.posts.createIndex({ "downvotedBy._id": 1 });
db.posts.createIndex({ "replies.author._id": 1 });

// Users Collection Indexes
db.users.createIndex({ "username": 1 });

// Replies Collection Indexes
db.replies.createIndex({ "taggedUsers._id": 1 });
db.replies.createIndex({ "_id": 1 });

// Votes Collection Indexes
db.votes.createIndex({ "userId": 1 });
db.votes.createIndex({ "postId": 1 });
db.votes.createIndex({ "replyId": 1 });
db.votes.createIndex({ "voteType": 1 });
db.votes.createIndex({ "postId": 1, "voteType": 1 });
db.votes.createIndex({ "replyId": 1, "voteType": 1 });
db.votes.createIndex({ "userId": 1, "postId": 1 }, { unique: true });
db.votes.createIndex({ "userId": 1, "replyId": 1 }, { unique: true });

print("Indexes created successfully!");
