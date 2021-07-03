package com.godd.mymyhome.controller;

import com.godd.mymyhome.model.Board;
import com.godd.mymyhome.repostory.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
class BoardApiController {

    @Autowired
    private BoardRepository repository;
    
    //전체 조회.
    @GetMapping("/boards")
    List<Board> all(@RequestParam(defaultValue = "") String title,
                    @RequestParam(defaultValue="") String content) {
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
            return repository.findAll();
        }else {
            return repository.findByTitleOrContent(title, title);
        }
    }

    //정보 수정.
    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    //하나만 조회.
    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {
        return repository.findById(id).map(Board -> {
                    Board.setTitle(newBoard.getTitle());
                    Board.setContent(newBoard.getContent());
                    return repository.save(Board);
                }).orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}