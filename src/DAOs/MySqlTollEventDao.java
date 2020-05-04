/*******************************************************************************
 * Object-Orientated Programming CA5 [Stage 1] | Toll System | CA Value: 10%
 * Author: Matthew Waller | D00218004
 ******************************************************************************/
package DAOs;

import DTOs.TollEvent;
import DAOs.MySqlDao;
import Exceptions.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlTollEventDao extends MySqlDao implements TollEventDaoInterface
{
    
    
    /* *************************************************************************
     * I tried to implement the Timestamp below for findAllEvents, it came up an
     * error with the format at the end, so i left the code commented and
     * kept the code for findAllEvents
     * *************************************************************************
    
    public List<TollEvent> findAllTollEvents() throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TollEvent> events = new ArrayList<>();

        try
        {
            //Get connection object using the methods in the Super Class
            con = this.getConnection();

            String query = "SELECT * FROM EVENTS";
            ps = con.prepareStatement(query);

            Instant inst = Instant.parse("TIMESTAMP");
            java.sql.Timestamp ts_now = java.sql.Timestamp.from(inst);

            // Prepared Statement for MySQL
            rs = ps.executeQuery();
            while (rs.next())
            {
                String registration = rs.getString("REGISTRATION");
                long imageId = rs.getLong("IMAGEID");
  >              String timestamp = rs.getString("TIMESTAMP");
  >             java.sql.Timestamp ts = rs.getTimestamp("timestamp");
  >            Instant instant = ts.toInstant();

  >           TollEvent event = new TollEvent(registration, imageId, instant.toString());

                events.add(event);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllTollEvents() " + e.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findAllTollEvents() " + e.getMessage());
            }
        }
        return events;
    }
     */
    
    
    @Override
    public List<TollEvent> findAllTollEvents() throws DaoException 
    {
    	Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TollEvent> events = new ArrayList<>();
        
        try 
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM EVENTS";
            ps = con.prepareStatement(query);
            
            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next()) 
            {
                String registration = rs.getString("REGISTRATION");
                long imageId = rs.getLong("IMAGEID");
                long timestamp = rs.getLong("TIMESTAMP");
                TollEvent e = new TollEvent(registration, imageId, timestamp);
                events.add(e);
            }
        } 
        catch (SQLException e) 
        {
            throw new DaoException("findAllTollEvents() " + e.getMessage());
        } 
        finally 
        {
            try 
            {
                if (rs != null) 
                {
                    rs.close();
                }
                if (ps != null) 
                {
                    ps.close();
                }
                if (con != null) 
                {
                    freeConnection(con);
                }
            } 
            catch (SQLException e) 
            {
                throw new DaoException("findAllTollEvents() " + e.getMessage());
            }
        }
        return events;     // may be empty
    }
    
    @Override
    public void writeTollEvent(String registration, long imageId, long timestamp) throws DaoException 
    {
    	Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try 
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "INSERT INTO EVENTS VALUES (null, ?, ?, ?)";
            ps = con.prepareStatement(query);
            
            // Do not add userID here - it will be auto-incremented automatically!
            ps.setString(1, registration);
            ps.setLong(2, imageId);
            ps.setLong(3, timestamp);
            
            // execute SQL Query
            ps.executeUpdate();
        } 
        catch (SQLException e) 
        {
            throw new DaoException("writeTollEvent() " + e.getMessage());
        } 
    }
    
    @Override
    public List<TollEvent> findAllTollEventsByRegistration(String reg) throws DaoException 
    {
    	Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TollEvent> events = new ArrayList<>();
        
        try 
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM EVENTS WHERE REGISTRATION = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, reg);
            
            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next()) 
            {
                String registration = rs.getString("REGISTRATION");
                long imageId = rs.getLong("IMAGEID");
                long timestamp = rs.getLong("TIMESTAMP");
                TollEvent e = new TollEvent(registration, imageId, timestamp);
                events.add(e);
            }
        } 
        catch (SQLException e) 
        {
            throw new DaoException("findAllTollEventsByRegistration() " + e.getMessage());
        } 
        finally 
        {
            try 
            {
                if (rs != null) 
                {
                    rs.close();
                }
                if (ps != null) 
                {
                    ps.close();
                }
                if (con != null) 
                {
                    freeConnection(con);
                }
            } 
            catch (SQLException e) 
            {
                throw new DaoException("findAllTollEventsByRegistration() " + e.getMessage());
            }
        }
        return events;     // may be empty
    }
    
    @Override
    public List<TollEvent> findAllTollEventsSinceSpecifiedDateTime(long timestamp) throws DaoException 
    {
    	Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TollEvent> events = new ArrayList<>();
        
        try 
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM EVENTS WHERE TIMESTAMP >= ?";
            ps = con.prepareStatement(query);
            ps.setLong(1, timestamp);
            
            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next()) 
            {
                String registration = rs.getString("REGISTRATION");
                long imageId = rs.getLong("IMAGEID");
                timestamp = rs.getLong("TIMESTAMP");
                TollEvent e = new TollEvent(registration, imageId, timestamp);
                events.add(e);
            }
        } 
        catch (SQLException e) 
        {
            throw new DaoException("findAllTollEventsSinceSpecifiedDateTime() " + e.getMessage());
        } 
        finally 
        {
            try 
            {
                if (rs != null) 
                {
                    rs.close();
                }
                if (ps != null) 
                {
                    ps.close();
                }
                if (con != null) 
                {
                    freeConnection(con);
                }
            } 
            catch (SQLException e) 
            {
                throw new DaoException("findAllTollEventsSinceSpecifiedDateTime() " + e.getMessage());
            }
        }
        return events;     // may be empty
    }
    
    @Override
    public List<TollEvent> findAllTollEventsBetweenSpecifiedDateTime(long timestampStart, long timestampFinish) throws DaoException 
    {
    	Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TollEvent> events = new ArrayList<>();
        
        try 
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM EVENTS WHERE TIMESTAMP >= ? && TIMESTAMP <= ?";
            ps = con.prepareStatement(query);
            ps.setLong(1, timestampStart);
            ps.setLong(2, timestampFinish);
            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next()) 
            {
                String registration = rs.getString("REGISTRATION");
                long imageId = rs.getLong("IMAGEID");
                long timestamp = rs.getLong("TIMESTAMP");
                TollEvent e = new TollEvent(registration, imageId, timestamp);
                events.add(e);
            }
        } 
        catch (SQLException e) 
        {
            throw new DaoException("findAllTollEventsSinceSpecifiedDateTime() " + e.getMessage());
        } 
        finally 
        {
            try 
            {
                if (rs != null) 
                {
                    rs.close();
                }
                if (ps != null) 
                {
                    ps.close();
                }
                if (con != null) 
                {
                    freeConnection(con);
                }
            } 
            catch (SQLException e) 
            {
                throw new DaoException("findAllTollEventsSinceSpecifiedDateTime() " + e.getMessage());
            }
        }
        return events;     // may be empty
    }
    
    @Override
    public List<TollEvent> finaAllTollEventsThatPassedThroughToll() throws DaoException 
    {
    	Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TollEvent> events = new ArrayList<>();
        
        try 
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT *  FROM (SELECT * FROM EVENTS ORDER BY REGISTRATION ASC) AS TOLL_DB GROUP BY REGISTRATION";
            ps = con.prepareStatement(query);
            
            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next()) 
            {
                String registration = rs.getString("REGISTRATION");
                long imageId = rs.getLong("IMAGEID");
                long timestamp = rs.getLong("TIMESTAMP");
                TollEvent e = new TollEvent(registration, imageId, timestamp);
                events.add(e);

            }
        } 
        catch (SQLException e) 
        {
            throw new DaoException("finaAllTollEventsThatPassedThroughToll() " + e.getMessage());
        } 
        finally 
        {
            try 
            {
                if (rs != null) 
                {
                    rs.close();
                }
                if (ps != null) 
                {
                    ps.close();
                }
                if (con != null) 
                {
                    freeConnection(con);
                }
            } 
            catch (SQLException e) 
            {
                throw new DaoException("finaAllTollEventsThatPassedThroughToll() " + e.getMessage());
            }
        }
        return events;     // may be empty
    }
}
