package com.example.testmail.models;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class MailData {
    private Set<String> to;
    private String subject;
    private String body;
}