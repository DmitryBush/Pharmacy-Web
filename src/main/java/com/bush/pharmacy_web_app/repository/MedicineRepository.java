package com.bush.pharmacy_web_app.repository;

import com.bush.pharmacy_web_app.repository.entity.Medicine;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
    @Query("select distinct m from Medicine m " +
            "join StorageItems s on m = s.medicine")
    Slice<Medicine> findDistinctMedicineStorageLocated(Pageable pageable);
}
