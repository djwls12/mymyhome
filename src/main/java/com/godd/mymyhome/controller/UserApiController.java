package com.godd.mymyhome.controller;

import com.godd.mymyhome.model.Board;
import com.godd.mymyhome.model.User;

import com.godd.mymyhome.repostory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
class UserApiController {

    @Autowired
    private UserRepository repository;
    
    //전체 조회.
    @GetMapping("/users")
    List<User> all(String content){
            return repository.findAll();
    }

    //정보 수정.
    @PostMapping("/users")
    User newuser(@RequestBody User user) {
        return repository.save(user);
    }

    //하나만 조회.
    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/users/{id}")
    User replaceuser(@RequestBody User newuser, @PathVariable Long id) {
        return repository.findById(id).map(user -> {
                   // user.setTitle(newuser.getTitle());
                   // user.setContent(newuser.getContent());
                      user.setBoards(newuser.getBoards());
                      for (Board board: user.getBoards()){
                          board.setUser(user);
                      }

                    return repository.save(user);
                }).orElseGet(() -> {
                    newuser.setId(id);
                    return repository.save(newuser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteuser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}