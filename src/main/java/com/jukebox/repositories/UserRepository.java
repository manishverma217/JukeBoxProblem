package com.jukebox.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.jukebox.entities.User;


public class UserRepository implements IUserRepository{
    private final Map<String,User> userMap;
    private Integer autoIncrement = 0;

    public UserRepository(){
        userMap = new HashMap<String,User>();
    }

    public UserRepository(Map<String,User> userMap){
        this.userMap = userMap;
        this.autoIncrement = userMap.size();
    }

    @Override
    public User save(User entity) {
        if(entity.getUserId() == null){
            autoIncrement++;
            User user = new User(Integer.toString(autoIncrement), entity.getUserName());
            userMap.put(user.getUserId(), user);
            return user;
        }
        userMap.put(entity.getUserId(), entity);
        return entity;
    }

    @Override
    public List<User> findAll() {
        return userMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return userMap.containsKey(id);
    }

    @Override
    public void delete(User entity) {
        //userMap.remove(entity.getUserId());
    }

    @Override
    public void deleteById(String id) {
        //userMap.remove(id);
    }
}

