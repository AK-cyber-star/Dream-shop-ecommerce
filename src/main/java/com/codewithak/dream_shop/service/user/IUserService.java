package com.codewithak.dream_shop.service.user;

import com.codewithak.dream_shop.model.User;
import com.codewithak.dream_shop.request.CreateUserRequest;
import com.codewithak.dream_shop.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

}
