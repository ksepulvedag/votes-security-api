package tutorial.minTIC.seguridad.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tutorial.minTIC.seguridad.Models.Permission;
import tutorial.minTIC.seguridad.Repositories.RepositoryPermission;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/permission")
public class ControllerPermission {

    @Autowired
    private RepositoryPermission myRepositoryPermission;

    @GetMapping("")
    public List<Permission> index(){
        return this.myRepositoryPermission.findAll();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Permission create(@RequestBody  Permission permissionInfo){
        return this.myRepositoryPermission.save(permissionInfo);
    }
    @GetMapping("{id}")
    public Permission show(@PathVariable String id){
        Permission actualPermission=this.myRepositoryPermission
                .findById(id)
                .orElse(null);
        return actualPermission;
    }
    @PutMapping("{id}")
    public Permission update(@PathVariable String id,@RequestBody  Permission permissionInfo){
        Permission actualPermission=this.myRepositoryPermission
                .findById(id)
                .orElse(null);
        if(actualPermission!=null){
            actualPermission.setMethod(permissionInfo.getMethod());
            actualPermission.setUrl(permissionInfo.getUrl());
            return this.myRepositoryPermission.save(actualPermission);
        }else{
            return null;
        }

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Permission actualPermission=this.myRepositoryPermission
                .findById(id)
                .orElse(null);
        if (actualPermission!=null){
            this.myRepositoryPermission.delete(actualPermission);
        }
    }

}
