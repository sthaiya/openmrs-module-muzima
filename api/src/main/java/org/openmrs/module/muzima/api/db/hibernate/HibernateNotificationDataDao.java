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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Person;
import org.openmrs.module.muzima.api.db.ErrorDataDao;
import org.openmrs.module.muzima.api.db.NotificationDataDao;
import org.openmrs.module.muzima.model.DataSource;
import org.openmrs.module.muzima.model.ErrorData;
import org.openmrs.module.muzima.model.NotificationData;

import java.util.List;

/**
 */
public class HibernateNotificationDataDao extends HibernateDataDao<NotificationData> implements NotificationDataDao {

    private final Log log = LogFactory.getLog(HibernateNotificationDataDao.class);

    /**
     * Default constructor.
     */
    protected HibernateNotificationDataDao() {
        super(NotificationData.class);
    }

    /**
     * Get all notification for this particular person.
     *
     * @param person the person for whom the notification designated to.
     * @return the list of all notification for that particular person.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<NotificationData> getNotificationFor(final Person person) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("forPerson", person));
        criteria.add(Restrictions.eq("voided", Boolean.FALSE));
        return criteria.list();
    }

    /**
     * Get all notification from this particular person.
     *
     * @param person the person from where the notification originated from.
     * @return the list of all notification from that particular person.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<NotificationData> getNotificationFrom(final Person person) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(mappedClass);
        criteria.add(Restrictions.eq("fromPerson", person));
        criteria.add(Restrictions.eq("voided", Boolean.FALSE));
        return criteria.list();
    }
}
