package com.zosh.service;

import com.zosh.config.JwtProvider;
import com.zosh.exceptions.UserException;
import com.zosh.models.User;
import com.zosh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        User savedUser = userRepository.save(newUser);
        return savedUser;
    }

    @Override
    public User findUserById(Integer userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()){
            return user.get();
        }
        throw  new UserException("USer Not Exist with this user id-> "+userId);
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        return user;
    }

    @Override
    public User followUser(Integer reqUserId, Integer userId2) throws UserException {

        User reqUser = findUserById(reqUserId);

        User user2 = findUserById(userId2);

        // If user1 wants to follow user2, so we update the followers of user 2
        user2.getFollowers().add(reqUser.getId());

        // user 1 follows the user2 then in user1 followings we add user2 id
        reqUser.getFollowings().add(user2.getId());


        // Both user are updated so we both of them in database
        userRepository.save(reqUser);
        userRepository.save(user2);

        return reqUser;

    }

    @Override
    public User updateUser(User user, Integer userId) throws UserException {
        // Check First  User is Exist or Not in the database
        Optional<User> user1 = userRepository.findById(userId);

        if (user1.isEmpty()){
            throw new UserException("no user exist by "+userId+" this id");
        }

        // If User exist then we proceed update operation
        User oldUSer     = user1.get();

        if (user.getFirstName() != null){
            oldUSer.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null){
            oldUSer.setLastName(user.getLastName());
        }

        if (user.getEmail() != null){
            oldUSer.setEmail(user.getEmail());
        }

        if (user.getPassword() != null){
            oldUSer.setPassword(user.getPassword());
        }

        if (user.getGender() != null){
            oldUSer.setGender(user.getGender());
        }
        // Save The Updated User
        User updatedUser = userRepository.save(oldUSer);
        return updatedUser;
    }

    @Override
    public List<User> searchUser(String query) {

        return userRepository.searchUser(query);
    }

    @Override
    public User findUserByJwt(String jwt) {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);
        return user;
    }
}
