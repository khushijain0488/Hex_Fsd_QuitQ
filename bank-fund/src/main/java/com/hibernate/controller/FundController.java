package com.hibernate.controller;

import com.hibernate.config.ProjConfig;
import com.hibernate.exception.ResourceNotFoundException;
import com.hibernate.model.Fund;
import com.hibernate.service.FundService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class FundController {

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjConfig.class);
        FundService fundService = context.getBean(FundService.class);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Fund");
            System.out.println("2. Show all Funds");
            System.out.println("3. Fetch all Funds by Manager");
            System.out.println("0. Exit");

            int input = sc.nextInt();
            if (input == 0)
                break;

            switch (input) {

                case 1:
                    Fund fund = new Fund();
                    System.out.println("Enter Fund name: ");
                    fund.setName(sc.next());
                    System.out.println("Enter AUM Amount: ");
                    fund.setAumAmount(sc.nextBigDecimal());
                    System.out.println("Enter Expense Ratio: ");
                    fund.setExpenseRatio(sc.nextBigDecimal());
                    System.out.println("Enter Manager ID: ");
                    int managerId = sc.nextInt();
                    try {
                        fundService.insert(fund, managerId);
                        System.out.println("Fund added to DB..");
                    } catch (ResourceNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    List<?> list = fundService.getAllFunds();
                    list.forEach(System.out::println);
                    break;

                case 3:
                    System.out.println("Enter Manager ID: ");
                    int mgrId = sc.nextInt();
                    try {
                        List<?> funds = fundService.getFundsByManager(mgrId);
                        funds.forEach(System.out::println);
                    } catch (ResourceNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

            } // switch ends
        } // while ends

        sc.close();
        context.close();
    } // main ends
}