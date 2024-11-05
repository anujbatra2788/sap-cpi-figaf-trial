package com.figaf.script1

import com.sap.it.api.msglog.MessageLog
import com.sap.it.api.msglog.MessageLogFactory
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

// src/main/resources/script/script1.groovy

@ExtendWith(MockitoExtension)
class processData_Unit {

    @Mock(lenient = true)
    MessageLogFactory messageLogFactory

    @Mock
    MessageLog messageLog


}
