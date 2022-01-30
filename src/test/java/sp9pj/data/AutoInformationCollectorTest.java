package sp9pj.data;

import org.junit.Assert;
import org.junit.Test;

public class AutoInformationCollectorTest {

    @Test
    public void addChar() {
        AutoInformationCollector aic = new AutoInformationCollector();
        Assert.assertFalse(aic.add('A'));
        Assert.assertFalse(aic.add('I'));
        Assert.assertFalse(aic.add('0'));
        Assert.assertTrue(aic.add(';'));
        Assert.assertEquals("AI0", aic.getInformation());
    }

    @Test
    public void addCharAsString() {
        AutoInformationCollector aic = new AutoInformationCollector();
        Assert.assertFalse(aic.add("A"));
        Assert.assertFalse(aic.add("I"));
        Assert.assertFalse(aic.add("1"));
        Assert.assertTrue(aic.add(";"));
        Assert.assertEquals("AI1", aic.getInformation());
    }
    @Test
    public void addString() {
        AutoInformationCollector aic = new AutoInformationCollector();
        Assert.assertTrue(aic.add("AI1;"));
        Assert.assertEquals("AI1", aic.getInformation());
    }

}