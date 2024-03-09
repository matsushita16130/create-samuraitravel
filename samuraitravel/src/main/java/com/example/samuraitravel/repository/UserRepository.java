package com.example.samuraitravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.User;

//public interface UserRepository {
// 認証・認可
public interface UserRepository extends JpaRepository<User, Integer> {
	// ログイン機能
	public User findByEmail(String email);
}
