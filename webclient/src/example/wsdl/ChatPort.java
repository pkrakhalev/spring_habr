
package example.wsdl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.7-b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ChatPort", targetNamespace = "http://www.baeldung.com/springsoap/gen")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ChatPort {


    /**
     * 
     * @param getLastMessagesRequest
     * @return
     *     returns example.wsdl.GetLastMessagesResponse
     */
    @WebMethod
    @WebResult(name = "getLastMessagesResponse", targetNamespace = "http://www.baeldung.com/springsoap/gen", partName = "getLastMessagesResponse")
    public GetLastMessagesResponse getLastMessages(
        @WebParam(name = "getLastMessagesRequest", targetNamespace = "http://www.baeldung.com/springsoap/gen", partName = "getLastMessagesRequest")
        GetLastMessagesRequest getLastMessagesRequest);

    /**
     * 
     * @param sendMessageRequest
     * @return
     *     returns example.wsdl.SendMessageResponse
     */
    @WebMethod
    @WebResult(name = "sendMessageResponse", targetNamespace = "http://www.baeldung.com/springsoap/gen", partName = "sendMessageResponse")
    public SendMessageResponse sendMessage(
        @WebParam(name = "sendMessageRequest", targetNamespace = "http://www.baeldung.com/springsoap/gen", partName = "sendMessageRequest")
        SendMessageRequest sendMessageRequest);

    /**
     * 
     * @param loginRequest
     * @return
     *     returns example.wsdl.LoginResponse
     */
    @WebMethod
    @WebResult(name = "loginResponse", targetNamespace = "http://www.baeldung.com/springsoap/gen", partName = "loginResponse")
    public LoginResponse login(
        @WebParam(name = "loginRequest", targetNamespace = "http://www.baeldung.com/springsoap/gen", partName = "loginRequest")
        LoginRequest loginRequest);

    /**
     * 
     * @param logoutRequest
     * @return
     *     returns example.wsdl.LogoutResponse
     */
    @WebMethod
    @WebResult(name = "logoutResponse", targetNamespace = "http://www.baeldung.com/springsoap/gen", partName = "logoutResponse")
    public LogoutResponse logout(
        @WebParam(name = "logoutRequest", targetNamespace = "http://www.baeldung.com/springsoap/gen", partName = "logoutRequest")
        LogoutRequest logoutRequest);

}