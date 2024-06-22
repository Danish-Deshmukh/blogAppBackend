package com.springboot.blog.payload;
import com.springboot.blog.entity.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JWTAuthResponse {
    private String accessToken;
    private String name;
    private String username;
    private String email;
    private Set<Role> role;
    private String tokenType = "Bearer";

    public JWTAuthResponse(String accessToken, String name, String username, String email, Set<Role> role) {
        this.accessToken = accessToken;
        this.name = name;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
