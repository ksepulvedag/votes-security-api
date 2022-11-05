package tutorial.minTIC.seguridad.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tutorial.minTIC.seguridad.Models.PermissionsRoles;
import org.springframework.data.mongodb.repository.Query;

public interface RepositoryPermissionsRoles extends MongoRepository<PermissionsRoles, String>{
    @Query("{'role.$id': ObjectId(?0),'permission.$id': ObjectId(?1)}")
    PermissionsRoles getPermissionRole(String roleId,String permissionId);
}
