package Pos;

import java.util.*;

public class Product {
    private Vector<String> productList = new Vector<String>(3);
    private Vector<String> productStockList = new Vector<String>(3);
    private Scanner sc = new Scanner(System.in);
    private Check ca = new Check();

    public void getProductList() {
        int menu;
        do {
            this.productStockList.removeAllElements();
            
            this.stockUpdate();

            System.out.println("");
            System.out.println("===== 관리자 상품 조회 페이지 =====");
            System.out.println("");
            System.out.println("1.전체 상품 조회");
            System.out.println("2.재고 부족 상품 조회");
            System.out.println("3.재고 부족 상품 주문");
            System.out.println("0.이전 메뉴");
            System.out.print("# 메뉴 선택: ");

            menu = sc.nextInt();

            switch(menu){
                case 1:
                    this.getAllProduct();
                    break;
                case 2:
                    this.getOutOfStockProduct();
                    break;
                case 3:
                    this.outOfStockProductOrder();
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

    public void getAllProduct() {
        if (this.productList.size() == 0) {
            System.out.println("");
            System.out.println("등록된 상품이 없습니다.");
            System.out.println("");
        } else {
            System.out.println("");
            for ( int i = 0; i < this.productList.size()/3; i++) {
                System.out.print("상품명: "+ this.productList.get(3*i)+" 가격: "+this.productList.get(3*i+1)+" 수량: "+this.productList.get(3*i+2));

                if (Integer.parseInt(this.productList.get(3*i + 2)) > 10) {
                    System.out.println("");
                } else {
                    System.out.println("    재고가 많이 없습니다. 상품 주문이 필요합니다!");
                }
            }
        }
    }

    public void getOutOfStockProduct() {
        if (this.productList.size() == 0) {
            System.out.println("");
            System.out.println("등록된 상품이 없습니다.");
            System.out.println("");
        } else if (this.productStockList.size() == 0) {
            System.out.println("");
            System.out.println("재고가 부족한 상품이 없습니다.");
            System.out.println("");
        } else {
            System.out.println("");
            for( int j = 0; j < this.productStockList.size()/3; j++ ) {
                System.out.println("상품명: "+ this.productStockList.get(3*j)+" 가격: "+this.productStockList.get(3*j+1)+" 수량: "+this.productStockList.get(3*j+2));
            }
        }
    }

    public void outOfStockProductOrder() {
            System.out.println("");
            System.out.println("===== 참고 =====");
            for ( int i = 0; i < this.productList.size()/3; i++) {
                System.out.print("상품명: "+ this.productList.get(3*i)+" 가격: "+this.productList.get(3*i+1)+" 수량: "+this.productList.get(3*i+2));

                if (Integer.parseInt(this.productList.get(3*i + 2)) > 10) {
                    System.out.println("");
                } else {
                    System.out.println("    *");
                }
            }

            String msg = ca.checkAdmin();

            if (msg.equals("turn")) return ;

            System.out.println("");
            System.out.print("주문할 상품명을 입력하세요: ");
            sc.nextLine();
            String orderName = sc.nextLine();

            int idx = this.productList.indexOf(orderName);

            if (idx == -1) {
                System.out.println("");
                System.out.println("찾는 상품이 없습니다.");
                System.out.println("");
            } else {
                System.out.print("주문할 상품의 수량을 입력하세요: ");
                int orderNumber = sc.nextInt();

                int oriNumber = Integer.parseInt(this.productList.get(idx+2));

                this.productList.set(idx+2, Integer.toString(oriNumber+orderNumber));

                System.out.println("주문 완료!");
            }
    }

    public void stockUpdate() {
        for ( int i = 0; i < this.productList.size()/3; i++) {
            if (Integer.parseInt(this.productList.get(3*i + 2)) <= 10) {
                this.productStockList.add(this.productList.get(3*i));
                this.productStockList.add(this.productList.get(3*i+1));
                this.productStockList.add(this.productList.get(3*i+2));
            }
        }
    }

    public void addProduct() {
        System.out.println("");
        System.out.print("등록할 상품명을 입력하세요: ");
        String productName = sc.nextLine();
        System.out.print("등록할 상품 가격을 입력하세요: ");
        String productPrice = sc.nextLine();
        System.out.print("등록할 상품 재고를 입력하세요: ");
        String productStock = sc.nextLine();

        this.productList.addElement(productName);
        this.productList.addElement(productPrice);
        this.productList.addElement(productStock);

        System.out.println("");
        System.out.println(productName + " 등록 완료!");
        System.out.println("");
    }

    public void removeProduct() {
        System.out.println("");
        System.out.print("삭제할 상품명을 입력하세요: ");
        String productName = sc.next();

        int idx = this.productList.indexOf(productName);

        if (idx == -1) {
            System.out.println("");
            System.out.println("찾는 상품이 없습니다.");
            System.out.println("");
        } else {
            this.productList.remove(productName);
            this.productList.remove(idx);
            this.productList.remove(idx);

            System.out.println("");
            System.out.println(productName + " 삭제 완료!");
            System.out.println("");
        }
    }
}