package com.ldidioni.classifiedads.repositories;

import com.ldidioni.classifiedads.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer>
{
    Role findByName(String name);
}
