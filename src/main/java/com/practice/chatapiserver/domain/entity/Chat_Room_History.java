package com.practice.chatapiserver.domain.entity;

import com.practice.chatapiserver.domain.embeddable.TimeData;
import jakarta.persistence.*;

@Entity
public class Chat_Room_History {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_history_id")
    private Long id;
    @Column(length = 500)
    private String message;
    @Embedded
    private TimeData timeData;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private Chat_Room chat_room;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    public Chat_Room_History(){}
}
