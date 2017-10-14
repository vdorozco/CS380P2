import java.net.Socket;
import java.util.Scanner;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.IOException;


public class PhysLayerClient {
	
	public static void main(String[] args) throws IOException{
		try(Socket socket = new Socket("cs380.codebank.xyz", 38002)){
			Scanner kb = new Scanner(System.in);
			InputStream in = socket.getInputStream();
			InputStreamReader inR = new InputStreamReader(in, "UTF-8");
			BufferedReader br = new BufferedReader(inR);
			OutputStream os = socket.getOutputStream();
			PrintStream out = new PrintStream(os);
			System.out.print("Enter user name: ");
			String username = kb.nextLine();
			out.println(username);

			Runnable receiver  = () -> {
			   try{
				String message;
				while((message = br.readLine())!=null){
					System.out.println(message);
				}
				System.exit(0);
			   }catch (IOException e){
				System.out.println("Exception thrown.");
			   }
			};
			new Thread(receiver).start();
			while(true){
				out.println(kb.nextLine());
			}
		}
	}  
	
}
