package kr.co.leehana.chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Hana Lee
 * @since 2015-11-13 15-54
 */
public class ClientBackground {

	private Socket socket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	private ClientGui clientGui;
	private String serverMsg;

	public ClientBackground(ClientGui clientGui) {
		this.clientGui = clientGui;
	}

	public void connect() {
		try {
			socket = new Socket("127.0.0.1", 7777);
			System.out.println("서버 연결 완료...");

			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			dataInputStream = new DataInputStream(socket.getInputStream());

			dataOutputStream.writeUTF("클라이언트 에서 메세지 보냄.");
			System.out.println("메세지 전송 완료");

			while (dataInputStream != null) {
				serverMsg = dataInputStream.readUTF();
				clientGui.appendMessage(serverMsg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ClientBackground clientBackground = new ClientBackground(null);
		clientBackground.connect();
	}

	public void sendMessage(String msg) {
		try {
			dataOutputStream.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
