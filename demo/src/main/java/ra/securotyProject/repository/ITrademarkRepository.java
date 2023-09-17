package ra.securotyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.securotyProject.model.domain.Trademark;
@Repository
public interface ITrademarkRepository extends JpaRepository<Trademark,Long> {
    boolean existsByNameTrademark(String nametrademark);
}
