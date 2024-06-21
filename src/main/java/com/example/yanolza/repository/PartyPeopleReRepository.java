package com.example.yanolza.repository;

import com.example.yanolza.entity.Member;
import com.example.yanolza.entity.Party;
import com.example.yanolza.entity.PartyPeople;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyPeopleReRepository extends JpaRepository<PartyPeople,Long> {
    List<PartyPeople> findByPartyPeopleNick(Member nick);

    List<PartyPeople> findByPartyPeoplePno(Party pno);
}
