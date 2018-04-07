import java.awt.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class finalServer {
	static DataOutputStream serverOutput;
	static DataInputStream serverInput;

	static DataOutputStream clientOutput;
	static DataInputStream clientInput;

	static ServerSocket server;
	static ArrayList<User> Onlineusers = new ArrayList<>();
	static ArrayList<User> BusyUsers = new ArrayList<>();
	static Scanner scan = new Scanner(System.in);
	static String message = "";
	static Socket client1;
	static Socket client2;

	public static void main(String[] args) throws Exception {

		server = new ServerSocket(8090);

		while (true) {

			client1 = server.accept();

			User a = new User("newUser", client1, serverInput, serverOutput);
			a.output = new DataOutputStream(a.getOutput());
			a.input = new DataInputStream(a.getInput());
			class ToAll extends Thread {
				@Override
				public void run() {
					String name = "";
					while (true) {
						String contact = "";
						User b = null;
						try {

							a.output.writeBytes("please choose one of the below options" + "\n");
							a.output.writeBytes("login/list/close" + "\n");
							String line = a.input.readLine();
							if (line.equals("login")) {
								a.output.writeBytes("what is your name?" + "\n");
								name = a.input.readLine();
								Onlineusers.add(new User(name, client1, serverInput, serverOutput));
								a.output.writeBytes("please choose one of the below options" + "\n");
								a.output.writeBytes("list/close" + "\n");
							}
							if (a.input.readLine().equals("list")) {
								a.output.writeBytes("please choose one of the people below" + "\n");
								for (int i = 0; i < Onlineusers.size(); i++) {
									a.output.writeBytes(Onlineusers.get(i).name + "\n");
								}
								contact = a.input.readLine();
								b = new User(contact, client2, clientInput, clientOutput);
								clientInput = new DataInputStream(b.getInput());
								clientOutput = new DataOutputStream(b.getOutput());

								for (int i = 0; i < Onlineusers.size(); i++) {
									if (contact.equals(Onlineusers.get(i).name)) {
										a.output.writeBytes(
												"you are connected to " + Onlineusers.get(i).getName() + "\n");
										String message1 = "";
										String message2 = "";
										final int j = i;
										int m = 0;
										while(true){
											if(!a.input.readLine().equals(null)){
												message1 = a.input.readLine();
												System.out.println(message1);
												Onlineusers.get(i).output.writeBytes(message1 + "\n");
												continue;
											}
											if(!Onlineusers.get(i).input.readLine().equals(null)){
												message2 = Onlineusers.get(i).input.readLine();
												System.out.println(message2);
												a.output.writeBytes(message2 + "\n");
												continue;
											}
										}
										/*
										 * while (true) { message1 =
										 * a.input.readLine();
										 * System.out.println(message1);
										 * Onlineusers.get(i).output.writeBytes(
										 * message1 + "\n");
										 * if(message1.equals("exit")){ break;
										 * }} while(true){
										 * 
										 * message2 =
										 * Onlineusers.get(i).input.readLine();
										 * System.out.println(message2);
										 * a.output.writeBytes(message2 + "\n");
										 * if(message2.equals("exit")){ break; }
										 * 
										 * }
										 */
									}
								}

							}

							if (a.input.readLine().equals("close")) {
								client1.close();
								client2.close();
							} else {

								continue;
							}
						} catch (Exception e) {
							e.getMessage();
						}
					}
				}

			}
			ToAll g = new ToAll();
			g.start();
		}

	}

}
