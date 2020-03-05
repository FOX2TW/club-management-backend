package com.tw.clubmanagement.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ClubType {
    SPORTS(1, "运动"),
    ELECTRICITY_GAME(2, "电竞"),
    CHESS_CARD(3, "棋牌"),
    OTHERS(0,"其它");

    private int id;
    private String name;
}
