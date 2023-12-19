import java.io.*;
import java.net.*;

public class MyServer {
  public static void main(String[] args) {
    try {
      ServerSocket serverSocket = new ServerSocket(8888);
      System.out.println("Сервер запущен. Ожидание подключения...");

      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("Клиент подключен: " + clientSocket);

        ClientHandler clientThread = new ClientHandler(clientSocket);
        clientThread.start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class ClientHandler extends Thread {
  private final Socket clientSocket;

  public ClientHandler(Socket socket) {
    this.clientSocket = socket;
  }

  public void run() {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      String message = reader.readLine();
      System.out.println("Получено от клиента [" + clientSocket + "]: " + message);

      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
