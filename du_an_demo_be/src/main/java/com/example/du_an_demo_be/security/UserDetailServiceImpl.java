package com.example.du_an_demo_be.security;
import com.example.du_an_demo_be.model.entity.UserEntity;
import com.example.du_an_demo_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    //todo: khi người dùng đăng nhập thì Spring Security sẽ
    // cần lấy các thông tin UserDetails hiện có để kiểm tra

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // todo: kiểm tra xem user có tồn tại trong database không
        UserEntity user = this.userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("User name not found"));
        return CustomerDetailService.build(user);
    }

}