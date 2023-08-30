package com.example.demo.user.controller.port;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserUpdate;

public interface UserService {

    User getById(long id);
    void verifyEmail(long id, String certificationCode);
    User getByEmail(String email);
    void login(long id);
    User update(long id, UserUpdate userUpdate);
}
