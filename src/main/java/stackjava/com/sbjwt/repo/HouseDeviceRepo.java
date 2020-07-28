package stackjava.com.sbjwt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import stackjava.com.sbjwt.entities.HouseDeviceEntity;
import stackjava.com.sbjwt.entities.UserEntity;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface HouseDeviceRepo extends JpaRepository<HouseDeviceEntity, Integer> {

    Optional<HouseDeviceEntity> findByName(String name);

    HouseDeviceEntity getByName(String name);

    Optional<HouseDeviceEntity> findById(int id);

//    @Procedure(value = "allHouseDeviceByUserId")
//    int getHouseDeviceById(int id);

    @Query(value = " call allHouseDeviceByUserName(:userName)", nativeQuery = true)
    List<HouseDeviceEntity> getHouseDeviceByUserName(@Param("userName") String userName);

//    @Transactional
//    @Procedure(procedureName = "allHouseDeviceByUserId")
//    List<HouseDeviceEntity> getHouseDeviceById(@Param("userId") int userId);

}
