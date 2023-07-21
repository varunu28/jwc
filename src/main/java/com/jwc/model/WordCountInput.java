package com.jwc.model;

import lombok.Builder;

@Builder
public record WordCountInput(boolean words, boolean lines, boolean bytes, boolean characters)  {}
