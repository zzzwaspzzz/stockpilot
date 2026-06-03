package pojos_package;

public class Usuario implements java.io.Serializable {

    private Integer idUsuario;
    private String username;
    private String passwordHash;
    private String email;
    private String googleId;

    
    public Usuario() {
    }

    
    public Usuario(String email) {
        this.email = email;
    }

    
    public Usuario(String username, String passwordHash, String email, String googleId) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.googleId = googleId;
    }

    
    public Integer getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogleId() {
        return this.googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }
}