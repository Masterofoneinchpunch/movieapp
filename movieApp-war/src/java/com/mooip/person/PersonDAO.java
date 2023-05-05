package com.mooip.person;

import com.mooip.util.SQLSupport;
import com.mooip.util.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public final class PersonDAO {
 
    private static final Logger log = Logger.getLogger(PersonDAO.class);
    private static final String PERSON = "select PERSONID, PERSON, BIRTHDATE, FRONTPICEMBED, LASTMODIFIED FROM PERSON WHERE PERSON = ?";
    
    private static final String PERSON_BIRTHDAYS =
        "select PERSON.*, " +
        "CASE " + 
        "  when TO_DATE(TO_CHAR(BIRTHDATE,'MM/DD/') || EXTRACT (YEAR FROM SYSDATE), 'MM/DD/YYYY') < TRUNC(SYSDATE) " +
        "    then ADD_MONTHS(TO_DATE(TO_CHAR(BIRTHDATE,'MM/DD/') || EXTRACT (YEAR FROM SYSDATE), 'MM/DD/YYYY'), 12) " +
        "  else " +
        "    TO_DATE(TO_CHAR(BIRTHDATE,'MM/DD/') || EXTRACT (YEAR FROM SYSDATE), 'MM/DD/YYYY') " +  
        "END AS FUTURE_BIRTHDAY " +
        "FROM PERSON " +
        "ORDER BY FUTURE_BIRTHDAY ";
    
    private PersonDAO () {        
    }
    
    public static PersonModel getPerson(String person) {
        PersonModel personModel = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet result = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(PERSON);
            SQLSupport.setString(person, 1, stmt);
            result = stmt.executeQuery();
            if (result.next()) {
                personModel = new PersonModel();
                personModel.setBirthDate(SQLSupport.getDateFromSqlDate("BIRTHDATE", result));
                personModel.setFrontPicEmbed(SQLSupport.getString("FRONTPICEMBED", result));
                personModel.setPersonId(SQLSupport.getLong("PERSONID", result));
                personModel.setPerson(SQLSupport.getString("PERSON", result));
                personModel.setLastModified(SQLSupport.getDateFromSqlDate("Lastmodified", result));
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getPerson method." + se); 
        } finally {
            SQLUtil.close(result);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }

        return personModel;
    }
    
    public static List<PersonModel> getFutureBirthdays() {
        List<PersonModel> persons = new ArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet result = null;
        try {
            conn = SQLUtil.getDBConnection();
            stmt = conn.prepareStatement(PERSON_BIRTHDAYS);
            result = stmt.executeQuery();
            while (result.next()) {
                PersonModel personModel = new PersonModel();
                personModel.setBirthDate(SQLSupport.getDateFromSqlDate("BIRTHDATE", result));
                personModel.setFrontPicEmbed(SQLSupport.getString("FRONTPICEMBED", result));
                personModel.setPersonId(SQLSupport.getLong("PERSONID", result));
                personModel.setPerson(SQLSupport.getString("PERSON", result));
                personModel.setLastModified(SQLSupport.getDateFromSqlDate("Lastmodified", result));
                personModel.setFutureBirthday(SQLSupport.getDateFromSqlDate("FUTURE_BIRTHDAY", result));
                persons.add(personModel);
            }
        } catch (SQLException se) {
            log.error(se);
            throw new RuntimeException("SQLException in getFutureBirthdays method." + se); 
        } finally {
            SQLUtil.close(result);
            SQLUtil.close(stmt);
            SQLUtil.close(conn);
        }
        
        return persons;
    }
    
}    
