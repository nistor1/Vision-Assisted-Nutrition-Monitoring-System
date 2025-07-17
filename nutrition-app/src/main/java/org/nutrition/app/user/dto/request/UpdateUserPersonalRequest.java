package org.nutrition.app.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class UpdateUserPersonalRequest {
    private String fullName;

    private String address;

    private String city;

    private String country;

    private String postalCode;

    private String phoneNumber;
}

