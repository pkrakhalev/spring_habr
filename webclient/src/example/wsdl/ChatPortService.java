
package example.wsdl;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.7-b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ChatPortService", targetNamespace = "http://www.baeldung.com/springsoap/gen", wsdlLocation = "http://localhost:8080/ws/chatapi.wsdl")
public class ChatPortService
    extends Service
{

    private final static URL CHATPORTSERVICE_WSDL_LOCATION;
    private final static WebServiceException CHATPORTSERVICE_EXCEPTION;
    private final static QName CHATPORTSERVICE_QNAME = new QName("http://www.baeldung.com/springsoap/gen", "ChatPortService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/ws/chatapi.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CHATPORTSERVICE_WSDL_LOCATION = url;
        CHATPORTSERVICE_EXCEPTION = e;
    }

    public ChatPortService() {
        super(__getWsdlLocation(), CHATPORTSERVICE_QNAME);
    }

    public ChatPortService(WebServiceFeature... features) {
        super(__getWsdlLocation(), CHATPORTSERVICE_QNAME, features);
    }

    public ChatPortService(URL wsdlLocation) {
        super(wsdlLocation, CHATPORTSERVICE_QNAME);
    }

    public ChatPortService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CHATPORTSERVICE_QNAME, features);
    }

    public ChatPortService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ChatPortService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ChatPort
     */
    @WebEndpoint(name = "ChatPortSoap11")
    public ChatPort getChatPortSoap11() {
        return super.getPort(new QName("http://www.baeldung.com/springsoap/gen", "ChatPortSoap11"), ChatPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ChatPort
     */
    @WebEndpoint(name = "ChatPortSoap11")
    public ChatPort getChatPortSoap11(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.baeldung.com/springsoap/gen", "ChatPortSoap11"), ChatPort.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CHATPORTSERVICE_EXCEPTION!= null) {
            throw CHATPORTSERVICE_EXCEPTION;
        }
        return CHATPORTSERVICE_WSDL_LOCATION;
    }

}
