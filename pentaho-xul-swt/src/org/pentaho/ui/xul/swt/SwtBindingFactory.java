package org.pentaho.ui.xul.swt;

import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.BindingConvertor;
import org.pentaho.ui.xul.binding.BindingFactory;
import org.pentaho.ui.xul.binding.Binding.Type;
import org.pentaho.ui.xul.dom.Document;

public class SwtBindingFactory implements BindingFactory {

  private Document document;

  private Binding.Type type = Binding.Type.BI_DIRECTIONAL;

  public void setDocument(Document document) {
    this.document = document;
  }

  public void setBindingType(Binding.Type type) {
    this.type = type;
  }

  private Binding applyBinding(Binding b, BindingConvertor... converters) {
    b.setBindingType(type);
    if (converters != null && converters.length > 0) {
      b.setConversion(converters[0]);
    }
    b.initialize();
    document.addInitializedBinding(b);
    return b;
  }

  private void constraintsCheck() {
    if (document == null) {
      throw new IllegalArgumentException(
          "document is null.  Did you forget to set the document on the DefaultBindingFactory?");
    }
  }

  public Binding createBinding(String sourceId, String sourceAttr, String targetId, String targetAttr,
      BindingConvertor... converters) {
    constraintsCheck();
    Binding b = new SwtBinding(document.getElementById(sourceId), sourceAttr, document.getElementById(targetId),
        targetAttr);
    return applyBinding(b, converters);
  }

  public Binding createBinding(Object source, String sourceAttr, String targetId, String targetAttr,
      BindingConvertor... converters) {
    constraintsCheck();
    Binding b = new SwtBinding(source, sourceAttr, document.getElementById(targetId), targetAttr);
    return applyBinding(b, converters);
  }

  public Binding createBinding(String sourceId, String sourceAttr, Object target, String targetAttr,
      BindingConvertor... converters) {
    constraintsCheck();
    Binding b = new SwtBinding(document.getElementById(sourceId), sourceAttr, target, targetAttr);
    return applyBinding(b, converters);
  }

  public Binding createBinding(Object source, String sourceAttr, Object target, String targetAttr,
      BindingConvertor... converters) {
    Binding b = new SwtBinding(source, sourceAttr, target, targetAttr);
    return applyBinding(b, converters);
  }

}