package com.practice.chatapiserver.domain.entity;

import com.practice.chatapiserver.domain.embeddable.TimeData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    @Column(length = 50)
    private String title;
    @Column(length = 1000)
    private String content;
    @Embedded
    private TimeData timeData;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "board")
    private List<File_Board> fileBoardList = new ArrayList<>();
    @OneToMany(mappedBy = "board")
    private List<Likes> likesList = new ArrayList<>();
    @OneToMany(mappedBy = "board")
    private List<Comment> commentList = new ArrayList<>();
    public Board(Long id) {this.id = id;}
    public Board() {}
}
