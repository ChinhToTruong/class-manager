package com.zev.studentmanager.entity.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Platform {
    @JsonProperty("web")
    WEB,

    @JsonProperty("android")
    ANDROID,

    @JsonProperty("ios")
    IOS;
}
