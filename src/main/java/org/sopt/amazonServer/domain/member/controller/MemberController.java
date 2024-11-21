package org.sopt.amazonServer.domain.member.controller;

import java.util.List;
import org.sopt.amazonServer.domain.member.model.dto.SearchHistoryResponse;
import org.sopt.amazonServer.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/search-history")
    public ResponseEntity<List<SearchHistoryResponse>> getSearchHistoryList(
            @RequestHeader(name = "memberId") final Long memberId
    ) {
        return ResponseEntity.ok(memberService.fetchSearchHistoryList(memberId));
    }
}
