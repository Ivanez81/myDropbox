package ru.geekbrains.dropbox.frontend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.dropbox.frontend.dao.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByLogin(String login);
}
