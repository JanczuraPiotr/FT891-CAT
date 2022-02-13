package sp9pj.data;

import org.junit.Assert;
import org.junit.Test;

public class AutoInformationCollectorTest {

    @Test
    public void addCharSingleInput() {
        AutoInformationCollector aic = new AutoInformationCollector();
        Assert.assertFalse(aic.add('A'));
        Assert.assertFalse(aic.add('I'));
        Assert.assertFalse(aic.add('0'));
        Assert.assertTrue(aic.add(';'));
        Assert.assertEquals("AI0", aic.getInformation());
    }

    @Test
    public void addCharDoubleInputOneReading() {
        AutoInformationCollector aic = new AutoInformationCollector();
        Assert.assertFalse(aic.add('A'));
        Assert.assertFalse(aic.add('I'));
        Assert.assertFalse(aic.add('0'));
        Assert.assertTrue(aic.add(';'));
        Assert.assertFalse(aic.add('A'));
        Assert.assertFalse(aic.add('I'));
        Assert.assertFalse(aic.add('1'));
        Assert.assertTrue(aic.add(';'));
        Assert.assertEquals("AI0", aic.getInformation());
        Assert.assertEquals("AI1", aic.getInformation());
    }

    @Test
    public void addCharDoubleInputDoubleReading() {
        AutoInformationCollector aic = new AutoInformationCollector();
        Assert.assertFalse(aic.add('A'));
        Assert.assertFalse(aic.add('I'));
        Assert.assertFalse(aic.add('0'));
        Assert.assertTrue(aic.add(';'));
        Assert.assertEquals("AI0", aic.getInformation());
        Assert.assertFalse(aic.add('A'));
        Assert.assertFalse(aic.add('I'));
        Assert.assertFalse(aic.add('1'));
        Assert.assertTrue(aic.add(';'));
        Assert.assertEquals("AI1", aic.getInformation());
    }

    @Test
    public void addCharDoubleInputOneReadingwWithIncompleteData() {
        AutoInformationCollector aic = new AutoInformationCollector();
        Assert.assertFalse(aic.add('A'));
        Assert.assertFalse(aic.add('I'));
        Assert.assertFalse(aic.add('0'));
        Assert.assertTrue(aic.add(';'));
        Assert.assertFalse(aic.add('A'));
        Assert.assertFalse(aic.add('I'));
        Assert.assertFalse(aic.add('1'));
        Assert.assertTrue(aic.add(';'));
        Assert.assertFalse(aic.add('!'));
        Assert.assertFalse(aic.add('?'));
        Assert.assertEquals("AI0", aic.getInformation());
        Assert.assertEquals("AI1", aic.getInformation());
        Assert.assertEquals("", aic.getInformation());
    }

    @Test
    public void addCharManyInputManyReading() {
        AutoInformationCollector aic = new AutoInformationCollector();
        Assert.assertFalse(aic.add('A'));
        Assert.assertFalse(aic.add('I'));
        Assert.assertEquals("", aic.getInformation());
        Assert.assertFalse(aic.add('0'));
        Assert.assertTrue(aic.add(';'));
        Assert.assertEquals("AI0", aic.getInformation());
        Assert.assertFalse(aic.add('A'));
        Assert.assertFalse(aic.add('I'));
        Assert.assertFalse(aic.add('0'));
        Assert.assertTrue(aic.add(';'));
        Assert.assertFalse(aic.add('A'));
        Assert.assertEquals("AI0", aic.getInformation());
        Assert.assertFalse(aic.add('I'));
        Assert.assertFalse(aic.add('1'));
        Assert.assertTrue(aic.add(';'));
        Assert.assertEquals("AI1", aic.getInformation());
        Assert.assertEquals("", aic.getInformation());
    }

    @Test
    public void addCharAsStringOneReading() {
        AutoInformationCollector aic = new AutoInformationCollector();
        Assert.assertTrue(aic.add("AI0;"));
        Assert.assertTrue(aic.add("AI1;"));
        Assert.assertEquals("AI0", aic.getInformation());
        Assert.assertEquals("AI1", aic.getInformation());
    }

    @Test
    public void addCharAsStringOneReadingwWithIncompleteData() {
        AutoInformationCollector aic = new AutoInformationCollector();
        Assert.assertTrue(aic.add("AI0;"));
        Assert.assertTrue(aic.add("AI1;"));
        Assert.assertFalse(aic.add("!?"));
        Assert.assertEquals("AI0", aic.getInformation());
        Assert.assertEquals("AI1", aic.getInformation());
        Assert.assertEquals("", aic.getInformation());
    }

    @Test
    public void addStringSingleInput() {
        AutoInformationCollector aic = new AutoInformationCollector();
        Assert.assertTrue(aic.add("AI0;"));
        Assert.assertEquals("AI0", aic.getInformation());
    }

    @Test
    public void addManyCommandsInOneReading() {
        AutoInformationCollector aic = new AutoInformationCollector();
        Assert.assertTrue(aic.add("AI0;AI1"));
        Assert.assertTrue(aic.add(";AI2;AI3"));
        Assert.assertTrue(aic.add(';'));
        Assert.assertEquals("AI0", aic.getInformation());
        Assert.assertEquals("AI1", aic.getInformation());
        Assert.assertEquals("AI2", aic.getInformation());
        Assert.assertEquals("AI3", aic.getInformation());

        Assert.assertTrue(aic.add("AI0;AI1;AI2;AI3;"));
        Assert.assertEquals("AI0", aic.getInformation());
        Assert.assertEquals("AI1", aic.getInformation());
        Assert.assertEquals("AI2", aic.getInformation());
        Assert.assertEquals("AI3", aic.getInformation());

        Assert.assertTrue(aic.add("AI0;AI1;AI2"));
        Assert.assertEquals("AI0", aic.getInformation());
        Assert.assertEquals("AI1", aic.getInformation());
        Assert.assertTrue(aic.add(';'));
        Assert.assertEquals("AI2", aic.getInformation());

        Assert.assertFalse(aic.add("AI"));
        Assert.assertEquals("", aic.getInformation());
        Assert.assertFalse(aic.add('1'));
        Assert.assertEquals("", aic.getInformation());
        Assert.assertTrue(aic.add(';'));
        Assert.assertEquals("AI1", aic.getInformation());
    }

    @Test
    public void addBytes() {
        AutoInformationCollector aic = new AutoInformationCollector();
        byte[] buffer = new byte[100];
        buffer[0] = (byte)'A';
        buffer[1] = (byte)'I';
        buffer[2] = (byte)'1';
        buffer[3] = (byte)';';

        Assert.assertTrue(aic.add(buffer, 4));
        Assert.assertEquals("AI1", aic.getInformation());
    }

}