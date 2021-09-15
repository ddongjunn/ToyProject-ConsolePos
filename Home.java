package Pos;

import java.util.Scanner;

public class Home {
    Scanner sc = new Scanner(System.in);

    public Home() {
        int menu;
        do {
            System.out.println("");
            System.out.println("===== 홈 =====");
            System.out.println("");
            System.out.println("1.관리자 로그인");
            System.out.println("2.판매");
            System.out.println("3.재고확인");
            System.out.println("4.출/퇴근확인");
            System.out.println("5.정산");
            System.out.println("0.종료");
            System.out.print("# 메뉴 선택: ");
    
            menu = sc.nextInt();

            switch(menu) {
                case 1:
                    new Admin().adminLogin();
                    break;
                case 2:
                    selling();
                    break;
                case 3:
                    stockCheck();
                    break;
                case 4:
                    workCheck();
                    break;
                case 5:
                    salesCheck();
                    break;
                case 0:
                    System.out.println("");
                    System.out.println("종료합니다.");
                    System.out.println("");
                    System.exit(0);
                    break;
                default:
                    System.out.println("");
                    System.out.println("잘못누르셨습니다");
                    System.out.println("");
                    break;
            }
        } while(menu > 0);
    }

    private static void selling() {
        System.out.println("");
        System.out.println("판매 완료!");
        System.out.println("");
    }
    private static void stockCheck() {
        System.out.println("");
        System.out.println("재고 확인 완료!");
        System.out.println("");
    }
    private static void workCheck() {
        System.out.println("");
        System.out.println("출/퇴근 확인 완료!");
        System.out.println("");
    }
    private static void salesCheck() {
        System.out.println("");
        System.out.println("정산");
        System.out.println("");
    }
}