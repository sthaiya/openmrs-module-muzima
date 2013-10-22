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
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.muzima.common.ObsServiceUtils;
import org.openmrs.module.muzima.web.controller.MuzimaRestController;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8.ConceptResource1_8;
import org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8.ObsResource1_8;
import org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8.PersonResource1_8;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO: Write brief description about the class here.
 */
@Resource(name = RestConstants.VERSION_1 + MuzimaRestController.MUZIMA_NAMESPACE + "/obs",
        supportedClass = Obs.class,
        supportedOpenmrsVersions = {"1.8.*", "1.9.*"})
public class ObsResource extends ObsResource1_8 {

    public ObsResource() {
        allowedMissingProperties.add("valueCoded");
    }

    /**
     * Gets obs by patient or encounter (paged according to context if necessary) only if a patient
     * or encounter parameter exists respectively in the request set on the {@link org.openmrs.module.webservices.rest.web.RequestContext}
     * otherwise searches for obs that match the specified query
     *
     * @param context
     * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource#doSearch(org.openmrs.module.webservices.rest.web.RequestContext)
     */
    @Override
    protected PageableResult doSearch(final RequestContext context) {
        RestService service = Context.getService(RestService.class);

        List<Person> persons = new ArrayList<Person>();
        String personParameter = context.getRequest().getParameter("person");
        if (personParameter != null) {
            String[] personUuids = StringUtils.split(personParameter, ",");
            PersonResource1_8 personResource = (PersonResource1_8) service.getResourceBySupportedClass(Person.class);
            for (String personUuid : personUuids) {
                Person person = personResource.getByUniqueId(personUuid);
                if (person != null) {
                    persons.add(person);
                }
            }
        }

        List<Concept> concepts = new ArrayList<Concept>();
        String conceptParameter = context.getRequest().getParameter("concept");
        if (conceptParameter != null) {
            String[] conceptUuids = StringUtils.split(conceptParameter, ",");
            ConceptResource1_8 conceptResource = (ConceptResource1_8) service.getResourceBySupportedClass(Concept.class);
            for (String conceptUuid : conceptUuids) {
                Concept concept = conceptResource.getByUniqueId(conceptUuid);
                if (concept != null) {
                    concepts.add(concept);
                }
            }
        }

        List<Obs> obsList = ObsServiceUtils.get(persons, concepts);
        return new NeedsPaging<Obs>(obsList, context);
    }
}
