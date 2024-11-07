
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
       def body        = message.getBody();
       def headers     = message.getHeaders();
       def contentType = headers.get("SAP_AS2MessageContentType");
       def comBody     = headers.get("SAP_COM_SND_Body");
       def newBody     = "";
    
    // message.setProperty("SAP_COM_Metadata_Available", "true");

    if (contentType == "application/XML") {
        newBody = body;
    }
    else {
        newBody = comBody;
    }
       
    message.setBody(newBody);

    return message;
}