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
package org.openmrs.module.muzima.task;

import org.openmrs.api.context.Context;
import org.openmrs.module.muzima.api.service.DataService;
import org.openmrs.module.muzima.model.handler.QueueDataHandler;
import org.openmrs.module.muzima.model.QueueData;
import org.openmrs.util.HandlerUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class QueueDataProcessor {

    public void processQueueData() {
        DataService dataService = Context.getService(DataService.class);
        List<QueueData> queueDataList = dataService.getAllQueueData();

        Map<String, QueueDataHandler> handlerMap = new HashMap<String, QueueDataHandler>();
        List<QueueDataHandler> processor = HandlerUtil.getHandlersForType(QueueDataHandler.class, QueueData.class);
        for (QueueDataHandler queueDataHandler : processor) {
            handlerMap.put(queueDataHandler.getHint(), queueDataHandler);
        }

        for (QueueData queueData : queueDataList) {
            QueueDataHandler handler = handlerMap.get(queueData.getDiscriminator());
            handler.process(queueData);
        }
    }
}
