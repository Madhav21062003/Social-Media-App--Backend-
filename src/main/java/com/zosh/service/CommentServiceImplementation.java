package com.zosh.service;

import com.zosh.models.Comment;
import com.zosh.models.Post;
import com.zosh.models.User;
import com.zosh.repository.CommentRepository;
import com.zosh.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImplementation implements  CommentService{

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;


    // ================================== Create Comment Method =====================================================
    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception {

        User user = userService.findUserById(userId);

        Post post = postService.findPostById(postId);

        comment.setUser(user);
        comment.setContent(comment.getContent());
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        post.getComments().add(savedComment);

        postRepository.save(post);
        return savedComment;
    }


    // ================================== Like Comment Method =====================================================
    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws Exception {
        // First Check that comment exist or not
        Comment comment = findCommentById(commentId);

        User user = userService.findUserById(userId);

        // if current not like a particular comment then it can like a particular comment
        if (!comment.getLiked().contains(user)){
            comment.getLiked().add(user);
        }else {
            // If comment is already like then remove like from comment of that particular user
            comment.getLiked().remove(user);
        }

        return commentRepository.save(comment);
    }


    // ================================== Find Comment By Id Method =====================================================
    @Override
    public Comment findCommentById(Integer commentId) throws Exception {
        Optional<Comment> opt =  commentRepository.findById(commentId);

        if (opt.isEmpty()){
            throw new Exception("Comment Not Exist");
        }
        return opt.get();
    }
}
