package sp9pj.data;


import java.util.LinkedList;
import java.util.List;

public class AutoInformationCollector {

    public AutoInformationCollector() {
        inputBuffer = new StringBuilder();
        readyCommands = new LinkedList<>();
    }

    public boolean add(char add) {
        if (add == ';') {
            readyCommands.add(inputBuffer.toString());
            inputBuffer.setLength(0);
            return true;
        }
        inputBuffer.append(add);
        return false;
    }

    public boolean add(byte[] bytes, int bytesAvailable) {
        return add(new String(bytes , 0, bytesAvailable));
    }

    public boolean add(String add) {
        inputBuffer.append(add);

        int beginCommandPosition = 0;
        int endCommandPosition = inputBuffer.indexOf(";");

        while (endCommandPosition != -1) {
            String ready = inputBuffer.substring(beginCommandPosition, endCommandPosition);
            readyCommands.add(ready);
            beginCommandPosition = endCommandPosition + 1;
            endCommandPosition = inputBuffer.indexOf(";", beginCommandPosition);
        }

        if (beginCommandPosition != 0) {
            inputBuffer.delete(0, beginCommandPosition);
            return true;
        }
        return false;
    }

    public String getInformation() {
        if (readyCommands.isEmpty()) {
            return "";
        }
        String ret = readyCommands.get(0);
        readyCommands.remove(0);
        return ret;
    }

    public int size() {
        return inputBuffer.length();
    }


    private StringBuilder inputBuffer;
    private final List<String> readyCommands;
}
