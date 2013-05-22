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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.muzima.api.service.DataService;
import org.openmrs.module.muzima.handler.EncounterQueueDataHandler;
import org.openmrs.module.muzima.handler.ObsQueueDataHandler;
import org.openmrs.module.muzima.model.QueueData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * The main controller.
 */
@Controller
public class MuzimaConfigurationController {

    protected final Log log = LogFactory.getLog(getClass());

    @RequestMapping(value = "/module/muzimaprocessor/manage", method = RequestMethod.GET)
    public void manage(ModelMap model) {
        DataService dataService = Context.getService(DataService.class);

        for (int i = 0; i < 10; i++) {
            QueueData queueData = new QueueData();
            queueData.setDiscriminator(ObsQueueDataHandler.DISCRIMINATOR_VALUE);
            dataService.saveQueueData(queueData);
        }

        for (int i = 0; i < 10; i++) {
            QueueData queueData = new QueueData();
            queueData.setDiscriminator(EncounterQueueDataHandler.DISCRIMINATOR_VALUE);
            dataService.saveQueueData(queueData);
        }

        List<QueueData> queueDatas = dataService.getAllQueueData();
        for (QueueData queueData : queueDatas) {
            System.out.println("Form data");
            System.out.println("Uuid: " + queueData.getUuid());
            System.out.println("Type: " + queueData.getDiscriminator());
        }
    }
}
