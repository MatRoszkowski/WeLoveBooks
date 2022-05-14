package com.mateuszroszkowski.WeLoveBooks.repository;


import com.mateuszroszkowski.WeLoveBooks.model.Role;
import com.mateuszroszkowski.WeLoveBooks.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);
}
