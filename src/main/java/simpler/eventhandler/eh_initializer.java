package simpler.eventhandler;

import java.util.Scanner;

public class eh_initializer {

    private static String endpoint;
    private static String dict_typeString;
    private static String stateString;
    private static String event;

    public static String getDict_typeString() {
        return dict_typeString;
    }

    public static String getStateString() {
        return stateString;
    }

    public static String getEvent() {
        return event;
    }

    public static void eh_initialize() {
 //Просим ввести необходимые данные для генерации запроса.
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

        String guid = "ASDASDASDASDA-ASDASDASDASD-ASDASDasdaAS-1231231231231";

        eh_requestGenerator.callSoapWebService(endpoint, soapAction,guid);


    }

}
