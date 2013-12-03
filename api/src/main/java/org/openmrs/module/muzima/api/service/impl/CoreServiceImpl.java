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
package org.openmrs.module.muzima.api.service.impl;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.muzima.api.db.CoreDao;
import org.openmrs.module.muzima.api.service.CoreService;

import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public class CoreServiceImpl extends BaseOpenmrsService implements CoreService {

    private CoreDao coreDao;

    public CoreDao getCoreDao() {
        return coreDao;
    }

    public void setCoreDao(final CoreDao coreDao) {
        this.coreDao = coreDao;
    }

    @Override
    public List<Obs> getObservations(final List<String> patientUuids, final List<String> conceptUuids,
                                     final int startIndex, final int size) throws APIException {
        return getCoreDao().getObservations(patientUuids, conceptUuids, startIndex, size);
    }

    @Override
    public Number countObservations(final List<String> patientUuids, final List<String> conceptUuids) throws APIException {
        return getCoreDao().countObservations(patientUuids, conceptUuids);
    }

    @Override
    public List<Encounter> getEncounters(final List<String> patientUuids, final int startIndex, final int size)
            throws APIException {
        return getCoreDao().getEncounters(patientUuids, startIndex, size);
    }

    @Override
    public Number countEncounters(final List<String> patientUuids) throws APIException {
        return getCoreDao().countEncounters(patientUuids);
    }
}
