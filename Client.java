import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	static DataInputStream is;
	static DataOutputStream os;
	static String send = " ";
	static String recieve = " ";

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		Socket client = new Socket("localhost", 8090);

		class read extends Thread {
			public void run() {
				while (send.equals("q") != true) {
					try {
						is = new DataInputStream(client.getInputStream());
						recieve = is.readLine();

						System.out.println(recieve);

						if (send.equals("q")) {
							client.close();
							System.exit(0);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
		;

		class write extends Thread {
			public void run() {
				while (send.equals("q") != true) {
					try {

						os = new DataOutputStream(client.getOutputStream());

						send = scanner.nextLine();

						os.writeBytes(send + "\n");

						if (send.equals("q")) {
							client.close();
							System.exit(0);
						}
					} catch (IOException e) {

						System.out.println("from write in clinet");
					}

				}
			}
		}
	;
		new read().start();
		new write().start();


	}

}
