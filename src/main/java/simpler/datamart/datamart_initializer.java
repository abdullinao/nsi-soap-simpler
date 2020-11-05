package simpler.datamart;

import simpler.eventhandler.eh_requestGenerator;

import java.io.*;
import java.util.Scanner;

/**
 * <soapenv:Header/>
 * <soapenv:Body>
 * <ws:RecordRequest>
 * <ns:Requestor>ASFK</ns:Requestor> +
 * <ns:Dictionary>
 * <ns:Code>REF_UBPandNUBP</ns:Code> +
 * <ns:Version>2.0</ns:Version> будет по дефолту
 * </ns:Dictionary>
 * <ns:GUID>87e3a303-3b21-f073-de05-347cb1fac4d3</ns:GUID> из файла
 * <ns:Format>1.4.2</ns:Format> +
 * </ws:RecordRequest>
 * </soapenv:Body>
 * </soapenv:Envelope>
 */

public class datamart_initializer {

    private static String endpoint;
    private static String dict_typeString;
    private static String tffString;
    private static final File dm_file_guids = new File("datamart_guids.txt");
    private static final String soapAction = "RecordRequest";

    public static String getEndpoint() {
        return endpoint;
    }

    public static String getDict_typeString() {
        return dict_typeString;
    }

    public static String getTffString() {
        return tffString;
    }

    public static void dm_initialize() throws IOException {

        Scanner sc = new Scanner(System.in);

        System.out.println("адрес датамарта: ");
          endpoint = sc.nextLine();

        System.out.println("Тип справочника (REF_UBPandNUBP, REF_PersAccount...):");
        dict_typeString = sc.nextLine();

        System.out.println("ТФФ (1.4.2, 1.4.1....): ");
        tffString = sc.nextLine();

        readFile();
    }

    public static void readFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(dm_file_guids));//каждый раз открываю ридер, чтоб сбросить его к началу
        String guid = reader.readLine();

        try {
            while (guid != null) {
                System.out.println("работаю с гуидом " + guid);
                eh_requestGenerator.callSoapWebService(endpoint, soapAction, guid); //создаем соап запрос для этого гуида
                // считываем остальные строки в цикле
                guid = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("файл eventhandler_guids.txt не найден!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        reader.close();

    }
}
