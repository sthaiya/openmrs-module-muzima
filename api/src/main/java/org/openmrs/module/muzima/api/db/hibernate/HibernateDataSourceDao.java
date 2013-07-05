/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.muzima.api.db.hibernate;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.muzima.api.db.DataSourceDao;
import org.openmrs.module.muzima.model.DataSource;

import java.util.List;

/**
 */
public class HibernateDataSourceDao extends HibernateSingleClassDao<DataSource> implements DataSourceDao {

    public HibernateDataSourceDao() {
        super(DataSource.class);
    }

    /**
     * @return the sessionFactory
     */
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Return the data source with the given uuid.
     *
     * @param uuid the data source uuid.
     * @return the data source with the matching uuid.
     * @should return data with matching uuid.
     * @should return null when no data with matching uuid.
     */
    public DataSource getDataSourceByUuid(final String uuid) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("uuid", uuid));
        criteria.add(Restrictions.eq("retired", Boolean.FALSE));
        return (DataSource) criteria.uniqueResult();
    }

    /**
     * Return all saved data with matching name.
     *
     * @param name           the name of the data.
     * @param exactMatchOnly flag whether matching should be exact.
     * @param includeRetired  flag whether voided data should be returned or not.
     * @return all saved data including voided data with matching name.
     * @should return empty list when no data are saved in the database with matching name.
     * @should return all saved data with matching name.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<DataSource> getAllDataSources(final String name, final boolean exactMatchOnly, final boolean includeRetired) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(mappedClass);
        // only use this matching if we're getting non empty name string
        if (StringUtils.isNotEmpty(name)) {
            MatchMode matchMode = MatchMode.START;
            if (exactMatchOnly) {
                matchMode = MatchMode.EXACT;
            }
            criteria.add(Restrictions.like("name", name, matchMode));
        }
        if (!includeRetired) {
            criteria.add(Restrictions.eq("retired", false));
        }
        return criteria.list();
    }

}
