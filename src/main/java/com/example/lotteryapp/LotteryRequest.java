package com.example.lotteryapp;

import lombok.Data;

@Data // Lombokのアノテーション。getter/setterなどを自動生成
public class LotteryRequest {
    private String participantsText; // 参加者リスト（改行区切り）
    private int numberOfWinners;     // 抽選人数
}
