package com.iteam_ramazanov.publicchatroomclientapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

public class PublicChatRoomClientActivity extends AppCompatActivity implements TCPConnectionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_chat_room_client);
    }

    public void btnSendOnClick(View v) {
        EditText edtHost = (EditText) findViewById(R.id.edtHost);
        EditText edtPort = (EditText) findViewById(R.id.edtPort);
        String ip_address = edtHost.getText().toString();
        int port = Integer.parseInt(edtPort.getText().toString());
        try {
            TCPConnection connection = new TCPConnection(this, ip_address, port);
        } catch (IOException e) {
            printMsg("Connection exception: " + e);
        }
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        printMsg("Connection established ...");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String message) {
        printMsg(message);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        printMsg("Connection with server is broken ...");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        printMsg("Connection exception: " + e);
    }

    private synchronized void printMsg(String message) {
        EditText edtNickName = (EditText) findViewById(R.id.edtNickName);
        edtNickName.append(message);
    }
}
