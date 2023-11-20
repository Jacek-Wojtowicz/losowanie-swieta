package com.jwojtowicz.losowanie.pair;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PairRepository extends CrudRepository<PairDTO, Integer> {
    PairDTO findPairDTOByGiver(String giver);
}
