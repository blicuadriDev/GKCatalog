package com.godknows.gkcatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godknows.gkcatalog.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByAuthority (String authority);

}
