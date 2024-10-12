package ru.ism.testBank.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.ism.testBank.domain.dto.UserPatchDto;
import ru.ism.testBank.domain.mapper.UserPatcher;

import ru.ism.testBank.domain.model.User;
import ru.ism.testBank.exception.exception.BaseRelationshipException;
import ru.ism.testBank.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository repository;

    private final UserPatcher userPatcher;


    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return repository.save(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        if (repository.existsByName(user.getName())) {
            throw new BaseRelationshipException("Пользователь с таким именем уже существует");
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new BaseRelationshipException("Пользователь с таким email уже существует");
        }

        User saveUser = save(user);


        return saveUser;
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    /**
     * Изменение данных от текущего пользователя
     *
     * @return обновленные данные о текущем пользователе
     */
    public User patchUserInfo(User patch) {
        User user = getCurrentUser();
        user = userPatcher.patch(user, patch);
        return repository.save(user);
    }

    public User patchUserInfo(UserPatchDto patch) {
        User user = getCurrentUser();
        user = userPatcher.patch(user, patch);
        return repository.save(user);
    }

    /**
     * Получение списка всех пользоватеой
     */
    public List<User> getUsers() {
        return repository.findAll();
    }

    public void deleteUser(String email){
        repository.deleteUserByEmail(email);
    }


    /**
     * Метод поиска информации о пользователе
     * @param birthday в выборке будут учавствовать пользователи с датой рождения больше указанной
     * @param phone В выборке будут учавствовать пользоватери с указанным номером
     * @param firstname В выборке будут учавствовать пользователи имя которых содержит указанный текст
     * @param lastname В выборке будут учавствовать пользователи фамилия которых содержит указанный текст
     * @param email
     * @param pageRequest
     * @return возвращается список пользователей
     */
    public List<User> getAllUsers(LocalDate birthday, String phone, String firstname,
                                  String lastname, String email, PageRequest pageRequest) {
        return null;
        //    return repository.findByManyParam(lastname, firstname, email, birthday, phone, pageRequest);
    }

    
}
