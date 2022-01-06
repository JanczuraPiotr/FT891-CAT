package sp9pj;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Enumeration;

public class Transport {

    static final String OFF = "PS0;";
    static final String ON = "PS1;";
    static final String AutoInformation = "AI1;";

    public Transport(int port) {
        comPort = SerialPort.getCommPorts()[port];
        out = comPort.getOutputStream();
        in = comPort.getInputStream();
    }

    public void startListening()
    {
        try {
            while (true)
            {
                while (comPort.bytesAvailable() == 0) {
                    Thread.sleep(10);
                    Thread.yield();
                }

                byte[] readBuffer = new byte[comPort.bytesAvailable()];
                int numRead = comPort.readBytes(readBuffer, readBuffer.length);
                System.out.println("Read " + numRead + " bytes.");
                System.out.println(Arrays.toString(readBuffer));

                for(int i = 0; i< numRead; ++i) {
                    System.out.print((char)readBuffer[i]);
                }
                System.out.println();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void connect() {
//        SerialPort[] commPorts = serialPort.getCommPorts();
//        for (int i = 0; i < commPorts.length; ++i) {
//            System.out.println(commPorts[i].getSystemPortName());
//        }


        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        System.out.print("Stan portu : ");
        System.out.println(comPort.isOpen());
        onAutoInformation();
    }

    public void close()
    {
        off();
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        comPort.closePort();

    }

    public void on()
    {
        try {
            Thread.sleep(10);
            out.write(ON.getBytes());
            out.write( (byte)('\n'));
            Thread.sleep(10);
            out.write(ON.getBytes());
            out.write( (byte)('\n'));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void off()
    {
        try {
            out.write(OFF.getBytes());
            out.write((byte)('\n'));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAutoInformation()
    {
        try {
            out.write("AI1;".getBytes());
            out.write( (byte)('\n'));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void offAutoInformation()
    {
        try {
            out.write("AI0;".getBytes());
            out.write( (byte)('\n'));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    static SerialPort serialPort;

    SerialPort comPort;
    OutputStream out;
    InputStream in;
}
