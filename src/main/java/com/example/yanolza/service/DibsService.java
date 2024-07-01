package com.example.yanolza.service;

import com.example.yanolza.dto.TravelDto;
import com.example.yanolza.entity.Dibs;
import com.example.yanolza.entity.Member;
import com.example.yanolza.entity.Travel;
import com.example.yanolza.repository.DibsRepository;
import com.example.yanolza.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DibsService {
    public final DibsRepository dibsRepository;
    public final MemberService memberService;
    public final TravelRepository travelRepository;

    public boolean dibs(Long tno) {
        try {
            Member member = memberService.memberIdFindMember();
            dibsRepository.save(Dibs.builder()
                    .dnick(member)
                    .tno(tno)
                    .build());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean undibs(Long tno) {
        try {
            Member member = memberService.memberIdFindMember();
            if (member != null) {
                dibsRepository.deleteByDnickAndTno(member, tno);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<TravelDto> dibsList(){
        Member member = memberService.memberIdFindMember();
        List<Dibs> dibs = dibsRepository.findByDnick(member);
        List<TravelDto> list = new ArrayList<>();
        for (Dibs dib : dibs){
           Travel travel = travelRepository.findByTno(dib.getTno()).orElseThrow(() -> new NoSuchElementException(dib.getTno() + "에대해 찾을수 없음"));
           list.add(TravelDto.of(travel));
        }
        return list;
    }
}
