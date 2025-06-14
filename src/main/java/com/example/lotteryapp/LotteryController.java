package com.example.lotteryapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays; // Arraysクラスを追加

@Controller
public class LotteryController {

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("lotteryRequest", new LotteryRequest());
        model.addAttribute("winners", new ArrayList<String>()); // 初回表示時に空のリストを渡す
        return "index"; // src/main/resources/templates/index.html を表示
    }

    @PostMapping("/draw")
    public String drawLottery(@ModelAttribute LotteryRequest lotteryRequest, Model model) {
        String participantsText = lotteryRequest.getParticipantsText();
        int numberOfWinners = lotteryRequest.getNumberOfWinners();

        // 入力されたテキストを改行で分割し、空行を除去してリストに変換
        List<String> participants = Arrays.stream(participantsText.split("\\r?\\n"))
                                      .map(String::trim) // 各要素の前後の空白を除去
                                      .filter(s -> !s.isEmpty()) // 空の文字列を除去
                                      .collect(Collectors.toList());

        List<String> winners = new ArrayList<>();

        if (participants.isEmpty()) {
            model.addAttribute("errorMessage", "参加者が入力されていません。");
        } else if (numberOfWinners <= 0) {
            model.addAttribute("errorMessage", "抽選人数は1人以上を指定してください。");
        } else if (numberOfWinners > participants.size()) {
            model.addAttribute("errorMessage", "抽選人数が参加者の総数を超えています。");
        } else {
            // リストをシャッフル
            Collections.shuffle(participants);
            // 指定された人数分だけ抽選
            winners = participants.subList(0, numberOfWinners);
        }

        model.addAttribute("lotteryRequest", lotteryRequest); // 入力値を保持
        model.addAttribute("winners", winners);
        return "index"; // 結果を表示するために同じHTMLテンプレートを再利用
    }
}
