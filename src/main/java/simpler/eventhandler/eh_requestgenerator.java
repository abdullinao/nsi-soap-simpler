package simpler.eventhandler;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.util.Scanner;


public class eh_requestgenerator {

    public static void eh_initialize() {
        /**
         * <even:dlc-action-event document-guid="?" document-type="?" document-state="?" operation-code="?">
         */
        Scanner sc = new Scanner(System.in);
        System.out.println("адрес эвентхендлера: ");
        String endpoint = sc.nextLine();
        System.out.println("document-type(REF_UBPandNUBP,REF_PersAccount...): ");
        String dict = sc.nextLine();
        System.out.println("document-state(ACTIVE/ARCHIVE...): ");
        String state = sc.nextLine();
        System.out.println("operation-code(CreateRecord/toArchive...): ");
        String event = sc.nextLine();
        String soapAction = "dlc-action-event";

        callSoapWebService(endpoint, soapAction);
    }



    private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();
        QName guid = new QName ("guid");//добавляем атрибут guid к запросу
        QName type = new QName ("type");//добавляем атрибут type к запросу
        QName state = new QName ("state");//добавляем атрибут тест к запросу
        QName operation_code = new QName ("operation-code");//добавляем атрибут тест к запросу
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
                    <even:dlc-action-event>

                    </even:dlc-action-event>
                </SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
            */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("dlc-action-event", myNamespace);//добавляю тег even:dlc-action-event в боди

        soapBodyElem.addAttribute(guid, "TEST_guid");
        soapBodyElem.addAttribute(type, "TEST_type");
        soapBodyElem.addAttribute(state, "TEST_state");
        soapBodyElem.addAttribute(operation_code, "TEST_operation_code");

        /**
         * <even:dlc-action-event test="TEST"  >
         */
       // SOAPElement soapBodyElem = soapBody.addChildElement("GetInfoByCity", myNamespace);
       // SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("USCity", myNamespace);
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
