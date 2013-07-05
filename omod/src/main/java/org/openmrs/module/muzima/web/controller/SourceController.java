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
package org.openmrs.module.muzima.web.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.muzima.api.service.DataService;
import org.openmrs.module.muzima.model.DataSource;
import org.openmrs.module.muzima.model.ErrorData;
import org.openmrs.module.muzima.web.utils.WebConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * TODO: Write brief description about the class here.
 */
@Controller
@RequestMapping(value = "/module/muzima/source.json")
public class SourceController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSource(final @RequestParam(value = "uuid") String uuid) {
        DataService dataService = Context.getService(DataService.class);
        DataSource dataSource = dataService.getDataSourceByUuid(uuid);
        return WebConverter.convertDataSource(dataSource);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void deleteSource(final @RequestBody Map<String, Object> map) {
        Object uuidObject = map.get("uuid");
        System.out.println("Uuid Object: " + uuidObject);
        Object nameObject = map.get("name");
        System.out.println("Name Object: " + nameObject);
        Object descriptionObject = map.get("description");
        System.out.println("Description Object: " + descriptionObject);
    }
}
