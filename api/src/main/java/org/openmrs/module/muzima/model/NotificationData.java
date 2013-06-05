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
package org.openmrs.module.muzima.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Person;

/**
 * TODO: Write brief description about the class here.
 */
public class NotificationData extends BaseOpenmrsData implements Data {

    private Integer id;

    private Person forPerson;

    private Person fromPerson;

    private String subject;

    private String payload;

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
     * Get the person who will receive this notification.
     *
     * @return the person who will receive this notification.
     */
    public Person getForPerson() {
        return forPerson;
    }

    /**
     * Set the person who will receive this notification.
     *
     * @param forPerson the person who will receive this notification.
     */
    public void setForPerson(final Person forPerson) {
        this.forPerson = forPerson;
    }

    /**
     * Get the person who will send this notification.
     *
     * @return the person who will send this notification.
     */
    public Person getFromPerson() {
        return fromPerson;
    }

    /**
     * Set the person who will send this notification.
     *
     * @param fromPerson the person who will send this notification.
     */
    public void setFromPerson(final Person fromPerson) {
        this.fromPerson = fromPerson;
    }

    /**
     * Set the subject of the notification.
     *
     * @return the subject of the notification.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set the subject of the notification.
     *
     * @param subject the subject of the notification.
     */
    public void setSubject(final String subject) {
        this.subject = subject;
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
     * Set the data payload of this data.
     */
    public void setPayload(final String payload) {
        this.payload = payload;
    }
}
