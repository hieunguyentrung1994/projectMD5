package ra.securotyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.securotyProject.model.domain.Color;
@Repository
public interface IColorRepository extends JpaRepository<Color,Long> {

    boolean existsByNameColor(String name);
}
