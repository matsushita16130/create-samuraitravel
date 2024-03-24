package com.example.samuraitravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.VerificationToken;

//public interface VerificationTokenRepository {
// メール認証機能
public interface VerificationTokenRepository extends JpaRepository< VerificationToken, Integer> {
	public VerificationToken findbyToken(String token);
}
