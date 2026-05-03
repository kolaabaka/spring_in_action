package com.fx.controller;

import com.fx.dto.mongo.Comment;
import com.fx.repository.mongo.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@AllArgsConstructor
@Slf4j
public class CommentController {

    private final CommentRepository commentRepository;

    @PostMapping("/create")
    public void createController(@RequestBody Comment comment){
        commentRepository.insert(comment);
    }

    @GetMapping
    public List<Comment> getAllComments(){
//        Pageable page = PageRequest.of(0, 20); //for paging could use Spring interface Pageable
        return commentRepository.findAll();
    }
}
