package com.example.yanolza.repository;

import com.example.yanolza.entity.Dibs;
import com.example.yanolza.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DibsRepository extends JpaRepository<Dibs,Long> {
    Optional<Dibs> findByTno(String dibsNick);
    List<Dibs> findByDnick(Member dnick);
    void deleteByDnickAndTno(Member dnick, Long Tno);
}
