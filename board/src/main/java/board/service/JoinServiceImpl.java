package board.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import board.dto.JoinDto;
import board.entity.UserEntity;
import board.repository.UserRepository;

@Service
public class JoinServiceImpl implements JoinService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;

    public JoinServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }
    
    @Override
    public boolean joinProcess(JoinDto joinDto) {
        if (userRepository.existsByUsername(joinDto.getUsername())) {
            return false;
        }
        
        if (!joinDto.checkPassword()) {
            return false;
        }
        
        UserEntity userEntity = new ModelMapper().map(joinDto, UserEntity.class);
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole("ROLE_USER");
        
        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }

}
