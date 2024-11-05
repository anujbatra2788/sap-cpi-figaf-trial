package com.figaf.script1

import com.figaf.MessageImpl
import com.sap.it.api.msglog.MessageLog
import com.sap.it.api.msglog.MessageLogFactory
import groovy.json.JsonSlurper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

import static org.mockito.ArgumentMatchers.any
import static org.mockito.BDDMockito.given

// src/main/resources/script/script1.groovy

@ExtendWith(MockitoExtension)
class processData_Unit {

    static GroovyShell shell = null
    static jsonSlurper = null

    @Mock(lenient = true)
    MessageLogFactory messageLogFactory

    @Mock
    MessageLog messageLog

    MessageImpl message;

    @BeforeEach
    void beforeEach() {
        given(messageLogFactory.getMessageLog(any()))
                .willReturn(messageLog)
        message = new MessageImpl()
    }

    @BeforeAll
    static void setUp() {
        shell = new GroovyShell()
        jsonSlurper = new JsonSlurper()
    }

    @Test
    void testProcessDataWithSampleBody() {
        def body = ''''''
    }

}
