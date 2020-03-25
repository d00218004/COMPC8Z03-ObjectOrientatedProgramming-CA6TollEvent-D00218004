/*******************************************************************************
 * Object-Orientated Programming CA5 [Stage 1] | Toll System | CA Value: 10%
 * Author: Matthew Waller | D00218004
 * Starter Code implemented via DataAccessLayer
 ******************************************************************************/

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