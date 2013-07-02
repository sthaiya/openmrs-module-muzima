package org.openmrs.module.muzima.api.db;

import org.openmrs.module.muzima.model.DataSource;

import java.util.List;

/**
 */
public interface DataSourceDao extends SingleClassDao<DataSource> {

    /**
     * Return the data source with the given uuid.
     *
     * @param uuid the data source uuid.
     * @return the data source with the matching uuid.
     * @should return data with matching uuid.
     * @should return null when no data with matching uuid.
     */
    DataSource getDataSourceByUuid(final String uuid);

    /**
     * Return all saved data source with matching name.
     *
     * @param name           the name of the data source.
     * @param exactMatchOnly flag whether matching should be exact.
     * @param includeVoided  flag whether voided data should be returned or not.
     * @return all saved data including voided data source with matching name.
     * @should return empty list when no data are saved in the database with matching name.
     * @should return all saved data with matching name.
     */
    List<DataSource> getAllDataSources(final String name, final boolean exactMatchOnly, final boolean includeVoided);
}
