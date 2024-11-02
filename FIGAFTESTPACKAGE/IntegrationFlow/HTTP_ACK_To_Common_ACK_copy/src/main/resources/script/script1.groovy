import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.xml.*;


def Message processData(Message message) {
    //Body
    def body = message.getBody(String) as String;
    
    def root = new XmlParser().parseText(body);
    
    def ackFor = root.AcknowledgementFor.text()
    if (ackFor.startsWith("Order")) {
        root.'**'.find { it.name() == 'AcknowledgementFor' }.value = ackFor.toUpperCase()
    }


    def xmlOutput = new StringWriter()
    def xmlNodePrinter = new XmlNodePrinter(new PrintWriter(xmlOutput))
    xmlNodePrinter.print(root)


    def xml = XmlUtil.serialize(root)
    message.setBody(xml)
    return message;
}