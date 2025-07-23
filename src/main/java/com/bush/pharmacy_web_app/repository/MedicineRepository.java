package com.bush.pharmacy_web_app.repository;

import com.bush.pharmacy_web_app.repository.entity.manufacturer.Manufacturer;
import com.bush.pharmacy_web_app.repository.entity.medicine.Medicine;
import com.bush.pharmacy_web_app.repository.entity.medicine.MedicineType;
import com.bush.pharmacy_web_app.repository.filter.FilterMedicineRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long>, FilterMedicineRepository {
    @Query("select distinct m from Medicine m " +
            "join StorageItems s on m = s.medicine")
    Slice<Medicine> findDistinctMedicineStorageLocated(Pageable pageable);

    List<Medicine> findByNameContainingIgnoreCase(String name);

    @Query("select distinct m.manufacturer from Medicine m ")
    List<Manufacturer> findDistinctMedicineManufacturer();

    @Query(value = """
            WITH RECURSIVE subcategories AS (
                        SELECT type_id
                        FROM medicine_types\s
                        WHERE type_id = :typeId
                       \s
                        UNION ALL
                       \s
                        SELECT c.type_id
                        FROM medicine_types c
                        INNER JOIN subcategories s ON c.parent_id = s.type_id
                    )
            SELECT DISTINCT p.*\s
            FROM medicine p
            JOIN product_categories pc ON p.medicine_id = pc.product_id
            WHERE pc.category_id IN (SELECT type_id FROM subcategories)
            """, nativeQuery = true)
    List<Medicine> findAllMedicineByTypeId(@PathVariable Integer typeId);
}
