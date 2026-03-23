package com.hibernate.controller;

import com.hibernate.config.ProjConfig;
import com.hibernate.exception.ResourceNotFoundException;
import com.hibernate.model.Manager;
import com.hibernate.service.ManagerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Scanner;

public class ManagerController {

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjConfig.class);
        ManagerService managerService = context.getBean(ManagerService.class);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Manager");
            System.out.println("2. Show all Managers");
            System.out.println("0. Exit");

            int input = sc.nextInt();
            if (input == 0)
                break;

            switch (input) {

                case 1:
                    Manager manager = new Manager();
                    System.out.println("Enter Manager name: ");
                    manager.setName(sc.next());
                    System.out.println("Enter Manager email: ");
                    manager.setEmail(sc.next());
                    managerService.insert(manager);
                    System.out.println("Manager added to DB..");
                    break;

                case 2:
                    List<?> list = managerService.getAllManagers();
                    list.forEach(System.out::println);
                    break;

            } // switch ends
        } // while ends

        sc.close();
        context.close();
    } // main ends
}