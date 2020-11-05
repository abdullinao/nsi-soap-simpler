package simpler.eventhandler;

import javax.xml.namespace.QName;
import javax.xml.soap.*;

import simpler.eventhandler.eh_initializer;

public class eh_requestGenerator {

    private static String guidString;


    static void callSoapWebService(String soapEndpointUrl, String soapAction, String guid) {
        try {
            guidString=guid;

            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

            // Print the SOAP Response
            System.out.println("Ответ:");
            soapResponse.writeTo(System.out);
            System.out.println();

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\nОшибка отправки запроса на ивентхендлер!\nПроверьте правильность адреса и доступность сервиса!\n");
            e.printStackTrace();
        }
    }

    private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance(/*SOAPConstants.SOAP_1_2_PROTOCOL */);
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("SOAP запрос отправленный в ивентхендлер:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
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

        dlc_action_event.addAttribute(guid, guidString);//добавляю атрибуты в тег even:dlc-action-event
        dlc_action_event.addAttribute(dict_type, simpler.eventhandler.eh_initializer.getDict_typeString());//добавляю атрибуты в тег even:dlc-action-event
        dlc_action_event.addAttribute(state, simpler.eventhandler.eh_initializer.getStateString());//добавляю атрибуты в тег even:dlc-action-event
        dlc_action_event.addAttribute(operation_code, simpler.eventhandler.eh_initializer.getEvent());//добавляю атрибуты в тег even:dlc-action-event


        /** в результате получается строка
         * <even:dlc-action-event guid="TEST_guid" operation-code="TEST_operation_code"state="TEST_state" type="TEST_type"/>
         */


        // SOAPElement dlc_action_event = soapBody.addChildElement("GetInfoByCity", myNamespace);
        // SOAPElement soapBodyElem1 = dlc_action_event.addChildElement("USCity", myNamespace);
        //soapBodyElem1.addTextNode("New York");
    }


}
