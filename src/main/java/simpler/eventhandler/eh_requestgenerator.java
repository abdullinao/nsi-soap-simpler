package simpler.eventhandler;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.util.Scanner;


public class eh_requestgenerator {

    private static String endpoint;
    private static String dict_typeString;
    private static String stateString;
    private static String event;


    public static void eh_initialize() {
        /**
         * <even:dlc-action-event document-guid="?" document-type="?" document-state="?" operation-code="?">
         */
        Scanner sc = new Scanner(System.in);
        System.out.println("адрес эвентхендлера: ");
        endpoint = sc.nextLine();
        System.out.println("document-type(REF_UBPandNUBP,REF_PersAccount...): ");
        dict_typeString = sc.nextLine();
        System.out.println("document-state(ACTIVE/ARCHIVE...): ");
        stateString = sc.nextLine();
        System.out.println("operation-code(CreateRecord/toArchive...): ");
        event = sc.nextLine();
        String soapAction = "dlc-action-event";

        callSoapWebService(endpoint, soapAction);
    }


    private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();
        QName guid = new QName("document-guid");//добавляем атрибут guid к запросу
        QName dict_type = new QName("document-type");//добавляем атрибут type к запросу
        QName state = new QName("document-state");//добавляем атрибут тест к запросу
        QName operation_code = new QName("operation-code");//добавляем атрибут тест к запросу
        String myNamespace = "even";
        String myNamespaceURI = "http://www.otr.ru/ufos/dlc/events";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

            /*
            Constructed SOAP Request Message:
            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:even="http://www.otr.ru/ufos/dlc/events">
                <SOAP-ENV:Header/>
                <SOAP-ENV:Body>
                 <even:dlc-action-event guid="TEST_guid" operation-code="TEST_operation_code"
                 state="TEST_state" type="TEST_type"/>
                </SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
            */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement dlc_action_event =
                soapBody.addChildElement("dlc-action-event", myNamespace);//добавляю тег even:dlc-action-event в боди

        dlc_action_event.addAttribute(guid, "TEST_guid");//добавляю атрибуты в тег even:dlc-action-event
        dlc_action_event.addAttribute(dict_type, dict_typeString);//добавляю атрибуты в тег even:dlc-action-event
        dlc_action_event.addAttribute(state, stateString);//добавляю атрибуты в тег even:dlc-action-event
        dlc_action_event.addAttribute(operation_code, event);//добавляю атрибуты в тег even:dlc-action-event

        // SOAPElement dlc_action_event = soapBody.addChildElement("GetInfoByCity", myNamespace);
        // SOAPElement soapBodyElem1 = dlc_action_event.addChildElement("USCity", myNamespace);
        //soapBodyElem1.addTextNode("New York");
    }

    private static void callSoapWebService(String soapEndpointUrl, String soapAction) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
    }

    private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }

}
