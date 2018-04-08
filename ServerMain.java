import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerMain {

	public static void main(String[] args) throws Exception {
		String message = "";
		Scanner scan = new Scanner(System.in);
		ServerSocket server = new ServerSocket(8090);
		Socket client1 = server.accept();
		Socket client2 = server.accept();

		class ServerRead1 extends Thread {
			@Override
			public void run() {
				try {

					while (!(message.equals("q"))) {
						DataInputStream is = new DataInputStream(client1.getInputStream());
						String line = is.readLine();

						DataOutputStream os = new DataOutputStream(client2.getOutputStream());

						os.writeBytes(line + "\n");

						// }
						if (message.equals("q")) {
							client1.close();
							System.exit(0);
						}

						System.out.println(line);
					}

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}

		}
		new ServerRead1().start();
		class ServerRead2 extends Thread {
			public void run() {
				try {

					while (!(message.equals("q"))) {
						DataInputStream is = new DataInputStream(client2.getInputStream());
						String line = is.readLine();

						DataOutputStream os = new DataOutputStream(client1.getOutputStream());

						os.writeBytes(line + "\n");

						if (message.equals("q")) {
							client2.close();
							System.exit(0);
						}

						System.out.println(line);
					}
				}

				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}

		}
		new ServerRead2().start();

	}
}
