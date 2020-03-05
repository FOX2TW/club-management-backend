package com.tw.clubmanagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ClubType {
    SPORTS(1, "运动"),
    ELECTRICITY_GAME(2, "电竞"),
    CHESS_CARD(3, "棋牌"),
    LEARNING(4, "学习"),
    OTHERS(0,"其它");

    @Getter
    private int id;

    @Getter
    private String name;
}
