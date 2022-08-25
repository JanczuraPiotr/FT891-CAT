package sp9pj.engine;

import com.fazecast.jSerialComm.SerialPort;
import com.google.common.eventbus.EventBus;
import sp9pj.data.AutoInformationCollector;
import sp9pj.event.RadioStateChangeEvent;
import sp9pj.gui.ControlPanel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;


public class Transport implements Runnable {

    static final String OFF = "PS0;";
    static final String ON = "PS1;";
    static final String StartAutoInformation = "AI1;";
    static final String StopAutoInformation = "AI0;";

    public Transport(EventBus eventBus, int port, ControlPanel controlPanel) {
        this.eventBus = eventBus;
        SerialPort[] commPorts = SerialPort.getCommPorts();
        autoInformationCollector = new AutoInformationCollector();
        for (int i = 0; i < commPorts.length; ++i) {
            System.out.println(commPorts[i].getSystemPortName());
        }

        comPort = SerialPort.getCommPorts()[port];
        out = comPort.getOutputStream();
        in = comPort.getInputStream();
        comPort.setBaudRate(38400);
        this.controlPanel =  controlPanel;
        thread = new Thread(this);
        tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS | ");
    }

    public void run()
    {
        System.out.println(tf.format(new Date()) + "Transport::run()");
        running.set(true);
        stopped.set(false);

        try {
            System.out.println(tf.format(new Date()) + "Transport::run() :: try");
            while (running.get()) {
                System.out.println(tf.format(new Date()) + "Transport::run() :: try :: while(running.get)");
                while (comPort.bytesAvailable() == 0) {
                    Thread.sleep(100);
                    Thread.yield();
                }

                lastReading = new byte[comPort.bytesAvailable()];
                comPort.readBytes(lastReading, lastReading.length);

                if (autoInformationCollector.add(lastReading, lastReading.length) ) {
                    while (autoInformationCollector.hasInformation()) {
                        eventBus.post(new RadioStateChangeEvent(autoInformationCollector.getInformation()));
                    }
                }


            }
        } catch (Exception e) {
            System.out.println(tf.format(new Date()) + "Transport::startListening() >> catch (Exception e)");
            e.printStackTrace();
        }

        stopped.set(true);
    }

    public void startListening()
    {
        System.out.println(tf.format(new Date()) + "Transport::startListening()");
        thread.start();
    }

    public byte[] getLastReading()
    {
        return lastReading;
    }

    public void connect()
    {
        System.out.println(tf.format(new Date()) + "Transport::connect()");

        try {
            comPort.openPort();
            comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
            System.out.print("comPort.isOpen() : " + comPort.isOpen());
            Thread.sleep(100);
            on();
            Thread.sleep(100);
            onAutoInformation();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close()
    {
        System.out.println(tf.format(new Date()) + "Transport::close()");
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
        System.out.println(tf.format(new Date()) + "Transport::on()");
//        try {
//            Thread.sleep(10);
//            out.write(ON.getBytes());
//            out.write( (byte)('\n'));
//            Thread.sleep(10);
//            out.write(ON.getBytes());
//            out.write( (byte)('\n'));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    public void off()
    {
        System.out.println(tf.format(new Date()) + "Transport::off()");
        running.set(false);
//        try {
//            out.write(OFF.getBytes());
//            out.write((byte)('\n'));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void onAutoInformation()
    {
        System.out.println(tf.format(new Date()) + "Transport::onAutoInformation()");
        try {
            out.write(StartAutoInformation.getBytes());
            out.write( (byte)('\n'));
            System.out.println(tf.format(new Date()) + "Transport::onAutoInformation() try write");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void offAutoInformation()
    {
        System.out.println(tf.format(new Date()) + "Transport::offAutoInformation()");
        try {
            out.write(StopAutoInformation.getBytes());
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
    ControlPanel controlPanel; // @task  Usunąć zależność od ControlPanel
    private final SimpleDateFormat tf;
    private Thread thread;
    private AtomicBoolean running = new AtomicBoolean(false);
    private AtomicBoolean stopped = new AtomicBoolean(true);
    private AutoInformationCollector autoInformationCollector;
    private final EventBus eventBus;
}
