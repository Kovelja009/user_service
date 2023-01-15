package com.komponente.user_service.controller;

import com.komponente.user_service.dto.RankDto;
import com.komponente.user_service.security.CheckSecurity;
import com.komponente.user_service.service.RankService;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rank")
public class RankController {
    private RankService rankService;

    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<List<RankDto>> getAllRanks() {
        return new ResponseEntity<>(rankService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<RankDto> addRank(@RequestHeader("Authorization") String authorization, @RequestBody @Valid RankDto rankDto) {
        return new ResponseEntity<>(rankService.addRank(rankDto), HttpStatus.CREATED);
    }

    @GetMapping("/getRankByDays")
    public ResponseEntity<RankDto> getRankByDays(@RequestParam @Valid int days) {
        return new ResponseEntity<>(rankService.getRankByDays(days), HttpStatus.OK);
    }

}
