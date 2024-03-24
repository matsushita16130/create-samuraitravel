package com.example.samuraitravel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.entity.VerificationToken;
import com.example.samuraitravel.event.SignupEventPublisher;
import com.example.samuraitravel.form.SignupForm;
import com.example.samuraitravel.service.UserService;
import com.example.samuraitravel.service.VerificationTokenService;

import jakarta.servlet.http.HttpServletRequest;

// 認証機能（ログイン、会員登録）用のコントローラ
@Controller
public class AuthController {
	
	// 会員登録機能を作成
	private final UserService userService;
	// メールの認証機能
	private final SignupEventPublisher signupEventPublisher;
	private final VerificationTokenService verificationTokenService;
	
	//public AuthController(UserService userService) {
	// メールの認証機能
	public AuthController(UserService userService, SignupEventPublisher signupEventPublisher, VerificationTokenService verificationTokenService) { 
		this.userService = userService;
		this.signupEventPublisher = signupEventPublisher;
		this.verificationTokenService = verificationTokenService;
	}
	
	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}
	
	// 会員登録機能を作成
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("signupForm", new SignupForm());
		return "auth/signup";
	}
	
	// 会員登録機能を作成
	@PostMapping("/signup")
	//public String signup(@ModelAttribute @Validated SignupForm signupForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
	// メールの認証機能
	public String signup(@ModelAttribute @Validated SignupForm signupForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		// メールアドレスが登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
		if (userService.isEmailRegistered(signupForm.getEmail())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
            bindingResult.addError(fieldError);                       
        }
		
		// パスワードと（確認用）パスワードの入力値が一致していれば、BondongResultオブジェクトにエラー内容を追加する
		if (!userService.isSamePassword(signupForm.getPassword(), signupForm.getPasswordConfirmation())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "パスワードが一致しません。");
            bindingResult.addError(fieldError);
        }
		
		if (bindingResult.hasErrors()) {
			return "auth/signup";
		}
		
		//userService.create(signupForm);
		//redirectAttributes.addFlashAttribute("successMessage", "会員登録が完了しました。");
		// メールの認証機能
		User createUser = userService.create(signupForm);
		String requestUrl = new String(httpServletRequest.getRequestURL());
		signupEventPublisher.publishSignupEvent(createUser, requestUrl);
		redirectAttributes.addFlashAttribute("successMessage", "ご入力いただいたメールアドレスに認証メールを送信しました。メールに記載されているリンクをクリックし、会員登録を完了してください。");
		
		return "redirect:/";
	}
	
	// メールの認証機能
	@GetMapping("/signup/verify")
	public String verify(@RequestParam(name = "token") String token, Model model) {
		VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
		
		if (verificationToken != null) {
			User user = verificationToken.getUser();
			userService.enableUser(user);
			String successMessage = "会員登録が完了しました。";
			model.addAttribute("successMessage", successMessage);
		} else {
			String errorMessage = "トークンが無効です。";
			model.addAttribute("errorMessage", errorMessage);
		}
		
		return "auth/verify";
	}
}

