package com.bush.pharmacy_web_app.repository.product;

import com.bush.pharmacy_web_app.model.entity.product.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
    Optional<ProductType> findByName(String name);

    @Query("select distinct mt from ProductType mt")
    List<ProductType> findAllDistinctTypes();

    @Query(value = """
            WITH RECURSIVE category_path AS (
                SELECT type_id, type_name, parent_id
                FROM medicine_types
                WHERE type_id = :typeId
                UNION ALL
                SELECT c.type_id, c.type_name, c.parent_id
                FROM medicine_types c
                JOIN category_path cp ON c.type_id = cp.parent_id
            )
            SELECT * FROM category_path;
            """, nativeQuery = true)
    List<ProductType> getTypeHierarchyPath(@PathVariable Integer typeId);

    List<ProductType> findByNameContainingIgnoreCaseAndParentIsNotNull(String name);

    List<ProductType> findByNameContainingIgnoreCase(String name);

    List<ProductType> findByParentName(String parentName);
}
