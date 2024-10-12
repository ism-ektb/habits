package ru.ism.testBank.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.ism.testBank.domain.dto.UserPatchDto;
import ru.ism.testBank.domain.model.User;

@Component
public class UserPatcher {


    public User patch(User user, User patch){

        if (patch.getEmail() != null) user.setEmail(patch.getEmail());
        if (patch.getName() != null) user.setName(patch.getName());
        if (patch.getPassword() != null) user.setPassword(patch.getPassword());
        return user;

    }

    public User patch(User user, UserPatchDto patch){

        if (patch.getEmail() != null) user.setEmail(patch.getEmail());
        if (patch.getName() != null) user.setName(patch.getName());

        return user;

    }
}
