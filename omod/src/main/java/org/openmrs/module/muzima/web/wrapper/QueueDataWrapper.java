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
package org.openmrs.module.muzima.web.wrapper;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.module.muzima.model.DataSource;

/**
 * TODO: Write brief description about the class here.
 */
public class QueueDataWrapper extends BaseOpenmrsData {

    private String payload;

    private DataSource dataSource;

    private Class returnType;

    @Override
    public Integer getId() {
        // we don't need to implement this because we're not exposing the internal id of the queue data.
        return null;
    }

    @Override
    public void setId(final Integer integer) {
        // we don't need to implement this because we're not exposing the internal id of the queue data.
    }
}
