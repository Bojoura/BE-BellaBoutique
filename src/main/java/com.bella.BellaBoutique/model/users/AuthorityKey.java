package com.bella.BellaBoutique.model.users;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AuthorityKey implements Serializable {
    private Long id;
    private String username;
    private String authority;
}