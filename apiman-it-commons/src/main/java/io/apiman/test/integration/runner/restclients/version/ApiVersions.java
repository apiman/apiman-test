/*
 * Copyright 2016 Red Hat Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apiman.test.integration.runner.restclients.version;

import io.apiman.test.integration.runner.restclients.AbstractEntityRestClient;
import io.apiman.test.integration.runner.restclients.VersionRestClient;
import io.apiman.test.integration.runner.restclients.entity.Policies;
import io.apiman.manager.api.beans.actions.ActionBean;
import io.apiman.manager.api.beans.actions.ActionType;
import io.apiman.manager.api.beans.apis.ApiBean;
import io.apiman.manager.api.beans.apis.ApiVersionBean;
import io.apiman.manager.api.beans.apis.NewApiVersionBean;
import io.apiman.manager.api.beans.apis.UpdateApiVersionBean;
import io.apiman.manager.api.beans.metrics.HistogramIntervalType;
import io.apiman.manager.api.beans.metrics.ResponseStatsHistogramBean;
import io.apiman.manager.api.beans.metrics.ResponseStatsPerClientBean;
import io.apiman.manager.api.beans.metrics.ResponseStatsPerPlanBean;
import io.apiman.manager.api.beans.metrics.ResponseStatsSummaryBean;
import io.apiman.manager.api.beans.metrics.UsageHistogramBean;
import io.apiman.manager.api.beans.metrics.UsagePerClientBean;
import io.apiman.manager.api.beans.metrics.UsagePerPlanBean;
import io.apiman.manager.api.beans.summary.ApiVersionEndpointSummaryBean;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author jcechace
 */
public class ApiVersions extends AbstractEntityRestClient<ApiVersionBean, NewApiVersionBean>
    implements VersionRestClient<ApiVersions> {

    public static final String RESOURCE_PATH = "/organizations/{0}/apis/{1}/versions";

    public static final String METRICS = "/metrics";
    public static final String METRICS_USAGE = METRICS + "/usage";
    public static final String METRICS_CLIENT_USAGE = METRICS + "/clientUsage";
    public static final String METRICS_PLAN_USAGE = METRICS + "/planUsage";
    public static final String METRICS_RESPONSE_STATS = METRICS + "/responseStats";
    public static final String METRICS_CLIENT_RESPONSE_STATS = METRICS + "/clientResponseStats";
    public static final String METRICS_PLAN_RESPONSE_STATS = METRICS + "/planResponseStats";
    public static final String METRICS_SUMMARY_RESPONSE_STATS = METRICS + "/summaryResponseStats";

    private final ApiBean api;

    public ApiVersions(ApiBean api) {
        super(path(RESOURCE_PATH, api.getOrganization().getId(), api.getId()), ApiVersionBean.class);
        this.api = api;
    }

    @Override
    public Policies<ApiVersions> policies() {
        return new Policies<>(this, getResourcePath(getBean().getVersion()));
    }

    @Override
    public ApiVersions publish() {
        ActionBean action = new ActionBean();
        action.setType(ActionType.publishAPI);
        action.setOrganizationId(getBean().getApi().getOrganization().getId());
        action.setEntityId(getBean().getApi().getId());
        action.setEntityVersion(getBean().getVersion());

        post(path(ACTION_PATH), action);
        return this;
    }

    public ApiVersions republish() {
        return publish();
    }

    public String getManagedEndpoint() {
        ApiVersionEndpointSummaryBean endpointSummary =
            get(getResourcePath(getBean().getVersion()) + "/endpoint", ApiVersionEndpointSummaryBean.class);

        return endpointSummary.getManagedEndpoint();
    }

    public ApiVersions update(UpdateApiVersionBean updateBean) {
        setBean(put(getResourcePath(getBean().getVersion()), updateBean, ApiVersionBean.class));
        return this;
    }

    public UsageHistogramBean metricsUsage(LocalDateTime from, LocalDateTime to, HistogramIntervalType interval) {
        return getMetrics(from, to, getBean().getVersion(), METRICS_USAGE, UsageHistogramBean.class, interval);
    }

    public UsagePerClientBean metricsClientUsage(LocalDateTime from, LocalDateTime to) {
        return getMetrics(from, to, getBean().getVersion(), METRICS_CLIENT_USAGE, UsagePerClientBean.class);
    }

    public UsagePerPlanBean metricsPlanUsage(LocalDateTime from, LocalDateTime to) {
        return getMetrics(from, to, getBean().getVersion(), METRICS_PLAN_USAGE, UsagePerPlanBean.class);
    }

    public ResponseStatsHistogramBean metricsResponseStats(LocalDateTime from, LocalDateTime to, HistogramIntervalType interval) {
        return getMetrics(from, to, getBean().getVersion(), METRICS_RESPONSE_STATS, ResponseStatsHistogramBean.class,
            interval);
    }

    public ResponseStatsPerClientBean metricsClientResponseStats(LocalDateTime from, LocalDateTime to) {
        return getMetrics(from, to, getBean().getVersion(), METRICS_CLIENT_RESPONSE_STATS,
            ResponseStatsPerClientBean.class);
    }

    public ResponseStatsPerPlanBean metricsPlanResponseStats(LocalDateTime from, LocalDateTime to) {
        return getMetrics(from, to, getBean().getVersion(), METRICS_PLAN_RESPONSE_STATS,
            ResponseStatsPerPlanBean.class);
    }

    public ResponseStatsSummaryBean metricsSummaryResponseStats(LocalDateTime from, LocalDateTime to) {
        return getMetrics(from, to, getBean().getVersion(), METRICS_SUMMARY_RESPONSE_STATS,
            ResponseStatsSummaryBean.class);
    }
}
