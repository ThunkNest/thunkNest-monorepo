db = db.getSiblingDB("your_database_name");

// Index for posts' author
db.posts.createIndex({ "author._id": 1 });

// Index for upvotedBy
db.posts.createIndex({ "upvotedBy._id": 1 });

// Index for downvotedBy
db.posts.createIndex({ "downvotedBy._id": 1 });

// Index for replies' author
db.posts.createIndex({ "replies.author._id": 1 });

// Index for username in users collection
db.users.createIndex({ "username": 1 });

// Index for tagged users in replies
db.replies.createIndex({ "taggedUsers._id": 1 });
db.replies.createIndex({ "_id": 1 });

// Add more indexes as needed
print("Indexes created successfully!");
