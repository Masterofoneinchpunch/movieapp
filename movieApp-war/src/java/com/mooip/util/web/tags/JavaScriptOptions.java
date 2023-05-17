package com.mooip.util.web.tags;

import com.mooip.util.LookupSupport;
import com.mooip.util.LookupModel;
import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 
 * @author masterofoneinchpunch
 */
public class JavaScriptOptions extends SimpleTagSupport {  
    private static final String NEWLINE = System.getProperty("line.separator");

    private String name;
    private String lookup;
    
    @Override
    public void doTag() throws JspException, IOException {
        getJspContext().getOut().write(createOptions());
    }

    private String createOptions() {
        StringBuilder sb = new StringBuilder();
        // empty option
        sb.append(getName()).append(".options[").append(getName()).append(".length] = new Option(\"\",\"\", false, false);");
        sb.append(NEWLINE);
        
        List<LookupModel> lookups = LookupSupport.getLookup(getLookup());
        for (LookupModel lm: lookups) {
            sb.append(getName()).append(".options[").append(getName()).append(".length]").append(" = new Option(\"").append(lm.getLookupValue()).append("\", \"").append(lm.getLookupValue()).append("\", false, false);");
            sb.append(NEWLINE);
        }   
        
        return sb.toString();
    }

    public String getLookup() {
        return lookup;
    }

    public void setLookup(String lookup) {
        this.lookup = lookup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }   
}
