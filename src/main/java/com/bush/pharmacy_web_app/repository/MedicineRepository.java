package com.bush.pharmacy_web_app.repository;

import com.bush.pharmacy_web_app.repository.entity.medicine.Medicine;
import com.bush.pharmacy_web_app.repository.filter.FilterMedicineRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long>, FilterMedicineRepository {
    @Query("select distinct m from Medicine m " +
            "join StorageItems s on m = s.medicine")
    Slice<Medicine> findDistinctMedicineStorageLocated(Pageable pageable);

    List<Medicine> findByNameContainingIgnoreCase(String name);

    @Query("select distinct m.type from Medicine m ")
    List<String> findDistinctMedicineType();
    @Query("select distinct m.manufacturer from Medicine m ")
    List<String> findDistinctMedicineManufacturer();
}
