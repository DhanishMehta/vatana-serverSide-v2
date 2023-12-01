package com.capstone.grocery.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
@ConfigurationProperties
public class Secrets {
    String rzp_key_id = "rzp_test_7o9eJC3c7HeJpn";
    String rzp_key_secret="QhytvwuD22b2GM6lf0MGVDnO";
    String rzp_currency="INR";
    String rzp_company_name="VATANA";
}