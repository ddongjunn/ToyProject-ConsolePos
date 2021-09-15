package Pos;

import java.util.*;

public class ProductManage {
    Scanner sc = new Scanner(System.in);

    public ProductManage() {
        Product product = new Product();
        int menu;

        do {
            System.out.println("");
            System.out.println("===== 관리자 상품 관리 페이지 =====");
            System.out.println("");
            System.out.println("1.상품 등록");
            System.out.println("2.상품 조회");
            System.out.println("3.상품 삭제");
            System.out.println("0.이전 메뉴");
            System.out.print("# 메뉴 선택: ");

            menu = sc.nextInt();

            switch(menu){
                case 1:
                    product.addProduct();
                    break;
                case 2:
                    product.getProductList();
                    break;
                case 3:
                    product.removeProduct();
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
        } while ( menu != 0);
    }
}