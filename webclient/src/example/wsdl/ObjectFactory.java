
package example.wsdl;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the example.wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: example.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetLastMessagesResponse }
     * 
     */
    public GetLastMessagesResponse createGetLastMessagesResponse() {
        return new GetLastMessagesResponse();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link LogoutResponse }
     * 
     */
    public LogoutResponse createLogoutResponse() {
        return new LogoutResponse();
    }

    /**
     * Create an instance of {@link LoginRequest }
     * 
     */
    public LoginRequest createLoginRequest() {
        return new LoginRequest();
    }

    /**
     * Create an instance of {@link GetLastMessagesResponse.Message }
     * 
     */
    public GetLastMessagesResponse.Message createGetLastMessagesResponseMessage() {
        return new GetLastMessagesResponse.Message();
    }

    /**
     * Create an instance of {@link LogoutRequest }
     * 
     */
    public LogoutRequest createLogoutRequest() {
        return new LogoutRequest();
    }

    /**
     * Create an instance of {@link SendMessageRequest }
     * 
     */
    public SendMessageRequest createSendMessageRequest() {
        return new SendMessageRequest();
    }

    /**
     * Create an instance of {@link GetLastMessagesRequest }
     * 
     */
    public GetLastMessagesRequest createGetLastMessagesRequest() {
        return new GetLastMessagesRequest();
    }

    /**
     * Create an instance of {@link SendMessageResponse }
     * 
     */
    public SendMessageResponse createSendMessageResponse() {
        return new SendMessageResponse();
    }

}
