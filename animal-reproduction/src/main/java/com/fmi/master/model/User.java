package com.fmi.master.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    private String username;
    private String email;
    private String password;
}
