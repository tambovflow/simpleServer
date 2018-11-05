import java.net.*;
import java.io.*;
import java.util.*;

public class Client{
	public static final String ANSI_RESET = "\u001B[0m";

	private Socket s;
	private BufferedReader reader;
	private PrintWriter writer;
	private BufferedWriter bwriter;

	public static void main(String... args){
		Client client = new Client();
		client.go();
	}

	public void go(){
		try{
			s = new Socket("localhost", 8080);
			System.out.print("Enter Your NickName: ");
			String name = (new Scanner(System.in)).nextLine();
 			writer = new PrintWriter(s.getOutputStream(),true);
			new Thread(new NewThread()).start();
			BufferedReader icq = new BufferedReader(new InputStreamReader(System.in));
			String color = color();
			while(true){
				writer.println(color + name +": " + icq.readLine() + ANSI_RESET);
			}
		} catch(IOException ex){
			ex.printStackTrace();
		}
	}
	private class NewThread implements Runnable{

		public void run(){
			try{
				InputStreamReader isr = new InputStreamReader(s.getInputStream());
				reader = new BufferedReader(isr);
			} catch(IOException ex){}

			String str;
			try{
				while((str = reader.readLine())!=null){
					System.out.println(str);
				}
			} catch(IOException ex){
			ex.printStackTrace();
			}
		}
	}

	private String color(){
		String[] colorArr = {"\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m","\u001B[35m", "\u001B[36m","\u001B[37m", 
		"\u001B[1m", "\u001B[2m", "\u001B[3m", "\u001B[4m", "\u001B[5m","\u001B[6m", "\u001B[7m","\u001B[40m", "\u001B[41m", 
		"\u001B[42m", "\u001B[43m", "\u001B[44m", "\u001B[45m","\u001B[46m", "\u001B[47m"};

		int count = (int) (Math.random()*colorArr.length);
		return colorArr[count];
	}
}
