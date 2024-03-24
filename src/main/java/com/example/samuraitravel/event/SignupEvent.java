package com.example.samuraitravel.event;

import org.springframework.context.ApplicationEvent;

import com.example.samuraitravel.entity.User;

import lombok.Getter;

// メール認証機能
@Getter
//public class SignupEvent {
public class SignupEvent extends ApplicationEvent {
	private User user;
	private String requestUrl;
	
	public SignupEvent(Object source, User user, String requestUrl) {
		super(source);
		
		this.user = user;
		this.requestUrl = requestUrl;
	}
}
