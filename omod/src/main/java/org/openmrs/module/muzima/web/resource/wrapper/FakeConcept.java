/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 * <p/>
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * <p/>
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.muzima.web.resource.wrapper;

import org.apache.commons.beanutils.PropertyUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptNumeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FakeConcept extends Concept {

    private static final Logger log = LoggerFactory.getLogger(FakeConcept.class.getSimpleName());

    private static final String[] properties = new String[]{
            "uuid", "descriptions", "datatype", "names",
            "creator", "dateCreated", "changedBy", "dateChanged", "voidedBy", "dateVoided", "voidReason"
    };

    private String units;
    private Boolean precise;

    private FakeConcept() {
    }

    public static FakeConcept copyConcept(final Concept concept) {
        FakeConcept fakeConcept = new FakeConcept();
        for (String property : properties) {
            try {
                Object o = PropertyUtils.getProperty(concept, property);
                PropertyUtils.setProperty(fakeConcept, property, o);
            } catch (Exception e) {
                log.error("Copying property failed for property: '" + property + "' with message: " + e.getMessage(), e);
            }
        }

        if (concept.isNumeric()) {
            ConceptNumeric numeric = (ConceptNumeric) concept;
            fakeConcept.setUnits(numeric.getUnits());
            fakeConcept.setPrecise(numeric.getPrecise());
        }

        fakeConcept.setRetired(concept.isRetired());
        return fakeConcept;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Boolean getPrecise() {
        return precise;
    }

    public void setPrecise(Boolean precise) {
        this.precise = precise;
    }
}
