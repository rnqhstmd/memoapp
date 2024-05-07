package org.mjulikelion.week3assignment.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.week3assignment.dto.requset.memo.MemoCreateDto;
import org.mjulikelion.week3assignment.dto.requset.memo.MemoUpdateDto;
import org.mjulikelion.week3assignment.dto.response.ResponseDto;
import org.mjulikelion.week3assignment.dto.response.memo.LikeListResponseData;
import org.mjulikelion.week3assignment.dto.response.memo.MemoListResponseData;
import org.mjulikelion.week3assignment.dto.response.memo.MemoResponseData;
import org.mjulikelion.week3assignment.service.MemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/memos")
@Slf4j
public class MemoController {

    private final MemoService memoService;

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createMemo(@RequestBody @Valid MemoCreateDto memoCreateDto,
                                                        @RequestHeader("User-Id") UUID userId) {
        memoService.createMemo(userId, memoCreateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "메모 생성 완료"), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<MemoListResponseData>> getAllMemosByUserId(@RequestHeader("User-Id") UUID userId) {
        MemoListResponseData memos = memoService.getAllMemosByWriterId(userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "메모 조회 완료", memos), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<MemoResponseData>> getMemoByMemoId(@PathVariable("id") UUID memoId) {
        MemoResponseData memoResponseData = memoService.getMemoByMemoId(memoId);
        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, "메모 조회 완료", memoResponseData));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> updateMemoByMemoId(@RequestHeader("User-Id") UUID userId,
                                                                @PathVariable("id") UUID memoId,
                                                                @RequestBody @Valid MemoUpdateDto memoUpdateDto) {
        memoService.updateMemoByMemoId(userId, memoId, memoUpdateDto);
        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, "메모 수정 성공"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteMemoByMemoId(@RequestHeader("User-Id") UUID userId,
                                                                @PathVariable("id") UUID memoId) {
        memoService.deleteMemoByMemoId(userId, memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "메모 삭제 완료"), HttpStatus.OK);
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<ResponseDto<Void>> like(@PathVariable("id") UUID memoId,
                                                  @RequestHeader("User-Id") UUID userId) {
        memoService.like(memoId, userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "좋아요 생성 완료"), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/likes")
    public ResponseEntity<ResponseDto<Void>> deleteLike(@PathVariable("id") UUID memoId,
                                                        @RequestHeader("User-Id") UUID userId) {
        memoService.unlike(memoId, userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.ACCEPTED, "좋아요 삭제 완료"), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<ResponseDto<LikeListResponseData>> getAllLikesInfo(@PathVariable("id") UUID memoId) {
        LikeListResponseData likeListResponseData = memoService.getAllLikesInfo(memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "좋아요 목록 조회 성공", likeListResponseData), HttpStatus.OK);
    }


}
