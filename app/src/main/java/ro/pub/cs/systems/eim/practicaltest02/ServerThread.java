package ro.pub.cs.systems.eim.practicaltest02;
import android.util.Log;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

    int port;
    ServerSocket serverSocket = null;
    public ServerThread(int port) {
        this.port = port;

        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[SERVER THREAD] An exception has occurred: " + ioException.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (!currentThread().isInterrupted()) {
                Socket socket;

                Log.d(Constants.TAG, "[SERVER THREAD] Calling serverSocket.accept()");
                socket = serverSocket.accept();
                Log.d(Constants.TAG, "[SERVER THREAD] Accepted connection from " + socket.getInetAddress() + ":" + socket.getLocalPort());

                CommunicationThread communicationThread = new CommunicationThread(this, socket);

                communicationThread.start();
            }

        } catch (IOException e) {
            Log.e(Constants.TAG, "[SERVER THREAD] An exception has occurred: " + e.getMessage());
        }
    }

    public void stopThread() {
        interrupt();
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ioException) {
                Log.e(Constants.TAG, "[SERVER THREAD] An exception has occurred: " + ioException.getMessage());
            }
        }
    }

}
