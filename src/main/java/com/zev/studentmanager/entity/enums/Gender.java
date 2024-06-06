package com.zev.studentmanager.entity.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Gender {
    @JsonProperty("male")
    MALE,

    @JsonProperty("female")
    FEMALE,

    @JsonProperty("other")
    OTHER;

}
