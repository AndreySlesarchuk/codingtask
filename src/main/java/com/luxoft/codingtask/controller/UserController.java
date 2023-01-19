package com.luxoft.codingtask.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "API for User controller")
@ApiResponse(responseCode = "500", description = "Internal error occurred")
@RestController
@RequestMapping("users")
public class UserController {

  @GetMapping("/")
  public ResponseEntity getUser() {
    try {
      return ResponseEntity.ok("Server is working");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error");
    }
  }
}
