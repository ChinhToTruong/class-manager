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
@Entity(name = "Group")
@Table(name = "tbl_group")
public class Group extends AbstractEntity<Integer> {
    private String name;

    private String description;

    @ManyToOne
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE,mappedBy = "groups")
    private Set<User> users = new HashSet<>();
}
