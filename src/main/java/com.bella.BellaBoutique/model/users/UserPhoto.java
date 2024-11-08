package com.bella.BellaBoutique.model.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoto {

    @Id
    private String fileName;

}

