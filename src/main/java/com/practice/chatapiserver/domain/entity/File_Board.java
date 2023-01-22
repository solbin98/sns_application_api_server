package com.practice.chatapiserver.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "BOARD")
@Getter
@Setter
public class File_Board extends File{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
    public File_Board(){}
}
