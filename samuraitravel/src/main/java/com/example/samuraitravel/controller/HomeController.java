package com.example.samuraitravel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// クラスに@Controllerアノテーションをつける
@Controller
public class HomeController {
	// メソッドに@GetMappingアノテーションをつける
	@GetMapping("/")
	public String index() {
		// 呼び出すビューの名前をreturnで返す
		return "index";
	}
}
