package com.example.redis_practice.leaderboard.controller;

import com.example.redis_practice.leaderboard.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LeaderBoardController {

    private final RankingService rankingService;

    @GetMapping("/setScore")
    public Boolean setScore(@RequestParam String userId,
                            @RequestParam int score) {
        return rankingService.setUserScore(userId, score);
    }

    @GetMapping("/getRank")
    public Long getUserRank(@RequestParam String userId) {
        return rankingService.getUserRanking(userId);
    }

    @GetMapping("/getTopRank")
    public List<String> getTopRanks() {
        return rankingService.getTopRank(3);
    }
}
