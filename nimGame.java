import java.util.Scanner;

public class nimGame{
 static Scanner in= new Scanner(System.in);
	public static void main (String[] args){
		System.out.println("Nim is a mathematical game of strategy in which two players take turns removing objects from 3 piles. On each turn, a player must remove at least one object, and may remove any number of objects provided they all come from the same pile. The goal of the game is to take the last object. \n");
		String again;
		do{
			System.out.println("Welcome to nim game! Please enter three numbers:");
			
			int a=in.nextInt(), b=in.nextInt(),c=in.nextInt();
			
			Nim game=new Nim(a,b,c);
			boolean result = game.run();
			if (result)System.out.println("Sorry today is not your day!");
				else System.out.println("I don't believe it you cheated");
				System.out.println("Type y to play again: ");
				again=in.next();
		}
		while(again.equalsIgnoreCase("y"));
	}

public static class Nim{   //takes in the initial piles and runs the game
	int a,b,c;
	Nim(int a,int b,int c){
		this.a=a;
		this.b=b;
		this.c=c;
		
	}
	boolean run(){

		System.out.println("Great\n"+ a + " "+ b + " "+c +"\nWould you like to go first or second:");
		String answ=in.next();
		if(answ.equals("f")|| answ.equals("F")||answ.equals("First")||answ.equals("first"))
			 return oppMove();  //recursive functions that call eachother after each round, if one returns true than com wins 
		else return comMove();
		

	}
	boolean comMove(){
		System.out.println(a+" "+b+ " "+c);
		int max;
		if(a>= b && a >=c)max=a;
			else if (b>=a && b>=c)max=b;
			else max=c;						//find max cause its helpful in the qualize function
			int [] result=equalize(max);  //equalize returns a array[2] where [0] is a,b,c respecitvely and [1] is the new value for it
			if (result[0]==0){
				System.out.println("COM: "+a+" " +(a-result[1]));
				a=result[1];
			}
			else if (result[0]==1){
				System.out.println("COM: "+b+" " +(b-result[1]));
				b=result[1];
			}
			else {
				System.out.println("COM: "+c+" " +(c-result[1]));
				c=result[1];
			}


		System.out.println("_______________________");
		if(a==0 && b==0 && c==0){			//game is over
			System.out.println(a+ " "+b + " "+c);
			return true;
		}
		return oppMove();

	}
	boolean oppMove(){		//user enters the column and the amount to be deducted from that column
		System.out.println(a+ " "+b + " "+c);
		System.out.print("USR: ");
		int inp=in.nextInt();
		int sub=in.nextInt();
		if (sub <1){
			System.out.println("Cannot subtract value");
			oppMove();
		}
		if (inp!=a && inp!=b && inp !=c ){
			System.out.println(inp +" does not equal a column");
			return oppMove();
		}
		else if(inp==a && a>=sub && a !=0){
			a-=sub;
			
		}
		else if(inp==b &&b>=sub && b !=0){
			b-=sub;
			
		}
		else if(inp==c && c>=sub && c !=0){
			c-=sub;

		}
		else {
			System.out.println("Invalid Move");
			return oppMove();
		}
		System.out.println("_______________________");
		if(a==0 && b==0 && c==0){
			System.out.println(a+ " "+b + " "+c);
			return false;
		}
		return comMove();
	}
	int [] equalize(int max){
		int n=0;
		while((int)Math.pow(2,n)<=max)n++;		//get bit length of max int
		int[][] arr= new int[3][n];
		int tempa=a,tempb=b,tempc=c;
		for(int i=n-1;i>=0;i--){		//deconstruct numbers to binary
			arr[0][i]=tempa%2;
			arr[1][i]=tempb%2;
			arr[2][i]=tempc%2;
			tempa/=2;
			tempb/=2;
			tempc/=2;
			

		}
	/*	for (int i = 0; i < arr.length; i++) {		//print loop
    		for (int j = 0; j <arr[i].length; j++) {
       			 System.out.print(arr[i][j] + " ");
    		}
    		System.out.println();
		}*/

		//loop to get number to change by finding the number with odd one in highest value bit
		 max=-1;
		 for(int i=0;i<n;i++){
		 	int count=0;
		 	for(int r=0;r<3;r++){
		 		if(arr[r][i]==1)count++;
		 	}
		 	if(count%2==1){
		 		if(arr[0][i]==1)max=0;
		 		else if(arr[1][i]==1)max=1;
		 		else if(arr[2][i]==1)max=2;
		 		break;
		 	}
		 }
		 if (max==-1){
		 	if (a!=0)max=0;
		 	else if(b!=0)max=1;
		 	else max=2;
		 }

		boolean changeFlag=false;		//loop that counts to see if 1's are even and if not makes changes to max
		 for(int i=0;i<n;i++){
		 	int count=0;
		 	for(int r=0;r<3;r++){
		 		
		 		if(arr[r][i]==1)count++;
		 	}
		 	if (count%2!=0 && arr[max][i]==1){
		 		arr[max][i]=0;
		 		changeFlag=true;
		 	}
		 	else if(count%2!=0 && arr[max][i]==0){
		 		arr[max][i]=1;
		 		changeFlag=true;
		 }
		}
			/*for (int i = 0; i < arr.length; i++) {		//print loop
    		for (int j = 0; j <arr[i].length; j++) {
       			 System.out.print(arr[i][j] + " ");
    		}
    		System.out.println();
		}*/
		int[] ret=new int[2];
		ret[0]=max;
		 if(!changeFlag){			//if the items were already balanced take one from max
		 	if(max==0)ret[1]=a-1;
		 	else if(max==1)ret[1]=b-1;
		 	else ret[1]=c-1;
		 }
		 else{	int result=0;		//rebuild max
		 	for(int i=0; i<n;i++){
		 		result+=(arr[max][n-i-1]*Math.pow(2,i));
		 	}
		 	ret[1]=result;}
		return  ret;
	
}
}
}