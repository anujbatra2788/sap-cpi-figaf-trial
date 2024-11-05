package com.figaf.utils

class XMLUtils {

    public static String getValueFromXPath(String body, String xPath) {
        // Parse the XML string
        def xml = new XmlSlurper().parseText(body)

        // Use a dynamic XPath-like expression
        def value = xml."${xPath}"?.text() // Safe navigation to avoid NullPointerException
        return value
    }
}
