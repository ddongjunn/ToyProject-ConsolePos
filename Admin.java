package Pos;

import java.util.Scanner;

public class Admin {
    Scanner sc = new Scanner(System.in);
    String id, pw;

    public Admin() {}

    public void adminLogin() {
        System.out.println("");
        System.out.println("===== 관리자 로그인 화면 =====");
        System.out.println("");
        System.out.print("ID: ");
        this.id = this.sc.nextLine();
        System.out.print("PW: ");
        this.pw = this.sc.nextLine();

        if ( this.id.equals("admin") && this.pw.equals("admin")) {
            this.adminHome();
        } else {
            System.out.println("");
            System.out.println("로그인 실패! 홈 화면으로 돌아갑니다");
            System.out.println("");
        }
    }

    private void adminHome() {
        int menu;
        do {
            System.out.println("");
            System.out.println("===== 관리자 홈 =====");
            System.out.println("");
            System.out.println("1.직원 관리");
            System.out.println("2.상품 관리");
            System.out.println("3.재고 확인");
            System.out.println("4.매출 통계");
            System.out.println("0.관리자 로그아웃");
            System.out.print("# 메뉴 선택: ");
    
            menu = sc.nextInt();

            switch(menu) {
                case 1:
                    new AdminEmployeeManage();
                    break;
                case 2:
                    new ProductManage();
                    break;
                case 3:
                    this.adminStockCheck();
                    break;
                case 4:
                    this.adminSalesManage();
                    break;
                case 0:
                    System.out.println("");
                    System.out.println("관리자 로그아웃");
                    System.out.println("");
                    break;
                default:
                    System.out.println("");
                    System.out.println("잘못누르셨습니다");
                    System.out.println("");
                    break;
            }

        } while( menu != 0);
        
    }

    private void adminStockCheck() {
        System.out.println("");
        System.out.println("관리자 재고 관리!!");
        System.out.println("");
    }

    private void adminSalesManage() {
        System.out.println("");
        System.out.println("관리자 매출 통계!!");
        System.out.println("");
    }
}