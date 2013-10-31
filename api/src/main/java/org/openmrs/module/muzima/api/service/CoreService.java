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
package org.openmrs.module.muzima.api.service;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;

import java.util.List;

/**
 * TODO: Write brief description about the class here.
 */
public interface CoreService extends OpenmrsService {

    List<Obs> getObservations(final List<String> patientUuids, final List<String> conceptUuids,
                              final int startIndex, final int size) throws APIException;

    Long countObservations(final List<String> patientUuids, final List<String> conceptUuids) throws APIException;

    List<Encounter> getEncounters(final List<String> patientUuids,
                                  final int startIndex, final int size) throws APIException;

    Long countEncounters(final List<String> patientUuids) throws APIException;
}
