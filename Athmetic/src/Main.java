import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
public class Main {
	static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript"); 
	public static void main(String[] args) throws ScriptException{
		ArrayList<String> expression=new ArrayList<String>();
		ArrayList<String> expression1=new ArrayList<String>();
		ArrayList<String> expression2=new ArrayList<String>();
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		boolean judge=judge(num);
		Random random=new Random();
		int realnum=num-random.nextInt(num);
		char[] operator=new char[]{'+','-','*','/'};
		//判断参数合法性。整数
		if(judge){			
			//编写加减乘除运算
			for(int i=0;i<num-realnum;i++){
				int n=random.nextInt(3)+3; //3-5个运算符
				
				int[] number=new int[n+1]; 
				String ex=new String();
				int numcount[]=new int [n+1];
				for(int o=0;o<n+1;o++) {
					numcount[o]=0;
				}
				for(int j=0;j<=n;j++){
					number[j]=random.nextInt(100)+1; 
				}//4-5个数字
				
				
				for(int o=0;o<=n;o++) {
					int temp=number[o];
//					System.out.print("此时计算的数字："+temp);
					while(temp!=0) {
						numcount[o]++;
						temp/=10;
					}
//					System.out.println("他的位数："+numcount[o]);
				}
					
				//确定生成的每个随机数的位数
				
				for(int j=0;j<n;j++){
					int s=random.nextInt(4);//随机选择某个运算符
					if(s==1) {
						number[j]=decide1(number[j],number[j+1]);
						int temp=number[j];
						numcount[j]=0;
						while(temp!=0) {
							numcount[j]++;
							temp/=10;
						}
					}
					if(s==3){
						number[j+1]=decide3(number[j],number[j+1]);
						int temp=number[j+1];
						numcount[j+1]=0;
						while(temp!=0) {
							numcount[j+1]++;
							temp/=10;
						}
					}
					ex+=String.valueOf(number[j]);
					ex+=String.valueOf(operator[s]);///5+4+6+9
				}
				ex+=String.valueOf(number[n]);
				int m=random.nextInt(n-1)+2;
				int num0=0;
			    while(num0==0){
					for(int k=0;k<m;k++) {	
						int insertPos1=random.nextInt(n)*2;				
						int insertPos2=n*2+1-random.nextInt(n-insertPos1/2)*2;
						int  rePos1=insertPos1-insertPos1/2;
						int rePos2=insertPos2-insertPos2/2;	
						for(int w=0;w<rePos1;w++) {
							insertPos1+=(numcount[w]-1);
	
						}
						numcount[rePos1]++;
						for(int w=0;w<rePos2;w++) {
							insertPos2+=(numcount[w]-1);
	
						}
						numcount[rePos2-1]++;
	
						StringBuilder ex0 = new StringBuilder(ex);
						ex0.insert(insertPos1, "(");
	
						ex0.insert(insertPos2, ")");
						ex=ex0.toString();
						
					}
				
					ArrayList<String> numbers=new ArrayList<String>();
					for(int b=0;b<=n;b++) {
						String number0='('+String.valueOf(number[b])+')';
						numbers.add(number0);
					}
					num0=0;
					for(String number0:numbers) {
						ex=ex.replace(number0, String.valueOf(number[num0]));
						num0++;
					}
			    }				
				try {
						String a ="";
						a=a+jse.eval(ex);
						int b = Integer.valueOf(a);
						if( b<0) {
							i=i-1;
					}
					else{
						expression1.add(ex);
					}
					}
					catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							i=i-1;
							
					}
			}
		//编写真分数加减运算
		if(judge) {
			
			for(int i=0;i<realnum;i++) {
			int n=random.nextInt(3)+3;//3-5个运算符
//			System.out.println(n);
			int[] number=new int[(n+1)*2];
			int result1 = 0,result2=0;//result1为最终结果的分子，result2为最终结果的分母
			for(int j=0;j<(n+1)*2;j++){
				number[j]=random.nextInt(10)+1; 
			}//4-5个数字
			for(int k=0;k<(n+1)*2;k+=2) {
				if(number[k]>number[k+1]) {
					int temp=number[k];
					number[k]=number[k+1];
					number[k+1]=temp;
				}
			}
			result1=number[0];
			result2=number[1];
			boolean flag=false;
			boolean flag2=false;
			for(int k=0;k<=n-1;k++) {
//				System.out.print("num1:"+number[(k+1)*2]+" num2:"+number[(k+1)*2+1]);
				result1=number[(k+1)*2]*result2+number[(k+1)*2+1]*result1;
				result2=result2*number[(k+1)*2+1];
				int result0=division(result1,result2);
//				System.out.print("result1:"+result1+" result2:"+result2);
//				System.out.println(" result0:"+result0);
				result1/=result0;
				result2/=result0;
				if(k==n-1&&result1>result2) {
					flag=true;
				}
				else if(k==n-1&&result1==result2) {
					flag2=true;
				}
			}
			if(flag) {
				i--;
				continue;
			}
			String ex = new String();
			for(int k=0;k<n;k++) {
				ex+=String.valueOf(number[k*2])+'/'+String.valueOf(number[k*2+1])+'+';
			}
			ex+=String.valueOf(number[n*2])+'/'+String.valueOf(number[n*2+1]);
			if(!flag2) {
			ex+='='+String.valueOf(result1)+'/'+String.valueOf(result2);
			}else {
				ex+='='+'1';
			}
			
			expression2.add(ex);
		}
		}
		calculate(expression1);
		for(String ex:calculate(expression1)) {
			System.out.println(ex);
			expression.add(ex);
		}
		for(String ex:expression2) {
			System.out.println(ex);
			expression.add(ex);
		}
		WriteToFile write=new WriteToFile(
				"ArithmeticExpression.txt",expression);
					
	}
		else{
			System.out.println("非法输入！");
	}
	}

	private static int division(int n,int m) {
		if(n<m) {
			return division(m,n);
		} //交换n，m的值
		else if(n==m) {
			 return n;
		}
		else
		{
			 int temp=n;
			 n=m;
			 m=temp-n;
			 return division(n,m); //重复上述过程
		}
		
	}
	private static int decide1(int x,int y) {
		Random random=new Random();
		if(x<y) {
			x=random.nextInt(100)+y;
			return x;
		}
		else {
			return x;
		}
	}
	private static int decide3(int x,int y){
		Random random=new Random();
		
		if(x%y!=0){
			y=random.nextInt(100)+1;
			return decide3(x,y);
		}
		else{
			return y; 
		}
	}
	
	/**
	 * 计算每个等式的结果，并返回运算式
	 
	 */
	  
	private static ArrayList<String> calculate(ArrayList<String> arrayList){
		ArrayList<String> ArithExpress=new ArrayList<String>();
		for(String ax:arrayList){
			try {
				ax=ax+"="+jse.eval(ax);
				ax=ax.replace("/", "÷");
				ArithExpress.add(ax);
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ArithExpress;
	}
	
	private static boolean judge(int data){
		if(data>=0){
			int temp=(int)data;
			if(temp==data)
				return true;
			else
				return false;
		}
		else{
			return false;
		}
	}
}

