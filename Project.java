import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

class Project{
   public static void main(String[] args){
      EmployeeManage employeeManage = new EmployeeManage();
      ProductManage productManage = new ProductManage();
      SalesValueManage salesValueManage = new SalesValueManage();
      Scanner scan = new Scanner(System.in);
      while(true){
      System.out.println();
      System.out.println("====WELCOME====");
      System.out.println("1.로그인");
      System.out.println("0.프로그램종료");
      System.out.print("#메뉴선택 >");
      int menuNumber = Integer.parseInt(scan.nextLine());
         if(menuNumber==1){
            System.out.println();
            System.out.println("====로그인====");
            System.out.print("ID를 입력하세요:");
            String uid = scan.nextLine();
            System.out.print("PW를 입력하세요:");
            String upw = scan.nextLine();
            System.out.println();
               if(employeeManage.check(uid,upw)){
                  EmployeeClient employeeClient = new EmployeeClient(employeeManage,productManage,salesValueManage);
                  employeeClient.start(uid); //uid == 프로그램 로그인하는 직원
               }
               else if(uid.equals("admin") && upw.equals("admin")){ //관리자 ID, PW 설정
                  AdminClient adminClient = new AdminClient(employeeManage,productManage,salesValueManage);
                  adminClient.start();
               }
               else{
                  System.out.println("ID,PW오류입니다! 다시 확인해주세요.");
                  System.out.println("입력하신 ID : "+uid);
                  System.out.println("입력하신 PW : "+upw);
               }
         }
         else if(menuNumber==0){
            break;
         }
         else{
            System.out.println("번호를 잘못 입력하셨습니다.");
         }
      }
            
   }
}
class Employee{
   String id;
   String pw;
   private String uid; //현재 프로그램 사용중인 직원 ID;
   Employee(){
   }
   Employee(String id, String pw){
      this.id = id;
      this.pw = pw;
   }
   public String getId(){
      return id;
   }
   public String getPw(){
      return pw;
   }
   public String getUid(){
      return uid;
   }
   public void setUid(String uid){
      this.uid = uid;
   }

   public void print(){
      System.out.println("ID(이름):"+id+" / PW(생년월일):"+pw);
   }
   boolean equals(String id, String pw){
      if(id.equals(this.id) && pw.equals(this.pw)){
         return true;
      }
      else {
         return false;
      }
   }

}
class EmployeeManage{
   Vector<Employee>employeeList = new Vector<Employee>(); //직원의 객체를 저장하는 백터
   EmployeeManage(){
      FileReader fr = null;
      try{
         fr = new FileReader("EmployeeList.txt");
      }catch(FileNotFoundException fnfe){
         System.out.println("EmployeeList.txt를 읽을 수 없습니다.");
      }
      BufferedReader br = new BufferedReader(fr);
      String data;
      try{
         while((data=br.readLine())!=null){
            String[]p = data.split(":");
               employeeList.add(new Employee(p[0],p[1]));
         }
      }catch(IOException ioe){
         System.out.println("EmployeeList.txt를 readLine()으로 읽을 수 없습니다");
      }
   }
   public void save(){   //EmployeeList 저장
      FileWriter fw = null;
      try{
         fw = new FileWriter("EmployeeList.txt");
      }catch(IOException ioe){
         System.out.println("EmployeeList.txt에 쓸 수 없습니다.");
      }
      PrintWriter pw = new PrintWriter(fw);
      for(Employee e : employeeList){
         String data = e.getId()+":"+e.getPw();
         pw.println(data);
      }
      pw.flush();
   }
   boolean check(String id, String pw){ //id와 pw중 동일한 id,pw를 가지고있으면 true, 아니면 false
      for(Employee e : employeeList){
         if(e.equals(id,pw)){
            return true;
         }   
      }
      return false;
   }
   public void add(String id, String pw){   //직원 추가
      employeeList.add(new Employee(id,pw));
   }
   public void print(){   //직원 출력
      for(Employee e : employeeList){
         e.print();
      }
   }         
}
class Product{
   private String name;
   private int price;
   private String code;
   private int number;

