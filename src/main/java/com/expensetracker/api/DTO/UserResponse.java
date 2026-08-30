package com.expensetracker.api.DTO;

import com.expensetracker.api.model.Role;
import lombok.Data;

@Data
public class UserResponse {

    private int id;
    private String username;
    private String firstname;
    private String lastname;
    private String mail;
    private String phone;
    private Role role;

  public   UserResponse(){};

}
