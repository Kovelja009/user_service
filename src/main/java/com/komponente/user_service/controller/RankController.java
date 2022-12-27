package com.komponente.user_service.controller;

import com.komponente.user_service.dto.RankDto;
import com.komponente.user_service.service.RankService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rank")
public class RankController {
    private RankService rankService;

    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    @GetMapping
    public ResponseEntity<Page<RankDto>> getAllRanks(Pageable pageable) {
        return new ResponseEntity<>(rankService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RankDto> addRank(@RequestBody @Valid RankDto rankDto) {
        return new ResponseEntity<>(rankService.addRank(rankDto), HttpStatus.CREATED);
    }

    @GetMapping("/getRankByDays")
    public ResponseEntity<RankDto> getRankByDays(@RequestParam int days) {
        return new ResponseEntity<>(rankService.getRankByDays(days), HttpStatus.OK);
    }

}
