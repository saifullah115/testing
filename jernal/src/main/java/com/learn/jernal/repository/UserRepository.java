package com.learn.jernal.repository;

import com.learn.jernal.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findUserByUserName(String userName);
}
