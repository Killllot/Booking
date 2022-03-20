package Data.Repository;

import Data.Entity.BookingEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingRepository extends CrudRepository<BookingEntity, Long> {
    List<BookingEntity> findAll();
}
