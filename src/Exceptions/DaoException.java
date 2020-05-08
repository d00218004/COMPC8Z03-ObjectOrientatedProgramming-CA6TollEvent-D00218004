/** *****************************************************************************
 * Object-Orientated Programming CA6 | Client-Server Toll System | CA Value: 35%
 * Author: Matthew Waller | D00218004
 ***************************************************************************** */

package Exceptions;

import java.sql.SQLException;

public class DaoException extends SQLException 
{
    public DaoException() {
    }

    public DaoException(String aMessage) 
    {
        super(aMessage);
    }
}