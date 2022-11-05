package tutorial.minTIC.seguridad.Controllers;
import tutorial.minTIC.seguridad.Models.Role;
import tutorial.minTIC.seguridad.Models.User;
import tutorial.minTIC.seguridad.Repositories.RepositoryRole;
import tutorial.minTIC.seguridad.Repositories.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class ControllerUser {

    @Autowired
    private RepositoryUser myRepositoryUser;

    @Autowired
    private RepositoryRole myRepositoryRole;

    @GetMapping("")
    public List<User> index(){
        return this.myRepositoryUser.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@RequestBody  User UserInfo){
        UserInfo.setPassword(encryptSHA256(UserInfo.getPassword()));
        return this.myRepositoryUser.save(UserInfo);
    }

    @GetMapping("{id}")
    public User show(@PathVariable String id){
        User ActualUser=this.myRepositoryUser
                .findById(id)
                .orElse(null);
        return ActualUser;
    }

    @PutMapping("{id}")
    public User update(@PathVariable String id,@RequestBody  User UserInfo){
        User ActualUser=this.myRepositoryUser
                .findById(id)
                .orElse(null);
        if (ActualUser!=null){
            ActualUser.setPseudonym(UserInfo.getPseudonym());
            ActualUser.setEmail(UserInfo.getEmail());
            ActualUser.setPassword(encryptSHA256(UserInfo.getPassword()));
            return this.myRepositoryUser.save(ActualUser);
        }else{
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        User ActualUser=this.myRepositoryUser
                .findById(id)
                .orElse(null);
        if (ActualUser!=null){
            this.myRepositoryUser.delete(ActualUser);
        }
    }

    /**
     * Relación (1 a n) entre rol y usuario
     * @param userId
     * @param roleId
     * @return
     */
    @PutMapping("{userId}/role/{roleId}")
    public User assignUserRol(@PathVariable String userId,@PathVariable String roleId){
        User  actualUser=this.myRepositoryUser.findById(userId).orElseThrow(RuntimeException::new);
        Role actualRole=this.myRepositoryRole.findById(roleId).orElseThrow(RuntimeException::new);
        actualUser.setRole(actualRole);
        return this.myRepositoryUser.save(actualUser);
    }

    @PostMapping("/validate")
    public User validate(@RequestBody  User userInfo, final HttpServletResponse response) throws IOException {
        User actualUser=this.myRepositoryUser
                .getUserByEmail(userInfo.getEmail());
        if (actualUser!=null && actualUser.getPassword().equals(encryptSHA256(userInfo.getPassword()))) {
            actualUser.setPassword("");
            return actualUser;
        }else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }

    public String encryptSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}