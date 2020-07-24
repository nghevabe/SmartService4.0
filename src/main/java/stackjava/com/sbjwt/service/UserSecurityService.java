package stackjava.com.sbjwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import stackjava.com.sbjwt.entities.UserEntity;
import stackjava.com.sbjwt.repo.UserRepo;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserEntity userEntity = userRepo.findByUsername(s).orElseThrow( () -> new UsernameNotFoundException(s + " not found") );

        return new User(userEntity.getUsername() , userEntity.getPassword() , null);

//        UserEntity userEntity = userRepo.findByUsername(s);
//
//        if(userEntity != null) {
//            return new User(userEntity.getUsername(), userEntity.getPassword(), null);
//        } else {
//            return null;
//        }
    }
}
