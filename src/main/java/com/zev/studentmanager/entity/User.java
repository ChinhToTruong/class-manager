package com.zev.studentmanager.entity;

import com.zev.studentmanager.enums.Gender;
import com.zev.studentmanager.enums.UserStatus;
import com.zev.studentmanager.enums.UserType;
import com.zev.studentmanager.validator.ValueOfEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "tbl_user")
public class User extends AbstractEntity implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "phone", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    private String phone;

    @Column(name = "email", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    private String email;

    @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    private String username;

    @Column(name = "password")
    private String password;

    @ValueOfEnum(enumClass = Gender.class)
    @Column(name = "gender")
    private String gender;

    @ValueOfEnum(enumClass = UserType.class)
    @Column(name = "type")
    private String type;

    @ValueOfEnum(enumClass = UserStatus.class)
    @Column(name = "status")
    private String status;


    @Column(name = "deleted")
    private boolean deleted;

    private String address;

    @OneToOne
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return role.getPermissions()
               .stream()
               .map(permission -> new SimpleGrantedAuthority(permission.getName()))
               .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return  password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

}
