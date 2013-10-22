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

import org.apache.commons.lang.StringUtils;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.muzima.web.controller.MuzimaRestController;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.resource.impl.ServiceSearcher;
import org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8.EncounterResource1_8;
import org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8.PatientResource1_8;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
@Resource(name = RestConstants.VERSION_1 + MuzimaRestController.MUZIMA_NAMESPACE + "/encounter",
        supportedClass = Encounter.class,
        supportedOpenmrsVersions = {"1.8.*", "1.9.*"})
public class EncounterResource extends EncounterResource1_8 {

    /**
     * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource#doSearch(org.openmrs.module.webservices.rest.web.RequestContext)
     */
    @Override
    protected PageableResult doSearch(RequestContext context) {
        String patientParameter = context.getRequest().getParameter("patient");
        if (patientParameter != null) {
            List<Encounter> encounters = new ArrayList<Encounter>();
            String[] patientUuids = StringUtils.split(patientParameter, ",");
            for (String patientUuid : patientUuids) {
                Patient patient = ((PatientResource1_8) Context.getService(RestService.class).getResourceBySupportedClass(
                        Patient.class)).getByUniqueId(patientUuid);
                if (patient != null) {
                    encounters.addAll(Context.getEncounterService().getEncountersByPatient(patient));
                }
            }
            return new NeedsPaging<Encounter>(encounters, context);
        }

        return new ServiceSearcher<Encounter>(EncounterService.class, "getEncounters", "getCountOfEncounters")
                .search(context.getParameter("q"), context);
    }
}
