/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.apm.collector.analysis.metric.provider.worker.application.mapping;

import org.apache.skywalking.apm.collector.analysis.metric.define.graph.MetricWorkerIdDefine;
import org.apache.skywalking.apm.collector.core.graph.Next;
import org.apache.skywalking.apm.collector.core.graph.NodeProcessor;
import org.apache.skywalking.apm.collector.core.util.Const;
import org.apache.skywalking.apm.collector.core.util.TimeBucketUtils;
import org.apache.skywalking.apm.collector.storage.table.application.ApplicationMapping;

/**
 * @author peng-yongsheng
 */
public class ApplicationMappingHourTransformNode implements NodeProcessor<ApplicationMapping, ApplicationMapping> {

    @Override public int id() {
        return MetricWorkerIdDefine.APPLICATION_MAPPING_HOUR_TRANSFORM_NODE_ID;
    }

    @Override
    public void process(ApplicationMapping applicationMapping, Next<ApplicationMapping> next) {
        long timeBucket = TimeBucketUtils.INSTANCE.minuteToHour(applicationMapping.getTimeBucket());

        ApplicationMapping newApplicationMapping = ApplicationMappingCopy.copy(applicationMapping);
        newApplicationMapping.setId(String.valueOf(timeBucket) + Const.ID_SPLIT + applicationMapping.getMetricId());
        newApplicationMapping.setTimeBucket(timeBucket);
        next.execute(newApplicationMapping);
    }
}
