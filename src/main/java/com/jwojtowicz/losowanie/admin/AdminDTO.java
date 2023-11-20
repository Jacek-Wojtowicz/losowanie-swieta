package com.jwojtowicz.losowanie.admin;

import com.jwojtowicz.losowanie.pair.PairDTO;
import com.jwojtowicz.losowanie.user.UserDTO;
import java.util.List;
import lombok.Data;

@Data
public class AdminDTO {
    private List<UserDTO> users;
    private List<PairDTO> pairs;
}
