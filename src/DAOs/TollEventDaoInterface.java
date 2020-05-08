/*******************************************************************************
 * Object-Orientated Programming CA5 [Stage 1] | Toll System | CA Value: 10%
 * Author: Matthew Waller | D00218004
 ******************************************************************************/

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
}