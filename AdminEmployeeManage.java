package Pos;

import java.util.Scanner;

public class AdminEmployeeManage {
    Scanner sc = new Scanner(System.in);

    public AdminEmployeeManage() {
        Employee employee = new Employee();
        int menu;
        do {
            System.out.println("");
            System.out.println("===== 관리자 직원 관리 페이지 =====");
            System.out.println("");
            System.out.println("1.직원 등록");
            System.out.println("2.직원 조회");
            System.out.println("3.직원 삭제");
            System.out.println("0.이전 메뉴");
            System.out.print("# 메뉴 선택: ");

            menu = sc.nextInt();

            switch(menu){
                case 1:
                    employee.addEmployee();
                    break;
                case 2:
                    employee.getEmployeeList();
                    break;
                case 3:
                    employee.removeEmployee();
                    break;
                case 0:
                    System.out.println("");
                    System.out.println("이전 메뉴로..");
                    System.out.println("");
                    break;
                default:
                    System.out.println("");
                    System.out.println("잘못누르셨습니다");
                    System.out.println("");
                    break;
            }
        } while ( menu != 4);
    }
}