   Product(String name,int price,String code,int number){
      this.name = name;
      this.price = price;
      this.code = code;
      this.number = number;
   }
   public String getName(){
      return name;
   }
   public int getPrice(){
      return price;
   }
   public String getCode(){
      return code;
   }
   public int getNumber(){
      return number;
   }
   public void setNumber(int n){
      this.number = n;
   }
   public void print(){
      System.out.println("상품명: "+name+", 가격: "+price+", 수량: "+number);
   }   
}
class ProductManage{
   Vector<Product>productList= new Vector<Product>();
   ProductManage(){
      FileReader fr = null;
      try{
         fr = new FileReader("ProductList.txt");
      }catch(FileNotFoundException fnfe){
         System.out.println("ProductList.txt를 읽을 수 없습니다.");
      }
      BufferedReader br = new BufferedReader(fr);
      String data;
      try{
         while((data=br.readLine())!=null){
            String[]p = data.split("-");
               productList.add(new Product(p[0],Integer.parseInt(p[1]),p[2],Integer.parseInt(p[3])));
         }
      }catch(IOException ioe){
         System.out.println("ProductList.txt를 readLine()으로 읽을 수 없습니다");
      }
   }
   public void save(){   //ProductList 저장
      FileWriter fw = null;
      try{
         fw = new FileWriter("ProductList.txt");
      }catch(IOException ioe){
         System.out.println("ProductList.txt에 쓸 수 없습니다.");
      }
      PrintWriter pw = new PrintWriter(fw);
      for(Product p : productList){
         String data = p.getName()+"-"+p.getPrice()+"-"+p.getCode()+"-"+p.getNumber();
         pw.println(data);
      }
      pw.flush();

   }
   public void add(Product p){   //상품추가
      productList.add(p);
   }
   public void print(){ //상품코드 별로 재고 확인
      for(Product p: productList){
         p.print();
      }
      Scanner scan = new Scanner(System.in);
      System.out.println();
      System.out.println("<상품코드>");
      System.out.println("(1)과자 (2)음료 (3)아이스크림 (4)즉석식품 (5)주류 (6)담배");
      System.out.println();
      System.out.print("재고를 확인하실 상품코드를 입력해주세요: ");
      String keyWord = scan.nextLine();
      System.out.println();
      Vector<Product> foundProduct = new Vector<Product>();
         for(Product p : productList){
            String code = p.getCode();
               if(code.contains(keyWord)){
                  foundProduct.add(p);
               }
         }
         int index=1;
         for(Product p : foundProduct){
            System.out.print(index+") ");
            p.print();
            index++;
         }
      System.out.println();
   }
}
class AdminClient{
   EmployeeManage employeeManage;
   ProductManage productManage;
   SalesValueManage salesValueManage;
   SalesValue salesValue = new SalesValue();
   Scanner scan = new Scanner(System.in);

