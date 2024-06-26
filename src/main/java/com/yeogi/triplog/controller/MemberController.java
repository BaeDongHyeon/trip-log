package com.yeogi.triplog.controller;

import com.yeogi.triplog.domain.member.form.EmailCheckRequest;
import com.yeogi.triplog.domain.member.form.LoginForm;
import com.yeogi.triplog.domain.member.form.MemberSignUpForm;
import com.yeogi.triplog.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signUpPage(@ModelAttribute MemberSignUpForm memberSignUpForm) {
        return "member/signUpPage";
    }

    @PostMapping("/signup")
    public String signUpMember(@Valid @ModelAttribute MemberSignUpForm memberSignUpForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "member/signUpPage";
        }

        memberService.signUpMember(memberSignUpForm);
        return "redirect:/";
    }

    @PostMapping("/email/duplicate/check")
    @ResponseBody
    public ResponseEntity<?> emailDuplicateCheck(@Valid @RequestBody EmailCheckRequest emailCheckRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getAllErrors().forEach(error -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(memberService.isExistsEmail(emailCheckRequest));
    }

    @GetMapping("/login")
    public String loginPage(@ModelAttribute LoginForm loginForm) {
        return "member/loginPage";
    }

    @PostMapping("/login")
    public String loginMember(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "member/loginPage";
        }

        return "forward:/login/process";
    }

    @GetMapping("/login/fail")
    public String loginFail(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception,
                            Model model) {

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        model.addAttribute("loginForm", new LoginForm());
        return "/member/loginPage";
    }
}
