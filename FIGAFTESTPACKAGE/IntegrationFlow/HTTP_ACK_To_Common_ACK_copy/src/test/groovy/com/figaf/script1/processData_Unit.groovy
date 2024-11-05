package com.figaf.script1

import com.figaf.MessageImpl
import com.figaf.utils.XMLUtils
import com.sap.it.api.msglog.MessageLog
import com.sap.it.api.msglog.MessageLogFactory
import groovy.json.JsonSlurper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import static org.junit.jupiter.api.Assertions.*

import static org.mockito.ArgumentMatchers.any
import static org.mockito.BDDMockito.given

// src/main/resources/script/script1.groovy

@ExtendWith(MockitoExtension)
class processData_Unit {

    private def script

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

        Binding binding = new Binding()
        GroovyShell shell = new GroovyShell(binding)
        script = shell.parse(new File("src/main/resources/script/script1.groovy"))
    }

    @BeforeAll
    static void setUp() {
        shell = new GroovyShell()
        jsonSlurper = new JsonSlurper()
    }

    @Test
    void testProcessDataWithSampleBody() {
        def body = '''<?xml version="1.0" encoding="UTF-8"?>
<AcknowledgementMetadata>
    <AcknowledgementFor>Orders</AcknowledgementFor>
    <AcknowledgementForFileName>Order_1.xml</AcknowledgementForFileName>
    <SentDateTime>22-03-2024T22:32:22</SentDateTime>
    <Receipt expected="true">
        <ExpectedBy>22-03-2024T22:32:22</ExpectedBy>
        <Received>false</Received>
        <NoAcknowledementProcessCompleted>false</NoAcknowledementProcessCompleted>
        <NotificationRecipients>anuj.batra@tcs.com</NotificationRecipients>
    </Receipt>
    <Processed expected="true">
        <ExpectedBy>22-03-2024T22:32:22</ExpectedBy>
        <Received>false</Received>
        <NoAcknowledementProcessCompleted>false</NoAcknowledementProcessCompleted>
        <NotificationRecipients>anuj.batra@tcs.com</NotificationRecipients>
    </Processed>
    <Technical>
        <NoReceiptAckProcessor>/cba/acknowledgements/noFileAck</NoReceiptAckProcessor>
        <NoProcessedAckProcessor>/cba/acknowledgements/noFileAck</NoProcessedAckProcessor>
    </Technical>
</AcknowledgementMetadata>'''
        message.setBody(body)
        MessageImpl actualMessage = script.processData(message);

        String actualValue = XMLUtils.getValueFromXPath((String)actualMessage.getBody(), "//AcknowledgementFor")


        assertEquals("ORDERS", actualValue)
    }

}
