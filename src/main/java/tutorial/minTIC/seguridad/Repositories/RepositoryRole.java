package tutorial.minTIC.seguridad.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tutorial.minTIC.seguridad.Models.Role;

public interface RepositoryRole extends MongoRepository<Role, String>{

}
