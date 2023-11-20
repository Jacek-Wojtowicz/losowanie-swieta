package com.jwojtowicz.losowanie.pair;

import com.jwojtowicz.losowanie.user.User;
import com.jwojtowicz.losowanie.user.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PairService {

    private final PairRepository repository;
    private final UserService userService;

    public Optional<String> findReceiverForGiver(String giver) {
        try {
            return Optional.of(repository.findPairDTOByGiver(giver).getReceiver());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<PairDTO> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).toList();
    }

    public void draw() {
        repository.deleteAll();
        List<User> users = userService.findAll();
        List<PairDTO> pairs = drawPairs(users);
        repository.saveAll(pairs);
    }

    private List<PairDTO> drawPairs(List<User> dbUsers) {
        List<User> users = new ArrayList<>(dbUsers);
        Collections.shuffle(users);

        List<PairDTO> pairs = new ArrayList<>();
        List<String> list = users.stream().map(User::getDrawName).map(String::toLowerCase).toList();
        if (list.contains("lukasz") || list.contains("julia")) {
            PairDTO lukaszToJulia = new PairDTO("Lukasz", "Julia");
            PairDTO juliaToLukasz = new PairDTO("Julia", "Lukasz");
            pairs.add(juliaToLukasz);
            pairs.add(lukaszToJulia);
        }

        users.removeIf(u -> u.getDrawName().equalsIgnoreCase("lukasz") || u.getDrawName().equalsIgnoreCase("julia"));
        for (int i = 0; i < users.size(); i++) {
            String giver = users.get(i).getDrawName();
            String receiver = users.get((i + 1) % users.size())
                    .getDrawName(); // Zapewnienie, że każda osoba jest giverem i receiverem
            PairDTO para = new PairDTO(giver, receiver);
            pairs.add(para);
        }

        return pairs;
    }

    public void delete() {
        repository.deleteAll();
    }
}