   AdminClient(EmployeeManage employeeManage, ProductManage productManage, SalesValueManage salesValueManage){
      this.employeeManage = employeeManage;
      this.productManage = productManage;
      this.salesValueManage = salesValueManage;
   }
   public void start(){
      boolean flag = true;
      while(flag){
         System.out.println();
         System.out.println("==== 관리자 메인 메뉴 ====");
         System.out.println("1.직원관리");
         System.out.println("2.상품등록");
         System.out.println("3.재고확인");
         System.out.println("4.매출통계");
         System.out.println("0.종료");
         System.out.print("#메뉴선택> ");
         int menuNumber = Integer.parseInt(scan.nextLine());
            switch(menuNumber){
               case 1:
               employeeManageMenu(); //관리자-직원관리메뉴
               break;

               case 2:
               addProduct();   //관리자-상품등록
               break;

               case 3:
               productManage.print(); //관리자-재고확인
               break;

               case 4:
               salesStatistics(); //관리자-매출통계
               break;

               case 0:
               flag = false;
               break;
               
               default:
               System.out.println("메뉴번호를 잘못  선택하셨습니다.");
               break;

            }   
         }
   }
   public void addEmployee(){ //직원관리>직원등록
      System.out.println();
      System.out.print("직원의 ID(이름)을 입력하세요:");
      String id = scan.nextLine();
      System.out.print("직원의 PW(생년월일)을 입력하세요:");
      String pw = scan.nextLine();
      employeeManage.add(id,pw);
      employeeManage.save();
      System.out.println("새로운 직원 "+id+"님이 등록 되었습니다.");
   }
   public void employeeManageMenu(){ //직원관리메뉴
      System.out.println();
      System.out.println("====직원관리====");
      System.out.println("1.직원등록");
      System.out.println("2.직원확인");
      System.out.println("3.월급관리");
      System.out.println("4.이전화면");
      System.out.print("#메뉴선택> ");
      int menuNumber = Integer.parseInt(scan.nextLine());
         if(menuNumber==1){
         addEmployee();
         }                  
         else if(menuNumber==2){
            System.out.println();
            System.out.println("====직원확인====");            
            employeeManage.print();
         }
         else if(menuNumber==3){
            salary();   
         }
         else if(menuNumber==4){
            
         }
         else{
         System.out.println("메뉴번호를 잘못 선택하셨습니다");
         }
   }
   public void addProduct(){
      System.out.println();
      System.out.println("=====상품등록====");
      System.out.print("상품명을 입력하세요:");
      String name = scan.nextLine();
      System.out.print("상품가격을 입력하세요:");
      int price = Integer.parseInt(scan.nextLine());
      System.out.print("상품코드를 입력하세요:");
      String code = scan.nextLine();
      System.out.print("상품수량을 입력하세요:");
      int number = Integer.parseInt(scan.nextLine());
      productManage.add(new Product(name,price,code,number));
      productManage.save();
      System.out.println("새로운 상품 "+name+" 이(가) 등록되었습니다.");
      System.out.println();
   }
   public void salesStatistics(){
      int weekSales = 0;
      System.out.println(weekSales);
         for(int i=0;i<=6;i++){
            weekSales += salesValueManage.salesValueList.get(i).getSalesValue();
         }         
      int TotalSales = 0;
            for(int i=0;i<salesValueManage.salesValueList.size();i++){
            TotalSales += salesValueManage.salesValueList.get(i).getSalesValue();
         }   
      System.out.println();
      System.out.println("====매출통계====");
      System.out.println("일주일 매출통계: "+weekSales+"원");
      System.out.println("한달 매출통계: " +TotalSales+"원");

   }
   public void salary(){
      WorkTimeManage workTimeManage = new WorkTimeManage();
      double daySalary = 0; //일급여
      int totalSalary = 0; //총급여
      System.out.println();
      System.out.println("====급여관리====");
      System.out.print("급여를 확인하실 직원의 이름을 입력하세요:");
      String keyWord = scan.nextLine();
         Vector<WorkTime> salaryCheck = new Vector<WorkTime>();
         for(WorkTime w : workTimeManage.workTimeList) {
            String name = w.getLogging();
               if(name.equals(keyWord)){
                  salaryCheck.add(w);
               } 
         }
         if(salaryCheck.size()==0){ //salaryCheck.size가 0이면 salary()메소드 다시시작
            System.out.println();
            System.out.println("직원이름 을 잘못입력하셨습니다.");
            salary();
         }
         else{
            System.out.println();
            System.out.println("<출근기록>");
            for(WorkTime w : salaryCheck){
               DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
               LocalDateTime startWork = LocalDateTime.parse(w.getInTime(), format); //출근시간
               LocalDateTime endWork = LocalDateTime.parse(w.getOutTime(), format); //퇴근시간
               long workMinute = ChronoUnit.MINUTES.between(startWork, endWork);    //출근시간과 퇴근시간 차이(분)           
               daySalary = workMinute * 150; //시급9000원을 분단위로(150원)
               totalSalary += daySalary;
               w.print();
               System.out.println(" 급여:" + daySalary);
            }
            System.out.println();
            System.out.println(keyWord+"님 총 급여:"+totalSalary+"원");
         } 
   }
}
class EmployeeClient{
   EmployeeManage employeeManage;
   ProductManage productManage;
   SalesValueManage salesValueManage;
   Day day = new Day();
   SalesValue salesValue = new SalesValue();
   Employee employee = new Employee();
   Scanner scan = new Scanner(System.in);
   int dailySales; //하루매출 변수
   String calculateTime = salesValue.getNowTime(); //정산초기화 시간을 저장하기 위한 변수
   
