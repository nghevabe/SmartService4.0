package stackjava.com.sbjwt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import stackjava.com.sbjwt.entities.DeviceActivityEntity;

import java.util.List;
import java.util.Optional;

public interface DeviceActivityRepo extends JpaRepository<DeviceActivityEntity, Integer> {

    //Optional<DeviceActivityEntity> findByName(String name);

    //DeviceActivityEntity getByName(String name);

    Optional<DeviceActivityEntity> findById(int id);

    @Query(value = " call allDeviceActivityByUserName(:userName)", nativeQuery = true)
    List<DeviceActivityEntity> getDeviceActivityByUserName(@Param("userName") String userName);

}
