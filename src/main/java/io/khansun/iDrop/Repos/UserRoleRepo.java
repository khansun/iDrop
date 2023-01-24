package io.khansun.iDrop.Repos;

import io.khansun.iDrop.Models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepo extends JpaRepository<UserRole, Long> {
    UserRole findByName (String name);
}
