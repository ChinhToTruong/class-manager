package com.zev.studentmanager.configuration;

import com.zev.studentmanager.entity.Permission;
import com.zev.studentmanager.entity.Role;
import com.zev.studentmanager.entity.User;
import com.zev.studentmanager.repository.PermissionRepository;
import com.zev.studentmanager.repository.RoleRepository;
import com.zev.studentmanager.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class ScriptRun {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository){
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()){
               var permission = Permission.builder()
                       .name("ALL")
                       .description("all access")
                       .build();
                permissionRepository.save(permission);


                Set<Permission> permissions = new HashSet<>();

                permissions.add(permission);
                var role = Role.builder()
                        .permissions(permissions)
                        .name("ADMIN").description("admin level")
                        .build();
                roleRepository.save(role);

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("Admin123@"))
                         .role(role)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}
