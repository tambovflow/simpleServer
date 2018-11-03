import java.net.*;
import java.io.*;
import java.util.*;

public class Server{
	BufferedReader reader;
	PrintWriter writer;
	ArrayList<PrintWriter> writerArray = new ArrayList<>();

	public static void main(String... args){	
		Server server = new Server();
		server.go();
	}
	public void go(){
		try{
			ServerSocket ss = new ServerSocket(8080);
			
			while(true){
				Socket socket = ss.accept();
				writer = new PrintWriter(socket.getOutputStream());
				writerArray.add(writer);
				new Thread(new NewThread(socket)).start();
			}
		} catch(IOException ex){
			ex.printStackTrace();
		}
		
	}
	private class NewThread implements Runnable{
		Socket socket;
		public NewThread(Socket socket){
			this.socket = socket;
			try{	
				InputStreamReader isr = new InputStreamReader(socket.getInputStream());
				reader = new BufferedReader(isr);
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}

		public void run(){
			String str;
			try{
				while((str = reader.readLine())!=null){
					System.out.println(str);
					tellEveryone(str);
				}
			} catch(Exception ex){}
		}
	}

		public void tellEveryone(String message){

		for(PrintWriter p : writerArray){
			try{
				p.println(message);
				p.flush();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}
