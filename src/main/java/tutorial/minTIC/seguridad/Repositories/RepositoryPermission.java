package tutorial.minTIC.seguridad.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tutorial.minTIC.seguridad.Models.Permission;
import org.springframework.data.mongodb.repository.Query;

public interface RepositoryPermission extends MongoRepository<Permission, String>{
    @Query("{'url':?0,'method':?1}")
    Permission getPermission(String url, String method);
}
