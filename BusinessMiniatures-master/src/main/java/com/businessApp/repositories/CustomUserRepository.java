package com.businessApp.repositories;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.businessApp.model.User;
@Transactional
public interface CustomUserRepository
{
	public Object updateUser(User user) throws Exception;
	public String saveUser(User user) throws Exception;
	public List<User> listUser(String id) throws Exception;
	public User getUserById(String userId) throws Exception;

}
