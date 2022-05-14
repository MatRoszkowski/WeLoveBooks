package com.mateuszroszkowski.WeLoveBooks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateDTO {
    private int rate;

    public RateDTO() {
    }

    public RateDTO(int rate) {
        this.rate = rate;
    }
}
