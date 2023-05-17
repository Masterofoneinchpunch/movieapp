package com.mooip.util.web.tags;

import com.mooip.util.LookupSupport;
import com.mooip.util.LookupModel;
import com.mooip.util.StringUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * A select element for HTML rendering.
 * 
 * @author masterofoneinchpunch
 */
public final class SelectElement extends SimpleTagSupport {   
    private static final String NEWLINE = System.getProperty("line.separator");

    private String lookup;
    private Boolean multiple = Boolean.FALSE;
    private String name;
    private String onchange;
    private Integer size = new Integer(1);
    private Integer tabindex;
    private String value;
    
    @Override
    public void doTag() throws JspException, IOException {
        createSelect();
        getJspContext().getOut().write(createSelect());
    }

    private String createSelect() {
        StringBuilder sb = new StringBuilder();
        sb.append("<select name=\"").append(getName()).append("\" tabindex=\"");
        sb.append(StringUtil.emptyStrIfNull(getTabindex())).append("\"");
        if (multiple) {
            sb.append(" multiple ");
        }
        if (onchange != null) {
            sb.append(" onchange=\"").append(onchange).append("\"");
        }
        sb.append(" size=\"").append(size).append("\" >");
        sb.append(NEWLINE);
        createOptions(sb);
        sb.append("</select>");
        return sb.toString();
    }
    
    private void createOptions(StringBuilder sb) {
        // empty option
        sb.append("<option value = \"\"></option>"); 
        sb.append(NEWLINE);
        
        List<LookupModel> lookups = LookupSupport.getLookup(getLookup());
        for (LookupModel lm: lookups) {
            String selected = "";
            if (lm.getKey().equals(getValue())) {
                selected = " selected";
            }
            sb.append("<option").append(selected).append(" value = \"").append(lm.getKey()).append("\">").append(lm.getLookupValue()).append("</option>");
            sb.append(NEWLINE);
        }        
    }
    
    public String getLookup() {
        return lookup;
    }

    public void setLookup(String lookup) {
        this.lookup = lookup;
    }

    public Boolean getMultiple() {
        return multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnchange() {
        return onchange;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }
    
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
  
    public Integer getTabindex() {
        return tabindex;
    }

    public void setTabindex(Integer tabindex) {
        this.tabindex = tabindex;
    }
           
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }      
}