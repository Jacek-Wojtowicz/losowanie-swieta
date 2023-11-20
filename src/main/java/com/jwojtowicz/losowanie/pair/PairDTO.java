package com.jwojtowicz.losowanie.pair;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "pairs")
@Entity
@NoArgsConstructor
public class PairDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String giver;
    private String receiver;

    public PairDTO(String giver, String receiver) {
        this.giver = giver;
        this.receiver = receiver;
    }
}
