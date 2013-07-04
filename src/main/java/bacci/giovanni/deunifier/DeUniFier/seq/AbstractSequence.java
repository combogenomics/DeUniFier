package bacci.giovanni.deunifier.DeUniFier.seq;

import java.util.Map;

public abstract class AbstractSequence implements Sequence
{
  private static final long serialVersionUID = -8036608080162365045L;
  private String sequence = null;
  private String id = null;
  
  public AbstractSequence(String sequence, String id) {
    this.sequence = sequence;
    this.id = id;
  }
  
  public String getSequence() {
    return this.sequence;
  }
  
  public String getId() {
    return this.id;
  }
  
  public void print() {
    System.out.println(this.id);
    System.out.println(this.sequence);
  }
  
  public abstract Map<Attributes, Object> getAttributes();
  
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append(this.id + System.lineSeparator());
    buffer.append(this.sequence + System.lineSeparator());
    return buffer.toString();
  }
}