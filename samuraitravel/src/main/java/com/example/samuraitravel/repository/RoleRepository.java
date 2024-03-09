package com.example.samuraitravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.Role;

//public interface RoleRepository {
// 認証・認可
public interface RoleRepository extends JpaRepository<Role, Integer> {
	
}
