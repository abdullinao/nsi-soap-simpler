package simpler;

import simpler.eventhandler.*;

import java.io.IOException;
import java.util.Scanner;

/**
 * todo
 * вывести отправку и обработку в соап в отдельный класс, оставить генерацию самого тела
 * в пакетах целевых вебсервисов
 */

public class main {
    public static void main(String[] args) throws IOException {


        Scanner sc = new Scanner(System.in);
        System.out.println("ввод: ");
        String key = sc.nextLine();
        if (key.equals("")) { //в кавычках указать ключ который нужно ввести при запуске
        run();
    }
        else System.out.println("hello world"); System.exit(0);
    }

    public static void run () throws IOException {
       // Runtime.getRuntime().exec("cls");
        Scanner sc = new Scanner(System.in);


            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n" +
                    "version: 1.2-SNAPSHOT\n");
            System.out.println("Инструкция:" +
                    "\n\n*Операции,адреса должны быть без пробелов" +
                    "\n*Требования для файлов с гуидами:" +
                    "\nДля NSI_EVENTHANDLER: файл в папке с программой с именем eventhandler_guids.txt" +
                    "\n*Каждый гуид должен быть на новой строчке!");


            System.out.println("\n\nвыберите вебсервис для работы: " +
                            "\n1 - NSI_EVENTHANDLER - отправка уфос-пим"
                    //  + "\n2 - NSI_DATAMART - выгрузка записей "
            );


            int choose = sc.nextInt();

            switch (choose) {
                case 1:
                    eh_initializer.eh_initialize();
                    break;

                case 2:
                    //simpler.datamart.datamart_initializer.dm_initialize();
                    break;
                default:
                    System.out.println("некорректный ввод");
            }

    }
}
