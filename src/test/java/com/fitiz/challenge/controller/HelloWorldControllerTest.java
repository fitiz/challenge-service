package com.fitiz.challenge.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HelloWorldControllerTest {

    @InjectMocks
    HelloWorldController helloWorldController;

    @Test
    void hello() {
        assertEquals( "Hello world!", helloWorldController.hello());
    }
}