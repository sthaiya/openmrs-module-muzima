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
import org.openmrs.Cohort;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.module.muzima.api.service.CoreService;
import org.openmrs.module.muzima.web.controller.MuzimaRestController;
import org.openmrs.module.muzima.web.resource.utils.ResourceUtils;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8.CohortResource1_8;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
@Resource(name = RestConstants.VERSION_1 + MuzimaRestController.MUZIMA_NAMESPACE + "/cohort",
        supportedClass = Cohort.class,
        supportedOpenmrsVersions = {"1.8.*", "1.9.*"})
public class CohortResource extends CohortResource1_8 {
    /**
     * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource#doSearch(org.openmrs.module.webservices.rest.web.RequestContext)
     */
    @Override
    protected NeedsPaging<Cohort> doSearch(final RequestContext context) {
        HttpServletRequest request = context.getRequest();
        String nameParameter = request.getParameter("q");
        String syncDateParameter = request.getParameter("syncDate");
        if (nameParameter != null) {
            Date syncDate = ResourceUtils.parseDate(syncDateParameter);
            CoreService coreService = Context.getService(CoreService.class);
            final int cohortCount = coreService.countCohorts(nameParameter, syncDate).intValue();
            final List<Cohort> cohorts = coreService.getCohorts(nameParameter, syncDate, context.getStartIndex(), context.getLimit());
            return new NeedsPaging<Cohort>(cohorts, context) {
                /**
                 * @see org.openmrs.module.webservices.rest.web.resource.impl.BasePageableResult#hasMoreResults()
                 */
                @Override
                public boolean hasMoreResults() {
                    return cohortCount > context.getStartIndex() + cohorts.size();
                }
            };
        }
        return super.doSearch(context);
    }
}
