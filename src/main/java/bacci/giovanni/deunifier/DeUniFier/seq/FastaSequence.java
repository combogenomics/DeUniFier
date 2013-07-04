package bacci.giovanni.deunifier.DeUniFier.seq;

import java.util.HashMap;
import java.util.Map;

public class FastaSequence
  extends AbstractSequence
{
  private static final long serialVersionUID = 1670711603764418070L;
  private String fileName = null;
  
  public FastaSequence(String sequence, String id) {
    super(sequence, id);
  }
  
  public Map<Attributes, Object> getAttributes()
  {
    Map<Attributes, Object> attributes = new HashMap<Attributes, Object>();
    attributes.put(Attributes.FILE_NAME, this.fileName);
    return attributes;
  }
  
  public int compareTo(Sequence o) {
    return getSequence().compareTo(o.getSequence());
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}