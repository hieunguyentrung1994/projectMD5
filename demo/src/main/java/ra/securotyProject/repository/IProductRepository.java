package ra.securotyProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.securotyProject.model.domain.Products;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Products,Long> {
    boolean existsByNameAndId(String name,Long id);
    List<Products> findAllBySellerId(Long id);
}
