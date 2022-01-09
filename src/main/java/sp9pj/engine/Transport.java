package sp9pj.engine;

import com.fazecast.jSerialComm.SerialPort;
import sp9pj.gui.ControlPanel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Transport {

    static final String OFF = "PS0;";
    static final String ON = "PS1;";
    static final String AutoInformation = "AI1;";

    public Transport(int port, ControlPanel controlPanel) {
        comPort = SerialPort.getCommPorts()[port];
        out = comPort.getOutputStream();
        in = comPort.getInputStream();
        this.controlPanel =  controlPanel;
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

                lastReading = new byte[comPort.bytesAvailable()];
                comPort.readBytes(lastReading, lastReading.length);
                NewTransportEvent.fireEvent(controlPanel, new NewTransportEvent(lastReading));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public byte[] getLastReading()
    {
        return lastReading;
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
    byte[] lastReading;
    ControlPanel controlPanel;
}
