package tutorial.minTIC.seguridad.Models;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document()
public class User {

    @Id
    private String _id;
    private String pseudonym;
    private String email;
    private String password;

    @DBRef
    private Role role;

    public User(String pseudonym, String email, String password) {
        this.pseudonym = pseudonym;
        this.email = email;
        this.password = password;
    }

    public String get_id() {
        return _id;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
