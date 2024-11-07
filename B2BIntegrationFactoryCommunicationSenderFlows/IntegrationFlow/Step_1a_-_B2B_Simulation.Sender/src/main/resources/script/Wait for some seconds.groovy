// =============================================================================================
// <Replace: Short description of function of Groovy script>
//
// History:
// 2023-<MM>-<DD> SAP [<Initals>]- Script created
// =============================================================================================
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

def Message processData(Message message) {
    //Body
    def body = message.getBody();
    sleep(4800);

    return message;
}