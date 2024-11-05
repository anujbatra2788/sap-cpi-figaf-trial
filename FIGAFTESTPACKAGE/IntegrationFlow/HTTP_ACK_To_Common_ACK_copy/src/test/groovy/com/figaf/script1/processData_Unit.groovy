package com.figaf.script1

import com.figaf.MessageImpl
import com.figaf.utils.XMLUtils
import com.sap.gateway.ip.core.customdev.util.Message
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

import spock.lang.Shared
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.BDDMockito.given



class processData_Unit extends Specification {

    @Shared GroovyShell shell = new GroovyShell()
    @Shared Script script

    private Message msg

    def setupSpec() {
        // Load Groovy Script
        script = shell.parse(new File("src/main/resources/script/script1.groovy"))
    }

    def setup() {
        this.msg = new MessageImpl()
    }

    def "Acknowledgement of Orders has a uppercase for AcknowledgementFor Tag"() {
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

        given: "Orders Acknowledgement message"
        this.msg.setBody(body);

        when: "we execute the CPI Groovy script"
        script.processData(this.msg)

        then: "we get the AcknowledgementFor tag populated with the uppercase of its own value"
        def result = true;
        String actualValue = XMLUtils.getValueFromField(this.msg.getBody(String), "AcknowledgementFor")
        assertEquals("ORDERS", actualValue)
    }


    def "Acknowledgement of Payments should not change the AcknowledgementFor Tag"() {
        def body = '''<?xml version="1.0" encoding="UTF-8"?>
            <AcknowledgementMetadata>
                <AcknowledgementFor>Payments</AcknowledgementFor>
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

        given: "Orders Acknowledgement message"
        this.msg.setBody(body);

        when: "we execute the Groovy script"
        script.processData(this.msg)

        then: "we get the AcknowledgementFor tag populated with the of its own value without any change"
        def result = true;
        String actualValue = XMLUtils.getValueFromField(this.msg.getBody(String), "AcknowledgementFor")
        assertEquals("Payments", actualValue)
    }
}
