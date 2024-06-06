package com.zev.studentmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Permission")
@Table(name = "tbl_permission")
public class Permission extends AbstractEntity<Integer> {
    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE,mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();
}
