package com.godd.mymyhome.service;

import com.godd.mymyhome.model.Board;
import com.godd.mymyhome.model.User;
import com.godd.mymyhome.repostory.BoardRepository;
import com.godd.mymyhome.repostory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Board save(String username, Board board){
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }
}
