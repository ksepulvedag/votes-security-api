package tutorial.minTIC.seguridad.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tutorial.minTIC.seguridad.Models.Permission;
import tutorial.minTIC.seguridad.Models.PermissionsRoles;
import tutorial.minTIC.seguridad.Models.Role;
import tutorial.minTIC.seguridad.Repositories.RepositoryPermission;
import tutorial.minTIC.seguridad.Repositories.RepositoryPermissionsRoles;
import tutorial.minTIC.seguridad.Repositories.RepositoryRole;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/permissions-roles")
public class ControllerPermissionsRoles {

    @Autowired
    private RepositoryPermissionsRoles myRepositoryPermissionsRoles;

    @Autowired
    private RepositoryPermission myRepositoryPermission;

    @Autowired
    private RepositoryRole myRepositoryRole;


    @GetMapping("")
    public List<PermissionsRoles> index(){
        return this.myRepositoryPermissionsRoles.findAll();
    }

    /**
     * Assign role and permission
     * @param roleId
     * @param permissionId
     * @return
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("role/{roleId}/permission/{permissionId}")
    public PermissionsRoles create(@PathVariable String roleId, @PathVariable String permissionId){
        PermissionsRoles newRolePermission=new PermissionsRoles();
        Role theRol=this.myRepositoryRole.findById(roleId).get();
        Permission thePermission=this.myRepositoryPermission.findById(permissionId).get();
        if (theRol!=null && thePermission!=null){
            newRolePermission.setPermission(thePermission);
            newRolePermission.setRole(theRol);
            return this.myRepositoryPermissionsRoles.save(newRolePermission);
        }else{
            return null;
        }
    }
    @GetMapping("{id}")
    public PermissionsRoles show(@PathVariable String id){
        PermissionsRoles actualPermissionsRoles=this.myRepositoryPermissionsRoles
                .findById(id)
                .orElse(null);
        return actualPermissionsRoles;
    }

    /**
     * Role & Permission update
     * @param id
     * @param roleId
     * @param permissionId
     * @return
     */
    @PutMapping("{id}/role/{roleId}/permission/{permissionId}")
    public PermissionsRoles update(@PathVariable String id,@PathVariable String roleId,@PathVariable String permissionId){
        PermissionsRoles actualPermissionsRoles=this.myRepositoryPermissionsRoles
                .findById(id)
                .orElse(null);
        Role theRol=this.myRepositoryRole.findById(roleId).get();
        Permission thePermission=this.myRepositoryPermission.findById(permissionId).get();
        if(actualPermissionsRoles!=null && thePermission!=null && theRol!=null){
            actualPermissionsRoles.setPermission(thePermission);
            actualPermissionsRoles.setRole(theRol);
            return this.myRepositoryPermissionsRoles.save(actualPermissionsRoles);
        }else{
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        PermissionsRoles actualPermissionsRoles=this.myRepositoryPermissionsRoles
                .findById(id)
                .orElse(null);
        if (actualPermissionsRoles!=null){
            this.myRepositoryPermissionsRoles.delete(actualPermissionsRoles);
        }
    }

    @GetMapping("validate-permission/role/{roleId}")
    public PermissionsRoles getPermission(@PathVariable String roleId,@RequestBody Permission PermissionInfo){
        Permission thePermission=this.myRepositoryPermission.getPermission(
                PermissionInfo.getUrl(),
                PermissionInfo.getMethod()
        );
        Role theRole=this.myRepositoryRole.findById(roleId).get();
        if (thePermission!=null && theRole!=null){
            return
                    this.myRepositoryPermissionsRoles.getPermissionRole(theRole.get_id(),thePermission.get_id());
        }else{
            return null;
        }
    }

}
