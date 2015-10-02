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
package org.openmrs.module.muzima.web.utils;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONObject;
import org.apache.commons.lang.math.NumberUtils;
import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.muzima.model.DataSource;
import org.openmrs.module.muzima.model.ErrorData;
import org.openmrs.module.muzima.model.ErrorMessage;
import org.openmrs.module.muzima.model.QueueData;
import org.openmrs.module.muzimaforms.MuzimaForm;
import org.openmrs.module.muzimaforms.api.MuzimaFormService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Write brief description about the class here.
 */
public class WebConverter {

    public static Map<String, Object> convertDataSource(final DataSource dataSource) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (dataSource != null) {
            map.put("uuid", dataSource.getUuid());
            map.put("name", dataSource.getName());
            map.put("description", dataSource.getDescription());
            map.put("created", Context.getDateFormat().format(dataSource.getDateCreated()));
        }
        return map;
    }

    public static Map<String, Object> convertQueueData(final QueueData queueData) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (queueData != null) {
            map.put("uuid", queueData.getUuid());
            map.put("discriminator", queueData.getDiscriminator());
            map.put("source", queueData.getDataSource().getName());
            map.put("payload", queueData.getPayload());
            map.put("submitted", Context.getDateFormat().format(queueData.getDateCreated()));
        }
        return map;
    }

    public static Map<String, Object> convertErrorData(final ErrorData errorData) {
        Map<String, Object> map = new HashMap<String, Object>();
        String emptyString = "";
        if (errorData != null) {
            map.put("uuid", errorData.getUuid());
            map.put("discriminator", errorData.getDiscriminator());
            map.put("source", errorData.getDataSource().getName());
            map.put("message", errorData.getMessage());
            map.put("payload", errorData.getPayload());
            if(errorData.getLocation() == null){
                map.put("locationId", emptyString);
                map.put("locationName", emptyString);
            }else{
                map.put("locationId", errorData.getLocation().getLocationId());
                map.put("locationName", errorData.getLocation().getName());
            }
            if(errorData.getProvider() == null){
                map.put("providerId", emptyString);
                map.put("providerName", emptyString);
            }else{
                map.put("providerId", errorData.getProvider().getSystemId());
                map.put("providerName", errorData.getProvider().getDisplayString());
            }
            if(errorData.getFormName() == null){
                map.put("formName", emptyString);
            }else{
                map.put("formName", errorData.getFormName());
            }
            map.put("submitted", Context.getDateFormat().format(errorData.getDateCreated()));
            map.put("processed", Context.getDateFormat().format(errorData.getDateProcessed()));

            Map<String, Object> errorMap = new HashMap<String, Object>();
            for(ErrorMessage e : errorData.getErrorMessages()){
                errorMap.put(e.getId().toString(), e.getMessage());
            }

            map.put("Errors", JSONObject.toJSONString(errorMap));
        }
        return map;
    }

    public static Map<String, Object> convertEditRegistrationData(final ErrorData errorData) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (errorData != null) {
            map.put("uuid", errorData.getUuid());
            map.put("discriminator", errorData.getDiscriminator());
            //map.put("form-template-name",errorData.)
            map.put("source", errorData.getDataSource().getName());
            map.put("message", errorData.getMessage());
            XmlJsonUtil.createPatientValuesFromPayload(map, errorData.getPayload());
            map.put("submitted", Context.getDateFormat().format(errorData.getDateCreated()));
            map.put("processed", Context.getDateFormat().format(errorData.getDateProcessed()));


            System.out.println("data" + map.toString());

        }
        return map;
    }

    public static  Map<String,Object> convertErrorMessages(List<ErrorMessage> errorMessages) {
        Map<String, Object> outerMap = new HashMap<String, Object>();
        Map<String, Object> innerMap = new HashMap<String, Object>();
        int count = 0;
        for (ErrorMessage errorMessage : errorMessages) {
            count++;
            innerMap.put(Integer.toString(count), errorMessage.getMessage());
        }
        outerMap.put("Errors", innerMap);
        return outerMap;
    }

    private static User extractProviderFromPayload(String payload) {
        String providerString = readAsString(payload, "$['encounter']['encounter.provider_id']");
        User user = Context.getUserService().getUserByUsername(providerString);
        return user;
    }

    private static Location extractLocationFromPayload(String payload) {
        String locationString = readAsString(payload, "$['encounter']['encounter.location_id']");
        int locationId = NumberUtils.toInt(locationString, -999);
        Location location = Context.getLocationService().getLocation(locationId);
        return location;
    }

    private static String extractFormNameFromPayload(String payload) {
        String formUuid = readAsString(payload, "$['encounter']['encounter.form_uuid']");
        MuzimaFormService muzimaFormService = Context.getService(MuzimaFormService.class);
        MuzimaForm muzimaForm = muzimaFormService.findByUniqueId(formUuid);
        return muzimaForm.getName();
    }

    /**
     * Read string value from the json object.
     *
     * @param jsonObject the json object.
     * @param path       the path inside the json object.
     * @return the string value in the json object. When the path is invalid, by default will return null.
     */
    private static String readAsString(final String jsonObject, final String path) {
        String returnedString = null;
        try {
            returnedString = JsonPath.read(jsonObject, path);
        } catch (Exception e) {
            System.out.println("Unable to read string value with path: " + path + " from: " + String.valueOf(jsonObject));
        }
        return returnedString;
    }
}
