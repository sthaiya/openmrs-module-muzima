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

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.muzima.api.db.CoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public class HibernateCoreDao implements CoreDao {

    @Autowired
    protected SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * {@inheritDoc}
     *
     * @see CoreDao#getObservations(java.util.List, java.util.List, int, int)
     */
    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Obs> getObservations(final List<String> patientUuids, final List<String> conceptUuids,
                                     final int startIndex, final int size) throws DAOException {

        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Obs.class);

        criteria.createAlias("person", "person");
        criteria.add(Restrictions.in("person.uuid", patientUuids));

        criteria.createAlias("concept", "concept");
        criteria.add(Restrictions.in("concept.uuid", conceptUuids));

        criteria.add(Restrictions.eq("voided", false));

        criteria.setMaxResults(size);
        criteria.setFirstResult(startIndex);
        return criteria.list();
    }

    /**
     * {@inheritDoc}
     *
     * @see CoreDao#countObservations(java.util.List, java.util.List)
     */
    @Override
    public Long countObservations(final List<String> patientUuids, final List<String> conceptUuids) throws DAOException {

        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Obs.class);

        criteria.createAlias("person", "person");
        criteria.add(Restrictions.in("person.uuid", patientUuids));

        criteria.createAlias("concept", "concept");
        criteria.add(Restrictions.in("concept.uuid", conceptUuids));

        criteria.add(Restrictions.eq("voided", false));

        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    /**
     * {@inheritDoc}
     *
     * @see CoreDao#getEncounters(java.util.List, int, int)
     */
    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Encounter> getEncounters(final List<String> patientUuids,
                                         final int startIndex, final int size) throws DAOException {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Encounter.class);

        criteria.createAlias("patient", "patient");
        criteria.add(Restrictions.in("patient.uuid", patientUuids));

        criteria.add(Restrictions.eq("voided", false));

        criteria.setMaxResults(size);
        criteria.setFirstResult(startIndex);
        return criteria.list();
    }

    /**
     * {@inheritDoc}
     *
     * @see CoreDao#countEncounters(java.util.List)
     */
    @Override
    public Long countEncounters(final List<String> patientUuids) throws DAOException {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Encounter.class);

        criteria.createAlias("patient", "patient");
        criteria.add(Restrictions.in("patient.uuid", patientUuids));

        criteria.add(Restrictions.eq("voided", false));

        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }
}
