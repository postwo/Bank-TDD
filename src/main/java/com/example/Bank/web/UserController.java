package com.example.Bank.web;

import com.example.Bank.Service.UserService;
import com.example.Bank.dto.ResponseDto;
import com.example.Bank.dto.user.UserReqDto;
import com.example.Bank.dto.user.UserRespDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserReqDto.JoinReqDto joinReqDto, BindingResult bindingResult){ // w-xxx-form 으로 안받고 json으로 받기 위해 requestbody를 사용
        UserRespDto.JoinRespDto  joinRespDto = userService.회원가입(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1,"회원가입 성공",joinRespDto), HttpStatus.CREATED);
    }
}
