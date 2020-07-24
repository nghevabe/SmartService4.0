package stackjava.com.sbjwt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import stackjava.com.sbjwt.entities.UserEntity;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);

    UserEntity getByUsername(String username);

    Optional<UserEntity> findById(int id);

}
