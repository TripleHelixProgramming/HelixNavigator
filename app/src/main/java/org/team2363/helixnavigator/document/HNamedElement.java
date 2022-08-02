package org.team2363.helixnavigator.document;

import javafx.beans.property.StringProperty;

public interface HNamedElement {
    
    public StringProperty nameProperty();

    public void setName(String value);

    public String getName();
}