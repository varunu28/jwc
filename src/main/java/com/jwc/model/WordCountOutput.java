package com.jwc.model;

import lombok.Builder;

@Builder
public record WordCountOutput(long words, long lines, long bytes, long characters) {}
