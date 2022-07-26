package com.ibm.publicsectordebt.repositories;

import com.ibm.publicsectordebt.entities.BcData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Class repository para acessar dados
 */
@Repository
public interface BcDataRepository extends JpaRepository<BcData, Long>{

    @Query("SELECT b FROM BcData b WHERE b.data BETWEEN :minData AND :maxData")
    Page<BcData> findDate(Date minData, Date maxData, Pageable pageable);

    @Query("SELECT b FROM BcData b WHERE b.data BETWEEN :startDate AND :endDate")
    List<BcData> findDateStartEnd(Date startDate, Date endDate);

    @Query("SELECT b FROM BcData b WHERE DAY(b.data) = :day")
    List<BcData> findDay(Integer day);

    @Query("SELECT b FROM BcData b WHERE MONTH(b.data) = :month")
    List<BcData> findMonth(Integer month);

    @Query("SELECT b FROM BcData b WHERE YEAR(b.data) = :year")
    List<BcData> findYear(Integer year);

    @Query("SELECT b.valor FROM BcData b WHERE YEAR(b.data) = :year")
    List<Double> findValueYear(Integer year);
}
