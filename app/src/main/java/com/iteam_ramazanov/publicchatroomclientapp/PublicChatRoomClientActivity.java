package com.iteam_ramazanov.publicchatroomclientapp;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        EditText edtNickName = (EditText) findViewById(R.id.edtNickName);
        EditText edtMessage = (EditText) findViewById(R.id.edtMessage) ;
        final String ip_address = edtHost.getText().toString();
        final int port = Integer.parseInt(edtPort.getText().toString());
        final String nick_name = edtNickName.getText().toString();
        final String message = edtMessage.getText().toString();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    TCPConnection connection = new TCPConnection(PublicChatRoomClientActivity.this, ip_address, port);
                    connection.sendString(nick_name + ": " + message);
                } catch (IOException e) {
                    printMsg("Connection exception: " + e);
                } catch (Exception e) {
                    printMsg("Connection exception: " + e);
                }
            }
        }).start();
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

    private synchronized void printMsg(final String message) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                TextView tvLogs = (TextView) findViewById(R.id.tvLogs);
                tvLogs.append(message);
            }
        });
    }
}
