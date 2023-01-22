package com.practice.chatapiserver.domain.entity;

import com.practice.chatapiserver.domain.embeddable.TimeData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@Getter
@Setter
public class File {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String path;
    @Column
    private Double size;
    @Embedded
    private TimeData timeData;
    @Column
    private Boolean used;
    public File(){}
}
