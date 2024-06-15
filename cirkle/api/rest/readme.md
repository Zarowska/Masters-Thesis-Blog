# files

# user
GET /user
GET /user/feed

# relations
POST /participants/{participantId}/relations // create request
PUT /participants/{participantId}/relations/{relation_id}
DELETE /participants/{participantId}/relations/{relation_id}

## aliases
POST /users/{userId}/follow
DELETE /users/{userId}/follow
POST /users/{userId}/friend
DELETE /users/{userId}/friend
POST /users/{userId}/block
DELETE /users/{userId}/unblock
POST /group/{groupId}/join
POST /group/{groupId}/leave

## posts
GET /user/{userId}/posts
POST /user/{userId}/posts
PUT /user/{userId}/posts/{postId}
DELETE /user/{userId}/posts/{postId}

## post reaction
POST /user/{userId}/posts/{postId}/reaction
DELETE /user/{userId}/posts/{postId}/reaction
POST /user/{userId}/posts/{postId}/reaction
DELETE /user/{userId}/posts/{postId}/reaction

## image reactions
POST /user/{userId}/posts/{postId}/images/{imageId}/reaction
DELETE /user/{userId}/posts/{postId}/images/{imageId}/reaction

## comments
GET /user/{userId}/posts/{postId}/comments
GET /user/{userId}/posts/{postId}/comments/{commentId}/comments
POST /user/{userId}/posts/{postId}/comments
POST /user/{userId}/posts/{postId}/comments/{commentId}
PUT /user/{userId}/posts/{postId}/comments/{commentId}
DELETE /user/{userId}/posts/{postId}/comments/{commentId}
