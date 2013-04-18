package org.openmrs.module.muzimaprocessor.api.db;

import org.openmrs.module.muzimaprocessor.model.DataSource;

import java.util.List;

/**
 */
public interface DataSourceDao extends DataDao<DataSource> {

    /**
     * Return all saved data with matching name.
     *
     * @param name           the name of the data.
     * @param exactMatchOnly flag whether matching should be exact.
     * @param includeVoided  flag whether voided data should be returned or not.
     * @return all saved data including voided data with matching name.
     * @should return empty list when no data are saved in the database with matching name.
     * @should return all saved data with matching name.
     */
    List<DataSource> getAllDataSource(final String name, final boolean exactMatchOnly, final boolean includeVoided);
}