   EmployeeClient(EmployeeManage employeeManage, ProductManage productManage, SalesValueManage salesValueManage){
      this.employeeManage = employeeManage;
      this.productManage = productManage;
      this.salesValueManage = salesValueManage;
   }
   EmployeeClient(){
   }
   public void start(String uid){
      boolean flag = true;
      employee.setUid(uid); //현재 사용중인 직원ID 값 setter
      while(flag){
         System.out.println();
         System.out.println("===="+employee.getUid()+"직원====");
         System.out.println("1.상품판매");
         System.out.println("2.재고확인");
         System.out.println("3.상품발주");
         System.out.println("4.출/퇴근확인");
         System.out.println("5.마감정산");
         System.out.println("0.종료");
         System.out.print("#메뉴선택> ");
         int menuNumber = Integer.parseInt(scan.nextLine());
         switch(menuNumber){
            case 1:
            sales(); //직원-판매
            break;

            case 2:
            productManage.print(); //직원-재고확인
            break;

            case 3:
            order(); //직원-상품발주
            break;

            case 4:
            checkCommuting(); //직원-출/퇴근확인
            break;

            case 5:
            deadLineCalculate();
            break;

            case 0:
            flag = false;
            break;

            default:
            System.out.println("메뉴번호를 잘못 입력하셨습니다.");
            break;
         }
      }
   }
   public void sales(){   //직원-판매
      System.out.println();
      System.out.println("====상품판매====");
      System.out.println("<상품코드>");
      System.out.println("(1)과자 (2)음료 (3)아이스크림 (4)즉석식품 (5)주류 (6)담배");
      System.out.println();
      System.out.print("상품코드를 입력해주세요:");
      String keyWord = scan.nextLine();
      Vector<Product> foundProduct = new Vector<Product>();
         for(Product p : productManage.productList){
            String code = p.getCode();
               if(code.contains(keyWord)){
                  foundProduct.add(p);
               }
         }
         int index=1;
         for(Product p : foundProduct){
            System.out.print(index+") ");
            p.print();
            index++;
         }
      System.out.println();
      System.out.print("판매할 상품의 번호를 입력하세요: ");
      int saleIndex = Integer.parseInt(scan.nextLine());
      System.out.print("수량을 입력해주세요: ");
      int amount = Integer.parseInt(scan.nextLine());
      int count = foundProduct.get(saleIndex-1).getNumber();   //판매할 상품의 현재 갯수를 count에 저장
           String productName = foundProduct.get(saleIndex-1).getName(); //판매할 상품명이름 저장
         if(count<amount){
            System.out.println();
            System.out.println("제품 수량 부족!!!");
            System.out.println("상품을 발주해주세요!!!");
         }
         else{
            count -= amount;   //현재수량 - 판매수량
            foundProduct.get(saleIndex-1).setNumber(count);   //남은수량을 현재 상품 갯수로 저장
            productManage.save(); //상품리스트 백터 저장
            int price = foundProduct.get(saleIndex-1).getPrice();   //판매한 상품 가격 저장
            int salePrice = price * amount;   // 판매금액 계산 상품가격 * 판매갯수
            dailySales += salePrice; //하루 총 매출금액 계산
            System.out.println();
            System.out.println("<결제알림>");
            System.out.println("상품명:"+productName+" 수량:"+amount+" 결제금액:"+salePrice+"원 입니다.");
            System.out.println();
                  
         }      
   }
   public void order(){
      System.out.println();
      System.out.println("====재고 부족 상품 목록===");
      System.out.println("<상품코드>");
      System.out.println("(1)과자 (2)음료 (3)아이스크림 (4)즉석식품 (5)주류 (6)담배");
      System.out.println();
      Vector<Product> orderProduct = new Vector<Product>();
         for(Product p : productManage.productList){
            int number = p.getNumber();
               if(number<3){
                  orderProduct.add(p);
               }
         }
         int index=1;
         for(Product p : orderProduct){
            System.out.print(index+") ");
            p.print();
            index++;
         }
            if(orderProduct.size()==0){
               System.out.println("모든 상품의 재고가 충분합니다.");
            }
            else{
               System.out.println();   
               System.out.print("주문하실 상품번호를 입력하세요:");
               int orderIndex = Integer.parseInt(scan.nextLine());
                  if(orderIndex>orderProduct.size() || orderIndex <= 0){
                     System.out.println("번호를 잘못 입력하셨습니다.");
                     order();
                  }
                  else{
                     System.out.print("개수를 입력하세요:");
                     int amount = Integer.parseInt(scan.nextLine());
                     int count = orderProduct.get(orderIndex-1).getNumber(); //현재상품 개수
                     orderProduct.get(orderIndex-1).setNumber(count+amount); //현재상품 개수 + 주문상품 개수
                     String orderName = orderProduct.get(orderIndex-1).getName(); //주문한 상품 이름
                     System.out.println(orderName+" "+amount+"개 발주 되었습니다");
                     System.out.println();
                     productManage.save();
                  }
               }
   }
   public void deadLineCalculate(){
      System.out.println();
      System.out.println("====마감정산====");
      System.out.println("1.정산확인");
      System.out.println("2.금일매출초기화");
      System.out.println("3.이전화면");
      System.out.print("#메뉴선택> ");
      int menuNumber = Integer.parseInt(scan.nextLine());
         if(menuNumber==1){
            System.out.println("금일 총 판매 금액은 "+dailySales+"원 입니다.");
            System.out.println();
         }
         else if(menuNumber==2){
            System.out.println("금일 매출 금액을 초기화하였습니다.");
            salesValueManage.add(new SalesValue(dailySales,calculateTime));
            salesValueManage.save();
            dailySales = 0;
         }
         else if(menuNumber==3){
         }
         else{
            System.out.println("번호를 잘못 입력하셨습니다");
         }

   }
   public void checkCommuting(){
      WorkTimeManage workTimeManage = new WorkTimeManage();
      Day day = new Day();
      
      String inTime = null;
      String outTime = null;
      String logging = employee.getUid();
      System.out.println();
      System.out.println("====출/퇴근확인====");
      System.out.println("1.출근기록");
      System.out.println("2.퇴근기록");
      System.out.println("0.이전화면");
      System.out.print("#메뉴선택> ");
      int menuNumber = Integer.parseInt(scan.nextLine());
         if(menuNumber==1){
            System.out.println();
            System.out.println("<"+logging+"님 출근시간>");
            inTime = day.getDay();
            System.out.println(inTime);
            workTimeManage.add(new WorkTime(logging,inTime,outTime)); //출근시간만저장 퇴근시간은 null로 저장
            workTimeManage.save();
         }
         else if(menuNumber==2){
            System.out.println();
            System.out.println("<"+logging+"님 퇴근시간>");
            outTime = day.getDay();
            System.out.println(outTime);
            int index = workTimeManage.workTimeList.size(); //현재 백터 사이즈
            workTimeManage.workTimeList.get(index-1).setOutTime(outTime); //출근시간이 저장되어있는 인덱스에 퇴근시간 저장
            workTimeManage.save();
         }
         else if(menuNumber==0){

         }
         else{
            System.out.println("번호를 잘못 입력하셨습니다.");
         }
   }
}
class WorkTime{
   Day day = new Day();
   String logging;
   String inTime;
   String outTime;
   WorkTime(String logging, String inTime, String outTime){ 
      this.logging = logging;
      this.inTime = inTime;
      this.outTime = outTime; 
   }
   public String getLogging(){
      return this.logging;
   }
   public String getInTime(){
      return inTime;
   }
   public String getOutTime(){
      return outTime;
   }
   public void setOutTime(String outTime){
      this.outTime=outTime;
   }
   public void print(){   
      System.out.print("출근시간:"+inTime+" 퇴근시간:"+outTime);
   }
}
class WorkTimeManage{
   Vector<WorkTime>workTimeList = new Vector<WorkTime>();
   WorkTimeManage(){
      FileReader fr = null;
      try{
         fr = new FileReader("WorkTimeList.txt");
      }catch(FileNotFoundException fnfe){
         System.out.println("WorkTimeList.txt 파일이 없습니다.");
      }
      BufferedReader br = new BufferedReader(fr);
      try{
         while(true){
            String line = br.readLine();
               if(line==null){
                  break;
               }
            String[] p = line.split(";");
            workTimeList.add(new WorkTime(p[0],p[1],p[2]));            
         }
      }catch(IOException ioe){
         System.out.println("WorkTimeList.txt를 readLine()으로 읽을 수 없습니다.");
      }
   }
   public void save(){
      FileWriter fw = null;
      try{
         fw = new FileWriter("WorkTimeList.txt");
      }catch(IOException ioe){
         System.out.println("WorkTimeList.txt에 쓸 수 없습니다.");
      }
      PrintWriter pw = new PrintWriter (fw);
      for(WorkTime w : workTimeList){
         String data = w.getLogging()+";"+w.getInTime()+";"+w.getOutTime(); 
         pw.println(data);
      }
      pw.flush();
   }
   public void add(WorkTime w){
      workTimeList.add(w);
   }
}

