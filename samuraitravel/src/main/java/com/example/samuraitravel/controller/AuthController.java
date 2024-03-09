package com.example.samuraitravel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 認証機能（ログイン、会員登録）用のコントローラ
@Controller
public class AuthController {
	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}
}
