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
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.muzima.api.service.CoreService;
import org.openmrs.module.muzima.web.controller.MuzimaRestController;
import org.openmrs.module.muzima.web.resource.utils.ResourceUtils;
import org.openmrs.module.muzima.web.resource.wrapper.CohortMember;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.RepHandler;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.annotation.SubResource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingSubResource;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ConversionException;
import org.openmrs.module.webservices.rest.web.response.ObjectNotFoundException;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8.CohortMemberResource1_8;
import org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8.CohortResource1_8;
import org.openmrs.module.webservices.rest.web.v1_0.wrapper.openmrs1_8.CohortMember1_8;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
@Resource(name = RestConstants.VERSION_1 + MuzimaRestController.MUZIMA_NAMESPACE + "/member",
        supportedClass = CohortMember.class,
        supportedOpenmrsVersions = {"1.8.*", "1.9.*"})
public class CohortMemberResource extends DelegatingCrudResource<CohortMember> {

    @Override
    public PageableResult doSearch(final RequestContext context) throws ResponseException {
        HttpServletRequest request = context.getRequest();
        String uuidParameter = request.getParameter("uuid");
        String syncDateParameter = request.getParameter("syncDate");
        List<CohortMember> members = new ArrayList<CohortMember>();
        if (uuidParameter != null) {
            Date syncDate = ResourceUtils.parseDate(syncDateParameter);
            CoreService coreService = Context.getService(CoreService.class);
            final int patientCount = coreService.countPatients(uuidParameter, syncDate).intValue();
            final List<Patient> patients = coreService.getPatients(uuidParameter, syncDate,
                    context.getStartIndex(), context.getLimit());
            final Cohort cohort = Context.getCohortService().getCohortByUuid(uuidParameter);
            for (Patient cohortMember : patients) {
                members.add(new CohortMember(cohortMember, cohort));
            }
            boolean hasMore = patientCount > context.getStartIndex() + patients.size();
            return new AlreadyPaged<CohortMember>(context, members, hasMore);
        } else {
            return new NeedsPaging<CohortMember>(members, context);
        }
    }

    /**
     * Gets the delegate object with the given unique id. Implementations may decide whether
     * "unique id" means a uuid, or if they also want to retrieve delegates based on a unique
     * human-readable property.
     *
     * @param uniqueId
     * @return the delegate for the given uniqueId
     */
    @Override
    public CohortMember getByUniqueId(final String uniqueId) {
        return new CohortMember(Context.getPatientService().getPatientByUuid(uniqueId), null);
    }

    /**
     * Void or retire delegate, whichever action is appropriate for the resource type. Subclasses
     * need to override this method, which is called internally by
     * {@link #delete(Object, String, org.openmrs.module.webservices.rest.web.RequestContext)}.
     *
     * @param delegate
     * @param reason
     * @param context
     * @throws org.openmrs.module.webservices.rest.web.response.ResponseException
     */
    @Override
    protected void delete(final CohortMember delegate, final String reason, final RequestContext context) throws ResponseException {
        removeMemberFromCohort(delegate);
    }

    /**
     * Instantiates a new instance of the handled delegate
     *
     * @return
     */
    @Override
    public CohortMember newDelegate() {
        return new CohortMember();
    }

    /**
     * Writes the delegate to the database
     *
     * @param delegate
     * @return the saved instance
     */
    @Override
    public CohortMember save(final CohortMember delegate) {
        addMemberToCohort(delegate);
        return delegate;
    }

    /**
     * Purge delegate from persistent storage. Subclasses need to override this method, which is
     * called internally by {@link #purge(Object, org.openmrs.module.webservices.rest.web.RequestContext)}.
     *
     * @param delegate
     * @param context
     * @throws org.openmrs.module.webservices.rest.web.response.ResponseException
     */
    @Override
    public void purge(final CohortMember delegate, final RequestContext context) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }

    /**
     * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#getRepresentationDescription(org.openmrs.module.webservices.rest.web.representation.Representation)
     */
    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        if (rep instanceof RefRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("display", findMethod("getDisplayString"));
            description.addSelfLink();
            return description;
        } else if (rep instanceof DefaultRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("display", findMethod("getDisplayString"));
            description.addProperty("patient");
            description.addSelfLink();
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
            return description;
        } else if (rep instanceof FullRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("display", findMethod("getDisplayString"));
            description.addProperty("patient");
            description.addSelfLink();
            return description;
        }
        return null;
    }

    /**
     * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#getCreatableProperties()
     */
    @Override
    public DelegatingResourceDescription getCreatableProperties() {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addRequiredProperty("patient");
        return description;
    }

    /**
     * @param member the patient to be added to cohort
     */
    public void addMemberToCohort(CohortMember member) {
        member.getCohort().addMember(member.getPatient().getId());
        Context.getCohortService().saveCohort(member.getCohort());
    }

    /**
     * @param member the patient to be removed from cohort
     */
    public void removeMemberFromCohort(CohortMember member) {
        member.getCohort().removeMember(member.getPatient().getId());
        Context.getCohortService().saveCohort(member.getCohort());
    }

    /**
     * Implementations should override this method if T is not uniquely identified by a "uuid"
     * property.
     *
     * @param delegate
     * @return the uuid property of delegate
     */
    @Override
    protected String getUniqueId(final CohortMember delegate) {
        return delegate.getPatient().getUuid();
    }

    /**
     * @param member the patient
     * @return string that contains cohort member's identifier and full name
     */
    public String getDisplayString(CohortMember1_8 member) {

        if (member.getPatient().getPatientIdentifier() == null)
            return "";

        return member.getPatient().getPatientIdentifier().getIdentifier() + " - "
                + member.getPatient().getPersonName().getFullName();
    }

    @RepHandler(RefRepresentation.class)
    public SimpleObject asRef(CohortMember delegate) throws ConversionException {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("display", findMethod("getDisplayString"));
        description.addSelfLink();
        return convertDelegateToRepresentation(delegate, description);
    }

    @RepHandler(DefaultRepresentation.class)
    public SimpleObject asDefaultRep(CohortMember delegate) throws Exception {
        SimpleObject ret = new SimpleObject();
        ret.put("display", delegate.toString());
        ret.put("links", "[ All Data resources need to define their representations ]");
        return ret;
    }
}
