package com.dot.ai.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String firstName;
    private String lastName;
    private String otherName;
    private String stateOrigin;
    private String gender;
    private String address;
    private String email;
    private String phoneNumber;
    private String alternatePhonenumber;
}