package simpler;

import simpler.eventhandler.eh_requestgenerator;

import java.util.Scanner;


public class main {
    public static void main(String[] args) {

        System.out.println("0.0.1");
        System.out.println("Инструкция:" +
                "\n*Операции,адреса должны быть без пробелов" +
                "\n*Файл с гуидами должен быть в папке с приложением, имя файлов guids.txt" +
                "\n*Каждый гуид должен быть на новой строчке");


        System.out.println("выберите вебсервис для работы: " +
                "\n1 - NSI_EVENTHANDLER - отправка уфос-пим" +
                "\n2- NSI_DATAMART - выгрузка записей ");

        Scanner sc = new Scanner(System.in);
        int choose = sc.nextInt();

        switch (choose) {
            case 1:
                eh_requestgenerator.eh_initialize();
                break;

            case 2:
                simpler.datamart.datamart_initializer.dm_initialize();
                break;
            default:
                System.out.println("некорректный ввод");
        }


    }
}