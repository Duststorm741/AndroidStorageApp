package example.com.storageapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends Activity {

    private EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText = findViewById(R.id.inputText);
    }

    public void onClick(View view) {
        String textToSend = inputText.getText().toString();

        // Run network operations in a separate thread to avoid blocking the main UI thread.
        Thread networkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String serverIP = "192.168.56.1";
                    int serverPort = 5678;

                    // Create a socket and connect to the server.
                    Socket socket = new Socket(serverIP, serverPort);

                    // Get the output stream of the socket and send the text.
                    OutputStream out = socket.getOutputStream();
                    out.write(textToSend.getBytes());
                    out.flush();

                    // Close the socket after sending the data.
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        networkThread.start();
    }
}
