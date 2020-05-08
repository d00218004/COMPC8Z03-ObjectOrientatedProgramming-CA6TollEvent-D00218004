/** *****************************************************************************
 * Object-Orientated Programming CA6 | Client-Server Toll System | CA Value: 35%
 * Author: Matthew Waller | D00218004
 ***************************************************************************** */

package DAOs;

import DTOs.TollEvent;
import Exceptions.DaoException;
import java.util.List;

public interface TollEventDaoInterface
{
    public List<TollEvent> findAllTollEvents() throws DaoException;
    public void writeTollEvent(String tollBoothID, String registration, long imageId, long timestamp) throws DaoException;
    public List<TollEvent> findAllTollEventsByRegistration(String reg) throws DaoException;
    public List<TollEvent> findAllTollEventsSinceSpecifiedDateTime(long timestamp) throws DaoException;
    public List<TollEvent> findAllTollEventsBetweenSpecifiedDateTime(long timestampStart, long timestampFinish) throws DaoException;
    public List<TollEvent> finaAllTollEventsThatPassedThroughToll() throws DaoException;
    public List<TollEvent> ProcessTollEventBillingByMonth() throws DaoException;
}