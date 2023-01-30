package csnojam.example.csnojam.user;

import csnojam.example.csnojam.user.dto.UserJoinDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UUID join(UserJoinDto userJoinDto) throws Exception {
        try{
            checkDuplicateEmail(userJoinDto.getEmail());
            User user = userJoinDto.asEntity();
            userRepository.save(user);
            return user.getId();
        }
        catch (Exception e){
            throw e;
        }
    }

    private void checkDuplicateEmail(String email) throws Exception{
        if (userRepository.findByEmail(email).isPresent()){
            throw new Exception();
        }
    }
}
