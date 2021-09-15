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
      System.out.println("1.�α���");
      System.out.println("0.���α׷�����");
      System.out.print("#�޴����� >");
      int menuNumber = Integer.parseInt(scan.nextLine());
         if(menuNumber==1){
            System.out.println();
            System.out.println("====�α���====");
            System.out.print("ID�� �Է��ϼ���:");
            String uid = scan.nextLine();
            System.out.print("PW�� �Է��ϼ���:");
            String upw = scan.nextLine();
            System.out.println();
               if(employeeManage.check(uid,upw)){
                  EmployeeClient employeeClient = new EmployeeClient(employeeManage,productManage,salesValueManage);
                  employeeClient.start(uid); //uid == ���α׷� �α����ϴ� ����
               }
               else if(uid.equals("admin") && upw.equals("admin")){ //������ ID, PW ����
                  AdminClient adminClient = new AdminClient(employeeManage,productManage,salesValueManage);
                  adminClient.start();
               }
               else{
                  System.out.println("ID,PW�����Դϴ�! �ٽ� Ȯ�����ּ���.");
                  System.out.println("�Է��Ͻ� ID : "+uid);
                  System.out.println("�Է��Ͻ� PW : "+upw);
               }
         }
         else if(menuNumber==0){
            break;
         }
         else{
            System.out.println("��ȣ�� �߸� �Է��ϼ̽��ϴ�.");
         }
      }
            
   }
}
class Employee{
   String id;
   String pw;
   private String uid; //���� ���α׷� ������� ���� ID;
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
      System.out.println("ID(�̸�):"+id+" / PW(�������):"+pw);
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
   Vector<Employee>employeeList = new Vector<Employee>(); //������ ��ü�� �����ϴ� ����
   EmployeeManage(){
      FileReader fr = null;
      try{
         fr = new FileReader("EmployeeList.txt");
      }catch(FileNotFoundException fnfe){
         System.out.println("EmployeeList.txt�� ���� �� �����ϴ�.");
      }
      BufferedReader br = new BufferedReader(fr);
      String data;
      try{
         while((data=br.readLine())!=null){
            String[]p = data.split(":");
               employeeList.add(new Employee(p[0],p[1]));
         }
      }catch(IOException ioe){
         System.out.println("EmployeeList.txt�� readLine()���� ���� �� �����ϴ�");
      }
   }
   public void save(){   //EmployeeList ����
      FileWriter fw = null;
      try{
         fw = new FileWriter("EmployeeList.txt");
      }catch(IOException ioe){
         System.out.println("EmployeeList.txt�� �� �� �����ϴ�.");
      }
      PrintWriter pw = new PrintWriter(fw);
      for(Employee e : employeeList){
         String data = e.getId()+":"+e.getPw();
         pw.println(data);
      }
      pw.flush();
   }
   boolean check(String id, String pw){ //id�� pw�� ������ id,pw�� ������������ true, �ƴϸ� false
      for(Employee e : employeeList){
         if(e.equals(id,pw)){
            return true;
         }   
      }
      return false;
   }
   public void add(String id, String pw){   //���� �߰�
      employeeList.add(new Employee(id,pw));
   }
   public void print(){   //���� ���
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
      System.out.println("��ǰ��: "+name+", ����: "+price+", ����: "+number);
   }   
}
class ProductManage{
   Vector<Product>productList= new Vector<Product>();
   ProductManage(){
      FileReader fr = null;
      try{
         fr = new FileReader("ProductList.txt");
      }catch(FileNotFoundException fnfe){
         System.out.println("ProductList.txt�� ���� �� �����ϴ�.");
      }
      BufferedReader br = new BufferedReader(fr);
      String data;
      try{
         while((data=br.readLine())!=null){
            String[]p = data.split("-");
               productList.add(new Product(p[0],Integer.parseInt(p[1]),p[2],Integer.parseInt(p[3])));
         }
      }catch(IOException ioe){
         System.out.println("ProductList.txt�� readLine()���� ���� �� �����ϴ�");
      }
   }
   public void save(){   //ProductList ����
      FileWriter fw = null;
      try{
         fw = new FileWriter("ProductList.txt");
      }catch(IOException ioe){
         System.out.println("ProductList.txt�� �� �� �����ϴ�.");
      }
      PrintWriter pw = new PrintWriter(fw);
      for(Product p : productList){
         String data = p.getName()+"-"+p.getPrice()+"-"+p.getCode()+"-"+p.getNumber();
         pw.println(data);
      }
      pw.flush();

   }
   public void add(Product p){   //��ǰ�߰�
      productList.add(p);
   }
   public void print(){ //��ǰ�ڵ� ���� ��� Ȯ��
      for(Product p: productList){
         p.print();
      }
      Scanner scan = new Scanner(System.in);
      System.out.println();
      System.out.println("<��ǰ�ڵ�>");
      System.out.println("(1)���� (2)���� (3)���̽�ũ�� (4)�Ｎ��ǰ (5)�ַ� (6)���");
      System.out.println();
      System.out.print("��� Ȯ���Ͻ� ��ǰ�ڵ带 �Է����ּ���: ");
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
         System.out.println("==== ������ ���� �޴� ====");
         System.out.println("1.��������");
         System.out.println("2.��ǰ���");
         System.out.println("3.���Ȯ��");
         System.out.println("4.�������");
         System.out.println("0.����");
         System.out.print("#�޴�����> ");
         int menuNumber = Integer.parseInt(scan.nextLine());
            switch(menuNumber){
               case 1:
               employeeManageMenu(); //������-���������޴�
               break;

               case 2:
               addProduct();   //������-��ǰ���
               break;

               case 3:
               productManage.print(); //������-���Ȯ��
               break;

               case 4:
               salesStatistics(); //������-�������
               break;

               case 0:
               flag = false;
               break;
               
               default:
               System.out.println("�޴���ȣ�� �߸�  �����ϼ̽��ϴ�.");
               break;

            }   
         }
   }
   public void addEmployee(){ //��������>�������
      System.out.println();
      System.out.print("������ ID(�̸�)�� �Է��ϼ���:");
      String id = scan.nextLine();
      System.out.print("������ PW(�������)�� �Է��ϼ���:");
      String pw = scan.nextLine();
      employeeManage.add(id,pw);
      employeeManage.save();
      System.out.println("���ο� ���� "+id+"���� ��� �Ǿ����ϴ�.");
   }
   public void employeeManageMenu(){ //���������޴�
      System.out.println();
      System.out.println("====��������====");
      System.out.println("1.�������");
      System.out.println("2.����Ȯ��");
      System.out.println("3.���ް���");
      System.out.println("4.����ȭ��");
      System.out.print("#�޴�����> ");
      int menuNumber = Integer.parseInt(scan.nextLine());
         if(menuNumber==1){
         addEmployee();
         }                  
         else if(menuNumber==2){
            System.out.println();
            System.out.println("====����Ȯ��====");            
            employeeManage.print();
         }
         else if(menuNumber==3){
            salary();   
         }
         else if(menuNumber==4){
            
         }
         else{
         System.out.println("�޴���ȣ�� �߸� �����ϼ̽��ϴ�");
         }
   }
   public void addProduct(){
      System.out.println();
      System.out.println("=====��ǰ���====");
      System.out.print("��ǰ���� �Է��ϼ���:");
      String name = scan.nextLine();
      System.out.print("��ǰ������ �Է��ϼ���:");
      int price = Integer.parseInt(scan.nextLine());
      System.out.print("��ǰ�ڵ带 �Է��ϼ���:");
      String code = scan.nextLine();
      System.out.print("��ǰ������ �Է��ϼ���:");
      int number = Integer.parseInt(scan.nextLine());
      productManage.add(new Product(name,price,code,number));
      productManage.save();
      System.out.println("���ο� ��ǰ "+name+" ��(��) ��ϵǾ����ϴ�.");
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
      System.out.println("====�������====");
      System.out.println("������ �������: "+weekSales+"��");
      System.out.println("�Ѵ� �������: " +TotalSales+"��");

   }
   public void salary(){
      WorkTimeManage workTimeManage = new WorkTimeManage();
      double daySalary = 0; //�ϱ޿�
      int totalSalary = 0; //�ѱ޿�
      System.out.println();
      System.out.println("====�޿�����====");
      System.out.print("�޿��� Ȯ���Ͻ� ������ �̸��� �Է��ϼ���:");
      String keyWord = scan.nextLine();
         Vector<WorkTime> salaryCheck = new Vector<WorkTime>();
         for(WorkTime w : workTimeManage.workTimeList) {
            String name = w.getLogging();
               if(name.equals(keyWord)){
                  salaryCheck.add(w);
               } 
         }
         if(salaryCheck.size()==0){ //salaryCheck.size�� 0�̸� salary()�޼ҵ� �ٽý���
            System.out.println();
            System.out.println("�����̸� �� �߸��Է��ϼ̽��ϴ�.");
            salary();
         }
         else{
            System.out.println();
            System.out.println("<��ٱ��>");
            for(WorkTime w : salaryCheck){
               DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
               LocalDateTime startWork = LocalDateTime.parse(w.getInTime(), format); //��ٽð�
               LocalDateTime endWork = LocalDateTime.parse(w.getOutTime(), format); //��ٽð�
               long workMinute = ChronoUnit.MINUTES.between(startWork, endWork);    //��ٽð��� ��ٽð� ����(��)           
               daySalary = workMinute * 150; //�ñ�9000���� �д�����(150��)
               totalSalary += daySalary;
               w.print();
               System.out.println(" �޿�:" + daySalary);
            }
            System.out.println();
            System.out.println(keyWord+"�� �� �޿�:"+totalSalary+"��");
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
   int dailySales; //�Ϸ���� ����
   String calculateTime = salesValue.getNowTime(); //�����ʱ�ȭ �ð��� �����ϱ� ���� ����
   
   EmployeeClient(EmployeeManage employeeManage, ProductManage productManage, SalesValueManage salesValueManage){
      this.employeeManage = employeeManage;
      this.productManage = productManage;
      this.salesValueManage = salesValueManage;
   }
   EmployeeClient(){
   }
   public void start(String uid){
      boolean flag = true;
      employee.setUid(uid); //���� ������� ����ID �� setter
      while(flag){
         System.out.println();
         System.out.println("===="+employee.getUid()+"����====");
         System.out.println("1.��ǰ�Ǹ�");
         System.out.println("2.���Ȯ��");
         System.out.println("3.��ǰ����");
         System.out.println("4.��/���Ȯ��");
         System.out.println("5.��������");
         System.out.println("0.����");
         System.out.print("#�޴�����> ");
         int menuNumber = Integer.parseInt(scan.nextLine());
         switch(menuNumber){
            case 1:
            sales(); //����-�Ǹ�
            break;

            case 2:
            productManage.print(); //����-���Ȯ��
            break;

            case 3:
            order(); //����-��ǰ����
            break;

            case 4:
            checkCommuting(); //����-��/���Ȯ��
            break;

            case 5:
            deadLineCalculate();
            break;

            case 0:
            flag = false;
            break;

            default:
            System.out.println("�޴���ȣ�� �߸� �Է��ϼ̽��ϴ�.");
            break;
         }
      }
   }
   public void sales(){   //����-�Ǹ�
      System.out.println();
      System.out.println("====��ǰ�Ǹ�====");
      System.out.println("<��ǰ�ڵ�>");
      System.out.println("(1)���� (2)���� (3)���̽�ũ�� (4)�Ｎ��ǰ (5)�ַ� (6)���");
      System.out.println();
      System.out.print("��ǰ�ڵ带 �Է����ּ���:");
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
      System.out.print("�Ǹ��� ��ǰ�� ��ȣ�� �Է��ϼ���: ");
      int saleIndex = Integer.parseInt(scan.nextLine());
      System.out.print("������ �Է����ּ���: ");
      int amount = Integer.parseInt(scan.nextLine());
      int count = foundProduct.get(saleIndex-1).getNumber();   //�Ǹ��� ��ǰ�� ���� ������ count�� ����
           String productName = foundProduct.get(saleIndex-1).getName(); //�Ǹ��� ��ǰ���̸� ����
         if(count<amount){
            System.out.println();
            System.out.println("��ǰ ���� ����!!!");
            System.out.println("��ǰ�� �������ּ���!!!");
         }
         else{
            count -= amount;   //������� - �Ǹż���
            foundProduct.get(saleIndex-1).setNumber(count);   //���������� ���� ��ǰ ������ ����
            productManage.save(); //��ǰ����Ʈ ���� ����
            int price = foundProduct.get(saleIndex-1).getPrice();   //�Ǹ��� ��ǰ ���� ����
            int salePrice = price * amount;   // �Ǹűݾ� ��� ��ǰ���� * �ǸŰ���
            dailySales += salePrice; //�Ϸ� �� ����ݾ� ���
            System.out.println();
            System.out.println("<�����˸�>");
            System.out.println("��ǰ��:"+productName+" ����:"+amount+" �����ݾ�:"+salePrice+"�� �Դϴ�.");
            System.out.println();
                  
         }      
   }
   public void order(){
      System.out.println();
      System.out.println("====��� ���� ��ǰ ���===");
      System.out.println("<��ǰ�ڵ�>");
      System.out.println("(1)���� (2)���� (3)���̽�ũ�� (4)�Ｎ��ǰ (5)�ַ� (6)���");
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
               System.out.println("��� ��ǰ�� ��� ����մϴ�.");
            }
            else{
               System.out.println();   
               System.out.print("�ֹ��Ͻ� ��ǰ��ȣ�� �Է��ϼ���:");
               int orderIndex = Integer.parseInt(scan.nextLine());
                  if(orderIndex>orderProduct.size() || orderIndex <= 0){
                     System.out.println("��ȣ�� �߸� �Է��ϼ̽��ϴ�.");
                     order();
                  }
                  else{
                     System.out.print("������ �Է��ϼ���:");
                     int amount = Integer.parseInt(scan.nextLine());
                     int count = orderProduct.get(orderIndex-1).getNumber(); //�����ǰ ����
                     orderProduct.get(orderIndex-1).setNumber(count+amount); //�����ǰ ���� + �ֹ���ǰ ����
                     String orderName = orderProduct.get(orderIndex-1).getName(); //�ֹ��� ��ǰ �̸�
                     System.out.println(orderName+" "+amount+"�� ���� �Ǿ����ϴ�");
                     System.out.println();
                     productManage.save();
                  }
               }
   }
   public void deadLineCalculate(){
      System.out.println();
      System.out.println("====��������====");
      System.out.println("1.����Ȯ��");
      System.out.println("2.���ϸ����ʱ�ȭ");
      System.out.println("3.����ȭ��");
      System.out.print("#�޴�����> ");
      int menuNumber = Integer.parseInt(scan.nextLine());
         if(menuNumber==1){
            System.out.println("���� �� �Ǹ� �ݾ��� "+dailySales+"�� �Դϴ�.");
            System.out.println();
         }
         else if(menuNumber==2){
            System.out.println("���� ���� �ݾ��� �ʱ�ȭ�Ͽ����ϴ�.");
            salesValueManage.add(new SalesValue(dailySales,calculateTime));
            salesValueManage.save();
            dailySales = 0;
         }
         else if(menuNumber==3){
         }
         else{
            System.out.println("��ȣ�� �߸� �Է��ϼ̽��ϴ�");
         }

   }
   public void checkCommuting(){
      WorkTimeManage workTimeManage = new WorkTimeManage();
      Day day = new Day();
      
      String inTime = null;
      String outTime = null;
      String logging = employee.getUid();
      System.out.println();
      System.out.println("====��/���Ȯ��====");
      System.out.println("1.��ٱ��");
      System.out.println("2.��ٱ��");
      System.out.println("0.����ȭ��");
      System.out.print("#�޴�����> ");
      int menuNumber = Integer.parseInt(scan.nextLine());
         if(menuNumber==1){
            System.out.println();
            System.out.println("<"+logging+"�� ��ٽð�>");
            inTime = day.getDay();
            System.out.println(inTime);
            workTimeManage.add(new WorkTime(logging,inTime,outTime)); //��ٽð������� ��ٽð��� null�� ����
            workTimeManage.save();
         }
         else if(menuNumber==2){
            System.out.println();
            System.out.println("<"+logging+"�� ��ٽð�>");
            outTime = day.getDay();
            System.out.println(outTime);
            int index = workTimeManage.workTimeList.size(); //���� ���� ������
            workTimeManage.workTimeList.get(index-1).setOutTime(outTime); //��ٽð��� ����Ǿ��ִ� �ε����� ��ٽð� ����
            workTimeManage.save();
         }
         else if(menuNumber==0){

         }
         else{
            System.out.println("��ȣ�� �߸� �Է��ϼ̽��ϴ�.");
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
      System.out.print("��ٽð�:"+inTime+" ��ٽð�:"+outTime);
   }
}
class WorkTimeManage{
   Vector<WorkTime>workTimeList = new Vector<WorkTime>();
   WorkTimeManage(){
      FileReader fr = null;
      try{
         fr = new FileReader("WorkTimeList.txt");
      }catch(FileNotFoundException fnfe){
         System.out.println("WorkTimeList.txt ������ �����ϴ�.");
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
         System.out.println("WorkTimeList.txt�� readLine()���� ���� �� �����ϴ�.");
      }
   }
   public void save(){
      FileWriter fw = null;
      try{
         fw = new FileWriter("WorkTimeList.txt");
      }catch(IOException ioe){
         System.out.println("WorkTimeList.txt�� �� �� �����ϴ�.");
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

class SalesValue{ // �Ǹ� �� Ŭ����
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
         System.out.println("salesValue.txt ������ �����ϴ�");
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
         System.out.println("salesValue.txt�� readLine()���� ���� �� �����ϴ�.");
      }
   }
   public void save(){
      FileWriter fw = null;
      try{
         fw = new FileWriter("salesValue.txt");
      }catch(IOException ioe){
         System.out.println("salesValue.txt�� �� �� �����ϴ�.");
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

   public String getDay(){ //�����ڿ� �ǽð��ð��� �ʱ�ȭ�� �־� �޼ҵ� ȣ��� �ǽð� ���� 
      new Day();
      return time;
   }
}





