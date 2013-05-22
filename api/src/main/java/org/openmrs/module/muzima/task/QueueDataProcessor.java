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
import org.openmrs.module.muzima.model.QueueData;
import org.openmrs.module.muzima.model.handler.QueueDataHandler;
import org.openmrs.util.HandlerUtil;

import java.util.List;

/**
 */
public class QueueDataProcessor {

    public void processQueueData() {
        DataService dataService = Context.getService(DataService.class);
        List<QueueData> queueDataList = dataService.getAllQueueData();
        List<QueueDataHandler> processors = HandlerUtil.getHandlersForType(QueueDataHandler.class, QueueData.class);
        // TODO: this is a proof of concept. The handler should handle the queue data one by one.
        for (QueueData queueData : queueDataList) {
            for (QueueDataHandler processor : processors) {
                if (processor.accept(queueData)) {
                    processor.process(queueData);
                }
            }
        }
    }
}
