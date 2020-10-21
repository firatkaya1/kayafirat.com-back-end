package com.kayafirat.model.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kayafirat.entity.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "userDetailExcept", types = User.class)
public interface UserDetailExcept {

    @JsonProperty("userId")
    String getUser_Id();

    @JsonProperty("UserProfilePhoto")
    String getUser_profile_photo();

    @JsonProperty("userName")
    String getUser_name();

    @JsonProperty("userEmail")
    String getUser_email();

    @JsonProperty("userRole")
    String getUser_role();

    @JsonProperty("isVerify")
    String getIs_verification();

}
