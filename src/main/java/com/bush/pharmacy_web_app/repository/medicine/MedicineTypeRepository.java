package com.bush.pharmacy_web_app.repository.medicine;

import com.bush.pharmacy_web_app.model.entity.medicine.MedicineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface MedicineTypeRepository extends JpaRepository<MedicineType, Integer> {
    Optional<MedicineType> findByType(String type);

    @Query("select distinct mt from MedicineType mt")
    List<MedicineType> findAllDistinctTypes();

    @Query(value = """
            WITH RECURSIVE category_path AS (
                SELECT type_id, type, parent_id
                FROM medicine_types
                WHERE type_id = :typeId
                UNION ALL
                SELECT c.type_id, c.type, c.parent_id
                FROM medicine_types c
                JOIN category_path cp ON c.type_id = cp.parent_id
            )
            SELECT * FROM category_path;
            """, nativeQuery = true)
    List<MedicineType> getTypeHierarchyPath(@PathVariable Integer typeId);

    List<MedicineType> findByTypeContainingIgnoreCaseAndParentIsNotNull(String type);

    List<MedicineType> findByTypeContainingIgnoreCase(String type);

    List<MedicineType> findByParentType(String parent);
}
