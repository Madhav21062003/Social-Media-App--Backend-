package com.zosh.service;

import com.zosh.models.Reels;
import com.zosh.models.User;
import com.zosh.repository.ReelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReelsServiceImplementation implements ReelsService{

    @Autowired
    private ReelsRepository reelsRepository;

    @Autowired
    private UserService userService;

    // ====================================== Create Reel ============================================================
    @Override
    public Reels createReel(Reels reel, User user) {

        Reels createReel = new Reels();

        createReel.setTitle(reel.getTitle());
        createReel.setUser(user);
        createReel.setVideo(reel.getVideo());

        return reelsRepository.save(createReel);
    }


    // ====================================== Get All Reels ===========================================================
    @Override
    public List<Reels> findAllReels() {
        return reelsRepository.findAll();
    }

    // ====================================== Find Users Reels ============================================================
    @Override
    public List<Reels> findUsersReel(Integer userId) throws Exception {
        userService.findUserById(userId);

        return reelsRepository.findByUserId(userId);
    }
}
