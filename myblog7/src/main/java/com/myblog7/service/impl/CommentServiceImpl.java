package com.myblog7.service.impl;

import com.myblog7.entity.Comment;
import com.myblog7.entity.Post;
import com.myblog7.exception.ResourceNotFound;
import com.myblog7.payload.CommentDto;
import com.myblog7.repository.CommentRepository;
import com.myblog7.repository.PostRepository;
import com.myblog7.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements  CommentService {

    private CommentRepository commentRepository;

    private PostRepository postRepository;

    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not Found with id: " + postId)
        );

        comment.setPost(post);

        Comment saveComment = commentRepository.save(comment);

        CommentDto dto = mapToDto(saveComment);

        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFound("Post not Find with id"+postId)
        );
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto getCommentsById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFound("Post not Find with id"+postId)
        );
        Comment comment= commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFound("Comment not Find with id"+  commentId)
        );
        CommentDto commentDto = mapToDto(comment);
        return commentDto;
    }

    @Override
    public List<CommentDto> getAllCommentsById() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFound("Post not Find with id"+postId)
        );
        Comment comment= commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFound("Comment not Find with id"+  commentId)
        );

        commentRepository.deleteById(commentId);

    }


    private CommentDto mapToDto(Comment saveComment) {
        CommentDto dto = mapper.map(saveComment, CommentDto.class);
        return dto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
        return comment;
    }
}
