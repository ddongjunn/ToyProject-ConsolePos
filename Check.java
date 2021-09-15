package Pos;

import java.util.*;

public class Check {

    Scanner sc = new Scanner(System.in);

    public String checkAdmin() {
        System.out.print("관리자 권한이 필요합니다. 관리자 비밀번호를 입력해주세요: ");
        String pw = sc.nextLine();

        if (pw.equals("admin")) {
            return "ok";
        } else {
            System.out.println("");
            System.out.println("접근이 제한됩니다.");
            System.out.println("");
            return "turn";
        }
    }
}