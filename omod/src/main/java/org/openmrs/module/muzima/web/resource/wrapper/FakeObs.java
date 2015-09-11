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
package org.openmrs.module.muzima.web.resource.wrapper;

import org.apache.commons.beanutils.PropertyUtils;
import org.openmrs.Obs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FakeObs extends Obs {

    private static final Logger log = LoggerFactory.getLogger(FakeCohort.class.getSimpleName());

    private static final String[] properties = new String[]{
            "uuid", "obsDatetime", "valueText", "valueNumeric", "valueDatetime", "valueCoded", "encounter", "person", "concept",
            "creator", "dateCreated", "changedBy", "dateChanged", "voidedBy", "dateVoided", "voidReason"
    };

    private FakeObs() {
    }

    public static FakeObs copyObs(final Obs obs) {
        FakeObs fakeObs = new FakeObs();
        for (String property : properties) {
            try {
                Object o = PropertyUtils.getProperty(obs, property);
                PropertyUtils.setProperty(fakeObs, property, o);
            } catch (Exception e) {
                log.error("Copying property failed for property: '" + property + "' with message: " + e.getMessage(), e);
            }
        }
        fakeObs.setVoided(obs.getVoided());
        return fakeObs;
    }
}
