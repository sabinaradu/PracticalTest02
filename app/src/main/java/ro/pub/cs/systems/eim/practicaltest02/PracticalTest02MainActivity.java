package ro.pub.cs.systems.eim.practicaltest02;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest02MainActivity extends AppCompatActivity {

    EditText serverPortEditText = null;
    Button serverConnectButton = null;
    EditText clientAddressEditText = null;
    EditText clientPortEditText = null;
    Button clientSendRequestButton = null;
    ServerThread serverThread = null;
    EditText keyEditText = null;
    EditText valueEditText = null;
    Spinner operationSpinner = null;
    TextView resultTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        serverPortEditText = findViewById(R.id.server_port_edit_text);
        serverConnectButton = findViewById(R.id.server_connect_button);
        clientAddressEditText = findViewById(R.id.client_address_edit_text);
        clientPortEditText = findViewById(R.id.client_port_edit_text);
        clientSendRequestButton = findViewById(R.id.client_send_request_button);
        resultTextView = findViewById(R.id.result_text_view);
        keyEditText = findViewById(R.id.key_edit_text);
        valueEditText = findViewById(R.id.value_edit_text);
        operationSpinner = findViewById(R.id.operation);
        serverConnectButton.setOnClickListener(v -> {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();

                return;
            }

            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.serverSocket == null) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Could not create server thread!", Toast.LENGTH_SHORT).show();

                return;
            }

            serverThread.start();
        });

        clientSendRequestButton.setOnClickListener(v -> {
            String serverAddress = clientAddressEditText.getText().toString();
            String serverPort = clientPortEditText.getText().toString();

            String operation = operationSpinner.getSelectedItem().toString();
            String key = keyEditText.getText().toString();
            String value = valueEditText.getText().toString();

            if (key.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Key should be filled!", Toast.LENGTH_SHORT).show();

                return;
            }

            if (serverAddress.isEmpty() || serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Client connection parameters should be filled!", Toast.LENGTH_SHORT).show();

                return;
            }

            if (serverThread == null || !serverThread.isAlive()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] There is no server to connect to!", Toast.LENGTH_SHORT).show();

                return;
            }

            ClientThread clientThread = new ClientThread(serverAddress, Integer.parseInt(serverPort), operation, key, value, resultTextView);
            clientThread.start();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (serverThread != null) {
            serverThread.stopThread();
        }
    }
}