package com.jwojtowicz.losowanie.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private Integer id;
    private String name;
    private String drawName;
    private String password;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.drawName = user.getDrawName();
    }
}