class SalesValue{ // 판매 값 클래스
   int todaySalesValue;
   Day day = new Day();
   String nowTime = day.getDay();

   public SalesValue(int Value,String time){
      this.todaySalesValue=Value;
      this.nowTime = time;
   }
   public SalesValue(){
   }
   
   public int getSalesValue(){
      return todaySalesValue;
   }
   public String getNowTime(){
      return nowTime;
   }
}

class SalesValueManage{
   Day day = new Day();
   Vector<SalesValue>salesValueList = new Vector<SalesValue>();
   SalesValueManage(){
      FileReader fr = null;
      try{
         fr = new FileReader("salesValue.txt");
      }catch(FileNotFoundException fnfe){
         System.out.println("salesValue.txt 파일이 없습니다");
      }
      BufferedReader br = new BufferedReader(fr);
      try{
         while(true){
            String line = br.readLine();
               if(line==null){
                  break;
               }
            String[] p = line.split(";");
            salesValueList.add(new SalesValue(Integer.parseInt(p[0]),p[1]));
         }
      }catch(IOException ioe){
         System.out.println("salesValue.txt를 readLine()으로 읽을 수 없습니다.");
      }
   }
   public void save(){
      FileWriter fw = null;
      try{
         fw = new FileWriter("salesValue.txt");
      }catch(IOException ioe){
         System.out.println("salesValue.txt에 쓸 수 없습니다.");
      }
      PrintWriter pw = new PrintWriter(fw);
      for(SalesValue s : salesValueList){
         String data = s.getSalesValue()+";"+s.getNowTime(); 
              pw.println(data);   
      }
      pw.flush();
   }
   public void add(SalesValue s){
      salesValueList.add(s);
   }
}

class Day{
   String time;

   Day(){
      long systemTime = System.currentTimeMillis();
      SimpleDateFormat date = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
      time = date.format(systemTime);   
   }

   public String getDay(){ //생성자에 실시간시간값 초기화를 넣어 메소드 호출시 실시간 전달 
      new Day();
      return time;
   }
}





