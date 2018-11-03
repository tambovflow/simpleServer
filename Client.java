import java.net.*;
import java.io.*;
import java.util.*;

public class Client{
	Socket socket;
	BufferedReader reader;
	PrintWriter writer;
	BufferedWriter bwriter;
	public static void main(String... args){
		Client client = new Client();
		client.go();
	}

	private class NewThread implements Runnable{

		public void run(){
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

	public void go(){
		try{
			Socket s = new Socket("localhost", 8080);
 			writer = new PrintWriter(s.getOutputStream());
			InputStreamReader isr = new InputStreamReader(s.getInputStream());
			reader = new BufferedReader(isr);
			new Thread(new NewThread()).start();
			System.out.print("Enter Your NickName: ");
			String name = (new Scanner(System.in)).nextLine();

			while(true){
				Scanner scanner = new Scanner(System.in);
				String st = scanner.nextLine();
				writer.write(name +": " + st + "\n");
				writer.flush();
			}
		} catch(IOException ex){
			ex.printStackTrace();
		}
	}
}
