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
package org.openmrs.module.muzima.web.resource.openmrs;

import org.openmrs.Cohort;
import org.openmrs.module.muzima.web.controller.MuzimaRestController;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8.ConceptResource1_8;

/**
 * TODO: Write brief description about the class here.
 */
@Resource(name = RestConstants.VERSION_1 + MuzimaRestController.MUZIMA_NAMESPACE + "/concept",
        supportedClass = Cohort.class,
        supportedOpenmrsVersions = {"1.8.*", "1.9.*"})
public class ConceptResource extends ConceptResource1_8 {

    public ConceptResource() {
        allowedMissingProperties.add("units");
    }
}
