package blog.cirkle.app.facade;

import blog.cirkle.app.api.graphql.model.auth.*;
import blog.cirkle.app.api.graphql.model.comment.Comment;
import blog.cirkle.app.api.graphql.model.comment.CommentPage;
import blog.cirkle.app.api.graphql.model.comment.CreateCommentInput;
import blog.cirkle.app.api.graphql.model.comment.UpdateCommentInput;
import blog.cirkle.app.api.graphql.model.post.CreatePostInput;
import blog.cirkle.app.api.graphql.model.post.Post;
import blog.cirkle.app.api.graphql.model.post.PostPage;
import blog.cirkle.app.api.graphql.model.post.UpdatePostInput;
import blog.cirkle.app.api.graphql.model.reaction.CreateReactionInput;
import blog.cirkle.app.api.graphql.model.reaction.ReactionList;
import blog.cirkle.app.api.graphql.model.relation.RequestPage;
import blog.cirkle.app.api.graphql.model.user.UpdateUserProfileInput;
import blog.cirkle.app.api.graphql.model.user.User;
import blog.cirkle.app.api.graphql.model.user.UserPage;
import blog.cirkle.app.api.graphql.model.user.UserProfile;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;

public interface GraphQlFacade {
	User getCurrentUserInfo();

	User getUserById(UUID userId);

	UserProfile getProfileByUserId(UUID id);

	UserPage listUsers(String query, PageRequest pageRequest);

	UserPage friendsByUserId(UUID id, PageRequest pageRequest);

	UserPage followersByUserId(UUID id, PageRequest pageRequest);

	RequestPage requestsByUserId(UUID id, PageRequest pageRequest);

	Post postByUserIdAndPostId(UUID id, UUID postId);

	PostPage postsByUserId(UUID id, PageRequest pageRequest);

	CommentPage commentsByPostId(UUID postId, PageRequest pageRequest);

	CommentPage commentsByCommentIdAndPostId(UUID postId, Long commentId, PageRequest of);

	UserProfile updateProfile(UpdateUserProfileInput input);

	Boolean acceptUserRequest(UUID requestId);

	Boolean rejectUserRequest(UUID requestId);

	NewUser registerUser(CreateUserInput input);

	AuthenticateResponse authenticate(String username, String password);

	AuthenticateResponse resetPassword(ResetPasswordInput input);

	Post createPost(CreatePostInput input);

	Post updatePost(UUID postId, UpdatePostInput input);

	Boolean deletePost(UUID postId);

	List<ReactionList> addReactionToPost(UUID postId, CreateReactionInput input);

	Comment addCommentToPost(UUID postId, CreateCommentInput input);

	Comment updateComment(UUID postId, Long commentId, UpdateCommentInput input);

	Boolean deleteComment(UUID postId, Long commentId);

	List<ReactionList> reactOnComment(UUID postId, Long commentId, CreateReactionInput input);

	Comment addCommentToComment(UUID postId, Long commentId, CreateCommentInput input);

	Boolean followUser(UUID userId);

	Boolean unFollowUser(UUID userId);

	Boolean friendUser(UUID userId);

	Boolean unFriendUser(UUID userId);

	PostPage feed(PageRequest of);
}
