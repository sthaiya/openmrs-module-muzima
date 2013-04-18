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
package org.openmrs.module.muzimaprocessor.model;

import org.openmrs.Auditable;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.User;

import java.util.Date;

/**
 */
public abstract class AuditableData extends BaseOpenmrsObject implements Data, Auditable {

    private Integer id;

    private String payload;

    private DataSource dataSource;

    /******* Audit information *******/

    private User creator;

    private Date dateCreated;

    private User changedBy;

    private Date dateChanged;

    /**
     * @return id - The unique Identifier for the object
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @param id - The unique Identifier for the object
     */
    @Override
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Get the data payload of this data.
     *
     * @return the payload of this data.
     */
    @Override
    public String getPayload() {
        return payload;
    }

    /**
     * Set the data payload for this data.
     * @param payload the payload for this data
     */
    public void setPayload(final String payload) {
        this.payload = payload;
    }

    /**
     * Get the data source of this data.
     *
     * @return the data source of this data.
     */
    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Set the data source for this data.
     *
     * @param dataSource the data source for this data.
     */
    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @return User - the user who created the object
     */
    @Override
    public User getCreator() {
        return creator;
    }

    /**
     * @param creator - the user who created the object
     */
    @Override
    public void setCreator(final User creator) {
        this.creator = creator;
    }

    /**
     * @return Date - the date the object was created
     */
    @Override
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated - the date the object was created
     */
    @Override
    public void setDateCreated(final Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return User - the user who last changed the object
     */
    @Override
    public User getChangedBy() {
        return changedBy;
    }

    /**
     * @param changedBy - the user who last changed the object
     */
    @Override
    public void setChangedBy(final User changedBy) {
        this.changedBy = changedBy;
    }

    /**
     * @return Date - the date the object was last changed
     */
    @Override
    public Date getDateChanged() {
        return dateChanged;
    }

    /**
     * @param dateChanged - the date the object was last changed
     */
    @Override
    public void setDateChanged(final Date dateChanged) {
        this.dateChanged = dateChanged;
    }
}
