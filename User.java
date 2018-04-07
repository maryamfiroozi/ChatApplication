import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class User {
	String name;
	Socket socket;
	DataOutputStream output;
	DataInputStream input;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public DataOutputStream getOutput() {
		return output;
	}
	public void setOutput(DataOutputStream output) {
		this.output = output;
	}
	public DataInputStream getInput() {
		return input;
	}
	public void setInput(DataInputStream input) {
		this.input = input;
	}
	
	User(String name, Socket socket, DataInputStream Is, DataOutputStream Os){
		this.name = name;
		this.socket = socket;
		input = Is;
		output = Os;
		try{
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
		}
		catch(Exception e){
			e.getMessage();
		}
	}
	User(){
		
	}
	
}
