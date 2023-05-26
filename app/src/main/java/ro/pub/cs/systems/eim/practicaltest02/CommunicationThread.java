package ro.pub.cs.systems.eim.practicaltest02;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommunicationThread extends Thread{
    ServerThread serverThread;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            bufferedReader = Utilities.getReader(socket);
            printWriter = Utilities.getWriter(socket);

            String line = bufferedReader.readLine();

            String operation = line.split(",")[0];
            String key = line.split(",")[1];
            String value = line.split(",")[2];


            if (operation.equals("mul")) {
                int op1 = Integer.parseInt(key);
                int op2 = Integer.parseInt(value);
                long mul = (long) op1 * op2;
                Thread.sleep(2000);

                String res = Long.toString(mul);
                printWriter.println(res);
                return;
            }

            if (operation.equals("add")) {
                int op1 = Integer.parseInt(key);
                int op2 = Integer.parseInt(value);
                long sum = op1 + op2;

                String res = Long.toString(sum);
                printWriter.println(res);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
