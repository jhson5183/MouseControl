package com.jhson.mousecontrol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MouseActivity extends Activity {

	final int PORT = 10000;
	final String MESSAGE_FULL = "MESSAGE:FULL";
	final String MESSAGE_SOUNDUP = "MESSAGE:SOUNDUP";
	final String MESSAGE_SOUNDDOWN = "MESSAGE:SOUNDDOWN";
	final String MESSAGE_LEFT = "MESSAGE:LEFT";
	final String MESSAGE_RIGHT = "MESSAGE:RIGHT";
	final String MESSAGE_MOUSELEFT = "MESSAGE:MOUSELEFT";
	final String MESSAGE_MOUSERIGHT = "MESSAGE:MOUSERIGHT";
	final String MESSAGE_MOUSE = "MESSAGE:MOUSE:";

	private int x = 0;
	private int y = 0;

	private InetAddress iNet = null;
	private DatagramSocket socket = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mouse_activity);

		Button left = (Button) findViewById(R.id.mouse_left);
		Button right = (Button) findViewById(R.id.mouse_right);
		View view = findViewById(R.id.mouse_view);
		Button soundUp = (Button) findViewById(R.id.mouse_activity_sound_up);
		Button soundDown = (Button) findViewById(R.id.mouse_activity_sound_down);
		Button moveLeft = (Button) findViewById(R.id.mouse_move_left);
		Button moveRight = (Button) findViewById(R.id.mouse_move_right);
		Button pull = (Button) findViewById(R.id.mouse_move_full);

		String ipStr = getIntent().getStringExtra("ip");
		try {
			iNet = InetAddress.getByName(ipStr);
			socket = new DatagramSocket();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}catch (SocketException e) {
			e.printStackTrace();
		}

		pull.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendMessageUDP(MESSAGE_FULL);
			}
		});
		soundUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendMessageUDP(MESSAGE_SOUNDUP);
			}
		});
		soundDown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendMessageUDP(MESSAGE_SOUNDDOWN);
			}
		});
		moveLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendMessageUDP(MESSAGE_LEFT);
			}
		});
		moveRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendMessageUDP(MESSAGE_RIGHT);
			}
		});
		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendMessageUDP(MESSAGE_MOUSELEFT);
			}
		});

		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendMessageUDP(MESSAGE_MOUSERIGHT);
			}
		});

		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, final MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					x = (int) event.getX();
					y = (int) event.getY();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {

				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					int moveX = (int) event.getX();
					int moveY = (int) event.getY();

					moveX -= x;
					moveY -= y;

					x = (int) event.getX();
					y = (int) event.getY();
					
					String message = MESSAGE_MOUSE + moveX + ":" + moveY;
					
					sendMessageUDP(message);
				}

				return true;
			}
		});
	}

	private void sendMessageUDP(final String message) {

		new Thread(new Runnable() {
			public void run() {
				try {

					DatagramPacket packet = new DatagramPacket(message.getBytes(), message.getBytes().length, iNet, PORT);

					socket.send(packet);

				} catch (SocketException e) {
					e.printStackTrace();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		socket.close();
	}

}
