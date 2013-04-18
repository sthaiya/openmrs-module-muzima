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
package org.openmrs.module.muzimaprocessor.api.db.hibernate;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.muzimaprocessor.api.db.DataSourceDao;
import org.openmrs.module.muzimaprocessor.model.DataSource;
import org.openmrs.ui.framework.db.hibernate.SingleClassHibernateDAO;

import java.util.List;

/**
 */
public class HibernateDataSourceDao extends HibernateDataDao<DataSource> implements DataSourceDao {

    public HibernateDataSourceDao() {
        super(DataSource.class);
    }

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
    @Override
    @SuppressWarnings("unchecked")
    public List<DataSource> getAllDataSource(final String name, final boolean exactMatchOnly, final boolean includeVoided) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(mappedClass);
        // only use this matching if we're getting non empty name string
        if (StringUtils.isNotEmpty(name)) {
            MatchMode matchMode = MatchMode.START;
            if (exactMatchOnly)
                matchMode = MatchMode.EXACT;
            criteria.add(Restrictions.like("name", name, matchMode));
        }
        if (!includeVoided)
            criteria.add(Restrictions.eq("voided", false));
        return criteria.list();
    }

}
