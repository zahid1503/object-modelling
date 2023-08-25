package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.User;

public class UserRepository implements CRUDRepository<User, String> {

  private Integer entityId = 0;

  private final Map<String, User> userMap;

  public UserRepository() {
      userMap = new HashMap<>();
      entityId = userMap.size();
  }

  @Override
  public User save(User entity) {
      if (entity.getId() == null) {
          entityId++;
          User user = new User(Integer.toString(entityId), entity.getName());
          userMap.put(Integer.toString(entityId), user);
          return user;
      }
      userMap.put(entity.getId(), entity);
      return entity;
  }

  @Override
  public List<User> findAll() {
      return new ArrayList<>(userMap.values());
  }

  @Override
  public Optional<User> findById(String id) {
      return Optional.of(userMap.get(id));
  }

  @Override
  public boolean existsById(String id) {
      return userMap.containsKey(id);
  }

  @Override
  public void delete(User entity) {
      userMap.remove(entity.getId());
  }

  @Override
  public void deleteById(String id) {
      userMap.remove(id);
  }

  @Override
  public long count() {
      return userMap.size();
  }



}
