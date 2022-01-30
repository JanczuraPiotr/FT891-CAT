package sp9pj.data;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class AutoInformationCollector {

    public AutoInformationCollector() {
        inputBuffer = new StringBuilder();
        readyCommands = new LinkedList<>();
    }


    public boolean add(char add) {
        inputBuffer.append(add);
        if (add == ';') {
            return informationCompleted();
        }
        return false;
    }

    public boolean add(String add) {
        inputBuffer.append(add);
        if (add.charAt(add.length() - 1) == ';') {
            return informationCompleted();
        } else {
            return false;
        }
    }

    public String getInformation() {
        String ret = readyCommands.get(0);
        readyCommands.remove(0);
        return ret;
    }

    public int size() {
        return inputBuffer.length();
    }


    private boolean informationCompleted() {
        readyCommands.add(inputBuffer.substring(0, inputBuffer.length() - 1));
        inputBuffer.setLength(0);
        return true;

    }

    private final StringBuilder inputBuffer;
    private final List<String> readyCommands;
}
