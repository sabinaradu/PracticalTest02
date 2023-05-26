package ro.pub.cs.systems.eim.practicaltest02;
import android.util.Log;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{

    int serverPort;
    String serverAddress;
    String key;
    String value;
    String operation;
    TextView resultTextView;

    Socket socket;

    public ClientThread(String serverAddress, int serverPort, String operation, String key, String value, TextView resultTextView) {
        this.serverPort = serverPort;
        this.serverAddress = serverAddress;
        this.key = key;
        this.value = value;
        this.operation = operation;
        this.resultTextView = resultTextView;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(serverAddress, serverPort);

            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);

            if (operation.equals("mul")) {
                printWriter.println(operation + "," + key + "," + value);
            }

            if (operation.equals("add")) {
                printWriter.println(operation + "," + key + "," + value);
            }

            String response;

            while ((response = bufferedReader.readLine()) != null) {
                final String finalizedResponse = response;

                System.out.println(response);

                resultTextView.post(() -> resultTextView.setText(finalizedResponse));
            }
        }
        catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());

        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
                }
            }
        }
    }

}
