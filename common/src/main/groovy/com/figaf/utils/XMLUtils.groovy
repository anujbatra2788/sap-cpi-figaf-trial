package com.figaf.utils

class XMLUtils {

    public static String getValueFromField(String body, String field) {
        // Parse the XML string
        def xml = new XmlSlurper().parseText(body)

        // Use a dynamic XPath-like expression
        def value = xml."${field}"?.text() // Safe navigation to avoid NullPointerException
        return value
    }
}
