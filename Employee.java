package Pos;

import java.util.*;

public class Employee {
    private Vector<String> employeeList = new Vector<String>(2);
    private Scanner sc = new Scanner(System.in);

    public void getEmployeeList() {
        if (this.employeeList.size() == 0) {
            System.out.println("");
            System.out.println("등록된 직원이 없습니다.");
            System.out.println("");
        } else {
            System.out.println("");
            for ( int i = 0; i < this.employeeList.size()/2; i++) {
                System.out.println("이름: "+ this.employeeList.get(2*i)+" 나이: "+this.employeeList.get(2*i+1));
            }
        }
    }

    public void addEmployee() {
        System.out.println("");
        System.out.print("등록할 직원의 이름을 입력하세요: ");
        String employeeName = sc.nextLine();
        System.out.print("등록할 지원의 나이를 입력하세요: ");
        String employeeAge = sc.nextLine();

        this.employeeList.addElement(employeeName);
        this.employeeList.addElement(employeeAge);

        System.out.println("");
        System.out.println(employeeName + " 님 등록 완료!");
        System.out.println("");
    }

    public void removeEmployee() {
        System.out.println("");
        System.out.print("삭제할 직원의 이름을 입력하세요: ");
        String employeeName = sc.nextLine();

        int idx = this.employeeList.indexOf(employeeName);

        if ( idx == -1) {
            System.out.println("");
            System.out.println("찾는 직원이 없습니다.");
            System.out.println("");
        } else {
            this.employeeList.remove(employeeName);
            this.employeeList.remove(idx);

            System.out.println("");
            System.out.println(employeeName+ " 님 삭제 완료!");
            System.out.println("");
        }
    }
}
