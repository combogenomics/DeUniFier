package bacci.giovanni.deunifier.DeUniFier.seq;

import java.io.Serializable;
import java.util.Map;

public abstract interface Sequence
  extends Comparable<Sequence>, Serializable
{
  public abstract String getSequence();
  
  public abstract String getId();
  
  public abstract Map<Attributes, Object> getAttributes();
}