package tutorial.minTIC.seguridad.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tutorial.minTIC.seguridad.Models.Role;
import tutorial.minTIC.seguridad.Repositories.RepositoryRole;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/role")
public class ControllerRole {

    @Autowired
    private RepositoryRole myRepositoryRole;

    @GetMapping("")
    public List<Role> index(){
        return this.myRepositoryRole.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Role create(@RequestBody  Role RoleInfo){
        return this.myRepositoryRole.save(RoleInfo);
    }
    @GetMapping("{id}")
    public Role show(@PathVariable String RoleId){
        Role actualRole=this.myRepositoryRole
                .findById(RoleId)
                .orElse(null);
        return actualRole;
    }
    @PutMapping("{id}")
    public Role update(@PathVariable String id,@RequestBody  Role RoleInfo){
        Role actualRole=this.myRepositoryRole
                .findById(id)
                .orElse(null);
        if (actualRole!=null){
            actualRole.setName(RoleInfo.getName());
            return this.myRepositoryRole.save(actualRole);
        }else{
            return  null;
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Role actualRole=this.myRepositoryRole
                .findById(id)
                .orElse(null);
        if (actualRole!=null){
            this.myRepositoryRole.delete(actualRole);
        }
    }

}
