package tutorial.minTIC.seguridad.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tutorial.minTIC.seguridad.Models.User;
import org.springframework.data.mongodb.repository.Query;

public interface RepositoryUser extends MongoRepository<User, String> {
    @Query("{'email': ?0}")
    public User getUserByEmail(String email);
}
