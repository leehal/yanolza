package com.example.yanolza.repository;

import com.example.yanolza.entity.Calendar;
import com.example.yanolza.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar,Long> {
    List<Calendar> findByCaDateAndCalenderPno(LocalDateTime date, Party pno);

}
