package board.dto;

import lombok.Data;

@Data
public class JoinDto {
    private String username;
    private String password;
    private String passwordConfirm;
    private String name;
    private String email;
    
    public boolean checkPassword() {
        return this.password != null && this.password.equals(this.passwordConfirm);
    }
}
