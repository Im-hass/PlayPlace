package kr.co.playplace.service.user;

import kr.co.playplace.entity.user.Users;
import kr.co.playplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Service
public class UserQueryService {
    private final UserRepository userRepository;

    public Optional<Users> findByEmail(String email) {
        return userRepository.findByOuthId(email);
    }
}
