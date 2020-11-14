package simpler.eventhandler;

import java.io.*;
import java.util.Scanner;

import simpler.main;

public class eh_initializer {

    private static String endpoint;
    private static String dict_typeString;
    private static String stateString;
    private static String event;
    private static final File eh_file_guids = new File("eventhandler_guids.txt");
    private static final String soapAction = "dlc-action-event";

    public static String getDict_typeString() {
        return dict_typeString;
    }

    public static String getStateString() {
        return stateString;
    }

    public static String getEvent() {
        return event;
    }

    public static void eh_initialize() throws IOException {
        readTest();
        //Просим ввести необходимые данные для генерации запроса.
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\nадрес эвентхендлера: ");
        endpoint = sc.nextLine();

        System.out.println("\ndocument-type(REF_UBPandNUBP, REF_PersAccount...): ");
        dict_typeString = sc.nextLine();

        System.out.println("\ndocument-state (ACTIVE/ARCHIVE...): ");
        stateString = sc.nextLine();

        System.out.println("\noperation-code(CreateRecord/toArchive...): ");
        event = sc.nextLine();


        readFile();
    }

    public static void readFile() throws IOException {
        System.out.println("\n\n*****************НАЧАЛО РАБОТЫ*****************\n\n");
        BufferedReader reader = new BufferedReader(new FileReader(eh_file_guids));//каждый раз открываю ридер, чтоб сбросить его к началу
        String guid = reader.readLine();

        try {
            while (guid != null) {
                System.out.println("\n\nработаю с гуидом " + guid);
                eh_requestGenerator.callSoapWebService(endpoint, soapAction, guid); //создаем соап запрос для этого гуида
                // считываем остальные строки в цикле
                guid = reader.readLine();
            }

        } catch (FileNotFoundException e) {

            System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!" +
                    "\nФайл eventhandler_guids.txt не найден!" +
                    "\n!!!!!!!!!!!!!!!!!!!!!!!!!!");

            main.run();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n\n*****************КОНЕЦ РАБОТЫ*****************\n\n");

        reader.close();
        main.run();
    }

    public static void readTest() throws IOException {
      //  System.out.flush();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(eh_file_guids));//каждый раз открываю ридер, чтоб сбросить его к началу
        } catch (FileNotFoundException e) {

            System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!" +
                    "\nФайл eventhandler_guids.txt не найден!" +
                    "\n!!!!!!!!!!!!!!!!!!!!!!!!!!");

            main.run();

        }

    }

}
