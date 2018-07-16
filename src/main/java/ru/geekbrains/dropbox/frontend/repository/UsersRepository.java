package ru.geekbrains.dropbox.frontend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.dropbox.frontend.dao.Users;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {
    public Users findByLogin(String login);
}
